package org.example.Controller;

import org.example.app.*;

import java.time.LocalDate;
import java.util.List;

public class AcademiaController {
    private final Academia academia;
    public AcademiaController(Academia academia) { this.academia = academia; }
    public List<Estudiante> estudiantes() { return academia.getEstudiantes(); }
    public List<Docente> docentes() { return academia.getDocentes(); }
    public List<Matricula> matriculas() { return academia.getTodasMatriculas(); }
    public List<ProgramaAcademico> programas() { return academia.getProgramas(); }
    public List<PeriodoAcademico> periodos() { return academia.getPeriodos(); }
    public void registrarEstudiante(String documento, String nombre, String telefono, String correo, int edad) {
        if (documento.isBlank() || nombre.isBlank() || telefono.isBlank() || correo.isBlank()) throw new IllegalArgumentException("Completa todos los campos obligatorios.");
        if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) throw new IllegalArgumentException("Escribe un correo electrónico válido.");
        academia.registrarEstudiante(new Estudiante(documento.trim(), nombre.trim(), telefono.trim(), correo.trim(), edad, LocalDate.now()));
    }
    public void registrarDocente(String documento, String nombre, String telefono, IdiomaEspecialidad especialidad, double tarifaSesion) {
        if (documento == null || documento.isBlank() || nombre == null || nombre.isBlank() || telefono == null || telefono.isBlank()) throw new IllegalArgumentException("Completa documento, nombre y teléfono.");
        if (especialidad == null) throw new IllegalArgumentException("Selecciona el idioma de especialidad.");
        if (!Double.isFinite(tarifaSesion) || tarifaSesion <= 0) throw new IllegalArgumentException("La tarifa por sesión debe ser mayor que cero.");
        academia.registrarDocente(new Docente(documento.trim(), nombre.trim(), telefono.trim(), especialidad, tarifaSesion));
    }
    public String consultarTelefono(String telefono) {
        return academia.buscarEstudiantePorTelefono(telefono.trim()).map(e -> e.getNombre() + " · " + e.getNumeroId())
                .orElse("No se encontró un estudiante con ese teléfono.");
    }
    public boolean telefonoEsPerfecto(String telefono) { return ConsultaAcademia.esNumeroPerfecto(telefono.trim()); }
    public double ingresos(LocalDate desde, LocalDate hasta) { return academia.calcularIngresos(desde, hasta); }

    public ProgramaAcademico registrarPrograma(String codigo, String nombre, String idioma, String descripcion,
                                               int duracionMeses, double valorMensual, String tipo, String beneficio) {
        if (codigo.isBlank() || nombre.isBlank() || idioma.isBlank()) throw new IllegalArgumentException("Código, nombre e idioma son obligatorios.");
        if (duracionMeses < 1 || valorMensual <= 0) throw new IllegalArgumentException("La duración y el valor deben ser mayores que cero.");
        ProgramaAcademico programa = new ProgramaAcademico(codigo.trim(), nombre.trim(), idioma.trim(), descripcion.trim(),
                duracionMeses, valorMensual, tipo, beneficio.trim(), EstadoProgra.ACTIVO, null);
        if (!academia.getPeriodos().isEmpty()) programa.setPeriodoAcademico(academia.getPeriodos().get(0));
        academia.registrarPrograma(programa);
        return programa;
    }

    public Matricula crearMatricula(String documentoEstudiante, String codigoPrograma, String codigoPeriodo,
                                    LocalDate fechaInicio, double descuentoPorcentaje, String observacion, ModalidadPrograma modalidad) {
        return crearMatricula(documentoEstudiante, codigoPrograma, codigoPeriodo, null, fechaInicio, descuentoPorcentaje, observacion, modalidad);
    }

    public Matricula crearMatricula(String documentoEstudiante, String codigoPrograma, String codigoPeriodo, String documentoDocente,
                                    LocalDate fechaInicio, double descuentoPorcentaje, String observacion, ModalidadPrograma modalidad) {
        Estudiante estudiante = academia.getEstudiantes().stream().filter(e -> e.getNumeroId().equals(documentoEstudiante)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Selecciona un estudiante registrado."));
        ProgramaAcademico programa = academia.getProgramas().stream().filter(p -> p.getCodigo().equals(codigoPrograma)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Selecciona un programa registrado."));
        PeriodoAcademico periodo = academia.getPeriodos().stream().filter(p -> p.getCodigo().equals(codigoPeriodo)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No hay un periodo académico disponible."));
        if (!periodo.getProgramasOfertados().contains(programa)) throw new IllegalArgumentException("El programa no está ofertado en este periodo.");
        if (fechaInicio == null) throw new IllegalArgumentException("Indica la fecha de inicio.");
        if (fechaInicio.isBefore(periodo.getFechaInicio()) || fechaInicio.isAfter(periodo.getFechaFin())) throw new IllegalArgumentException("La fecha de inicio debe estar dentro del periodo académico.");
        double descuento = descuentoPorcentaje / 100.0;
        if (modalidad == null) throw new IllegalArgumentException("Selecciona una modalidad.");
        Docente docente = documentoDocente == null || documentoDocente.isBlank() ? null : academia.getDocentes().stream()
                .filter(d -> d.getNumeroId().equals(documentoDocente)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Selecciona un docente registrado."));
        ServicioEntrega entrega = new ServicioEntrega();
        entrega.prepararEntregables(modalidad == ModalidadPrograma.PRESENCIAL ? new FabricaPresencial() : new FabricaVirtual());
        Matricula matricula = new Matricula.MatriculaBuilder().setEstudiante(estudiante)
                .agregarProgramaAcademico(programa).setPeriodoAcademico(periodo).setFechaInicio(fechaInicio)
                .setDocenteTutor(docente)
                .setDescuento(descuento).setObservacion(observacion == null ? "" : observacion.trim())
                .setServicioEntrega(entrega).setComprobantePago(new ComprobantePDF()).build();
        academia.realizarMatricula(matricula);
        programa.agregarMatricula(matricula);
        return matricula;
    }
    public Academia getAcademia() { return academia; }
}

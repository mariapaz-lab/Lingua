

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

public class Academia {
    private String nombre;
    private List<Estudiante> estudiantes;
    private List<Docente> docentes;
    private List<ProgramaAcademico> programas;
    private List<PeriodoAcademico> periodos;
    private List<Matricula> matriculas;

    public Academia(String nombre) {
        this.nombre = nombre;
        this.estudiantes = new ArrayList<>();
        this.docentes = new ArrayList<>();
        this.programas = new ArrayList<>();
        this.periodos = new ArrayList<>();
        this.matriculas = new ArrayList<>();
    }

    public void registrarEstudiante(Estudiante estudiante) {
        if (estudiante == null) throw new IllegalArgumentException("El estudiante es obligatorio.");
        if (estudiantes.stream().anyMatch(e -> e.getNumeroId().equals(estudiante.getNumeroId()))) throw new IllegalArgumentException("Ya existe un estudiante con ese documento.");
        this.estudiantes.add(estudiante);
    }

    public void registrarDocente(Docente docente) {
        if (docente == null) throw new IllegalArgumentException("El docente es obligatorio.");
        if (docentes.stream().anyMatch(d -> d.getNumeroId().equalsIgnoreCase(docente.getNumeroId()))) throw new IllegalArgumentException("Ya existe un docente con ese documento.");
        docentes.add(docente);
    }

    public void registrarPrograma(ProgramaAcademico programa) {
        if (programa == null) throw new IllegalArgumentException("El programa es obligatorio.");
        if (programas.stream().anyMatch(p -> p.getCodigo().equalsIgnoreCase(programa.getCodigo()))) throw new IllegalArgumentException("Ya existe un programa con ese código.");
        this.programas.add(programa);
        for (PeriodoAcademico periodo : periodos) periodo.agregarProgramaOfertado(programa);
    }

    public void registrarPeriodo(PeriodoAcademico periodo) {
        if (periodo == null) throw new IllegalArgumentException("El periodo es obligatorio.");
        for (ProgramaAcademico programa : programas) periodo.agregarProgramaOfertado(programa);
        this.periodos.add(periodo);
    }

    public Matricula realizarMatricula(Matricula matricula) {
        if (matricula == null) throw new IllegalArgumentException("La matrícula es obligatoria.");
        if (matriculas.stream().anyMatch(m -> m.getNumeroMatricula() == matricula.getNumeroMatricula())) throw new IllegalArgumentException("El número de matrícula ya existe.");
        this.matriculas.add(matricula);
        return matricula;
    }

    public Optional<Estudiante> buscarEstudiantePorTelefono(String telefono) {
        return estudiantes.stream().filter(e -> e.getTelefono().equals(telefono)).findFirst();
    }

    public double calcularIngresos(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null || hasta.isBefore(desde)) throw new IllegalArgumentException("El rango de fechas no es válido.");
        return matriculas.stream().filter(m -> !m.getFechaInicio().isBefore(desde) && !m.getFechaInicio().isAfter(hasta)).mapToDouble(Matricula::getValorTotal).sum();
    }

    public List<Docente> getDocentes() { return List.copyOf(docentes); }
    public List<ProgramaAcademico> getProgramas() { return List.copyOf(programas); }
    public List<PeriodoAcademico> getPeriodos() { return List.copyOf(periodos); }
    public List<Matricula> getTodasMatriculas() { return List.copyOf(matriculas); }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Estudiante> getEstudiantes() {
        return List.copyOf(estudiantes);
    }

    public List<Matricula> getMatriculas() {
        return List.copyOf(matriculas);
    }
}


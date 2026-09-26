package org.example.app;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Matricula {
    private int numeroMatricula;
    private Estudiante estudiante;
    private List<ProgramaAcademico> programasAcademicos;
    private PeriodoAcademico periodoAcademico;
    private Docente docenteTutor;
    private List<ServicioAdicional> serviciosAdicionales;
    private ServicioEntrega servicioEntrega;
    private ComprobantePago comprobantePago;
    private double descuento;
    private double valorTotal;
    private String observacion;
    private LocalDate fechaInicio;

    private Matricula(MatriculaBuilder builder) {
        // Se utiliza el Singleton ConsecutivoMatricula para asegurar un número único y consecutivo
        this.numeroMatricula = ConsecutivoMatricula.getInstancia().siguiente();
        this.estudiante = builder.estudiante;
        this.programasAcademicos = builder.programasAcademicos;
        this.periodoAcademico = builder.periodoAcademico;
        this.docenteTutor = builder.docenteTutor;
        this.serviciosAdicionales = builder.serviciosAdicionales;
        this.servicioEntrega = builder.servicioEntrega;
        this.comprobantePago = builder.comprobantePago;
        this.descuento = builder.descuento;
        this.observacion = builder.observacion;
        this.fechaInicio = builder.fechaInicio;
        this.valorTotal = calcularValorTotal();
        this.estudiante.agregarMatricula(this);
        this.periodoAcademico.agregarMatricula(this);
        if (this.docenteTutor != null) this.docenteTutor.agregarMatricula(this);
    }

    public double calcularValorTotal() {
        return getValorInicial() - getValorDescontado() + getValorServicios();
    }

    /** Valor de los programas antes de aplicar el descuento. */
    public double getValorInicial() { return programasAcademicos.stream().mapToDouble(p -> p.getValorMensual() * p.getDuracionMeses()).sum(); }
    /** Valor descontado, calculado sobre el valor inicial de los programas. */
    public double getValorDescontado() { return getValorInicial() * descuento; }
    public double getValorServicios() { return serviciosAdicionales.stream().mapToDouble(ServicioAdicional::getPrecio).sum(); }

    public String obtenerDetallesMatricula() {
        return "Matrícula N°: " + numeroMatricula +
                " | Estudiante: " + (estudiante != null ? estudiante.getNombre() : "N/A") +
                " | Programas inscritos: " + programasAcademicos.size() +
                " | Valor Total: " + valorTotal;
    }

    public int getNumeroMatricula() { return numeroMatricula; }
    public Estudiante getEstudiante() { return estudiante; }
    public List<ProgramaAcademico> getProgramasAcademicos() { return programasAcademicos; }
    public PeriodoAcademico getPeriodoAcademico() { return periodoAcademico; }
    public Docente getDocenteTutor() { return docenteTutor; }
    public List<ServicioAdicional> getServiciosAdicionales() { return serviciosAdicionales; }
    public double getDescuento() { return descuento; }
    public double getValorTotal() { return valorTotal; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public String getObservacion() { return observacion; }
    public ServicioEntrega getServicioEntrega() { return servicioEntrega; }

    public static class MatriculaBuilder {
        private Estudiante estudiante;
        private List<ProgramaAcademico> programasAcademicos = new ArrayList<>();
        private PeriodoAcademico periodoAcademico;
        private Docente docenteTutor; // Opcional
        private List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();
        private ServicioEntrega servicioEntrega;
        private ComprobantePago comprobantePago;
        private double descuento = 0.0;
        private String observacion = "";
        private LocalDate fechaInicio;

        public MatriculaBuilder setEstudiante(Estudiante estudiante) {
            this.estudiante = estudiante;
            return this;
        }

        public MatriculaBuilder agregarProgramaAcademico(ProgramaAcademico programa) {
            this.programasAcademicos.add(programa);
            return this;
        }

        public MatriculaBuilder setPeriodoAcademico(PeriodoAcademico periodoAcademico) {
            this.periodoAcademico = periodoAcademico;
            return this;
        }

        public MatriculaBuilder setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; return this; }

        public MatriculaBuilder setDocenteTutor(Docente docenteTutor) {
            this.docenteTutor = docenteTutor;
            return this;
        }

        public MatriculaBuilder agregarServicioAdicional(ServicioAdicional servicio) {
            this.serviciosAdicionales.add(servicio);
            return this;
        }

        public MatriculaBuilder setServicioEntrega(ServicioEntrega servicioEntrega) {
            this.servicioEntrega = servicioEntrega;
            return this;
        }

        public MatriculaBuilder setComprobantePago(ComprobantePago comprobantePago) {
            this.comprobantePago = comprobantePago;
            return this;
        }

        public MatriculaBuilder setDescuento(double descuento) {
            if (descuento < 0 || descuento > 0.30) throw new IllegalArgumentException("El descuento debe estar entre 0 % y 30 %.");
            this.descuento = descuento;
            return this;
        }

        public MatriculaBuilder setObservacion(String observacion) {
            this.observacion = observacion;
            return this;
        }

        public Matricula build() {
            if (estudiante == null || programasAcademicos.isEmpty() || periodoAcademico == null || servicioEntrega == null || comprobantePago == null || fechaInicio == null) {
                throw new IllegalStateException("Faltan datos obligatorios para consolidar la matrícula.");
            }
            if (!periodoAcademico.validarCupos()) throw new IllegalStateException("El periodo no tiene cupos disponibles.");
            for (ProgramaAcademico programa : programasAcademicos) {
                if (programa == null || programa.getEstado() != EstadoProgra.ACTIVO) throw new IllegalStateException("Solo se pueden matricular programas activos.");
            }
            return new Matricula(this);
        }
    }

}

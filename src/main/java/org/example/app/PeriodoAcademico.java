package org.example.app;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;

public class PeriodoAcademico {
    private String codigo; // Identificador del periodo
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int cuposDisponibles; // Atributo clave para la validación y descuento de cupos
    private List<ProgramaAcademico> programasOfertados;
    private List<Matricula> matriculas; // Relación con las matrículas realizadas en este periodo

    public PeriodoAcademico(String codigo, LocalDate fechaInicio, LocalDate fechaFin, int cuposDisponibles) {
        this.codigo = codigo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cuposDisponibles = cuposDisponibles;
        this.programasOfertados = new ArrayList<>();
        this.matriculas = new ArrayList<>();
    }

    public void agregarProgramaOfertado(ProgramaAcademico programa) {
        this.programasOfertados.add(programa);
    }

    public void agregarMatricula(Matricula matricula) {
        if (!validarCupos()) throw new IllegalStateException("No hay cupos disponibles.");
        if (!this.matriculas.contains(matricula)) { this.matriculas.add(matricula); descontarCupo(); }
    }

    public PeriodoAcademico clone() {
        PeriodoAcademico clon = new PeriodoAcademico(
                this.codigo + "-CLONE",
                this.fechaInicio,
                this.fechaFin,
                this.cuposDisponibles
        );
        clon.programasOfertados = new ArrayList<>(this.programasOfertados);
        return clon;
    }

    public boolean validarCupos() {
        return this.cuposDisponibles > 0;
    }

    public void descontarCupo() {
        if (validarCupos()) {
            this.cuposDisponibles--;
        }
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public List<ProgramaAcademico> getProgramasOfertados() {
        return programasOfertados;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }
}

package org.example.app;

import java.util.ArrayList;
import java.util.List;

public class Docente extends Usuario {
    private IdiomaEspecialidad idiomaEspecialidad;
    private double tarifaSesion;
    private List<Matricula> matriculas; // Relación 0..* con Matrícula

    public Docente(String numeroId, String nombre, String telefono, IdiomaEspecialidad idiomaEspecialidad, double tarifaSesion) {
        super(numeroId, nombre, telefono);
        this.idiomaEspecialidad = idiomaEspecialidad;
        this.tarifaSesion = tarifaSesion;
        this.matriculas = new ArrayList<>();
    }

    public void agregarMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
    }

    public double calcularTarifaSesion() {
        return this.tarifaSesion;
    }


    public IdiomaEspecialidad getIdiomaEspecialidad() {
        return idiomaEspecialidad;
    }

    public void setIdiomaEspecialidad(IdiomaEspecialidad idiomaEspecialidad) {
        this.idiomaEspecialidad = idiomaEspecialidad;
    }

    public double getTarifaSesion() {
        return tarifaSesion;
    }

    public void setTarifaSesion(double tarifaSesion) {
        this.tarifaSesion = tarifaSesion;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    @Override
    public String getDetallesUsuario() {
        return "Docente: " + nombre + " " +
                "Idioma: " + idiomaEspecialidad;
    }
}



package org.example.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Usuario{
    private String correoElectronico;
    private int edad;
    private LocalDate fechaRegistro;
    private List<Matricula> matriculas;

    public Estudiante(String numeroId, String nombre, String telefono, String correoElectronico, LocalDate fechaRegistro) {
        this(numeroId, nombre, telefono, correoElectronico, 0, fechaRegistro);
    }

    public Estudiante(String numeroId, String nombre, String telefono, String correoElectronico, int edad, LocalDate fechaRegistro) {
        super(numeroId, nombre, telefono);
        if (edad < 0 || edad > 120) throw new IllegalArgumentException("La edad debe estar entre 0 y 120 años.");
        this.correoElectronico = correoElectronico;
        this.edad = edad;
        this.fechaRegistro = fechaRegistro;
        this.matriculas = new ArrayList<>();
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { if (edad < 0 || edad > 120) throw new IllegalArgumentException("Edad no válida."); this.edad = edad; }

    public List<Matricula> getMatriculas() { return List.copyOf(matriculas); }

    public void agregarMatricula(Matricula matricula) {
        if (matricula != null && !matriculas.contains(matricula)) matriculas.add(matricula);
    }

    @Override
    public String getDetallesUsuario() {
        return "Estudiante: " + nombre + " " +
                "Correo: " + correoElectronico + "  " +
                "Teléfono: " + telefono;
    }
}

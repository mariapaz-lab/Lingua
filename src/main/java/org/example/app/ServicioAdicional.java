

import java.util.ArrayList;
import java.util.List;

public class ServicioAdicional {
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponibilidad;

    // Relación bidireccional: un servicio adicional puede estar asociado a cero o muchas matrículas (0..*)
    private List<Matricula> listaMatriculas;

    public ServicioAdicional(String codigo, String nombre, String descripcion, double precio, boolean disponibilidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponibilidad = disponibilidad;
        this.listaMatriculas = new ArrayList<>();
    }

    public void agregarMatricula(Matricula matricula) {
        this.listaMatriculas.add(matricula);
    }

    // Métodos especificados en el diagrama
    public double getPrecio() {
        return this.precio;
    }

    public boolean estadoDisponibilidad() {
        return this.disponibilidad;
    }

    // Getters y Setters adicionales
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public List<Matricula> getListaMatriculas() {
        return listaMatriculas;
    }
}

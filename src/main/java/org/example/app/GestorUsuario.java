package org.example.app;

import java.util.ArrayList;
import java.util.List;

public class GestorUsuario {
    private String tipo;
    private String registrarUsuarioDocumento;
    private String registrarUsuarioTipo;
    private String registrarUsuarioNombre;
    private String registrarUsuarioCorreo;
    private String registrarUsuarioContrasena;
    private String registrarUsuarioTelefono;
    private String actualizarUsuarioDocumento;
    private String actualizarUsuarioTipo;
    private String actualizarUsuarioNombre;
    private String actualizarUsuarioCorreo;
    private String actualizarUsuarioContrasena;
    private String actualizarUsuarioTelefono;

    private List<Usuario> listaUsuarios;

    public GestorUsuario() {
        this.listaUsuarios = new ArrayList<>();
    }

    public void registrarUsuario(String documento, String tipo, String nombre, String correo, String contrasena, String telefono) {
        System.out.println("Registrando usuario: " + nombre + " (" + tipo + ")");
    }

    public void actualizarUsuario(String documento, String tipo, String nombre, String correo, String contrasena, String telefono) {
        System.out.println("Actualizando usuario con documento: " + documento);
    }

    public void eliminarUsuario(String documento) {
        System.out.println("Eliminando usuario con documento: " + documento);
    }

    public List<Usuario> consultarUsuarios() {
        return this.listaUsuarios;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

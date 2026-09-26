package org.example.app;

import java.util.ArrayList;

import java.util.List;

public class GestorPrograma {
    private List<ProgramaAcademico> listaProgramas;

    public GestorPrograma() {
        this.listaProgramas = new ArrayList<>();
    }

    public ProgramaAcademico crearPrograma(String codigo, String nombre, String idioma,
                                           String descripcion, int duracionMeses,
                                           double valorMensual, String tipo, String beneficio) {
        ProgramaAcademico nuevoPrograma = new ProgramaAcademico(codigo, nombre, idioma, descripcion,
                duracionMeses, valorMensual, tipo, beneficio, EstadoProgra.ACTIVO, null);
        this.listaProgramas.add(nuevoPrograma);
        System.out.println("Programa académico creado: " + nombre);
        return nuevoPrograma;
    }

    public ProgramaAcademico clonarPrograma(ProgramaAcademico programaBase) {
        // Lógica de clonación (Patrón Prototype para duplicar programas existentes)
        if (programaBase == null) {
            return null;
        }
        ProgramaAcademico programaClonado = programaBase.clone();
        this.listaProgramas.add(programaClonado);
        System.out.println("Programa clonado con éxito.");
        return programaClonado;
    }

    public void actualizarEstadoPrograma(String codigo, EstadoProgra nuevoEstado) {
        ProgramaAcademico prog = buscarPrograma(codigo);
        if (prog != null) {
            prog.setEstado(nuevoEstado);
            System.out.println("Estado del programa " + codigo + " actualizado.");
        }
    }

    public ProgramaAcademico buscarPrograma(String codigo) {
        for (ProgramaAcademico p : listaProgramas) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public List<ProgramaAcademico> listarProgramas() {
        return List.copyOf(this.listaProgramas);
    }
}

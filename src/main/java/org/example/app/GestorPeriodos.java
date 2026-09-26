package org.example.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorPeriodos {
    private List<PeriodoAcademico> listaPeriodos;

    public GestorPeriodos() {
        this.listaPeriodos = new ArrayList<>();
    }

    public PeriodoAcademico crearPeriodo(LocalDate fechaInicio, LocalDate fechaFin, int cuposDisponibles) {
        String codigo = "PER-" + (listaPeriodos.size() + 1);
        PeriodoAcademico nuevoPeriodo = new PeriodoAcademico(codigo, fechaInicio, fechaFin, cuposDisponibles);
        this.listaPeriodos.add(nuevoPeriodo);
        System.out.println("Periodo creado exitosamente: " + codigo);
        return nuevoPeriodo;
    }

    public PeriodoAcademico clonarPeriodoAcademico(PeriodoAcademico periodoExistente) {
        if (periodoExistente == null) {
            return null;
        }
        PeriodoAcademico periodoClonado = new PeriodoAcademico(
                periodoExistente.getCodigo() + "-CLONE",
                periodoExistente.getFechaInicio(),
                periodoExistente.getFechaFin(),
                periodoExistente.getCuposDisponibles()
        );
        this.listaPeriodos.add(periodoClonado);
        System.out.println("Periodo clonado con éxito a partir de: " + periodoExistente.getCodigo());
        return periodoClonado;
    }

    public PeriodoAcademico buscarPeriodo(LocalDate fechaInicio) {
        for (PeriodoAcademico p : listaPeriodos) {
            if (p.getFechaInicio().equals(fechaInicio)) {
                return p;
            }
        }
        return null;
    }

    public List<PeriodoAcademico> listarPeriodosActivos() {
        List<PeriodoAcademico> activos = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        for (PeriodoAcademico p : listaPeriodos) {
            if (p.getFechaInicio().isBefore(fechaActual) && p.getFechaFin().isAfter(fechaActual)) {
                activos.add(p);
            }
        }
        return activos;
    }
}

package org.example.View;

import org.example.Controller.AcademiaController;
import org.example.app.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.time.LocalDate;

public class LinguaPlusApplication extends Application {
    @Override public void start(Stage stage) {
        Academia academia = crearDemo();
        AcademiaView view = new AcademiaView(new AcademiaController(academia));
        Scene scene = new Scene(view.getRoot(), 1180, 760);
        var css = getClass().getResource("/styles.css");
        if (css != null) scene.getStylesheets().add(css.toExternalForm());
        stage.setTitle("LinguaPlus · Gestión académica"); stage.setMinWidth(900); stage.setMinHeight(650); stage.setScene(scene); stage.show();
    }

    private Academia crearDemo() {
        Academia academia = new Academia("LinguaPlus");
        PeriodoAcademico periodo = new PeriodoAcademico("2026-2", LocalDate.now(), LocalDate.now().plusMonths(4), 30);
        academia.registrarPeriodo(periodo);
        academia.registrarEstudiante(new Estudiante("1094901234", "Valentina Gómez", "3001234567", "valentina@email.com", 20, LocalDate.now().minusDays(12)));
        academia.registrarEstudiante(new Estudiante("1094905678", "Santiago Ramírez", "3015550198", "santiago@email.com", 22, LocalDate.now().minusDays(5)));
        academia.registrarPrograma(new ProgramaAcademico("ING-BAS", "Inglés básico", "Inglés", "Nivel inicial", 6, 180000, "Básico", "Club de conversación", EstadoProgra.ACTIVO, periodo));
        return academia;
    }

}

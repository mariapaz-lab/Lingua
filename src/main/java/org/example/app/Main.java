package org.example.app;

import org.example.View.LinguaPlusApplication;
import javafx.application.Application;

import java.nio.file.Path;

public class Main {
    private Main() { }
    public static void main(String[] args) {
        String temporaryDirectory = System.getProperty("java.io.tmpdir");
        if (temporaryDirectory != null) {
            System.setProperty("javafx.cachedir", Path.of(temporaryDirectory, "LinguaPlus", "javafx-cache").toString());
        }
        Application.launch(LinguaPlusApplication.class, args);
    }
}

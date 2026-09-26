package org.example.app;

public class FabricaVirtual implements FabricaEntregable {
    @Override public MaterialEstudio crearMaterial() { return new LicenciaPlataforma(); }
    @Override public Carne crearCarnet() { return new CarnetDigital(); }
}

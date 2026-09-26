package org.example.app;

public class FabricaPresencial implements FabricaEntregable{
    @Override
    public MaterialEstudio crearMaterial() {
        return new MaterialImpreso();
    }

    @Override
    public Carne crearCarnet() {
        return new CarnetFisico();
    }
}

package org.example.app;

public class ServicioEntrega {
    private MaterialEstudio materialEstudio;
    private Carne carnet;

    public void prepararEntregables(FabricaEntregable fabrica) {
        if (fabrica == null) {
            throw new IllegalArgumentException("La fábrica de entregables no puede ser nula.");
        }

        this.materialEstudio = fabrica.crearMaterial();
        this.carnet = fabrica.crearCarnet();
    }

    public MaterialEstudio getMaterialEstudio() { return materialEstudio; }
    public Carne getCarnet() { return carnet; }
}

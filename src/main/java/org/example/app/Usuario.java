

public abstract class Usuario {
    protected String numeroId;
    protected String nombre;
    protected String telefono;

    public Usuario(String numeroId, String nombre, String telefono) {
        this.numeroId = numeroId;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNumeroId() {
        return numeroId;
    }

    public void setNumeroId(String numeroId) {
        this.numeroId = numeroId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public abstract String getDetallesUsuario();
}

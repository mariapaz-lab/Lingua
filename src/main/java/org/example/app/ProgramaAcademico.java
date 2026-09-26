

import java.util.ArrayList;

import java.util.List;
public class ProgramaAcademico {
    private String codigo;
    private String nombre;
    private String idioma;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private String tipo;
    private String beneficio;
    private EstadoProgra estado; // Atributo integrado del enum EstadoProgra

    // Relaciones basadas en el diagrama
    private PeriodoAcademico periodoAcademico;
    private List<Matricula> matriculas;

    public ProgramaAcademico(String codigo, String nombre, String idioma, String descripcion,
                             int duracionMeses, double valorMensual, String tipo, String beneficio,
                             EstadoProgra estado, PeriodoAcademico periodoAcademico) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idioma = idioma;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
        this.tipo = tipo;
        this.beneficio = beneficio;
        this.estado = estado;
        this.periodoAcademico = periodoAcademico;
        this.matriculas = new ArrayList<>();
    }

    // Método de clonación especificado en el diagrama (Patrón Prototype)
    public ProgramaAcademico clone() {
        ProgramaAcademico clon = new ProgramaAcademico(
                this.codigo + "-CLONE",
                this.nombre,
                this.idioma,
                this.descripcion,
                this.duracionMeses,
                this.valorMensual,
                this.tipo,
                this.beneficio,
                this.estado,
                this.periodoAcademico
        );
        return clon;
    }

    public void agregarMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
    }

    // Getters y Setters requeridos por el diagrama
    public String getCodigo() {
        return codigo;
    }

    public double getValorMensual() {
        return valorMensual;
    }
    public int getDuracionMeses() { return duracionMeses; }
    public String getIdioma() { return idioma; }

    public String getNombre() {
        return nombre;
    }

    public EstadoProgra getEstado() {
        return estado;
    }

    public void setEstado(EstadoProgra estado) {
        this.estado = estado;
    }

    public PeriodoAcademico getPeriodoAcademico() {
        return periodoAcademico;
    }

    public void setPeriodoAcademico(PeriodoAcademico periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }

    public List<Matricula> getMatriculas() {return matriculas;
    }
}

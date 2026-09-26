

public class ComprobantePDF implements ComprobantePago{
    @Override
    public void generarComprobante(Matricula matricula) {
        System.out.println("Generando comprobante de pago en formato " +
                "PDF para la matrícula N°: " + matricula.getNumeroMatricula());
    }
}

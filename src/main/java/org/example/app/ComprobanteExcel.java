

public class ComprobanteExcel implements ComprobantePago{
    @Override
    public void generarComprobante(Matricula matricula) {
        System.out.println("Generando comprobante de pago en formato Excel (.xlsx) " +
                "para la matrícula N°: " + matricula.getNumeroMatricula());
    }
}

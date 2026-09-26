

public final class ConsultaAcademia {
    private ConsultaAcademia() { }
    public static boolean esNumeroPerfecto(String valor) {
        if (valor == null || !valor.matches("\\d+")) return false;
        try {
            long numero = Long.parseLong(valor);
            if (numero < 2) return false;
            long suma = 1;
            for (long divisor = 2; divisor <= numero / divisor; divisor++) {
                if (numero % divisor == 0) {
                    suma += divisor;
                    long pareja = numero / divisor;
                    if (pareja != divisor) suma += pareja;
                    if (suma > numero) return false;
                }
            }
            return suma == numero;
        } catch (NumberFormatException ex) { return false; }
    }
}

package org.example.app;

public class ConsecutivoMatricula {
        private static ConsecutivoMatricula instancia;
        private int ultimoNumero;
        private ConsecutivoMatricula() {
            this.ultimoNumero = 0;
        }

        public static synchronized ConsecutivoMatricula getInstancia() {
            if (instancia == null) {
                instancia = new ConsecutivoMatricula();
            }
            return instancia;
        }

        public synchronized int siguiente() {
            return ++this.ultimoNumero;
        }

        public int getUltimoNumero() {
            return ultimoNumero;
        }
}

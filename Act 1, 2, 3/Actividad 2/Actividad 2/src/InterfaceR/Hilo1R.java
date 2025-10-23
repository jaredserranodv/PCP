package InterfaceR;


public class Hilo1R implements Runnable {
    private RecursoCompartidoR numero;
    private String nombreh;


    public Hilo1R(RecursoCompartidoR numero1, String nombre1) {
        numero = numero1;
        nombreh = nombre1;
    }

    public void run() {
        for (int i = 1; i <= 10; i++) {
            numero.incrementar(nombreh);
        }

        //decrementar
        for (int i = 1; i <= 10; i++) {
            numero.decrementar(nombreh);
        }

    }
}

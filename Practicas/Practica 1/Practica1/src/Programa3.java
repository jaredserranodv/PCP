// Clase compartida que controla la impresión alternada
class NumeroCompartido {
    private int numero = 1;
    private final int limite = 20;
    private boolean turnoImpar = true;   // true = turno de impares, false = turno de pares

    public synchronized void imprimirImpar() {
        while (numero <= limite) {
            // Si no es turno de impares, el hilo espera
            while (!turnoImpar) {
                try {
                    wait(); // El hilo se bloquea esperando su turno
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (numero % 2 != 0) {
                System.out.println("Hilo A (impar): " + numero + " | Estado: " + Thread.currentThread().getState());
                numero++;
                turnoImpar = false; // Ahora le toca al hilo par
                notify(); // Despierta al hilo par
            }
        }
    }

    public synchronized void imprimirPar() {
        while (numero <= limite) {
            while (turnoImpar) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (numero % 2 == 0) {
                System.out.println("Hilo B (par): " + numero + " | Estado: " + Thread.currentThread().getState());
                numero++;
                turnoImpar = true; // Ahora le toca al hilo impar
                notify(); // Despierta al hilo impar
            }
        }
    }
}


class HiloImpar extends Thread {
    private final NumeroCompartido compartido;

    //Constructor que recibe el objeto compartido
    public HiloImpar(NumeroCompartido compartido) {
        this.compartido = compartido;
    }

    @Override
    public void run() {
        System.out.println(">>> Hilo A (impar) iniciado");
        compartido.imprimirImpar();
        System.out.println(">>> Hilo A (impar) finalizado");
    }
}

class HiloPar extends Thread {
    private final NumeroCompartido compartido;

    public HiloPar(NumeroCompartido compartido) {
        this.compartido = compartido;
    }

    @Override
    public void run() {
        System.out.println(">>> Hilo B (par) iniciado");
        compartido.imprimirPar();
        System.out.println(">>> Hilo B (par) finalizado");
    }
}

// Clase principal
public class Programa3 {
    public static void main(String[] args) throws InterruptedException {
        NumeroCompartido compartido = new NumeroCompartido();

        HiloImpar hiloA = new HiloImpar(compartido);
        HiloPar hiloB = new HiloPar(compartido);

        System.out.println("=== Inicio de la alternancia de hilos ===\n");

        // Inicia los hilos
        hiloA.start();
        hiloB.start();

        // Espera que terminen ambos hilos
        hiloA.join();
        hiloB.join();

        System.out.println("\n=== Fin de la alternancia de hilos ===");
    }
}

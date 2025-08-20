// Clase que hereda de Thread para crear un hilo propio
class HiloHerencia extends Thread {
    private final String nombre;

    // Constructor que recibe el nombre del hilo
    public HiloHerencia(String nombre) {
        this.nombre = nombre;
    }

    // Método que se ejecuta cuando se inicia el hilo
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Este es el hilo " + nombre + " con el número " + i);
            try {
                // Pausa para que se note la ejecución concurrente
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("El hilo " + nombre + " fue interrumpido.");
            }
        }
    }
}

// Clase principal
public class Programa1 {
    public static void main(String[] args) throws InterruptedException {
        // Se crean tres hilos (a, b, c)
        HiloHerencia a = new HiloHerencia("Uno");
        HiloHerencia b = new HiloHerencia("Dos");
        HiloHerencia c = new HiloHerencia("Tres");

        // Estado inicial de los hilos -> NEW
        System.out.println("Estado inicial:");
        System.out.println("Hilo A: " + a.getState());
        System.out.println("Hilo B: " + b.getState());
        System.out.println("Hilo C: " + c.getState());

        // Se inician los hilos con el start (ahora pasan a RUNNABLE)
        a.start();
        b.start();
        c.start();

        // Estado después de iniciar los hilos
        System.out.println("\nEstado después de start():");
        System.out.println("Hilo A: " + a.getState());
        System.out.println("Hilo B: " + b.getState());
        System.out.println("Hilo C: " + c.getState());

        // join() -> Espera a que los hilos terminen antes de continuar
        a.join();
        b.join();
        c.join();

        // Estado final
        System.out.println("\nEstado final:");
        System.out.println("Hilo A: " + a.getState());
        System.out.println("Hilo B: " + b.getState());
        System.out.println("Hilo C: " + c.getState());
    }
}
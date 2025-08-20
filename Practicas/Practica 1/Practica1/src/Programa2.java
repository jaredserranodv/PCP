// Clase que implementa la interfaz Runnable
class HiloInterfaz implements Runnable {
    private String nombre;

    // Constructor que asigna el nombre al hilo
    public HiloInterfaz(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Este es el hilo " + nombre + " con el número " + i);
            try {
                Thread.sleep(200); // Pausa para ver concurrencia
            } catch (InterruptedException e) {
                System.out.println("El hilo " + nombre + " fue interrumpido.");
            }
        }
    }
}

// Clase principal
public class Programa2 {
    public static void main(String[] args) throws InterruptedException {
        // Se crean los objetos runnable (no son hilos aún)
        HiloInterfaz d = new HiloInterfaz("Uno");
        HiloInterfaz e = new HiloInterfaz("Dos");
        HiloInterfaz f = new HiloInterfaz("Tres");

        // Se convierten los objetos runnable en hilos Thread
        Thread hiloD = new Thread(d);
        Thread hiloE = new Thread(e);
        Thread hiloF = new Thread(f);

        // Estado inicial de los hilos -> NEW
        System.out.println("Estado inicial:");
        System.out.println("Hilo D: " + hiloD.getState());
        System.out.println("Hilo E: " + hiloE.getState());
        System.out.println("Hilo F: " + hiloF.getState());

        // Se inician los hilos con el start (ahora pasan a RUNNABLE)
        hiloD.start();
        hiloE.start();
        hiloF.start();

        // Estado después de iniciar los hilos
        System.out.println("\nEstado después de start():");
        System.out.println("Hilo D: " + hiloD.getState());
        System.out.println("Hilo E: " + hiloE.getState());
        System.out.println("Hilo F: " + hiloF.getState());

        // join() -> Espera a que los hilos terminen antes de continuar
        hiloD.join();
        hiloE.join();
        hiloF.join();

        // Estado final
        System.out.println("\nEstado final:");
        System.out.println("Hilo D: " + hiloD.getState());
        System.out.println("Hilo E: " + hiloE.getState());
        System.out.println("Hilo F: " + hiloF.getState());
    }
}

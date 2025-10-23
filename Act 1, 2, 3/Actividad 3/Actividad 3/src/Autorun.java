class AutoThread implements Runnable {
    private Thread mio;   // atributo para manejar el hilo
    private String nombre;

    public AutoThread() {
        // Se crea un hilo que ejecuta este mismo objeto
        mio = new Thread(this);
        nombre = "mio";
        mio.start();          // <<<--- AUTORUN: el hilo inicia automáticamente
    }

    // Constructor con parámetro (se le pasa un nombre)
    public AutoThread(String nom) {
        nombre = nom;
        mio = new Thread(this);
        mio.start();          // <<<--- AUTORUN: también arranca solo
    }

    public void run() {
        // Verifica que el hilo que está corriendo sea el mismo que creamos en el objeto
        if (mio == Thread.currentThread()) {
            for (int i = 0; i < 10; i++) {
                System.out.println("Dentro del Autorun() i=" + i + " " + mio.getName());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Ambos arrancan automáticamente por el "autorun" en el constructor
        AutoThread miThred = new AutoThread("Autorun");
        AutoThread miThred1 = new AutoThread();

        Thread.sleep(10);

        // Se crea un tercer hilo (Thread w) a partir del primer objeto (miThred)
        Thread w = new Thread(miThred);
        w.start();

        for (int i = 0; i < 10; i++) {
            System.out.println("Dentro del main " + w.getName() + " " + miThred.nombre);
            System.out.println("Dentro del main " + w.getName() + " " + miThred1.nombre);
        }
    }
}

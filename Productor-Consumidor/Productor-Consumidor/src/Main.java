import java.util.Random;

class Monitor_Almacen {
    private int valor;
    private boolean disponible = false;
    private final Random rand = new Random();

    public synchronized void almacenar(int num) throws InterruptedException {
        while (disponible) {
            wait();
        }
        valor = num;
        disponible = true;
        System.out.println("Productor produce: " + valor);
        notifyAll();
    }

    public synchronized int retirar() throws InterruptedException {
        while (!disponible) {
            wait();
        }
        disponible = false;
        System.out.println("                Consumidor consume: " + valor);
        notifyAll();
        return valor;
    }
}

class Productor implements Runnable {
    private final Monitor_Almacen monitor;
    private final Random rand = new Random();

    public Productor(Monitor_Almacen m) {
        this.monitor = m;
    }

    public void run() {
        try {
            while (true) {
                int num = rand.nextInt(10);
                monitor.almacenar(num);
                Thread.sleep(rand.nextInt(100));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumidor implements Runnable {
    private final Monitor_Almacen monitor;

    public Consumidor(Monitor_Almacen m) {
        this.monitor = m;
    }

    public void run() {
        try {
            while (true) {
                monitor.retirar();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class EjemploPC {
    public static void main(String[] args) {
        Monitor_Almacen almacen = new Monitor_Almacen();
        Thread hiloProductor = new Thread(new Productor(almacen));
        Thread hiloConsumidor = new Thread(new Consumidor(almacen));

        hiloProductor.start();
        hiloConsumidor.start();
    }
}
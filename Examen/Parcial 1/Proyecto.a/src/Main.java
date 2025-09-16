import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

// Clase buffer compartido entre productor y consumidor
class CalificacionBuffer {
    private final Queue<Float> buffer = new LinkedList<>();
    private boolean terminado = false;

    // Método para producir calificación
    public synchronized void producir(float cal) {
        buffer.add(cal);
        notifyAll(); // Despierta al consumidor
    }

    // Método para consumir calificación
    public synchronized Float consumir() throws InterruptedException {
        while (buffer.isEmpty() && !terminado) {
            wait(); // Espera si no hay datos
        }
        return buffer.poll();
    }

    // Avisar que el productor ya terminó
    public synchronized void setTerminado() {
        terminado = true;
        notifyAll();
    }

    public synchronized boolean isTerminado() {
        return terminado && buffer.isEmpty();
    }
}

// Productor: lee calificaciones del archivo
class Productor implements Runnable {
    private final CalificacionBuffer buffer;
    private final String archivoEntrada;

    public Productor(CalificacionBuffer buffer, String archivoEntrada) {
        this.buffer = buffer;
        this.archivoEntrada = archivoEntrada;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoEntrada))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                float cal = Float.parseFloat(linea.trim());
                buffer.producir(cal);
                System.out.println("Productor generó: " + cal);
                Thread.sleep(100); // Simula tiempo de producción
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            buffer.setTerminado();
        }
    }
}

// Consumidor: procesa y guarda aprobados
class Consumidor implements Runnable {
    private final CalificacionBuffer buffer;
    private final String archivoSalida;

    public Consumidor(CalificacionBuffer buffer, String archivoSalida) {
        this.buffer = buffer;
        this.archivoSalida = archivoSalida;
    }

    @Override
    public void run() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))) {
            while (!buffer.isTerminado()) {
                Float cal = buffer.consumir();
                if (cal != null && cal >= 6.0f) {
                    System.out.println("Consumidor aprobó: " + cal);
                    bw.write(cal + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Clase principal
class ProductorConsumidor {
    public static void main(String[] args) {
        CalificacionBuffer buffer = new CalificacionBuffer();

        String archivoEntrada = "src/calificaciones.txt";
        String archivoSalida = "src/aprobados.txt";       // se creará con las ≥6

        Thread productor = new Thread(new Productor(buffer, archivoEntrada));
        Thread consumidor = new Thread(new Consumidor(buffer, archivoSalida));

        productor.start();
        consumidor.start();
    }
}

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.nio.file.*;

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
                float cal = Float.parseFloat(linea.trim().replace(',', '.')); // por si usan coma
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

class ProductorConsumidor {
    public static void main(String[] args) throws Exception {
        CalificacionBuffer buffer = new CalificacionBuffer();

        Path in = Paths.get("C:\\Users\\leyix\\Documents\\PCP\\Examen\\Parcial 1\\data\\calificaciones.txt");
        Path out = Paths.get("C:\\Users\\leyix\\Documents\\PCP\\Examen\\Parcial 1\\data\\aprobados.txt");

        System.out.println("Working dir: " + System.getProperty("user.dir"));
        System.out.println("Leyendo de: " + in.toAbsolutePath());
        System.out.println("Escribiendo en: " + out.toAbsolutePath());

        // Asegura que la carpeta exista
        Files.createDirectories(in.getParent());

        // Si no existe el archivo de entrada, creamos uno con datos de ejemplo
        if (!Files.exists(in)) {
            Files.write(in, String.join(System.lineSeparator(),
                    "5.5", "6.0", "7.8", "4.0", "10").getBytes());
            System.out.println("Se creó archivo de ejemplo: " + in.toAbsolutePath());
        }

        Thread productor = new Thread(new Productor(buffer, in.toString()));
        Thread consumidor = new Thread(new Consumidor(buffer, out.toString()));

        productor.start();
        consumidor.start();

        // Esperar a que terminen
        productor.join();
        consumidor.join();

    }
}

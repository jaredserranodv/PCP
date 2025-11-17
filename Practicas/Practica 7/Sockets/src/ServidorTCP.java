import java.net.*;
import java.io.*;
import java.util.*;

public class ServidorTCP {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Servidor TCP iniciado en puerto 5000...");

            while (true) {
                Socket cliente = server.accept();
                System.out.println("Cliente TCP conectado: " + cliente.getInetAddress());
                new ManejadorCliente(cliente).start();
            }
        } catch (IOException ioe) {
            System.out.println("Error en servidor TCP: " + ioe);
        }
    }
}

class ManejadorCliente extends Thread {
    private Socket cliente;
    private DataInputStream entrada;
    private DataOutputStream salida;

    public ManejadorCliente(Socket socket) {
        this.cliente = socket;
    }

    public void run() {
        try {
            entrada = new DataInputStream(cliente.getInputStream());
            salida = new DataOutputStream(cliente.getOutputStream());

            System.out.println("Manejando cliente: " + cliente.getInetAddress());

            while (true) {
                String mensaje = entrada.readUTF();
                System.out.println("Mensaje recibido: " + mensaje);

                if (mensaje.equals("salir")) {
                    System.out.println("Cliente desconectado: " + cliente.getInetAddress());
                    break;
                } else if (mensaje.equals("hora")) {
                    Date ahora = new Date();
                    String horaServidor = ahora.toString();
                    salida.writeUTF(horaServidor);
                    salida.flush();
                    System.out.println("Hora enviada: " + horaServidor);
                }
            }

            cliente.close();
        } catch (IOException ioe) {
            System.out.println("Error con cliente: " + ioe);
        }
    }
}
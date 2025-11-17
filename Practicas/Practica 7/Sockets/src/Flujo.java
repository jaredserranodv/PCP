//Flujo.java
import java.net.*;
import java.io.*;
import java.util.*;

class Flujo extends Thread {
    Socket nsfd;
    DataInputStream FlujoLectura;
    DataOutputStream FlujoEscritura;

    public Flujo(Socket sfd) {
        nsfd = sfd;
        try {
            FlujoLectura = new DataInputStream(new BufferedInputStream(sfd.getInputStream()));
            FlujoEscritura = new DataOutputStream(new BufferedOutputStream(sfd.getOutputStream()));
        } catch(IOException ioe) {
            System.out.println("IOException(Flujo): "+ioe);
        }
    }

    public void run() {
        broadcast(nsfd.getInetAddress()+"> se ha conectado");
        Servidor.usuarios.add(this);

        while(true) {
            try {
                String linea = FlujoLectura.readUTF();
                if (linea != null && !linea.trim().equals("")) {
                    String mensaje = nsfd.getInetAddress() + "> " + linea;
                    broadcast(mensaje);
                }
            } catch(IOException ioe) {
                Servidor.usuarios.remove(this);
                broadcast(nsfd.getInetAddress()+"> se ha desconectado");
                break;
            }
        }
    }

    public void broadcast(String mensaje) {
        synchronized (Servidor.usuarios) {
            for (int i = 0; i < Servidor.usuarios.size(); i++) {
                Flujo f = (Flujo) Servidor.usuarios.get(i);
                try {
                    synchronized(f.FlujoEscritura) {
                        f.FlujoEscritura.writeUTF(mensaje);
                        f.FlujoEscritura.flush();
                    }
                } catch(IOException ioe) {
                    System.out.println("Error en broadcast: "+ioe);
                }
            }
        }
    }
}
//ClienteTCP.java
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;

class ClienteTCP extends Frame implements ActionListener {
    Panel panel;
    Socket conexion;
    TextField textent, textent2, textent3;
    Button actualizar, conectar, desconectar;
    DataOutputStream salida;
    DataInputStream entrada;
    long horaConexion;

    ClienteTCP(String nombre) {
        super(nombre);
        setSize(350, 250); // Aumenté el tamaño
        panel = new Panel();
        panel.setLayout(new GridLayout(7, 2));

        textent = new TextField(30);
        textent.setText("Pulsa el boton \"Conectar\" para conectarte");
        textent.setEditable(false);

        textent2 = new TextField(30);
        textent2.setText("Pulsa el boton \"Actualizar\" para actualizar la hora");
        textent2.setEditable(false);

        textent3 = new TextField(30);
        textent3.setText("Pulsa el boton \"Actualizar\" para calcular la diferencia");
        textent3.setEditable(false);

        actualizar = new Button("Actualizar");
        actualizar.setEnabled(false);
        conectar = new Button("Conectar");
        desconectar = new Button("Desconectar");
        desconectar.setEnabled(false);

        panel.add(new Label("Hora de Conexion del Cliente:"));
        panel.add(textent);
        panel.add(new Label("Hora del Servidor:"));
        panel.add(textent2);
        panel.add(new Label("Diferencia de Horas:"));
        panel.add(textent3);
        panel.add(actualizar);
        panel.add(conectar);
        panel.add(desconectar);

        actualizar.addActionListener(this);
        conectar.addActionListener(this);
        desconectar.addActionListener(this);
        addWindowListener(new Cerrar());
        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();

        if (com.equals("Actualizar")) {
            try {
                // Enviar solicitud y recibir hora del servidor
                salida.writeUTF("hora");
                salida.flush();
                String horaServidor = entrada.readUTF();
                textent2.setText(horaServidor);

                // Calcular diferencia
                long horaActual = System.currentTimeMillis();
                long diferencia = horaActual - horaConexion;
                long segundos = diferencia / 1000;
                long minutos = segundos / 60;
                long horas = minutos / 60;

                textent3.setText(String.format("Diferencia: %d:%02d:%02d",
                        horas, minutos % 60, segundos % 60));

            } catch(IOException excepcion) {
                textent2.setText("Error al comunicar con servidor");
            }
        } else if (com.equals("Conectar")) {
            try {
                conexion = new Socket("localhost", 5000); // Cambié a localhost
                salida = new DataOutputStream(conexion.getOutputStream());
                entrada = new DataInputStream(conexion.getInputStream());

                conectar.setEnabled(false);
                desconectar.setEnabled(true);
                actualizar.setEnabled(true);

                horaConexion = System.currentTimeMillis();
                Date fechaConexion = new Date(horaConexion);
                textent.setText("Conectado a: " + fechaConexion.toString());

            } catch(IOException excepcion) {
                textent.setText("Error al conectar con servidor");
            }
        } else if (com.equals("Desconectar")) {
            try {
                if (salida != null) {
                    salida.writeUTF("salir");
                    salida.flush();
                }
                if (conexion != null) {
                    conexion.close();
                }
                conectar.setEnabled(true);
                desconectar.setEnabled(false);
                actualizar.setEnabled(false);
                textent.setText("Pulsa el boton \"Conectar\" para conectarte");
                textent2.setText("Pulsa el boton \"Actualizar\" para actualizar la hora");
                textent3.setText("Pulsa el boton \"Actualizar\" para calcular la diferencia");
            } catch(IOException excepcion) {
                System.out.println("Error al desconectar: " + excepcion);
            }
        }
    }

    class Cerrar extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (IOException ex) {
                System.out.println("Error al cerrar: " + ex);
            }
            dispose();
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        new ClienteTCP("Cliente Arturo");
        new ClienteTCP("Cliente Jesus");
    }
}
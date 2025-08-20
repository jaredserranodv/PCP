package Herencia;

public class Hilo2 extends Thread {


    private RecursoCompartido n;
    private String nombre;


    public Hilo2(RecursoCompartido numero, String nombre1) {
        // TODO Auto-generated constructor stub
        n=numero;
        nombre=nombre1;
    }
    public void run(){
        for(int i=1; i<=10; i++){
            n.incrementar(nombre);
        }

        //decrementar
        for(int i=1; i<=10; i++) {
            n.decrementar(nombre);
        }
    }
}



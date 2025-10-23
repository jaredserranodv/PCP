package Herencia;

public class PrincipalHerencia {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        RecursoCompartido n=new RecursoCompartido();
        Hilo1 h1=new Hilo1(n,"Hilo A");
        Hilo2 h2=new Hilo2(n,"Hilo B");

        h1.start();
        h2.start();
    }

}

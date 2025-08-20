package InterfaceR;

public class PrincipalRunnable {

    public static void main(String[] args)
    {

        RecursoCompartidoR recurso= new RecursoCompartidoR();
        Hilo1R objeto1 =new Hilo1R (recurso,"hiloA");
        Hilo2R objeto2 =new Hilo2R (recurso,"hiloB");

        Thread h1= new Thread(objeto1);
        Thread h2= new Thread(objeto2);
        h1.start();
        h2.start();


    }

}

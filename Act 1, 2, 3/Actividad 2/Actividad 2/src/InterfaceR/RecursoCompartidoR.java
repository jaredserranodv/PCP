package InterfaceR;


public class RecursoCompartidoR{
    private int N;
    public RecursoCompartidoR ()
    {
        N=0;
    }
    public synchronized void incrementar(String Nombre){
        N=N+1;
        System.out.println ( "Nombre: "+ Thread.currentThread().getName() + " "+ Nombre + " numero  = " + N );
    }

    public synchronized void decrementar(String Nombre){
        N=N-1;
        System.out.println ( "Nombre: "+ Thread.currentThread().getName() + " "+ Nombre + " numero  = " + N );
    }
}


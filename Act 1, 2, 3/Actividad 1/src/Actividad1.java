class ThreadConHerencia extends Thread
{
    String palabra;
    public ThreadConHerencia (String _palabra) {
        palabra = _palabra;
    }
    public void run( ) {
        for (int i=0; i<10; i++)
            System.out.println (palabra);
    }

    public static void main(String[] args) {
        Thread a = new ThreadConHerencia ("hiloUno ");
        Thread b = new ThreadConHerencia ("hiloDos ");
        Thread c= new ThreadConHerencia ("hiloTres ");
        Thread d = new ThreadConHerencia ("hiloCuatro ");
        Thread e = new ThreadConHerencia ("hiloCinco ");

        a.start();
        b.start();
        c.start();
        d.start();
        e.start();

        System.out.println ("Hilo Uno"+ " "+a.getName());
        System.out.println ("Hilo Dos"+ " "+b.getName());
        System.out.println ("Hilo Tres"+ " "+c.getName());
        System.out.println ("Hilo Cuatro"+ " "+d.getName());
        System.out.println ("Hilo Cinco"+ " "+e.getName());
        System.out.println ("Fin del hilo principal");
    }

}

class ThreadConRunnable implements Runnable{

    String palabra;

    public ThreadConRunnable (String _palabra)
    {
        palabra = _palabra;
    }

    public String par(int num)
    {
        return (num %2==0) ? "par":"impar";
    }
    public void run()
    {
        for (int i = 0;i<10;i++)
            System.out.println (palabra + " "+ i + " "+ par(i));

    }

    public static void main (String args []) {
        ThreadConRunnable a = new ThreadConRunnable ("hiloUno");
        ThreadConRunnable b = new ThreadConRunnable ("hiloDos");
        ThreadConRunnable c = new ThreadConRunnable ("hiloTres");
        ThreadConRunnable d = new ThreadConRunnable ("hiloCuatro");
        ThreadConRunnable e = new ThreadConRunnable ("hiloCinco");

        Thread t1 = new Thread (a);
        Thread t2 = new Thread (b);
        Thread t3 = new Thread (c);
        Thread t4 = new Thread (a);
        Thread t5 = new Thread (b);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        t1.setPriority(10); // Prioridad máxima
        t2.setPriority(5);
        t3.setPriority(3);
        System.out.println("----------------Hilos con Runnable----------------");
        System.out.println ("prioridad hiloUno"+" "+t1.getName()+" "+t1.getPriority());
        System.out.println ("prioridad hilodos"+t2.getName()+t2.getPriority());
        System.out.println ("prioridad hiloUno"+t3.getName()+t3.getPriority());
        System.out.println("Prioridad hiloCuatro: "+t4.getName()+" "+t4.getPriority());

        System.out.println ("Fin del hilo principal");
    }
}
import Server.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main
{
    public static void main (String[] args) {


        SaveServer saveServer = new SaveServer ();
        LoadServer loadServer = new LoadServer ();
        ExecutorService pool = Executors.newCachedThreadPool ();

        pool.execute (new Runnable () {
            @Override
            public void run () {
                saveServer.startServer ();
            }
        });

        pool.execute (new Runnable () {
            @Override
            public void run () {
                loadServer.startServer ();
            }
        });
        pool.shutdown ();


    }
}

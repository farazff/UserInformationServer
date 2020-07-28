import java.util.Scanner;
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


        int i = 2;
        while(i > 0)
        {
            Scanner scanner = new Scanner (System.in);
            String line = scanner.nextLine ();
            if (line.contains ("End; LoadServer"))
            {
                loadServer.setRunning (false);
                i--;
            } else if (line.contains ("End; SaveServer"))
            {
                saveServer.setRunning (false);
                i--;
            }
        }

    }
}

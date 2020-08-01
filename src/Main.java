import java.util.Scanner;

public class Main
{
    public static void main (String[] args) {
        ServerHandler serverHandler = new ServerHandler ();

        serverHandler.start ();
        Runtime.getRuntime ().addShutdownHook (serverHandler.getFinishProcess ());
        Scanner scanner = new Scanner (System.in);
        while (true)
        {
            if (scanner.nextLine ().contains ("Down Server"))
                System.exit (0);
        }
    }
}

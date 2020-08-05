import GameData.ServerInformationStorage;
import GameData.UsersStorage;
import Server.LoadServer;
import Server.SaveServer;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this class handles server and response them
 */
public class ServerHandler
{
    private UsersStorage usersStorage;
    private ServerInformationStorage serverInformationStorage;
    private SaveServer saveServer;
    private LoadServer loadServer;
    private FinishProcess finishProcess;

    /**
     * creates a new ServerHandler
     */
    public ServerHandler ()
    {
        loadUserStorage ();
        loadServerStorage ();
        finishProcess = new FinishProcess ();
        saveServer = new SaveServer (usersStorage,serverInformationStorage);
        loadServer = new LoadServer (usersStorage,serverInformationStorage);
        new Thread (new Runnable () {
            @Override
            public void run () {
                while (true)
                {
                    try {
                        Thread.sleep (1000 * 60 * 5);
                        System.out.println ("Auto Saved");
                        saveUserStorage ();
                        saveServerStorage ();
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace ();
                    }
                }
            }
        }).start (); // Auto Save in every 5 minutes
    }

    /**
     * starts response
     */
    public void start ()
    {

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

    /**
     *
     * @return get FinishProcess
     */
    public FinishProcess getFinishProcess () {
        return finishProcess;
    }

    /**
     * saves data of users
     */
    private void saveUserStorage ()
    {
        try (ObjectOutputStream out = new ObjectOutputStream (
                new FileOutputStream (
                        new File ("./Data/usersData.ser")))){

            out.writeObject (usersStorage);
        } catch (IOException e)
        {
            System.out.println ("some thing went wrong in save");
        }
    }

    /**
     * load data of Users
     */
    private void loadUserStorage ()
    {
        try (ObjectInputStream in = new ObjectInputStream (
                new FileInputStream (
                        new File ("./Data/usersData.ser")))){
            Object o = in.readObject ();
            this.usersStorage =  (UsersStorage) o;

        } catch (FileNotFoundException e)
        {
            this.usersStorage = new UsersStorage ();
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println ("some thing was wrong in load");
        }
    }

    /**
     * load data of servers
     */
    private void loadServerStorage ()
    {
        try (ObjectInputStream in = new ObjectInputStream (
                new FileInputStream (
                        new File ("./Data/serverData.ser")))){
            Object o = in.readObject ();
            this.serverInformationStorage =  (ServerInformationStorage) o;

        } catch (FileNotFoundException e)
        {
            this.serverInformationStorage = new ServerInformationStorage ();
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println ("some thing was wrong in load");
        }
    }

    /**
     * save data of servers
     */
    private void saveServerStorage ()
    {
        try (ObjectOutputStream out = new ObjectOutputStream (
                new FileOutputStream (
                        new File ("./Data/serverData.ser")))){

            out.writeObject (serverInformationStorage);
        } catch (IOException e)
        {
            System.out.println ("some thing went wrong in save");
        }
    }

    /**
     * this class built for save anything before termination
     */
    private class FinishProcess extends Thread
    {
        @Override
        public void run () {
            System.out.println ("Auto Saved");
            saveUserStorage ();
            saveServerStorage ();
        }
    }
}

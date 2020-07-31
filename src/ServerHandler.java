import GameData.ServerInformationStorage;
import GameData.UsersStorage;
import Server.LoadServer;
import Server.SaveServer;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerHandler
{
    private UsersStorage usersStorage;
    private ServerInformationStorage serverInformationStorage;
    private SaveServer saveServer;
    private LoadServer loadServer;


    public ServerHandler ()
    {
        loadUserStorage ();
        loadServerStorage ();
        saveServer = new SaveServer (usersStorage);
        loadServer = new LoadServer (usersStorage);
    }

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

    private void loadServerStorage ()
    {
        try (ObjectInputStream in = new ObjectInputStream (
                new FileInputStream (
                        new File ("./Data/serverData.ser")))){
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

    private void saveServerStorage ()
    {
        try (ObjectOutputStream out = new ObjectOutputStream (
                new FileOutputStream (
                        new File ("./Data/serverData.ser")))){

            out.writeObject (usersStorage);
        } catch (IOException e)
        {
            System.out.println ("some thing went wrong in save");
        }
    }
}

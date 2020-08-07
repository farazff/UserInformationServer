package Server;

import GameData.ServerInformationStorage;
import GameData.UsersStorage;
import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Parent Server for Login/logout/List
 */
public class Server
{
    private boolean running;
    private int numOfClients;
    private ExecutorService pool;
    private int port;
    private UsersStorage usersStorage;
    private ServerInformationStorage serverInformationStorage;

    /**
     * creates a new Server
     * @param port port
     * @param usersStorage usersStorage
     * @param serverInformationStorage serverInformationStorage
     */
    public Server (int port, UsersStorage usersStorage,
                   ServerInformationStorage serverInformationStorage)
    {
        pool = Executors.newCachedThreadPool ();
        running = true;
        numOfClients = 0;
        this.port = port;
        this.usersStorage = usersStorage;
        this.serverInformationStorage = serverInformationStorage;
    }

    /**
     * starts server
     */
    public void startServer ()
    {
        try (ServerSocket welcomingConnection = new ServerSocket (port)) {
            System.out.println ("Server with port : " + port + getServerName (port)+
                    " Started \nWaiting for Client .....");
            int i = 1;
            while (running)
            {
                pool.execute (new ClientHandler (welcomingConnection.accept (),i,port,usersStorage,
                        serverInformationStorage));
                System.out.println ("Server with port : " + port + getServerName (port) +
                        " connected to new Client : Client " + i);
                i++;
            }
            pool.shutdown ();
        } catch (IOException e)
        {
            e.printStackTrace ();
        }
    }

    /**
     * get server's name depends on it's port
     * @param port port
     * @return Server's name
     */
    private String getServerName (int port)
    {
        switch (port)
        {
            case 8083 : return " (Load Server) ";
            case 4787 : return " (Save Server) ";
            case 6050 : return " (List Server) ";
        }
        return "";
    }




}

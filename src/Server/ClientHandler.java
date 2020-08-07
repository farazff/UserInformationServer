package Server;

import GameData.NullUser;
import GameData.ServerInformationStorage;
import GameData.User;
import GameData.UsersStorage;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * this class handles a client
 */
public class ClientHandler implements Runnable
{
    private Socket socket;
    private int id;
    private int port;
    private UsersStorage userStorage;
    private ServerInformationStorage serverInformationStorage;

    /**
     * creates new Handler
     * @param socket connection
     * @param id id
     * @param port port
     * @param usersStorage usersStorage
     * @param serverInformationStorage serverInformationStorage
     */
    public ClientHandler (Socket socket, int id, int port, UsersStorage usersStorage,
                          ServerInformationStorage serverInformationStorage)
    {
        this.socket = socket;
        this.id = id;
        this.port = port;
        this.userStorage = usersStorage;
        this.serverInformationStorage = serverInformationStorage;
    }

    @Override
    public void run () {

        OutputStream out = null;
        InputStream in = null;
        try {

            if (port == 8083)
            {
                // receive
                User user;
                in = new DataInputStream (socket.getInputStream ());
                System.out.println (port + getServerName (port)
                        + "<- data received from client " + id);
                String request = ((DataInputStream) in).readUTF ();
                String[] split = request.split (" ");
                if (split[0].equals ("Login"))
                {
                    user = userStorage.getUser (split[1], split[2].toCharArray ());
                    if (user == null)
                        user = new NullUser ();
                } else // sign up
                {
                    if (userStorage.hasUserNameUsed (split[1]))
                        user = new NullUser ();
                    else
                    {
                        user = new User (split[1],split[2].toCharArray (),serverInformationStorage);
                        userStorage.addUser (user);
                    }
                }

                // send


                out = new ObjectOutputStream (socket.getOutputStream ());
                ((ObjectOutputStream) out).writeObject (user);
                System.out.println (port + getServerName (port) +
                        "-> data sent to client " + id);

            } else if (port == 4787)
            {
                // receive
                in = new ObjectInputStream (socket.getInputStream ());
                User user = (User) ((ObjectInputStream) in).readObject ();
                boolean res = userStorage.update (user);

                System.out.println (port + getServerName (port)
                        + "<- data received from client " + id);

                // send
                out = new DataOutputStream (socket.getOutputStream ());

                if (res)
                    ((DataOutputStream) out).writeUTF ("Successful");
                else
                    ((DataOutputStream) out).writeUTF ("Error");
                System.out.println (port + getServerName (port)
                        + "-> data sent to client " + id);
            } else if (port == 6050)
            {
                // receive
                in = new DataInputStream (socket.getInputStream ());
                ((DataInputStream) in).readUTF ();
                System.out.println (port + getServerName (port)
                        + "<- data received from client " + id);

                // send
                out = new ObjectOutputStream (socket.getOutputStream ());
                UsersStorage usersStorage1 = new UsersStorage ();
                usersStorage1.setUsers (new ArrayList<> (userStorage.getUsers ()));
                ((ObjectOutputStream) out).writeObject (usersStorage1);
                System.out.println (port + getServerName (port)
                        + "-> data sent to client " + id);
            }

        } catch (ClassNotFoundException e) {
            System.out.println (port + getServerName (port)
                    + "Some thing went wrong in reading objects from server");
        } catch (SocketException e)
        {
            System.err.println (port + getServerName (port)
                    + "Client " + id + " 's connection Terminated");
        } catch (IOException e)
        {
            System.err.println (port + getServerName (port)
                    + "Some thing went wrong with Client " + id);
        } finally
        {
            try {
                if (in != null)
                    in.close ();
            }
            catch (SocketException ignore)
            {
            }
            catch (IOException e)
            {
                System.err.println (port + getServerName (port)
                        + "Some thing went wrong in closing ServerInputStream" +
                        " in Client " + id);
            }
            try {

                if (out != null)
                    out.close ();
            }
            catch (SocketException ignore)
            {
            }
            catch (IOException e)
            {
                System.err.println (port + getServerName (port)
                        + "Some thing went wrong in closing ServerOutputStream" +
                        " in Client " + id);
            }
            try {
                socket.close ();
                System.out.println (port + getServerName (port)
                        + "Client " + id + " closed");
            }
            catch (SocketException ignore)
            {
            }
            catch (IOException e)
            {
                System.err.println (port + getServerName (port)
                        + "Some thing went wrong in closing client " + id +
                        " connection");
            }
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

package Server;

import GameData.NullUser;
import GameData.User;
import GameData.UsersStorage;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable
{
    private Socket socket;
    private int id;
    private int port;
    private UsersStorage userStorage;

    public ClientHandler (Socket socket, int id, int port, UsersStorage usersStorage)
    {
        this.socket = socket;
        this.id = id;
        this.port = port;
        this.userStorage = usersStorage;
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
                System.out.println (port + ((port == 8083)? " (Load Server) " :
                        " (Save Server) ") + "<- data received from client " + id);
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
                        user = new User (split[1],split[2].toCharArray ());
                        userStorage.addUser (user);
                    }
                }

                // send


                out = new ObjectOutputStream (socket.getOutputStream ());
                ((ObjectOutputStream) out).writeObject (user);
                System.out.println (port + ((port == 8083)? " (Load Server) " :
                        " (Save Server) ") + "-> data sent to client " + id);

            } else if (port == 4787)
            {
                // receive
                in = new ObjectInputStream (socket.getInputStream ());
                User user = (User) ((ObjectInputStream) in).readObject ();
                boolean res = userStorage.update (user);

                System.out.println (port + ((port == 8083)? " (Load Server) " :
                        " (Save Server) ") + "<- data received from client " + id);

                // send
                out = new DataOutputStream (socket.getOutputStream ());

                if (res)
                    ((DataOutputStream) out).writeUTF ("Successful");
                else
                    ((DataOutputStream) out).writeUTF ("Error");
                System.out.println (port + ((port == 8083)? " (Load Server) " :
                        " (Save Server) ") + "-> data sent to client " + id);
            }

        } catch (ClassNotFoundException e) {
            System.out.println (port + ((port == 8083)? " (Load Server) " :
                    " (Save Server) ") + "Some thing went wrong in reading objects from server");
        } catch (SocketException e)
        {
            System.err.println (port + ((port == 8083)? " (Load Server) " :
                    " (Save Server) ") + "Client " + id + " 's connection Terminated");
        } catch (IOException e)
        {
            System.err.println (port + ((port == 8083)? " (Load Server) " :
                    " (Save Server) ") + "Some thing went wrong with Client " + id);
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
                System.err.println (port + ((port == 8083)? " (Load Server) " :
                        " (Save Server) ") + "Some thing went wrong in closing ServerInputStream" +
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
                System.err.println (port + ((port == 8083)? " (Load Server) " :
                        " (Save Server) ") + "Some thing went wrong in closing ServerOutputStream" +
                        " in Client " + id);
            }
            try {
                socket.close ();
                System.out.println (port + ((port == 8083)? " (Load Server) " :
                        " (Save Server) ") + "Client " + id + " closed");
            }
            catch (SocketException ignore)
            {
            }
            catch (IOException e)
            {
                System.err.println (port + ((port == 8083)? " (Load Server) " :
                        " (Save Server) ") + "Some thing went wrong in closing client " + id +
                        " connection");
            }
        }
    }


}

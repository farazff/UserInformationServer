package GameData;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerInformation implements Serializable
{
    private final char[] password;
    private final String url;
    private static final int MAX_CAPACITY = 100;
    private int currentCapacity;
    private int numOfActiveGames;
    private final ArrayList<Game> games;

    public ServerInformation (String url, ArrayList<Game> games, char[] password)
    {
        this.url = url;
        this.password = password;
        if (games != null)
            this.games = new ArrayList<> (games);
        else
            this.games = new ArrayList<> ();

        if (games == null)
            numOfActiveGames = 0;
        else
            numOfActiveGames = games.size ();

        currentCapacity = MAX_CAPACITY - numOfActiveGames;
    }

    public char[] getPassword () {
        return password;
    }

    public int getNumOfActiveGames () {
        return numOfActiveGames;
    }

    public int getCurrentCapacity () {
        return currentCapacity;
    }

    public String getUrl () {
        return url;
    }

    public void addGame (Game Game)
    {
        games.add (Game);
        numOfActiveGames = games.size ();
    }

    public void removeMultiGame (int index)
    {
        try {
            games.remove (index);
            numOfActiveGames = games.size ();
        } catch (IndexOutOfBoundsException e)
        {
            System.out.println ("Some Thing went Wrong in removing indexed multi game");
        }
    }

    public ArrayList<Game> getGames () {
        return games;
    }

    public Game getMultiGame (int index)
    {
        try {
            return games.get (index);
        } catch (IndexOutOfBoundsException e)
        {
            System.out.println ("Some Thing went Wrong in getting indexed multi game");
            return null;
        }
    }
}

package GameData;


import java.io.Serializable;
import java.util.ArrayList;

public class UsersStorage implements Serializable
{

    private ArrayList<User> users;
    private boolean isIterate;

    public UsersStorage ()
    {
        users = new ArrayList<> ();
        isIterate = false;
    }

    public void addUser (User user)
    {
        if (user == null)
            return;
        try {
            while (isIterate)
            {
                Thread.sleep (250);
            }
            users.add (user);
        } catch (InterruptedException e)
        {
            e.printStackTrace ();
        }
    }

    public boolean update (User user)
    {
        boolean res = false;
        isIterate = true;
        for (User user1 : users)
        {
            if (user1.getUserName ().equals (user.getUserName ()))
            {
                user1.setRank (user.getRank ());
                user1.setScore (user.getScore ());
                user1.setDefaultCanonPower (user.getDefaultCanonPower ());
                user1.setDefaultTankStamina (user.getDefaultTankStamina ());
                user1.setDefaultWallStamina (user.getDefaultWallStamina ());
                res = true;
                break;
            }
        }
        isIterate = false;
        return res;
    }

    public User getUser (String userName, char[] password)
    {
        isIterate = true;
        for (User user : users)
        {
            if (user.equals (new User (userName,password)))
            {
                isIterate = false;
                return user;
            }
        }
        isIterate = false;
        return null;
    }

    public boolean hasUserNameUsed (String userName)
    {
        User user = new User (userName,new char[]{'.','.','.'});
        isIterate = true;
        for (User user1 : users)
        {
            if (user1.equals (user))
            {
                isIterate = false;
                return true;
            }
        }
        isIterate = false;
        return false;
    }
}

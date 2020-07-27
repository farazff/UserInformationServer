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
            if (user1.equals (user))
            {
                user1.setRank (user.getRank ());
                user1.setScore (user.getScore ());
                // and another data
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
            if (user.isSame (userName,password))
            {
                isIterate = false;
                return user;
            }
        }
        isIterate = false;
        return new NullUser ();
    }
}

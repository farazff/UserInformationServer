import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class User implements Serializable
{
    private char[] password;
    private String userName;
    private long signedUpTime;
    private int score;
    private int rank;

    public User (String userName, char[] password)
    {
        this.password = password;
        this.userName = userName;
        signedUpTime = System.currentTimeMillis ();
        this.score = 0;
        rank = -1;
    }




    public void setRank (int rank) {
        this.rank = rank;
    }

    public void setScore (int score) {
        this.score = score;
    }

    public long getSignedUpTime () {
        return signedUpTime;
    }

    public int getScore () {
        return score;
    }

    public int getRank () {
        return rank;
    }

    public boolean isSame (String userName, char[] password)
    {
        return Arrays.equals (password,this.password) &&
                userName.equals (this.userName);
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return isSame (user.userName, user.password);
    }

    @Override
    public int hashCode () {
        int result = Objects.hash (userName);
        result = 31 * result + Arrays.hashCode (password);
        return result;
    }
}

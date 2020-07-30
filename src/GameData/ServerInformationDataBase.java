package GameData;

import java.util.ArrayList;
import java.util.Objects;

public class ServerInformationDataBase
{
    ArrayList<ServerInformation> serverData;

    public ServerInformationDataBase (ArrayList<ServerInformation> serverData)
    {
        this.serverData = Objects.requireNonNullElseGet (serverData, ArrayList::new);
    }

    public ArrayList<ServerInformation> getServerData () {
        return serverData;
    }

    public void addNewServer (ServerInformation serverInformation)
    {
        this.serverData.add (serverInformation);
    }

    public void removeServer (ServerInformation serverInformation)
    {
        this.serverData.remove (serverInformation);
    }
}

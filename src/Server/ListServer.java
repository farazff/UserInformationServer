package Server;

import GameData.ServerInformationStorage;
import GameData.UsersStorage;

public class ListServer extends Server
{

    public ListServer (UsersStorage usersStorage, ServerInformationStorage serverInformationStorage) {
        super (6050, usersStorage, serverInformationStorage);
    }
}

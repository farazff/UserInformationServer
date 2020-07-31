package Server;

import GameData.ServerInformationStorage;
import GameData.UsersStorage;

public class LoadServer extends Server
{

    public LoadServer (UsersStorage usersStorage, ServerInformationStorage serverInformationStorage) {
        super (8083,usersStorage,serverInformationStorage);
    }
}

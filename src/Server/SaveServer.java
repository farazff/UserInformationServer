package Server;

import GameData.ServerInformationStorage;
import GameData.UsersStorage;
import Server.Server;

public class SaveServer extends Server
{

    public SaveServer (UsersStorage usersStorage, ServerInformationStorage serverInformationStorage) {
        super (4787,usersStorage,serverInformationStorage);
    }
}

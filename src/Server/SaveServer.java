package Server;

import GameData.UsersStorage;
import Server.Server;

public class SaveServer extends Server
{

    public SaveServer (UsersStorage usersStorage) {
        super (4787,usersStorage);
    }
}

package Server;

import GameData.UsersStorage;

public class LoadServer extends Server
{

    public LoadServer (UsersStorage usersStorage) {
        super (8083,usersStorage);
    }
}

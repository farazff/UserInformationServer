package Server;

import GameData.ServerInformationStorage;
import GameData.UsersStorage;

/**
 * this server gets list of All players
 */
public class ListServer extends Server
{

    /**
     * creates new List server
     * @param usersStorage usersStorage
     * @param serverInformationStorage serverInformationStorage
     */
    public ListServer (UsersStorage usersStorage, ServerInformationStorage serverInformationStorage) {
        super (6050, usersStorage, serverInformationStorage);
    }
}

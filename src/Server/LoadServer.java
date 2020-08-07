package Server;

import GameData.ServerInformationStorage;
import GameData.UsersStorage;

/**
 * this Server get's User for Login or Refresh
 */
public class LoadServer extends Server
{

    /**
     * creates a new Load Server
     * @param usersStorage usersStorage
     * @param serverInformationStorage serverInformationStorage
     */
    public LoadServer (UsersStorage usersStorage, ServerInformationStorage serverInformationStorage) {
        super (8083,usersStorage,serverInformationStorage);
    }
}

package Server;

import GameData.ServerInformationStorage;
import GameData.UsersStorage;

/**
 * this Server Update's storage from client
 * mainly used for Saving
 */
public class SaveServer extends Server
{

    /**
     * creates a new Save Server
     * @param usersStorage usersStorage
     * @param serverInformationStorage serverInformationStorage
     */
    public SaveServer (UsersStorage usersStorage, ServerInformationStorage serverInformationStorage) {
        super (4787,usersStorage,serverInformationStorage);
    }
}

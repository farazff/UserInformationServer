public class LoadServer extends Server
{

    public LoadServer (UsersStorage usersStorage) {
        super (usersStorage, 8083);
    }
}

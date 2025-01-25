package models.user;

public abstract class UserDao {
    public abstract void addUser(String username, User user);
    public abstract User getUser(String username, String password);
    public abstract User getUserByUsername(String username);
}

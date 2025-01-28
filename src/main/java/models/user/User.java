package models.user;

public abstract class User {
    private String username;
    private String password;
    private String type;

    protected User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" + "username=" +  ", type=" + type + '}';
    }

}

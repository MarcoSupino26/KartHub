package beans;

public class loginBean {
    private String username;
    private String password;
    private String type; //sto facendo dummy, da modificare

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getType() {
        return type;
    }

    public void setUsername(String text){
        this.username = text;
    }

    public void setPassword(String text){
        this.password = text;
    }

    public void setType(String text){this.type = text;}
}

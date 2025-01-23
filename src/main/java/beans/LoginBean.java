package beans;

public class LoginBean {
    private String username;
    private String password;
    private String type;

    public LoginBean(){//La new non ha bisogno di un parametro
    }


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

    public void setType(String text){
        this.type = text;
    }
}

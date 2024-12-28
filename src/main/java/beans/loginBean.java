package beans;

public class loginBean {
    private static loginBean instance;
    private String username;
    private String password;
    private String type;

    private loginBean(){}

    public static loginBean getInstance(){
        if(instance == null){
            instance = new loginBean();
        }
        return instance;
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

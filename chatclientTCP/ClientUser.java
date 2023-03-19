import java.io.Serializable;

public class ClientUser implements Serializable {
    private String name;

    public ClientUser() {
    }

    public String getName() {
        return name;
    }

    public ClientUser(String name){
        this.name=name;
    }
}

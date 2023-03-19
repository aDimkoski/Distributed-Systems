import java.io.Serializable;

public class Response implements Serializable {
    private String type; //list ... poraka
    private ClientUser[] users;

    private String poraka;

    public String getPoraka() {
        return poraka;
    }

    public void setPoraka(String poraka) {
        this.poraka = poraka;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ClientUser[] getUsers() {
        return users;
    }

    public void setUsers(ClientUser[] users) {
        this.users = users;
    }
}

import java.io.Serializable;

public class Request implements Serializable {
    private String type; //login ... list ... poraka
    private String message;

    private ClientUser clientUser;

    public Request(){}
    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }
}

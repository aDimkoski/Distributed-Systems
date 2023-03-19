package enums;

public enum Username {
    U1("Marco"), U2("Lucifer"), U3("Tomek"), U4("Alex"), U5("Lisa"), U6("Niko"), U7("Gabriel"), U8("Taco");
    private final String username;

    Username(String username) {
        this.username = username;
    }

    public String getName() {
        return username;
    }
}

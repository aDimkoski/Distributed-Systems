package enums;

public enum RouteTypes {

    R1("*." + UserType.T2.getType() + ".*." + RoomType.T3.getType()),
    R2("*." + UserType.T1.getType() + ".*.*"),
    R3("*." + UserType.T2.getType() + ".*.*");
    private final String type;

    RouteTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
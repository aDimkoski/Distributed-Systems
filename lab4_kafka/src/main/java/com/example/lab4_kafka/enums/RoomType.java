package com.example.lab4_kafka.enums;

public enum RoomType {
    T1("Classroom"), T2("Laboratory"), T3("Office");
    private final String type;

    RoomType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

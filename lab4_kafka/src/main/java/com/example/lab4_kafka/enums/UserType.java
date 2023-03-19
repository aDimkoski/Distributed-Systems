package com.example.lab4_kafka.enums;

public enum UserType {
    T1("professor"), T2("student");
    private final String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

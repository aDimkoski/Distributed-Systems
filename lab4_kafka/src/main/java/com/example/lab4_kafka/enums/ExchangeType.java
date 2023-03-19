package com.example.lab4_kafka.enums;

public enum ExchangeType {

    DIRECT("direct"),
    TOPIC("topic"),
    FANOUT("fanout"),
    HEADER("headers");


    public static final String EXCHANGE_NAME = "topic-exchange";
    private final String exchangeName;

    ExchangeType(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getExchangeName() {
        return this.exchangeName;
    }

}
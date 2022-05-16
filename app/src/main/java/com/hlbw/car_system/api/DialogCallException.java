package com.hlbw.car_system.api;

public class DialogCallException extends Exception {

    public String message;

    public DialogCallException(String message) {
        super(message);
        this.message = message;
    }

}

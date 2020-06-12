package com.ecommerceapp.ecommerceserver.controller;

public final class RegistrationConstants {
    public static final String SUBJECT = "Account confirmation!";
    public static final String MESSAGE_TEXT = "To confirm registration click in the link below.\n";
    public static final String CONFIRMATION_ENDPOINT = "http://localhost:8080/confirm?token=";

    private RegistrationConstants() {}
}

package com.moderntech.ecommerce.models;

public class Customer {

    private final String id;
    private final String fullName;
    private final String email;

    public Customer(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "ID=" + id + ", имя=" + fullName + ", эл. почта=" + email;
    }
}

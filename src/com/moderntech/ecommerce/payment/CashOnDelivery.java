package com.moderntech.ecommerce.payment;

public final class CashOnDelivery implements PaymentMethod {

    private final String recipientName;
    private final String deliveryAddress;

    public CashOnDelivery(String recipientName, String deliveryAddress) {
        this.recipientName = recipientName;
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public String getMethodName() {
        return "Наложенный платеж";
    }

    @Override
    public String getDescription() {
        return getMethodName()
                + ": получатель "
                + recipientName
                + ", адрес "
                + deliveryAddress;
    }
}

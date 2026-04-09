package com.moderntech.ecommerce.payment;

public final class CreditCardPayment implements PaymentMethod {

    private final String cardHolder;
    private final String cardNumber;
    private final String bankName;

    public CreditCardPayment(String cardHolder, String cardNumber, String bankName) {
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.bankName = bankName;
    }

    @Override
    public String getMethodName() {
        return "Банковская карта";
    }

    @Override
    public String getDescription() {
        return getMethodName()
                + ": "
                + bankName
                + ", "
                + getMaskedCardNumber()
                + ", владелец "
                + cardHolder;
    }

    private String getMaskedCardNumber() {
        if (cardNumber.length() <= 4) {
            return cardNumber;
        }

        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}

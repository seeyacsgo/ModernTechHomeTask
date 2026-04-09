package com.moderntech.ecommerce.payment;

public final class DigitalWalletPayment implements PaymentMethod {

    private final String walletName;
    private final String walletId;

    public DigitalWalletPayment(String walletName, String walletId) {
        this.walletName = walletName;
        this.walletId = walletId;
    }

    @Override
    public String getMethodName() {
        return "Электронный кошелек";
    }

    @Override
    public String getDescription() {
        return getMethodName() + ": " + walletName + ", идентификатор " + walletId;
    }
}

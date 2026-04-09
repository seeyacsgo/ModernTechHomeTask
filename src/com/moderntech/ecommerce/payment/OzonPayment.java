package com.moderntech.ecommerce.payment;

import com.moderntech.ecommerce.models.Order;

public class OzonPayment implements Payment {

    @Override
    public String getProviderName() {
        return "Ozon";
    }

    @Override
    public PaymentStatus pay(Order order, PaymentMethod paymentMethod) {
        System.out.println("Провайдер оплаты: " + getProviderName());
        System.out.println("Номер заказа: " + order.getOrderId());
        System.out.println("Сумма к оплате: " + formatMoney(order.getTotalAmount()));
        System.out.println("Способ оплаты: " + paymentMethod.getDescription());

        PaymentStatus status;

        if (paymentMethod instanceof CashOnDelivery) {
            System.out.println("Ozon принял заказ. Оплата будет выполнена при получении.");
            status = PaymentStatus.PROCESSING;
        } else {
            System.out.println("Ozon успешно обработал онлайн-платеж.");
            status = PaymentStatus.SUCCESS;
        }

        order.updatePaymentInfo(getProviderName(), paymentMethod.getDescription(), status);
        System.out.println("Статус платежа: " + status);

        return status;
    }

    private String formatMoney(double amount) {
        return String.format("%.2f руб.", amount);
    }
}

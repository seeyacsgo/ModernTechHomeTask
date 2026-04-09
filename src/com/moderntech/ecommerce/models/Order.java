package com.moderntech.ecommerce.models;

import java.util.ArrayList;

import com.moderntech.ecommerce.enums.OrderStatus;
import com.moderntech.ecommerce.payment.PaymentStatus;

public class Order {

    private static int nextOrderNumber = 1001;

    private final String orderId;
    private final Customer customer;
    private final ArrayList<OrderItem> items;
    private final double subtotal;
    private final double vatAmount;
    private final double totalAmount;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private String paymentProvider;
    private String paymentMethodDescription;

    private Order(
            Customer customer,
            ArrayList<OrderItem> items,
            double subtotal,
            double vatAmount,
            double totalAmount) {
        this.orderId = "ORD-" + nextOrderNumber++;
        this.customer = customer;
        this.items = items;
        this.subtotal = subtotal;
        this.vatAmount = vatAmount;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
        this.paymentStatus = PaymentStatus.PENDING;
        this.paymentProvider = "Не выбран";
        this.paymentMethodDescription = "Не выбран";
    }

    public static Order createFromCart(ShoppingCart cart) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Нельзя оформить заказ из пустой корзины.");
        }

        ArrayList<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            orderItems.add(new OrderItem(
                    cartItem.product(),
                    cartItem.quantity(),
                    cartItem.getLineTotal()));
        }

        return new Order(
                cart.getCustomer(),
                orderItems,
                cart.getSubtotal(),
                cart.getVatAmount(),
                cart.getTotalWithVat());
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getVatAmount() {
        return vatAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public String getPaymentMethodDescription() {
        return paymentMethodDescription;
    }

    public void updateStatus(OrderStatus newStatus) {
        System.out.println("Статус заказа " + orderId + " изменен: " + status + " -> " + newStatus);
        this.status = newStatus;
    }

    public void updatePaymentInfo(
            String paymentProvider,
            String paymentMethodDescription,
            PaymentStatus paymentStatus) {
        this.paymentProvider = paymentProvider;
        this.paymentMethodDescription = paymentMethodDescription;
        this.paymentStatus = paymentStatus;
    }

    public void printOrderDetails() {
        System.out.println("Детали заказа:");
        System.out.println("Номер заказа: " + orderId);
        System.out.println("Покупатель: " + customer.getFullName());
        System.out.println("Текущий статус заказа: " + status);

        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            System.out.println(
                    (i + 1) + ". " + item.product().name()
                            + " | Количество: " + item.quantity()
                            + " | Сумма: " + formatMoney(item.lineTotal()));
        }

        System.out.println("Сумма без НДС: " + formatMoney(subtotal));
        System.out.println("НДС 22%: " + formatMoney(vatAmount));
        System.out.println("Итого к оплате: " + formatMoney(totalAmount));
    }

    public void printSummary() {
        System.out.println("Заказ " + orderId
                + " | Покупатель: " + customer.getFullName()
                + " | Итог: " + formatMoney(totalAmount)
                + " | Статус заказа: " + status
                + " | Провайдер оплаты: " + paymentProvider
                + " | Статус платежа: " + paymentStatus);
        System.out.println("Способ оплаты: " + paymentMethodDescription);
    }

    private String formatMoney(double amount) {
        return String.format("%.2f руб.", amount);
    }
}

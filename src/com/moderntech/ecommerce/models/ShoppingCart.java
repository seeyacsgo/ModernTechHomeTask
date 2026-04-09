package com.moderntech.ecommerce.models;

import java.util.ArrayList;

public class ShoppingCart {

    private static final double VAT_RATE = 0.22;

    private final Customer customer;
    private final ArrayList<CartItem> items;

    public ShoppingCart(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Нельзя добавить пустой товар.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть больше нуля.");
        }

        for (int i = 0; i < items.size(); i++) {
            CartItem currentItem = items.get(i);

            if (currentItem.product().id().equals(product.id())) {
                int newQuantity = currentItem.quantity() + quantity;

                if (newQuantity > product.stock()) {
                    throw new IllegalArgumentException("Недостаточно товара на складе.");
                }

                items.set(i, new CartItem(product, newQuantity));
                return;
            }
        }

        if (quantity > product.stock()) {
            throw new IllegalArgumentException("Недостаточно товара на складе.");
        }

        items.add(new CartItem(product, quantity));
    }

    public void removeProduct(String productId) {
        items.removeIf(item -> item.product().id().equals(productId));
    }

    public ArrayList<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        double subtotal = 0.0;

        for (CartItem item : items) {
            subtotal += item.getLineTotal();
        }

        return subtotal;
    }

    public double getVatAmount() {
        return getSubtotal() * VAT_RATE;
    }

    public double getTotalWithVat() {
        return getSubtotal() + getVatAmount();
    }

    public void clear() {
        items.clear();
    }

    public void printCart() {
        System.out.println("Корзина покупателя: " + customer.getFullName());

        if (items.isEmpty()) {
            System.out.println("Корзина пустая.");
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            System.out.println(
                    (i + 1) + ". " + item.product().name()
                            + " | Количество: " + item.quantity()
                            + " | Цена: " + formatMoney(item.product().price())
                            + " | Сумма: " + formatMoney(item.getLineTotal()));
        }

        System.out.println("Сумма без НДС: " + formatMoney(getSubtotal()));
        System.out.println("НДС 22%: " + formatMoney(getVatAmount()));
        System.out.println("Итого с НДС: " + formatMoney(getTotalWithVat()));
    }

    private String formatMoney(double amount) {
        return String.format("%.2f руб.", amount);
    }
}

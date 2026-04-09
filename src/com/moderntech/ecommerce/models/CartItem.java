package com.moderntech.ecommerce.models;

public record CartItem(Product product, int quantity) {

    public CartItem {
        if (product == null) {
            throw new IllegalArgumentException("Товар не должен быть пустым.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество товара должно быть больше нуля.");
        }
    }

    public double getLineTotal() {
        return product.price() * quantity;
    }
}

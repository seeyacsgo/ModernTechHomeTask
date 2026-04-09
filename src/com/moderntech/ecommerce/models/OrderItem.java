package com.moderntech.ecommerce.models;

public record OrderItem(Product product, int quantity, double lineTotal) {

    public OrderItem {
        if (product == null) {
            throw new IllegalArgumentException("Товар не должен быть пустым.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество товара должно быть больше нуля.");
        }

        if (lineTotal < 0) {
            throw new IllegalArgumentException("Сумма строки не может быть отрицательной.");
        }
    }
}

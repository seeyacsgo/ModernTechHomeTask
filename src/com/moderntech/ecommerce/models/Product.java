package com.moderntech.ecommerce.models;

import com.moderntech.ecommerce.enums.ProductCategory;

public record Product(String id, String name, ProductCategory category, double price, int stock) {

    public Product {
        if (price < 0) {
            throw new IllegalArgumentException("Цена товара не может быть отрицательной.");
        }

        if (stock < 0) {
            throw new IllegalArgumentException("Количество товара не может быть отрицательным.");
        }
    }
}

package com.moderntech.ecommerce.payment;

import com.moderntech.ecommerce.models.Order;

public interface Payment {

    String getProviderName();

    PaymentStatus pay(Order order, PaymentMethod paymentMethod);
}

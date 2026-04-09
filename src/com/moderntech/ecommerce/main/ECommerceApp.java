package com.moderntech.ecommerce.main;

import java.util.HashMap;

import com.moderntech.ecommerce.enums.OrderStatus;
import com.moderntech.ecommerce.enums.ProductCategory;
import com.moderntech.ecommerce.models.Customer;
import com.moderntech.ecommerce.models.Order;
import com.moderntech.ecommerce.models.Product;
import com.moderntech.ecommerce.models.ShoppingCart;
import com.moderntech.ecommerce.payment.CashOnDelivery;
import com.moderntech.ecommerce.payment.CreditCardPayment;
import com.moderntech.ecommerce.payment.DigitalWalletPayment;
import com.moderntech.ecommerce.payment.OzonPayment;
import com.moderntech.ecommerce.payment.Payment;
import com.moderntech.ecommerce.payment.PaymentMethod;
import com.moderntech.ecommerce.payment.WildberriesPayment;

public class ECommerceApp {

    public static void main(String[] args) {
        HashMap<String, Product> catalog = createCatalog();

        System.out.println("Каталог товаров магазина:");
        printCatalog(catalog);

        Customer customer = new Customer("CUST-001", "Иван Иванов", "ivanov@mail.ru");
        System.out.println();
        System.out.println("Покупатель создан: " + customer);

        ShoppingCart cart = new ShoppingCart(customer);
        cart.addProduct(catalog.get("P-1001"), 1);
        cart.addProduct(catalog.get("P-1004"), 2);
        cart.addProduct(catalog.get("P-1005"), 1);
        cart.addProduct(catalog.get("P-1003"), 1);

        System.out.println();
        System.out.println("В корзину добавлены товары.");
        cart.printCart();

        cart.removeProduct("P-1003");
        System.out.println();
        System.out.println("После удаления одной позиции из корзины:");
        cart.printCart();

        Order firstOrder = Order.createFromCart(cart);
        System.out.println();
        System.out.println("Оформление заказа выполнено.");
        firstOrder.printOrderDetails();

        firstOrder.updateStatus(OrderStatus.CONFIRMED);
        firstOrder.updateStatus(OrderStatus.PROCESSING);

        Payment ozonPayment = new OzonPayment();
        Payment wildberriesPayment = new WildberriesPayment();

        System.out.println();
        System.out.println("Сценарий оплаты 1: Ozon + банковская карта");
        PaymentMethod creditCardPayment = new CreditCardPayment(
                "Иван Иванов",
                "1234567812345678",
                "СберБанк");
        ozonPayment.pay(firstOrder, creditCardPayment);
        firstOrder.updateStatus(OrderStatus.SHIPPED);

        ShoppingCart secondCart = new ShoppingCart(customer);
        secondCart.addProduct(catalog.get("P-1002"), 1);
        secondCart.addProduct(catalog.get("P-1005"), 2);
        Order secondOrder = Order.createFromCart(secondCart);
        secondOrder.updateStatus(OrderStatus.CONFIRMED);

        System.out.println();
        System.out.println("Сценарий оплаты 2: Wildberries + электронный кошелек");
        secondOrder.printOrderDetails();
        PaymentMethod digitalWalletPayment = new DigitalWalletPayment(
                "Киви",
                "1234567890");
        wildberriesPayment.pay(secondOrder, digitalWalletPayment);

        ShoppingCart thirdCart = new ShoppingCart(customer);
        thirdCart.addProduct(catalog.get("P-1003"), 1);
        thirdCart.addProduct(catalog.get("P-1004"), 1);
        Order thirdOrder = Order.createFromCart(thirdCart);
        thirdOrder.updateStatus(OrderStatus.CONFIRMED);

        System.out.println();
        System.out.println("Сценарий оплаты 3: Ozon + наложенный платеж");
        thirdOrder.printOrderDetails();
        PaymentMethod cashOnDelivery = new CashOnDelivery(
                customer.getFullName(),
                "Москва, ул. красная, д. 10");
        ozonPayment.pay(thirdOrder, cashOnDelivery);

        System.out.println();
        System.out.println("Итоговая сводка по заказам:");
        firstOrder.printSummary();
        secondOrder.printSummary();
        thirdOrder.printSummary();
    }

    private static HashMap<String, Product> createCatalog() {
        HashMap<String, Product> catalog = new HashMap<>();

        catalog.put("P-1001", new Product(
                "P-1001",
                "Смартфон (крутой)",
                ProductCategory.SMARTPHONE,
                32990.00,
                8));
        catalog.put("P-1002", new Product(
                "P-1002",
                "Ноутбук (дорогой)",
                ProductCategory.LAPTOP,
                58990.00,
                5));
        catalog.put("P-1003", new Product(
                "P-1003",
                "Планшет (классный)",
                ProductCategory.TABLET,
                21990.00,
                6));
        catalog.put("P-1004", new Product(
                "P-1004",
                "Беспроводные наушники (топовые)",
                ProductCategory.ACCESSORY,
                4990.00,
                20));
        catalog.put("P-1005", new Product(
                "P-1005",
                "Экшн-камера (стильная)",
                ProductCategory.CAMERA,
                27990.00,
                4));

        return catalog;
    }

    private static void printCatalog(HashMap<String, Product> catalog) {
        String[] productIds = {"P-1001", "P-1002", "P-1003", "P-1004", "P-1005"};

        for (int i = 0; i < productIds.length; i++) {
            Product product = catalog.get(productIds[i]);
            System.out.println(
                    (i + 1) + ". "
                            + product.name()
                            + " | Категория: " + product.category()
                            + " | Цена: " + formatMoney(product.price())
                            + " | Остаток: " + product.stock());
        }
    }

    private static String formatMoney(double amount) {
        return String.format("%.2f руб.", amount);
    }
}

package ru.ilmira;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        CartService cartService1 = context.getBean("cartService", CartService.class);
        CartService cartService2 = context.getBean("cartService", CartService.class);

        cartService1.addProduct(cartService1.getProductRepository().findById(1L));
        cartService1.addProduct(cartService1.getProductRepository().findById(5L));
        cartService1.addProduct(cartService1.getProductRepository().findById(4L));
        cartService2.addProduct(cartService2.getProductRepository().findById(2L));
        cartService2.addProduct(cartService2.getProductRepository().findById(2L));
        cartService2.addProduct(cartService2.getProductRepository().findById(3L));
        cartService2.addProduct(cartService2.getProductRepository().findById(1L));

        System.out.println("Cart 1:\n" + cartService1);
        System.out.println("-------------------");
        System.out.println("Cart 2:\n" + cartService2);

        cartService2.deleteProduct(cartService2.getProductRepository().findById(1L));
        cartService1.deleteProduct(cartService1.getProductRepository().findById(5L));

        System.out.println("===================");
        System.out.println("");
        System.out.println("Cart 1:\n" + cartService1);
        System.out.println("-------------------");
        System.out.println("Cart 2:\n" + cartService2);
    }
}

package ru.ilmira;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.ilmira.persist.ProductRepository;
import ru.ilmira.persist.ProductRepositoryImpl;

@ComponentScan("ru.ilmira")
@Configuration
public class Config {

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepositoryImpl();
    }

    @Bean
    public ProductService productService() {
        return new ProductService();
    }

    @Bean
    @Scope("prototype")
    public CartService cartService() {
        return new CartService();
    }
}

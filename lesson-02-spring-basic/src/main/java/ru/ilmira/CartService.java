package ru.ilmira;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ilmira.persist.Product;
import ru.ilmira.persist.ProductRepository;

import java.util.HashMap;
import java.util.Map;


public class CartService {

    @Autowired
    private ProductRepository productRepository;

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    private final Map<Product, Integer> cart = new HashMap<>();

    public void addProduct(Product product) {
        cart.merge(product, 1, Integer::sum);
    }

    public void deleteProduct(Product product) {
        if (getProductCount(product) > 1) {
            cart.put(product, getProductCount(product) - 1);
        } else if (getProductCount(product) == 1) {
            cart.remove(product);
        }
    }

    public int getProductCount(Product product) {
        if (!cart.containsKey(product)) {
            return 0;
        }
        return cart.get(product);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        cart.forEach((p,i) -> {
            sb.append("Product: ");
            sb.append(p.getName());
            sb.append(", count = ");
            sb.append(i.intValue());
            sb.append("\n");
        });
        return sb.toString();
    }
}

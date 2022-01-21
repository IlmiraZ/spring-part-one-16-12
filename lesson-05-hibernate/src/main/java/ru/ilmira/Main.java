package ru.ilmira;

import org.hibernate.cfg.Configuration;
import ru.ilmira.dao.ProductDao;
import ru.ilmira.dao.ProductDaoImpl;
import ru.ilmira.entity.Product;

import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        final EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        final ProductDao productDao = new ProductDaoImpl(emFactory);

        // -saveOrUpdate-
        System.out.println("save or update product: ");
        System.out.println("---------------------------");
        Product product1 = new Product("Apple",  new BigDecimal("12.20"));
        productDao.save(product1);
        System.out.println();

        // -findById-
        System.out.println("find a product by id: ");
        System.out.println("---------------------------");
        long id = 9L;
        Optional<Product> product = productDao.findById(id);
        product.ifPresentOrElse(System.out::println,() -> System.out.println("product with id = " + id + " not found.")
        );
        System.out.println();

        // -findAll-
        System.out.println("find all product: ");
        System.out.println("---------------------------");
        List<Product> products = productDao.findAll();
        System.out.println(products);
        System.out.println();

        // -delete-
        System.out.println("delete product: ");
        System.out.println("---------------------------");
        productDao.deleteById(7L);
        products = productDao.findAll();
        System.out.println(products);

        emFactory.close();
    }
}



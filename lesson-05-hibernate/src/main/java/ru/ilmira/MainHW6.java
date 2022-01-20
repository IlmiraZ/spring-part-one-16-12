package ru.ilmira;

import org.hibernate.cfg.Configuration;
import ru.ilmira.dao.ProductDao;
import ru.ilmira.dao.ProductDaoImpl;
import ru.ilmira.entity.Client;
import ru.ilmira.entity.Product;
import ru.ilmira.service.ClientService;
import ru.ilmira.service.ClientServiceImpl;

import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.List;

public class MainHW6 {
    public static void main(String[] args) {
        final EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        final ProductDao productDao = new ProductDaoImpl(emFactory);
        final ClientService clientService = new ClientServiceImpl(emFactory);

        List<Client> clients= clientService.findListOfClientsByProductId(3L);
        System.out.println(clients);

        productDao.findById(2L)
                .ifPresent(product -> {
                    product.setPrice(new BigDecimal("10.11"));
                    productDao.save(product);
                });
        productDao.findById(1L).ifPresent(System.out::println);


        List<Product> products = clientService.findListOfProductsByClientId(2L);
        System.out.println(products.toString());

    }
//      [Product{id=3, title='product3', price=10.02}]
}

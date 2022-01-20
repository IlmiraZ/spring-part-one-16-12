package ru.ilmira.service;

import org.springframework.stereotype.Service;
import ru.ilmira.dao.Util;
import ru.ilmira.entity.Client;
import ru.ilmira.entity.Product;
import ru.ilmira.entity.OrderItem;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService, Util {

    private final EntityManagerFactory emFactory;

    public ClientServiceImpl(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    @Override
    public List<Product> findListOfProductsByClientId(Long client_id) {
        final List<OrderItem> orderItems = executeForEntityManager(
                emFactory,
                em -> em.createQuery("" +
                                "select i from OrderItem i" +
                                " join i.order o" +
                                " join o.client c" +
                                " where c.id = :client_id", OrderItem.class)
                        .setParameter("client_id", client_id)
                        .getResultList());

        return orderItems.stream()
                .map(orderItem -> {
                    Product product = orderItem.getProduct();
                    product.setPrice(orderItem.getPrice());
                    return product;
                }).collect(Collectors.toList());
    }

    @Override
    public List<Client> findListOfClientsByProductId(Long product_id) {
        return executeForEntityManager(
                emFactory,
                em -> em.createQuery("" +
                                "select c from Client c" +
                                " join c.orderList o" +
                                " join o.orderItems i" +
                                " join i.product p" +
                                " where p.id = :product_id", Client.class)
                        .setParameter("product_id", product_id)
                        .getResultList()
        );
    }
}

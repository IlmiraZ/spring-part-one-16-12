package ru.ilmira.service;

import ru.ilmira.entity.Client;
import ru.ilmira.entity.Product;

import java.util.List;

public interface ClientService {
    List<Product> findListOfProductsByClientId(Long client_id);

    List<Client> findListOfClientsByProductId(Long product_id);
}

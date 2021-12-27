package ru.ilmira;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ilmira.persist.Product;
import ru.ilmira.persist.ProductRepository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void init(){
        productRepository.save(new Product(1L,"Product1", new BigDecimal(100)));
        productRepository.save(new Product(2L,"Product2", new BigDecimal(200)));
        productRepository.save(new Product(3L,"Product3", new BigDecimal(300)));
        productRepository.save(new Product(4L,"Product4", new BigDecimal(400)));
        productRepository.save(new Product(5L,"Product5", new BigDecimal(500)));
    }

    public long count(){
        return productRepository.findAll().size();

    }
}

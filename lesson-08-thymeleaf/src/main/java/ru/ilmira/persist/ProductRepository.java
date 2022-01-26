package ru.ilmira.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

//    List<Product> findAllByNameLike(String pattern);
//
//    List<Product> findAllByNameLikeAndPriceLessThan(String pattern, BigDecimal maxPrice);
//
//    @Query("select p " +
//            "from Product p " +
//            "where (p.name like :pattern or :pattern is null) and " +
//            "(p.price >= :minPrice or :minPrice is null) and " +
//            "(p.price <= :maxPrice or :maxPrice is null)")
//    List<Product> findByFilter(@Param("pattern") String pattern,
//                               @Param("minPrice")BigDecimal minPrice,
//                               @Param("maxPrice") BigDecimal maxPrice);

}


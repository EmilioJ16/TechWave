package es.uc3m.microblog.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    // Encuentra todos los productos cuya categoría tenga un nombre específico
    List<Product> findByCategoryName(String categoryName);

    @Query("SELECT p FROM Product p WHERE " +
       "LOWER(CAST(p.name AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
       "LOWER(CAST(p.description AS string)) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.category.id = :catId")
    List<Product> findByCategoryId(@Param("catId") Integer catId);

}

package es.uc3m.microblog.model;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    // Encuentra todos los productos cuya categoría tenga un nombre específico
    List<Product> findByCategoryName(String categoryName);
}

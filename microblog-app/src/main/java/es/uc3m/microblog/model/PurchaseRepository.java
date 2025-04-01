package es.uc3m.microblog.model;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {
    List<Purchase> findByUser(User user);
}

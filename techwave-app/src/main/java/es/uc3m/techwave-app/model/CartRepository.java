package es.uc3m.microblog.model;

import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    Cart findByUser(User user);
}

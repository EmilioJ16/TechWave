package es.uc3m.microblog.model;

import org.springframework.data.repository.CrudRepository;

import org.springframework.cache.annotation.Cacheable;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email); // Busca usuario por email
}

package es.uc3m.microblog.model;

import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Integer> {
    // Métodos personalizados si fuera necesario
}

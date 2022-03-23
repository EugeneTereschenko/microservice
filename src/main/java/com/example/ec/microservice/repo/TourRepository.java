package com.example.ec.microservice.repo;

import com.example.ec.microservice.domain.Tour;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<Tour, Integer> {
}

package com.twolak.springframework.spring5webfluxrest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.twolak.springframework.spring5webfluxrest.domain.Category;

/**
 * @author twolak
 *
 */
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
//	Mono<Category> findByDescription(String description);
}

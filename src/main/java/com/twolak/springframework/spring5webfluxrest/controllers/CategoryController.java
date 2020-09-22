package com.twolak.springframework.spring5webfluxrest.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.twolak.springframework.spring5webfluxrest.domain.Category;
import com.twolak.springframework.spring5webfluxrest.repositories.CategoryRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author twolak
 *
 */
@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
	
	public static final String BASE_URL = "/api/v1/categories";
	
	private final CategoryRepository categoryRepository;

	public CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@GetMapping
	public Flux<Category> getAllCategories() {
		return this.categoryRepository.findAll();
	}
	
	@GetMapping("{id}")
	public Mono<Category> getCategoryById(@PathVariable("id") String id) {
		return this.categoryRepository.findById(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Mono<Void> createCategories(@RequestBody Publisher<Category> categoryStream) {
		return this.categoryRepository.saveAll(categoryStream).then();
	}
}

/**
 * 
 */
package com.twolak.springframework.spring5webfluxrest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.twolak.springframework.spring5webfluxrest.domain.Category;
import com.twolak.springframework.spring5webfluxrest.repositories.CategoryRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.*;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
@WebFluxTest(controllers = CategoryController.class)
class CategoryControllerTest {
	
	@MockBean
	private CategoryRepository categoryRepository;
	
	@InjectMocks
	private CategoryController categoryController;
	
	@Autowired
	private WebTestClient webTestClient;
	
	@BeforeEach
	void setUp() throws Exception {
//		this.categoryRepository = Mockito.mock(CategoryRepository.class);
//		this.categoryController = new CategoryController(this.categoryRepository);
//		this.webTestClient = WebTestClient.bindToController(this.categoryController).build();
	}
	
	@Test
	void testGetAllCategories() {
		
		given(this.categoryRepository.findAll()).willReturn(Flux.just(
				Category.builder().description("cat1").build(), 
				Category.builder().description("cat2").build()));
		
		this.webTestClient.get().uri(CategoryController.BASE_URL)
			.header(HttpHeaders.ACCEPT, "application/json")
	//		.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(Category.class).hasSize(2);
		then(this.categoryRepository).should(times(1)).findAll();
		then(this.categoryRepository).shouldHaveNoMoreInteractions();
		
	}
	
	@Test
	void testGetCategoryById() {
		given(this.categoryRepository.findById(anyString())).willReturn(Mono.just(
				Category.builder().description("cat1").build()));
		
		this.webTestClient.get().uri(CategoryController.BASE_URL + "/abc")
			.header(HttpHeaders.ACCEPT, "application/json")
			.exchange()
			.expectStatus().isOk()
			.expectBody(Category.class);
		then(this.categoryRepository).should(times(1)).findById(anyString());
		then(this.categoryRepository).shouldHaveNoMoreInteractions();
	}

}

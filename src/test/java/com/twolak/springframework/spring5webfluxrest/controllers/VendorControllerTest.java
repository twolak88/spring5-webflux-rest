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
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.twolak.springframework.spring5webfluxrest.domain.Vendor;
import com.twolak.springframework.spring5webfluxrest.repositories.VendorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
@WebFluxTest(controllers = VendorController.class)
class VendorControllerTest {
	
	@MockBean
	private VendorRepository vendorRepository;
	
	@InjectMocks
	private VendorController vendorController;
	
	@Autowired
	private WebTestClient webTestClient;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void testGetAllVendors() {
		given(this.vendorRepository.findAll()).willReturn(Flux.just(
				Vendor.builder().firstname("Rob").lastname("Kent").build(),
				Vendor.builder().firstname("Bob").lastname("Last").build()));
		
		this.webTestClient.get().uri(VendorController.BASE_URL)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(Vendor.class).hasSize(2);
		then(this.vendorRepository).should(times(1)).findAll();
		then(this.vendorRepository).shouldHaveNoMoreInteractions();
		
	}
	
	@Test
	void testGetVendorById() {
		given(this.vendorRepository.findById(anyString())).willReturn(Mono.just(
				Vendor.builder().firstname("Bob").lastname("Uncle").build()));
		
		this.webTestClient.get().uri(VendorController.BASE_URL + "/abc")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Vendor.class);
		then(this.vendorRepository).should(times(1)).findById(anyString());
		then(this.vendorRepository).shouldHaveNoMoreInteractions();
	}

}

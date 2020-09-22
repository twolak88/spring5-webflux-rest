/**
 * 
 */
package com.twolak.springframework.spring5webfluxrest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twolak.springframework.spring5webfluxrest.domain.Vendor;
import com.twolak.springframework.spring5webfluxrest.repositories.VendorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author twolak
 *
 */
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
	public static final String BASE_URL = "/api/v1/vendors";
	
	public final VendorRepository vendorRepository;
	
	public VendorController(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}
	
	@GetMapping
	public Flux<Vendor> getAllVendors() {
		return this.vendorRepository.findAll();
	}
	
	@GetMapping("{id}")
	public Mono<Vendor> getVendorById(@PathVariable("id") String id) {
		return this.vendorRepository.findById(id);
	}
}

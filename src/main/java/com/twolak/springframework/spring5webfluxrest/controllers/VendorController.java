/**
 * 
 */
package com.twolak.springframework.spring5webfluxrest.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorStream) {
		return this.vendorRepository.saveAll(vendorStream).then();
	}
	
	@PutMapping("{id}")
	public Mono<Vendor> updateVendor(@PathVariable("id") String id, @RequestBody Vendor vendor) {
		vendor.setId(id);
		return this.vendorRepository.save(vendor);
	}
	
	@PatchMapping("{id}")
	public Mono<Vendor> patchVendor(@PathVariable("id") String id, @RequestBody Vendor vendor) {
		Vendor foundVendor = this.vendorRepository.findById(id).block();
		boolean hasChanges = false;
		if (vendor.getFirstname() != null && !foundVendor.getFirstname().equals(vendor.getFirstname())) {
			foundVendor.setFirstname(vendor.getFirstname());
			hasChanges = true;
		}
		if (vendor.getLastname() != null && !foundVendor.getLastname().equals(vendor.getLastname())) {
			foundVendor.setLastname(vendor.getLastname());
			hasChanges = true;
		}
		if (hasChanges) {
			return this.vendorRepository.save(foundVendor);
		}
		return Mono.just(foundVendor);
	}
}

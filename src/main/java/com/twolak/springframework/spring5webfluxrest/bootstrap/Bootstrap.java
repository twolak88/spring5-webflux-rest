package com.twolak.springframework.spring5webfluxrest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.twolak.springframework.spring5webfluxrest.domain.Category;
import com.twolak.springframework.spring5webfluxrest.domain.Vendor;
import com.twolak.springframework.spring5webfluxrest.repositories.CategoryRepository;
import com.twolak.springframework.spring5webfluxrest.repositories.VendorRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twolak
 *
 */
@Slf4j
@Component
public class Bootstrap implements CommandLineRunner{
	
	private final CategoryRepository categoryRepository;
	private final VendorRepository vendorRepository;

	public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
		this.categoryRepository = categoryRepository;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		loadVendors();
		log.info("Vendor loaded: " + this.vendorRepository.count().block());
		loadCategories();
		log.info("Categories loaded: " + this.categoryRepository.count().block());
	}
	
	private void loadCategories() {
		if (this.categoryRepository.count().block() > 0) return;
		log.info("Loading categories...");
		createCategory("Fruits");
		createCategory("Dried");
		createCategory("Fresh");
		createCategory("Exotic");
		createCategory("Nuts");
	}
	
	private void loadVendors() {
		if (this.vendorRepository.count().block() > 0) return;
		log.info("Loading vendors...");
		createVendor("Mike", "Weston");
		createVendor("Sam", "Axe");
		createVendor("Joe", "Buck");
		createVendor("Clark", "Kent");
	}
	
	private void createCategory(String description) {
		Category category = Category.builder().description(description).build();
		this.categoryRepository.save(category).block();
	}
	
	private void createVendor(String firstname, String lastname) {
		Vendor vendor = Vendor.builder().firstname(firstname).lastname(lastname).build();
		this.vendorRepository.save(vendor).block();
	}
}

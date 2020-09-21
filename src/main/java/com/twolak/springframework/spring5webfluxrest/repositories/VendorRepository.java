/**
 * 
 */
package com.twolak.springframework.spring5webfluxrest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.twolak.springframework.spring5webfluxrest.domain.Vendor;

/**
 * @author twolak
 *
 */
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {

}

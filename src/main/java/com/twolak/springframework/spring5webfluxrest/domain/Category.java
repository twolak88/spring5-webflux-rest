/**
 * 
 */
package com.twolak.springframework.spring5webfluxrest.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author twolak
 *
 */
@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
	@Id
	private String id;
	
	private String description;
}

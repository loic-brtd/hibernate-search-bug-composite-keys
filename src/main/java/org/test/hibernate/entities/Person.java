/*
 * (c) Copyright 1998-2024, ANS. All rights reserved.
 */

package org.test.hibernate.entities;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Indexed
public class Person {

	@Id
	private Long id;

	@FullTextField
	private String name;

	@OneToOne
	@IndexedEmbedded
	private Task task;

}

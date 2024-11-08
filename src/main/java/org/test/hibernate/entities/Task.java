/*
 * (c) Copyright 1998-2024, ANS. All rights reserved.
 */

package org.test.hibernate.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@IdClass(TaskKey.class)
public class Task {

	@Id
	private String code;

	@Id
	private String reference;

}

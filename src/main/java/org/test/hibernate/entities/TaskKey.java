/*
 * (c) Copyright 1998-2024, ANS. All rights reserved.
 */

package org.test.hibernate.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskKey {
	private String code;
	private String reference;
}

/*
 * (c) Copyright 1998-2024, ANS. All rights reserved.
 */
package org.test.hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HibernateSearchBugCompositeKeysApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HibernateSearchBugCompositeKeysApplication.class, args);
	}

}
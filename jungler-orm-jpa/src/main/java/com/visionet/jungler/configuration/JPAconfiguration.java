/*
 * Copyright (c) 2018. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.configuration;

import com.visionet.jungler.support.SimpleBaseRepositoryFactoryBean;
import com.visionet.jungler.support.hibernate.ImplicitStandardNamingStrategy;
import com.visionet.jungler.support.hibernate.PhysicalStandardNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author TC.Ubuntu
 * @since 2018/6/9.
 */
@Configuration
@EntityScan("com.visionet.jungler.**.entity")
@EnableJpaRepositories(value = "com.visionet.jungler.**.repository",
					   repositoryFactoryBeanClass = SimpleBaseRepositoryFactoryBean.class)
public class JPAconfiguration {

	@Bean
	public PhysicalNamingStrategy physicalNamingStrategy() {
		return new PhysicalStandardNamingStrategy();
	}

	@Bean
	public ImplicitNamingStrategy implicitNamingStrategy() {
		return new ImplicitStandardNamingStrategy();
	}
}

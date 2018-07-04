/**
 * Copyright (c) 2016. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.support;

import com.visionet.jungler.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class SimpleBaseRepositoryFactoryBean<R extends JpaRepository<M, ID>, M extends BaseEntity, ID extends Serializable> extends
		JpaRepositoryFactoryBean<R, M, ID> {

	/**
	 * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
	 *
	 * @param repositoryInterface must not be {@literal null}.
	 */
	public SimpleBaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new SimpleBaseRepositoryFactory<M, String>(entityManager);
	}

	class SimpleBaseRepositoryFactory<M extends BaseEntity, ID extends Serializable> extends JpaRepositoryFactory {
		private EntityManager entityManager;

		public SimpleBaseRepositoryFactory(EntityManager entityManager) {
			super(entityManager);
			this.entityManager = entityManager;
		}

		protected Object getTargetRepository(RepositoryMetadata metadata) {
			JpaEntityInformation<M, ID> entityInformation = getEntityInformation((Class<M>) metadata.getDomainType());
			return new SimpleBaseRepository<M, ID>(entityInformation, entityManager);
		}

		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return SimpleBaseRepository.class;
		}
	}

}

/**
 * Copyright (c) 2016. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.support;

import com.visionet.jungler.domain.Page;
import com.visionet.jungler.entity.BaseEntity;
import com.visionet.jungler.repository.BaseRepository;
import com.visionet.jungler.sql.SQL;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public class SimpleBaseRepository<M extends BaseEntity, ID extends Serializable> extends SimpleJpaRepository<M, ID>
		implements BaseRepository<M, ID> {

    private final EntityManager em;
    private final JpaEntityInformation<M, ID> entityInformation;

    private Class<M> entityClass;

	public SimpleBaseRepository(JpaEntityInformation<M, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
      	this.entityClass = this.entityInformation.getJavaType();
        this.em = entityManager;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<M> findAll(SQL sql) {
		Query query = em.createNativeQuery(sql.toString());
		if(sql.getParams() != null && sql.getParams().size() > 0) {
			for (int i = 0; i < sql.getParams().size(); i++) {
				query.setParameter(i + 1, sql.getParams().get(i));
			}
		}
		return query.unwrap(org.hibernate.Query.class)
				.setResultTransformer(Transformers.aliasToBean(this.entityClass)).list();
	}

	@Override
	public Page findPage(SQL sql) {
		Query query = em.createNativeQuery(sql.toString());
		if(sql.getParams() != null && sql.getParams().size() > 0) {
			for (int i = 0; i < sql.getParams().size(); i++) {
				query.setParameter(i + 1, sql.getParams().get(i));
			}
		}
		Page page = sql.getPage();
		if(page == null) {
			page = new Page();
			page.setCurPage(0);
		}
		query.setFirstResult((page.getCurPage()) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		page.setTotal(this.count(sql));
		page.setRows(query.unwrap(org.hibernate.Query.class)
				.setResultTransformer(Transformers.aliasToBean(this.entityClass)).list());
		return page;
	}

	@Override
	public long count(SQL sql) {
		Query query = em.createNativeQuery(sql.count());
		if(sql.getParams() != null && sql.getParams().size() > 0) {
			for (int i = 0; i < sql.getParams().size(); i++) {
				query.setParameter(i + 1, sql.getParams().get(i));
			}
		}
		return Long.valueOf(query.getSingleResult().toString());
	}
}

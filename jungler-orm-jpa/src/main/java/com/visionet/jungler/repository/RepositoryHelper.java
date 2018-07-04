/*
 * Copyright (c) 2018. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.repository;

import com.visionet.jungler.domain.Page;
import com.visionet.jungler.sql.SQL;
import org.hibernate.transform.Transformers;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author TC.Ubuntu
 * @since 2018/6/13.
 */
public abstract class RepositoryHelper {

	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(SQL sql, Class<T> resultMap) {
		Query query = entityManager.createNativeQuery(sql.toString());
		if(sql.getParams() != null && sql.getParams().size() > 0) {
			for (int i = 0; i < sql.getParams().size(); i++) {
				query.setParameter(i + 1, sql.getParams().get(i));
			}
		}
		return query.unwrap(org.hibernate.Query.class)
				.setResultTransformer(Transformers.aliasToBean(resultMap)).list();
	}

	public Page findPage(SQL sql, Class resultMap) {
		Query query = entityManager.createNativeQuery(sql.toString());
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
				.setResultTransformer(Transformers.aliasToBean(resultMap)).list());
		return page;
	}

	long count(SQL sql) {
		Query query = entityManager.createNativeQuery(sql.count());
		if(sql.getParams() != null && sql.getParams().size() > 0) {
			for (int i = 0; i < sql.getParams().size(); i++) {
				query.setParameter(i + 1, sql.getParams().get(i));
			}
		}
		return Long.valueOf(query.getSingleResult().toString());
	}
}

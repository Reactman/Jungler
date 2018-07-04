/*
 * Copyright (c) 2018. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.repository;

import com.visionet.jungler.domain.Page;
import com.visionet.jungler.entity.BaseEntity;
import com.visionet.jungler.sql.SQL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @author TC.Ubuntu
 * @since 2018/6/9.
 */
public interface BaseRepository<E extends BaseEntity, ID extends Serializable> extends JpaRepository<E, ID> {

	List<E> findAll(SQL sql);

	Page findPage(SQL sql);

	long count(SQL sql);

}

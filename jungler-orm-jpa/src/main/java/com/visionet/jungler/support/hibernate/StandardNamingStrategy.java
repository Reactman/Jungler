/*
 * Copyright (c) 2015. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.support.hibernate;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * @author Sin
 * @since 0.0.1
 * 
 */
public class StandardNamingStrategy extends ImprovedNamingStrategy {
	private static final long serialVersionUID = 1L;

	private static final String TABLE_PREFIX = "t_%s";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hibernate.cfg.ImprovedNamingStrategy#classToTableName(java.lang.String
	 * )
	 */
	@Override
	public String classToTableName(String className) {
		return String.format(TABLE_PREFIX, super.classToTableName(className));
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hibernate.cfg.ImprovedNamingStrategy#foreignKeyColumnName(java.lang
	 * .String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String foreignKeyColumnName(String propertyName, String propertyEntityName,
			String propertyTableName, String referencedColumnName) {
		return super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName,
				referencedColumnName) + "_" + referencedColumnName;
	}

}

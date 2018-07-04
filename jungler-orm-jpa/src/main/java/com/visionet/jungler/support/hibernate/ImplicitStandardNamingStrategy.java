/*
 * Copyright (c) 2015. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.support.hibernate;

import com.visionet.jungler.utils.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitEntityNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

import java.util.Locale;

public class ImplicitStandardNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {
	protected static final String TABLE_PATTERN = "T_{}";

	@Override
	public Identifier determinePrimaryTableName(ImplicitEntityNameSource source) {
		if (source == null) {
			// should never happen, but to be defensive...
			throw new HibernateException("Entity naming information was not provided.");
		}
		String tableName = transformEntityName(source.getEntityNaming());
		if (tableName == null) {
			// todo : add info to error message - but how to know what to write since we failed to interpret the naming source
			throw new HibernateException("Could not determine primary table name for entity");
		}
		return toIdentifier(
				StringUtils.assemble(TABLE_PATTERN, addUnderscores(tableName)), source.getBuildingContext());
	}

	protected String addUnderscores(String name) {
		StringBuilder builder = new StringBuilder(name);
		for (int i = 1; i < builder.length() - 1; i++) {
			if (Character.isLowerCase(builder.charAt(i - 1)) && Character.isUpperCase(builder.charAt(i))
					&& Character.isLowerCase(builder.charAt(i + 1))) {
				builder.insert(i++, '_');
			}
		}
		return builder.toString().toUpperCase(Locale.ROOT);
	}
}

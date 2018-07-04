/*
 * Copyright (c) 2018. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.utils.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author TC.Ubuntu
 * @since 2018/6/6.
 */
public class BeanCopier {

	private final Logger logger = LoggerFactory.getLogger(BeanCopier.class);
	private static BeanCopier _instance;
	private static final int DEFAULT_COPIER_MAP_SIZE = 128;
	private static final String TRANSFER_INDICATOR = "->";
	private LinkedHashMap<String, org.springframework.cglib.beans.BeanCopier> copierMap;

	private BeanCopier() {
		init();
	}

	public static BeanCopier getInstance() {
		if (_instance == null) {
			_instance = new BeanCopier();
		}
		return _instance;
	}

	private void init() {
		copierMap = new LinkedHashMap<String, org.springframework.cglib.beans.BeanCopier>(16,
				0.75f, true) {
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean removeEldestEntry(
					Map.Entry<String, org.springframework.cglib.beans.BeanCopier> eldest) {
				return size() > DEFAULT_COPIER_MAP_SIZE;
			}
		};
	}

	public void copyBean(Object original, Object target) {
		String fromName = original.getClass().getSimpleName();
		String toName = target.getClass().getSimpleName();
		StringBuilder key = new StringBuilder(fromName).append(TRANSFER_INDICATOR).append(toName);
		org.springframework.cglib.beans.BeanCopier copier = copierMap.get(key.toString());
		if (copier == null) {
			copier = org.springframework.cglib.beans.BeanCopier.create(original.getClass(),
					target.getClass(), false);
			copierMap.put(key.toString(), copier);
		}
		copier.copy(original, target, null);
	}

	public <T1, T2> List<T2> copyCollection(Collection<T1> originalCollection, Class<T2> targetClass) {
		List<T2> toList = new ArrayList<T2>();
		for (T1 from : originalCollection) {
			try {
				T2 to = targetClass.newInstance();
				copyBean(from, to);
				toList.add(to);
			} catch (Exception e) {
				logger.error("Bean copy error occurs when from [{}] to [{}]", from.getClass()
						.getName(), from.getClass().getName(), e);
				return null;
			}
		}
		return toList;
	}
}

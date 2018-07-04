/*
 * Copyright (c) 2018. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.visionet.jungler.entity.Sample;
import com.visionet.jungler.repository.SampleRepository;
import com.visionet.jungler.sql.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author TC.Ubuntu
 * @since 2018/6/10.
 */
@RestController
public class SampleRestController {

	private Logger logger = LoggerFactory.getLogger(SampleRestController.class);

	@Resource
	private SampleRepository sampleRepository;

	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	public String sayHello() {
		SQL sql = new SQL() {{
			SELECT(Sample.class, "s");
			FROM(Sample.class, "s");
		}};
		List<Sample> list = sampleRepository.findAll(sql);

		return "hello world";
	}
}

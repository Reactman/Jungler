/*
 * Copyright (c) 2018. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.entity;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author TC.Ubuntu
 * @since 2018/6/10.
 */
@Entity
public class Sample extends BaseEntity<String> implements Serializable {

	private String name;

	private String age;

	private String nickName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}

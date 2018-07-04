/*
 * Copyright (c) 2018. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.exception;

/**
 * @author TC.Ubuntu
 * @since 2018/6/6.
 */
public class BusinessException extends Exception {

	public BusinessException(String detailMessage) {
		super(detailMessage);
	}

	public BusinessException(String detailMessage, Throwable cause) {
		super(detailMessage, cause);
	}
}

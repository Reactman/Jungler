/*
 * Copyright (c) 2018. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.exception;

/**
 * @author TC.Ubuntu
 * @since 2018/6/6.
 */
public class ValidationException extends RuntimeException {

	public ValidationException(String detailMessage) {
		super(detailMessage);
	}

	public ValidationException(String detailMessage, Throwable cause) {
		super(detailMessage, cause);
	}
}

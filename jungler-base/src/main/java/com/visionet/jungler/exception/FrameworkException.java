/*
 * Copyright (c) 2018. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.exception;

/**
 * @author TC.Ubuntu
 * @since 2018/6/5.
 */
public class FrameworkException extends RuntimeException {

	public FrameworkException(String detailMessage) {
		super(detailMessage);
	}

	public FrameworkException(String detailMessage, Throwable cause) {
		super(detailMessage, cause);
	}

}

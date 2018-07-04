/*
 * Copyright (c) 2018. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.domain;

/**
 * @author TC.Ubuntu
 * @since 2018/6/6.
 */
public class ApiResponse {

	public static String SUCCESS = "1";

	public static String FAILED = "0";

	// 提示信息编码
	private String code;
	// 提示信息
	private String msg;
	//返回数据对象
	private Object data;

	private ApiResponse(String code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static ApiResponse success(String msg, Object data) {
		return new ApiResponse(SUCCESS, msg, data);
	}

	public static ApiResponse failed(String msg, Object data) {
		return new ApiResponse(FAILED, msg, data);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}

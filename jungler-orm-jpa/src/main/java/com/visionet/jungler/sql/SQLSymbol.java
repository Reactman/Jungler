package com.visionet.jungler.sql;

/**
 * @author TC.Ubuntu
 * @DESCRIPTION ${DESCRIPTION}
 * @since 2018/6/12.
 */
public enum SQLSymbol {
	LIKE("LIKE"),
	EQUALS("="),
	GREATER(">"),
	GREATER_EQUALS(">="),
	LESS("<"),
	LESS_EQUALS("<=");

	private String symbol;

	SQLSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}

/*
 * Copyright (c) 2018. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.sql;

import com.visionet.jungler.domain.Page;
import com.visionet.jungler.entity.BaseEntity;
import com.visionet.jungler.exception.FrameworkException;
import com.visionet.jungler.utils.StringUtils;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

/**
 * @author TC.Ubuntu
 * @since 2018/6/9.
 */
public abstract class AbstractSQL<T> {

	private static final String AND = ") \nAND (";
	private static final String OR = ") \nOR (";
	private static final String JOIN_ON = "ON";
	private static final String JOIN_AND = "AND";

	private static final String TABLE_PREFIX = "T_";
	private static final String SPACE = " ";
	private static final String IS_NULL = "IS NULL";
	private static final String IS_NOT_NULL = "IS NOT NULL";

	private final SQLStatement sql = new SQLStatement();

	private StringBuilder preSql = new StringBuilder();
	private int paramsIndex = 1;
	private List<Object> params = new ArrayList<>();

	private boolean refreshJoinCondition = false;

	public abstract T getSelf();

	private Page page;

	public T SELECT(String columns) {
		sql().statementType = SQLStatement.StatementType.SELECT;
		sql().select.add(columns);
		return getSelf();
	}

	/**
	 * @since 3.4.2
	 */
	public T SELECT(String... columns) {
		sql().statementType = SQLStatement.StatementType.SELECT;
		sql().select.addAll(Arrays.asList(columns));
		return getSelf();
	}

	public T SELECT(Class resultMap, String tableAlign) {
		Method[] methods = resultMap.getMethods();
		StringBuilder selectSql = new StringBuilder();
		for(Method method : methods) {
			if(method.getName().indexOf("set") >= 0) {
				String methodName = method.getName().substring(3);
				if(StringUtils.isNotBlank(tableAlign)) {
					selectSql.append(tableAlign).append(".");
				}
				selectSql.append(methodName).append(",").append(SPACE);
			}
		}
		this.SELECT(selectSql.substring(0, selectSql.length()-2).toString());
		return getSelf();
	}

	public T SELECT_DISTINCT(String columns) {
		sql().distinct = true;
		SELECT(columns);
		return getSelf();
	}

	/**
	 * @since 3.4.2
	 */
	public T SELECT_DISTINCT(String... columns) {
		sql().distinct = true;
		SELECT(columns);
		return getSelf();
	}

	public T FROM(String table , String align) {
		if (StringUtils.isBlank(align)) {
			sql().tables.add(table);
		} else {
			sql().tables.add(table + SPACE + align);
		}
		return getSelf();
	}

	public <E extends BaseEntity> T FROM(Class<E> entity, String align) {
		this.FROM(generateTableName(entity), align);
		return getSelf();
	}

	public T INNER_JOIN(String table , String align) {
		if (StringUtils.isBlank(align)) {
			sql().join.add(table);
		} else {
			sql().innerJoin.add(table + SPACE + align);
		}
		sql().lastJoinList = sql().innerJoin;
		sql().lastJoin = sql().innerJoin.get(sql().innerJoin.size() - 1);
		this.refreshJoinCondition = true;
		return getSelf();
	}

	public <E extends BaseEntity> T INNER_JOIN(Class<E> entity, String align) {
		this.INNER_JOIN(generateTableName(entity), align);
		return getSelf();
	}

	public T JOIN_WHERE(String column, SQLSymbol sqlSymbol, Object param) {
		String sql = assembleSql(column, param, sqlSymbol, sql().lastJoin, false);
		sql().lastJoinList.set(sql().lastJoinList.size() - 1, sql);
		sql().lastJoin = sql;
		return getSelf();
	}

	public T JOIN_WHERE_CONSTANT(String column, SQLSymbol sqlSymbol, String constant) {
		String sql = assembleSql(column, constant, sqlSymbol, sql().lastJoin, true);
		sql().lastJoinList.set(sql().lastJoinList.size() - 1, sql);
		sql().lastJoin = sql;
		return getSelf();
	}

	public T LEFT_OUTER_JOIN(String table , String align) {
		if (StringUtils.isBlank(align)) {
			sql().leftOuterJoin.add(table);
		} else {
			sql().leftOuterJoin.add(table + SPACE + align);
		}
		sql().lastJoin = sql().leftOuterJoin.get(sql().leftOuterJoin.size() - 1);
		sql().lastJoinList = sql().leftOuterJoin;
		this.refreshJoinCondition = true;
		return getSelf();
	}

	public <E extends BaseEntity> T LEFT_OUTER_JOIN(Class<E> entity, String align) {
		return this.LEFT_OUTER_JOIN(generateTableName(entity), align);
	}

	public T RIGHT_OUTER_JOIN(String table , String align) {
		if (StringUtils.isBlank(align)) {
			sql().rightOuterJoin.add(table);
		} else {
			sql().rightOuterJoin.add(table + SPACE + align);
		}
		sql().lastJoinList = sql().rightOuterJoin;
		sql().lastJoin = sql().rightOuterJoin.get(sql().rightOuterJoin.size() - 1);
		this.refreshJoinCondition = true;
		return getSelf();
	}

	public <E extends BaseEntity> T RIGHT_OUTER_JOIN(Class<E> entity, String align) {
		return this.RIGHT_OUTER_JOIN(generateTableName(entity), align);
	}

	public T OUTER_JOIN(String table , String align) {
		if (StringUtils.isBlank(align)) {
			sql().outerJoin.add(table);
		} else {
			sql().outerJoin.add(table + SPACE + align);
		}
		sql().lastJoinList = sql().outerJoin;
		sql().lastJoin = sql().outerJoin.get(sql().outerJoin.size() - 1);
		this.refreshJoinCondition = true;
		return getSelf();
	}

	public <E extends BaseEntity> T OUTER_JOIN(Class<E> entity, String align) {
		return this.OUTER_JOIN(generateTableName(entity), align);
	}

	private T WHERE(String conditions) {
		sql().where.add(conditions);
		sql().lastList = sql().where;
		return getSelf();
	}

	/**
	 * @param column 列名
	 * @param param 列参数
	 * @return
	 */
	public T WHERE(String column, SQLSymbol sqlSymbol, Object param) {
		return this.WHERE(assembleSql(column, param, sqlSymbol, false));
	}

	/**
	 * 用于变量非参数形式
	 * @param column 列名
	 * @param constant 条件的恒等常量
	 * @return
	 */
	public T WHERE_CONSTANT(String column, SQLSymbol sqlSymbol, String constant) {
		return this.WHERE(assembleSql(column, constant, sqlSymbol, true));
	}

	public T WHERE_IS_NULL(String column) {
		if(StringUtils.isBlank(column)) {
			throw new FrameworkException("Column name can not be null or empty!");
		}
		preSql = new StringBuilder();
		preSql.append(column).append(SPACE).append(IS_NULL);
		return this.WHERE(preSql.toString());
	}

	public T WHERE_NOT_NULL(String column) {
		if(StringUtils.isBlank(column)) {
			throw new FrameworkException("Column name can not be null or empty!");
		}
		preSql = new StringBuilder();
		preSql.append(column).append(SPACE).append(IS_NOT_NULL);
		return this.WHERE(preSql.toString());
	}

	public T OR() {
		sql().lastList.add(OR);
		return getSelf();
	}

	public T AND() {
		sql().lastList.add(AND);
		return getSelf();
	}

	public T GROUP_BY(String columns) {
		sql().groupBy.add(columns);
		return getSelf();
	}


	private T HAVING(String conditions) {
		sql().having.add(conditions);
		sql().lastList = sql().having;
		return getSelf();
	}

	/**
	 * @param column 列名
	 * @param param 列参数
	 * @return
	 */
	public T HAVING(String column, SQLSymbol sqlSymbol, Object param) {
		return this.HAVING(assembleSql(column, param, sqlSymbol, false));
	}

	/**
	 * 用于变量非参数形式
	 * @param column 列名
	 * @param constant 条件的恒等常量
	 * @return
	 */
	public T HAVING_CONSTANT(String column, SQLSymbol sqlSymbol, String constant) {
		return this.HAVING(assembleSql(column, constant, sqlSymbol, true));
	}

	public T HAVING_IS_NULL(String column) {
		if(StringUtils.isBlank(column)) {
			throw new FrameworkException("Column name can not be null or empty!");
		}
		preSql = new StringBuilder();
		preSql.append(column).append(SPACE).append(IS_NULL);
		return this.HAVING(preSql.toString());
	}

	public T HAVING_NOT_NULL(String column) {
		if(StringUtils.isBlank(column)) {
			throw new FrameworkException("Column name can not be null or empty!");
		}
		preSql = new StringBuilder();
		preSql.append(column).append(SPACE).append(IS_NOT_NULL);
		return this.HAVING(preSql.toString());
	}


	public T ORDER_BY(String columns) {
		sql().orderBy.add(columns);
		return getSelf();
	}


	private SQLStatement sql() {
		return sql;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sql().sql(sb, false);
		return sb.toString();
	}

	public String count() {
		StringBuilder sb = new StringBuilder();
		sql().sql(sb, true);
		return sb.toString();
	}

	public List<Object> getParams() {
		return this.params;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	private String generateTableName(Class entity) {
		String entityName = entity.getSimpleName();
		StringBuilder builder = new StringBuilder(entityName);
		for (int i = 1; i < builder.length() - 1; i++) {
			if (Character.isLowerCase(builder.charAt(i - 1)) && Character.isUpperCase(builder.charAt(i))
					&& Character.isLowerCase(builder.charAt(i + 1))) {
				builder.insert(i++, '_');
			}
		}
		builder.insert(0, TABLE_PREFIX);
		return builder.toString();
	}

	private String assembleSql(String column, Object param, SQLSymbol symbol, boolean isConstant) {
		if(StringUtils.isBlank(column)) {
			throw new FrameworkException("Column name can not be null or empty!");
		}
		preSql = new StringBuilder();
		if(isConstant) {
			preSql.append(column).append(symbol.getSymbol()).append(SPACE).append(param);
		} else {
			preSql.append(column).append(symbol.getSymbol()).append(SPACE).append("?").append(paramsIndex);
			params.add(param);
			paramsIndex ++;
		}
		return preSql.toString();
	}

	private String assembleSql(String column, Object param, SQLSymbol symbol, String orgJoinSql, boolean isConstant) {
		if(StringUtils.isBlank(column)) {
			throw new FrameworkException("Column name can not be null or empty!");
		}
		preSql = new StringBuilder(orgJoinSql).append(SPACE);
		if(refreshJoinCondition) {
			preSql.append(JOIN_ON).append(SPACE).append(column);
			refreshJoinCondition = false;
		} else {
			preSql.append(JOIN_AND).append(SPACE).append(column);
		}
		if(isConstant) {
			preSql.append(SPACE).append(symbol.getSymbol()).append(SPACE).append(param);
		} else {
			preSql.append(SPACE).append(symbol.getSymbol()).append(SPACE).append("?").append(paramsIndex);
			params.add(param);
			paramsIndex ++;
		}
		return preSql.toString();
	}

	private static class SafeAppendable {
		private final Appendable a;
		private boolean empty = true;

		public SafeAppendable(Appendable a) {
			super();
			this.a = a;
		}

		public SafeAppendable append(CharSequence s) {
			try {
				if (empty && s.length() > 0) {
					empty = false;
				}
				a.append(s);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return this;
		}

		public boolean isEmpty() {
			return empty;
		}

	}

	private static class SQLStatement {

		public enum StatementType {
			SELECT
		}

		StatementType statementType = StatementType.SELECT;
		List<String> select = new ArrayList<String>();
		List<String> tables = new ArrayList<String>();
		List<String> join = new ArrayList<String>();
		List<String> innerJoin = new ArrayList<String>();
		List<String> outerJoin = new ArrayList<String>();
		List<String> leftOuterJoin = new ArrayList<String>();
		List<String> rightOuterJoin = new ArrayList<String>();
		List<String> where = new ArrayList<String>();
		List<String> having = new ArrayList<String>();
		List<String> groupBy = new ArrayList<String>();
		List<String> orderBy = new ArrayList<String>();
		List<String> lastList = new ArrayList<String>();
		List<String> columns = new ArrayList<String>();

		List<String> lastJoinList = new ArrayList<>();
		String lastJoin = new String();
		boolean distinct;

		public SQLStatement() {
			// Prevent Synthetic Access
		}

		private void sqlClause(SafeAppendable builder, String keyword, List<String> parts, String open, String close,
				String conjunction) {
			if (!parts.isEmpty()) {
				if (!builder.isEmpty()) {
					builder.append("\n");
				}
				builder.append(keyword);
				builder.append(" ");
				builder.append(open);
				String last = "________";
				for (int i = 0, n = parts.size(); i < n; i++) {
					String part = parts.get(i);
					if (i > 0 && !part.equals(AND) && !part.equals(OR) && !last.equals(AND) && !last.equals(OR)) {
						builder.append(conjunction);
					}
					builder.append(part);
					last = part;
				}
				builder.append(close);
			}
		}

		private String selectSQL(SafeAppendable builder, boolean isCount) {
			if(isCount) {
				builder.append("SELECT COUNT(1) ");
			} else {
				if (distinct) {
					sqlClause(builder, "SELECT DISTINCT", select, "", "", ", ");
				} else {
					sqlClause(builder, "SELECT", select, "", "", ", ");
				}
			}

			sqlClause(builder, "FROM", tables, "", "", ", ");
			joins(builder);
			sqlClause(builder, "WHERE", where, "(", ")", " AND ");
			sqlClause(builder, "GROUP BY", groupBy, "", "", ", ");
			sqlClause(builder, "HAVING", having, "(", ")", " AND ");
			sqlClause(builder, "ORDER BY", orderBy, "", "", ", ");
			return builder.toString();
		}

		private void joins(SafeAppendable builder) {
			sqlClause(builder, "JOIN", join, "", "", "\nJOIN ");
			sqlClause(builder, "INNER JOIN", innerJoin, "", "", "\nINNER JOIN ");
			sqlClause(builder, "OUTER JOIN", outerJoin, "", "", "\nOUTER JOIN ");
			sqlClause(builder, "LEFT OUTER JOIN", leftOuterJoin, "", "", "\nLEFT OUTER JOIN ");
			sqlClause(builder, "RIGHT OUTER JOIN", rightOuterJoin, "", "", "\nRIGHT OUTER JOIN ");
		}


		public String sql(Appendable a, boolean isCount) {
			SafeAppendable builder = new SafeAppendable(a);
			if (statementType == null) {
				return null;
			}
			return selectSQL(builder, isCount);
		}
	}
}

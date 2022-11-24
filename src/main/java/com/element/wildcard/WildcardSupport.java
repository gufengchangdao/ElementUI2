package com.element.wildcard;

public interface WildcardSupport {
	/**
	 * 获取为零或一个的量词。通常'?'
	 *
	 * @return 表示前面元素为零或一个的量词
	 */
	char getZeroOrOneQuantifier();

	/**
	 * 获取表示前面有零个或多个元素的量词。通常'*'
	 *
	 * @return 表示前面元素有零个或多个的量词
	 */
	char getZeroOrMoreQuantifier();

	/**
	 * 获取表示前面有一个或多个元素的量词。通常'+'，
	 *
	 * @return 表示前面有一个或多个元素的量词
	 */
	char getOneOrMoreQuantifier();

	/**
	 * 将带有通配符的字符串转换为与{@link java.util.regex.Pattern}兼容的正则表达式。如果字符串没有通配符，将返回相同的字符串。
	 *
	 * @param s 带通配符的字符串
	 * @return 与 {@link java.util.regex.Pattern}兼容的正则表达式.
	 */
	String convert(String s);
}

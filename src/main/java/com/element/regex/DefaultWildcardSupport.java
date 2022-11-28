package com.element.regex;

/**
 * {@link WildcardSupport}的默认实现。它使用以下三个字符作为通配符。
 * <ul>
 *     <li>“？”问号表示恰好缺少一个元素。</li>
 *     <li>'*' 星号表示缺少零个或多个元素。</li>
 *     <li>'+' 加号表示至少有一个缺失的元素。</li>
 * </ul>
 */
public class DefaultWildcardSupport extends AbstractWildcardSupport {
	public char getZeroOrOneQuantifier() {
		return '?';
	}

	public char getZeroOrMoreQuantifier() {
		return '*';
	}

	public char getOneOrMoreQuantifier() {
		return '+';
	}
}

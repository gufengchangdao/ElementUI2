package com.element.wildcard;

import java.io.Serializable;

/**
 * {@link WildcardSupport}的抽象实现。它实现了 convert 方法，但将定义通配符的其他三个方法留给了子类。
 */
abstract public class AbstractWildcardSupport implements WildcardSupport, Serializable {

	public String convert(String s) {
		// 如果它没有我们支持的两个特殊字符，我们就不需要使用正则表达式。
		char zeroOrMoreQuantifier = getZeroOrMoreQuantifier();
		int posAny = zeroOrMoreQuantifier == 0 ? -1 : s.indexOf(zeroOrMoreQuantifier);
		char zeroOrOneQuantifier = getZeroOrOneQuantifier();
		int posOne = zeroOrOneQuantifier == 0 ? -1 : s.indexOf(zeroOrOneQuantifier);
		char oneOrMoreQuantifier = getOneOrMoreQuantifier();
		int posOneOrMore = oneOrMoreQuantifier == 0 ? -1 : s.indexOf(oneOrMoreQuantifier);
		//
		if (posAny == -1 && posOne == -1 && posOneOrMore == -1) {
			return s;
		}

		StringBuilder buffer = new StringBuilder();
		int length = s.length();
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);
			if (zeroOrOneQuantifier != 0 && c == zeroOrOneQuantifier) {
				buffer.append(".");
			} else if (zeroOrMoreQuantifier != 0 && c == zeroOrMoreQuantifier) {
				buffer.append(".*");
			} else if (oneOrMoreQuantifier != 0 && c == oneOrMoreQuantifier) {
				buffer.append("..*");
			} else if ("(){}[].^$\\".indexOf(c) != -1) { // 转义所有其他特殊字符
				buffer.append('\\');
				buffer.append(c);
			} else {
				buffer.append(c);
			}
		}

		return buffer.toString();
	}
}

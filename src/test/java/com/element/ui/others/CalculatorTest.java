package com.element.ui.others;

import static org.junit.Assert.*;

public class CalculatorTest {
	public static void main(String[] args) {
		Calculator calculator = new Calculator();
		calculator.input('1');
		calculator.input('0');
		calculator.input('*');
		calculator.input('2');
		calculator.input('4');
		calculator.input('=');
		System.out.println("10 * 24 = " + calculator.getDisplayText());
	}
}
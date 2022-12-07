package com.element.ui.spinner;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public class PointFormatterTest {
	public static void main(String[] args) {
		Point point = new Point(5, -5);
		JFormattedTextField.AbstractFormatter formatter = PointFormatter.getInstance();
		String value;
		try {
			value = formatter.valueToString(point);
		} catch (ParseException e) {
			value = null;
		}
		System.out.println(value);
		value = "(3, -3)";
		try {
			point = (Point) formatter.stringToValue(value);
		} catch (ParseException e) {
			point = null;
		}
		System.out.println(point);
	}
}
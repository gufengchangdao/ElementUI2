package com.element.ui.label;

import demo.DemoData;

import javax.swing.*;

public class MultilineLabelTest {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		int count = 100000;

		String s = "<HTML>" + DemoData.LONG_TEXT + "</HTML>";
		new MultilineLabel(DemoData.LONG_TEXT);
		new JLabel(s);

		for (int i = 0; i < count; i++) {
			new JLabel(DemoData.LONG_TEXT);
		}
		System.out.println("JLabel不换行：" + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			new JLabel(s);
		}
		System.out.println("JLabel使用HTML换行：" + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			new MultilineLabel(DemoData.LONG_TEXT);
		}
		System.out.println("JTextArea换行：" + (System.currentTimeMillis() - startTime));
	}
}
// JLabel不换行：280
// JLabel使用HTML换行：111854
// JTextArea换行：4863

// JLabel不换行：694
// JLabel使用HTML换行：104259
// JTextArea换行：4266
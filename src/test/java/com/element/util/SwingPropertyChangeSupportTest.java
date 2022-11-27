package com.element.util;

import demo.SwingTestUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * 测试SwingPropertyChangeSupport的调用时机
 */
public class SwingPropertyChangeSupportTest {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JPanel p = SwingTestUtil.init(new FlowLayout());

			PropertyChangeListener listener = evt -> {
				System.out.println("text属性修改为" + evt.getNewValue());
				System.out.println(Thread.currentThread().getName());
			};

			JButton b1 = new JButton("原始的PropertyChangeListener");
			b1.setActionCommand("b1");
			JButton b2 = new JButton("只在EDH中执行的PropertyChangeListener");
			b2.setActionCommand("b2");
			PropertyChangeSupport pcs1 = new PropertyChangeSupport(b1);
			pcs1.addPropertyChangeListener("text", listener);

			SwingPropertyChangeSupport pcs2 = new SwingPropertyChangeSupport(b2, true);
			pcs2.addPropertyChangeListener("text", listener);

			ActionListener l = e -> {
				JButton b = (JButton) e.getSource();
				String oldVal = b.getText();
				String newVal = ""+System.currentTimeMillis();
				b.setText(newVal);
				new Thread(() -> {
					if (e.getActionCommand().equals("b1")) pcs1.firePropertyChange("text", oldVal, newVal);
					else pcs2.firePropertyChange("text", oldVal, newVal);
				}).start();
			};

			b1.addActionListener(l);
			b2.addActionListener(l);

			p.add(b1);
			p.add(b2);

			SwingTestUtil.test();
		});
	}
}
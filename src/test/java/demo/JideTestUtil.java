package demo;

import com.element.plaf.UIDefaultsLookup;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class JideTestUtil {
	public static void printUIDefaults() {
		Enumeration<Object> e = UIManager.getDefaults().keys();
		List<String> list = new ArrayList<>();

		System.out.println("Non-string keys ---");
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			if (key instanceof String) {
				list.add((String) key);
			} else {
				System.out.println(key + " => " + UIDefaultsLookup.get(key));
			}
		}

		System.out.println();

		Collections.sort(list);
		System.out.println("String keys ---");
		for (Object key : list) {
			System.out.println(key + " => " + UIDefaultsLookup.get(key));
		}
	}
}

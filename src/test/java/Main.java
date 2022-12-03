import javax.swing.*;

public class Main {
	public static void main(String[] args) {

		UIManager.getLookAndFeel().getDefaults().forEach((key, val) -> {
			System.out.println("key = " + key);
			System.out.println("val = " + val);
		});
	}
}
import static com.element.util.UIUtil.MAC_USE_QUARTZ;

public class Main {
	public static void main(String[] args) {
		// EventQueue.invokeLater(() -> {
		// 	JPanel p = SwingTestUtil.init(new FlowLayout());
		//
		// 	p.add(new JLabel("1") {
		// 		@Override
		// 		protected void paintComponent(Graphics g) {
		// 			super.paintComponent(g);
		// 			Graphics2D g2 = (Graphics2D) g;
		// 			g2.setClip(10,10,20,20);
		// 			g2.scale(2.1,2.1);
		// 			System.out.println(g2.getTransform().getScaleX());
		// 			System.out.println(JideSwingUtilities.isIntegerScaleFactor(g2));
		// 		}
		// 	});
		//
		// 	SwingTestUtil.test();
		// });
		System.out.println(MAC_USE_QUARTZ);
	}
}
package demo;

import com.element.plaf.UIDefaultsLookup;
import com.element.ui.dialog.ButtonPanel;
import com.element.ui.dialog.StandardDialog;
import com.element.ui.label.MultilineLabel;
import com.element.ui.layout.JideBoxLayout;
import com.element.ui.others.collapse.AccordionPanel;
import com.element.ui.tabs.JideTabbedPane;
import com.element.util.SearchableUtil;
import com.element.util.UIUtil;
import com.element.util.WrapperUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

/**
 * A template to create additional demo module.
 */
abstract public class AbstractDemo implements Demo {
	static JFrame frame = new JFrame();

	public AbstractDemo() {
	}

	@Override
	public String getName() {
		return Resource.RB.getString("Demo.title");
	}

	public String getDescription() {
		return null;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public String toString() {
		return getName();
	}

	public Component getOptionsPanel() {
		return null;
	}

	public boolean isCommonOptionsPaneVisible() {
		return true;
	}

	public void dispose() {
	}

	/**
	 * 启动窗口运行示例
	 *
	 * @param demo 要演示的组件
	 * @return frame
	 */
	public static JFrame showAsFrame(final Demo demo) {
		frame.setTitle(demo.getName());
		// 关闭窗口时停止动画
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (demo instanceof AnimatedDemo) {
					((AnimatedDemo) demo).stopAnimation();
				}
				demo.dispose();
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Component demoPanel = demo.getDemoPanel();
		JComponent pane = createOptionsPanel(frame, demo, demoPanel);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(demoPanel, BorderLayout.CENTER);

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		if (pane != null) {
			JScrollPane scrollPane = new JScrollPane(pane);
			scrollPane.getVerticalScrollBar().setUnitIncrement(5);
			frame.getContentPane().add(scrollPane, BorderLayout.LINE_START);
		}

		// 使用快捷键 运行 GC 并打印可用内存
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK
						| InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK), "printMem");
		frame.getRootPane().getActionMap().put("printMem", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				UIUtil.runGCAndPrintFreeMemory();
			}
		});

		frame.pack();

		// 运行动画
		if (demo instanceof AnimatedDemo) {
			((AnimatedDemo) demo).startAnimation();
		}

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.toFront();
		return frame;
	}

	public static JFrame getFrame() {
		return frame;
	}

	/**
	 * 创建左侧操作面板。含有通用选项、详细介绍和源代码
	 *
	 * @param parentFrame 窗体frame
	 * @param demo        演示组件
	 * @param demoPanel   组件的主演示面板
	 * @return 左侧操作面板
	 */
	protected static JComponent createOptionsPanel(JFrame parentFrame, Demo demo, Component demoPanel) {
		if (demoPanel != null) {
			ArrayList<String> nameList = new ArrayList<>();
			ArrayList<Component> list = new ArrayList<>();
			demoPanel.setName("Demo.DemoPanel");
			Component optionsPanel = demo.getOptionsPanel();
			if (optionsPanel != null) {
				optionsPanel.setName("Demo.OptionsPanel");
			}

			if (optionsPanel != null) {
				nameList.add(Resource.RB.getString("Demo.options"));
				if (optionsPanel instanceof JComponent optionPanel) {
					optionPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
					UIUtil.setOpaqueRecursively(optionPanel, false);
				}
				list.add(optionsPanel);
			}

			// 通用选项
			if (demo.isCommonOptionsPaneVisible()) {
				nameList.add(Resource.RB.getString("Demo.commonOptions"));
				JComponent commonOptionsPanel = createCommonOptions(demoPanel);
				commonOptionsPanel.setName("Demo.CommonOptionsPanel");
				commonOptionsPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
				UIUtil.setOpaqueRecursively(commonOptionsPanel, false);
				list.add(commonOptionsPanel);
			}

			// 详细介绍
			String description = demo.getDescription();
			if (description != null && description.trim().length() > 0) {
				nameList.add(Resource.RB.getString("Demo.description"));
				MultilineLabel label = new MultilineLabel(description);
				label.setColumns(30);
				label.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
				list.add(label);
			}

			// 源代码
			String[] source = Arrays.stream(demo.getDemoSource())
					.map(aClass -> aClass.getName() + ".java")
					.toArray(String[]::new);//java演示文件名
			if (source.length > 0) {
				nameList.add(Resource.RB.getString("Demo.source"));
				JPanel panel = new JPanel(new BorderLayout(4, 4));
				StringBuilder sourceFiles = new StringBuilder(MessageFormat.format(Resource.RB.getString("Demo.location"), "test/java/"));
				for (String s : source) {
					sourceFiles.append("\n  - ");
					sourceFiles.append(s);
				}
				MultilineLabel label = new MultilineLabel(sourceFiles.toString());
				label.setColumns(30);
				panel.add(label);
				panel.add(WrapperUtil.createLeftPanel(AbstractDemo.createBrowseSourceCodeButton(parentFrame, demo)), BorderLayout.AFTER_LAST_LINE);
				panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
				UIUtil.setOpaqueRecursively(panel, false);
				list.add(panel);
			}

			if (list.size() >= 1) {
				// 展开第一个，但是手风琴暂时没有展开功能
			} else {
				return null;
			}

			return new AccordionPanel(list, nameList, Color.WHITE, new Color(0xC8_C8_FF));
		}
		return null;
	}

	private static JComponent createCommonOptions(final Component component) {
		JCheckBox toggleLTR = new JCheckBox(Resource.RB.getString("Demo.toggleLTR"));
		toggleLTR.setSelected(component.getComponentOrientation().isLeftToRight());
		toggleLTR.addItemListener(e -> {
			UIUtil.toggleRTLnLTR(component);
			UIUtil.invalidateRecursively(component);
		});
		toggleLTR.setMnemonic('T');

		Locale[] locales = Locale.getAvailableLocales();
		Arrays.sort(locales, (Comparator) (o1, o2) -> {
			if (o1 instanceof Locale l1 && o2 instanceof Locale l2) {
				return l1.toString().compareTo(l2.toString());
			}
			return 0;
		});
		JComboBox locale = new JComboBox(locales);
		locale.setSelectedItem(Locale.getDefault());
		locale.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED && e.getItem() instanceof Locale l) {
				UIUtil.setLocaleRecursively(component, l);
				SwingUtilities.updateComponentTreeUI(component);
			}
		});
		SearchableUtil.installSearchable(locale);

		JPanel panel = new JPanel();
		panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS, 3));
		panel.add(toggleLTR);
		JPanel localePanel = new JPanel(new BorderLayout(3, 3));
		JLabel label = new JLabel(Resource.RB.getString("Demo.changeLocale"));
		label.setDisplayedMnemonic(Resource.RB.getString("Demo.changeLocale.mnemonic").charAt(0));
		label.setLabelFor(locale);
		localePanel.add(label, BorderLayout.BEFORE_LINE_BEGINS);
		localePanel.add(locale);
		panel.add(localePanel);
		return panel;
	}

	/**
	 * 获取示例源码文件信息，返回要展示的类对象
	 */
	public Class<?>[] getDemoSource() {
		return new Class[]{getClass()};
	}

	public static JButton createBrowseSourceCodeButton(final JFrame frame, final Demo demo) {
		return new JButton(new AbstractAction(Resource.RB.getString("Demo.browseSourceCode")) {
			public void actionPerformed(ActionEvent e) {
				StandardDialog dialog = new StandardDialog(frame, Resource.RB.getString("Demo.browseSourceCode"), false) {
					@Override
					public JComponent createBannerPanel() {
						return null;
					}

					@Override
					public JComponent createContentPanel() {
						JPanel panel = new JPanel(new BorderLayout());
						panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
						panel.add(createSourceCodePanel(demo.getDemoSource()));
						panel.setPreferredSize(new Dimension(600, 500));
						return panel;
					}

					@Override
					public ButtonPanel createButtonPanel() {
						JButton closeButton = new JButton();
						closeButton.setName(CLOSE);
						closeButton.setAction(new AbstractAction(UIDefaultsLookup.getString("OptionPane.okButtonText")) {
							public void actionPerformed(ActionEvent e) {
								setDialogResult(RESULT_AFFIRMED);
								setVisible(false);
								dispose();
							}
						});

						setDefaultCancelAction(closeButton.getAction());
						return null;
					}
				};
				dialog.pack();
				dialog.setLocationRelativeTo(frame);
				dialog.setVisible(true);
			}
		});
	}

	/**
	 * 浏览源代码tab面板
	 *
	 * @param sourceCode 要展示的源代码的包名列表
	 * @return 源代码展示面板
	 */
	public static JComponent createSourceCodePanel(Class<?>[] sourceCode) {
		JideTabbedPane tabbedPane = new JideTabbedPane(JideTabbedPane.TOP, JideTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setOpaque(true);
		for (Class<?> c : sourceCode) {
			tabbedPane.addTab(c.getSimpleName(), new JScrollPane(createTextComponent(c)));
		}
		// 设置关闭按钮
		tabbedPane.setShowCloseButtonOnTab(true);
		tabbedPane.setShowCloseButtonOnSelectedTab(true);

		return tabbedPane;
	}

	/** 单个源代码面板，为了省事，这里不是编辑器打开的，没有语法高亮效果 */
	public static JComponent createTextComponent(Class<?> c) {
		JTextArea area = new JTextArea();
		area.setEditable(false);
		// 这里使用的是路径是：项目根目录\src\test\java\...
		String packageName = c.getName();
		String suffix = ".java";
		String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator
				+ packageName.replaceAll("\\.", "\\\\") + suffix;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				area.append(line + "\n");
			}
		} catch (IOException ignored) {
			area.setText(Resource.RB.getString("Demo.copySourceCode.msg"));
		}
		return area;
	}
}




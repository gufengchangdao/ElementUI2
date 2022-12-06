package com.element.radiance.app;

import com.element.util.SwingTestUtil;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * 为指定目录下的SVG图片生成一个表格，供预览使用。建议添加了SVG矢量图后及时更新一下，便于直观的看到项目中可用的SVG
 */
public class SvgHtmlFactory extends JFrame {
	JXTextField dirField = new JXTextField("SVG图片所在目录");
	JButton choiceButton = new JButton("选择");
	JFileChooser fc = new JFileChooser();
	JButton createButton = new JButton("生成");

	public SvgHtmlFactory() {
		setTitle("Svg预览生成器");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container p = getContentPane();
		p.setLayout(new MigLayout("debug, wrap 1", "grow"));

		initComponents();

		p.add(dirField, "growx, split 2");
		p.add(choiceButton);
		p.add(createButton, "center");

		pack();
		setLocationRelativeTo(null);
	}

	private void initComponents() {
		dirField.setColumns(30);

		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));

		choiceButton.addActionListener(e -> {
			int choice = fc.showOpenDialog(this);
			if (choice == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				dirField.setText(file.getAbsolutePath());
			}
		});

		createButton.addActionListener(e -> {
			File dir = new File(dirField.getText());

			if (!dir.isDirectory()) {
				JOptionPane.showConfirmDialog(this, "路径必须为目录", "提示", JOptionPane.DEFAULT_OPTION);
				return;
			}
			generateHTML(dir);
		});

	}

	public static void generateHTML(File dir) {
		HashMap<String, Item> java2IconMap = new HashMap<>();
		Optional.ofNullable(dir.listFiles(pathname ->
						pathname.getName().endsWith("Svg.java") || pathname.getName().endsWith(".svg")))
				.ifPresent(files -> Arrays.stream(files)
						.map(File::getName)
						.forEach(s -> {
							if (s.endsWith(".svg")) {
								// svg图标
								String svgClassName = s.substring(0, s.length() - 4);
								svgClassName = ConvertWork.toCamelCase(svgClassName) + "Svg.java";
								java2IconMap.put(svgClassName, new Item(s));
							} else {
								// java代码
								Item item = java2IconMap.get(s);
								if (item != null) {
									item.existed = true;
								}
							}
						}));

		File target = new File(dir.getName() + ".html");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(target))) {
			StringBuilder b = new StringBuilder();
			b.append("""
							<!doctype html>
							<html lang="en">
							<head>
							    <meta charset="UTF-8">
							    <title>图标列表</title>
							    <style>
							        body {font-family: "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;background: #E6EAE9;}
							        .mytable {margin: 10px;border-spacing: 0;border-collapse: collapse;}
							        tr {background: #fff;color: #4f6b72;}
							        th {font: bold 18px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;color: #fff;
							            border-right: 1px solid #C1DAD7;border-bottom: 1px solid #C1DAD7;border-top: 1px solid #C1DAD7;
							            letter-spacing: 2px;text-align: left;padding: 6px 6px 6px 12px;background: #0098a2 no-repeat;}
							        td {border-right: 1px solid #C1DAD7;border-bottom: 1px solid #C1DAD7;font-size: 16px;padding: 6px 6px 6px 12px;}
							        .tr1 {background-color: #269fa7;color: white;}
							        .tr2 {background-color: white;}
							    </style>
							</head>
							""")
					.append("<body>\n");

			b.append("<p><b>").append(dir.getAbsolutePath()).append(" 目录下</b></p>\n");
			b.append("<table class=\"mytable\">\n");
			b.append("<tr>\n");
			b.append("<th>Svg Filename</th>\n");
			b.append("<th>Image</th>\n");
			b.append("<th>Class Name</th>\n");
			b.append("</tr>\n");
			for (Map.Entry<String, Item> entry : java2IconMap.entrySet()) {
				String classFilename = entry.getValue().existed ? entry.getKey() : "没有找到对应的java文件";
				String svgFilename = entry.getValue().svgName;

				b.append("<tr>\n");
				b.append("<td>").append(svgFilename).append("</td>\n");
				b.append("<td><img src=\"").append(dir.getName()).append("/").append(svgFilename).append("\"></td>\n");
				b.append("<td>").append(classFilename).append("</td>\n");
				b.append("</tr>\n");
			}
			b.append("</table><br>\n");

			b.append("""
					<script>
					    let aTr = document.getElementsByTagName("tr");
					    for (let i = 1; i < aTr.length; i++) {
					        aTr[i].onmouseover = function () {
					            aTr[i].className = "tr1";
					        }
					        aTr[i].onmouseout = function () {
					            aTr[i].className = "tr2";
					        }
					    }
					</script>
					""");
			b.append("</body>\n</html>");
			bw.write(b.toString());
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		System.out.println("文件生成在 \"" + target.getAbsolutePath() +
				"\". 请复制到与 " + dir.getAbsolutePath() + " 同2级目录下");
	}

	private static class Item {
		String svgName; //svg文件名
		boolean existed; //Java代码是否存在

		public Item(String svgName) {
			this.svgName = svgName;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Item item = (Item) o;
			return svgName.equals(item.svgName);
		}

		@Override
		public int hashCode() {
			return Objects.hash(svgName);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			new SvgHtmlFactory().setVisible(true);
		});
	}
}

package lab.component.table;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 表格单元格中绘制进度条
 */
public class StringPaintedCellProgressBar extends JPanel {
	private final String[] columnNames = {"No.", "Name", "Progress", ""};
	private final DefaultTableModel model = new DefaultTableModel(null, columnNames);
	private final JTable table = new JTable(model) {
		@Override
		public void updateUI() {
			super.updateUI();
			removeColumn(getColumnModel().getColumn(3));
			JProgressBar progress = new JProgressBar();
			TableCellRenderer r = new DefaultTableCellRenderer();
			TableColumn tc = getColumnModel().getColumn(2);
			tc.setCellRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
				String msg;
				if (value instanceof ProgressValue pv) {
					Integer current = pv.progress();
					Integer lengthOfTask = pv.lengthOfTask();
					if (current < 0) {
						msg = "Canceled";
					} else if (current < lengthOfTask) {
						// progress.setValue(0);
						// progress.setMaximum(lengthOfTask);
						// progress.setEnabled(true);
						progress.setValue(current * 100 / lengthOfTask);
						progress.setStringPainted(true);
						progress.setString(String.format("%d/%d", current, lengthOfTask));
						return progress;
					} else {
						msg = "Done";
					}
				} else {
					msg = "Waiting...";
				}
				return r.getTableCellRendererComponent(tbl, msg, isSelected, hasFocus, row, column);
			});
		}
	};
	private final Set<Integer> deletedRowSet = new TreeSet<>();
	private int number;
	private final Random rnd = new Random();

	private StringPaintedCellProgressBar() {
		super(new BorderLayout());
		table.setRowSorter(new TableRowSorter<>(model));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.WHITE);
		table.setComponentPopupMenu(new TablePopupMenu());
		table.setFillsViewportHeight(true);
		table.setIntercellSpacing(new Dimension());
		table.setShowGrid(false);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		TableColumn column = table.getColumnModel().getColumn(0);
		column.setMaxWidth(60);
		column.setMinWidth(60);
		column.setResizable(false);

		JButton button = new JButton("add");
		button.addActionListener(e -> addActionPerformed());
		add(button, BorderLayout.SOUTH);
		add(scrollPane);
		setPreferredSize(new Dimension(320, 240));
	}

	public void addProgressValue(String name, ProgressValue value, SwingWorker<?, ?> worker) {
		Object[] obj = {number, name, value, worker};
		model.addRow(obj);
		number++;
	}

	public void addActionPerformed() {
		int key = model.getRowCount();
		int lengthOfTask = rnd.nextInt(100) + 100;
		SwingWorker<Integer, ProgressValue> worker = new BackgroundTask(lengthOfTask) {
			@Override
			protected void process(List<ProgressValue> c) {
				if (isCancelled()) {
					return;
				}
				if (!isDisplayable()) {
					// System.out.println("process: DISPOSE_ON_CLOSE");
					cancel(true);
					// executor.shutdown();
					return;
				}
				c.forEach(v -> model.setValueAt(v, key, 2));
			}

			@Override
			protected void done() {
				// if (!isDisplayable()) {
				//   System.out.println("done: DISPOSE_ON_CLOSE");
				//   cancel(true);
				//   // executor.shutdown();
				//   return;
				// }
				String text;
				int i = -1;
				if (isCancelled()) {
					text = "Cancelled";
				} else {
					try {
						i = get();
						text = i >= 0 ? "Done" : "Disposed";
					} catch (InterruptedException | ExecutionException ex) {
						text = ex.getMessage();
						Thread.currentThread().interrupt();
					}
				}
				System.out.format("%s:%s(%dms)%n", key, text, i);
			}
		};
		ProgressValue pv = new ProgressValue(lengthOfTask, 0);
		addProgressValue("example(max: " + lengthOfTask + ")", pv, worker);
		// executor.execute(worker);
		worker.execute();
	}

	private class TablePopupMenu extends JPopupMenu {
		private final JMenuItem cancelMenuItem;
		private final JMenuItem deleteMenuItem;

		protected TablePopupMenu() {
			super();
			add("add").addActionListener(e -> addActionPerformed());
			addSeparator();
			cancelMenuItem = add("cancel");
			cancelMenuItem.addActionListener(e -> cancelActionPerformed());
			deleteMenuItem = add("delete");
			deleteMenuItem.addActionListener(e -> deleteActionPerformed());
		}

		@Override
		public void show(Component c, int x, int y) {
			if (c instanceof JTable) {
				boolean flag = ((JTable) c).getSelectedRowCount() > 0;
				cancelMenuItem.setEnabled(flag);
				deleteMenuItem.setEnabled(flag);
				super.show(c, x, y);
			}
		}

		private SwingWorker<?, ?> getSwingWorker(int identifier) {
			return (SwingWorker<?, ?>) model.getValueAt(identifier, 3);
		}

		private void deleteActionPerformed() {
			int[] selection = table.getSelectedRows();
			if (selection.length == 0) {
				return;
			}
			for (int i : selection) {
				int mi = table.convertRowIndexToModel(i);
				deletedRowSet.add(mi);
				SwingWorker<?, ?> worker = getSwingWorker(mi);
				if (Objects.nonNull(worker) && !worker.isDone()) {
					worker.cancel(true);
				}
				// worker = null;
			}
			RowSorter<? extends TableModel> sorter = table.getRowSorter();
			if (sorter instanceof TableRowSorter) {
				RowFilter<TableModel, Integer> filter = new RowFilter<>() {
					@Override
					public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
						return !deletedRowSet.contains(entry.getIdentifier());
					}
				};
				((TableRowSorter<? extends TableModel>) sorter).setRowFilter(filter);
			}
			table.clearSelection();
			table.repaint();
		}

		private void cancelActionPerformed() {
			int[] selection = table.getSelectedRows();
			for (int i : selection) {
				int mi = table.convertRowIndexToModel(i);
				SwingWorker<?, ?> worker = getSwingWorker(mi);
				if (Objects.nonNull(worker) && !worker.isDone()) {
					worker.cancel(true);
				}
				// worker = null;
			}
			table.repaint();
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(StringPaintedCellProgressBar::createAndShowGui);
	}

	private static void createAndShowGui() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
		         UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
			Toolkit.getDefaultToolkit().beep();
		}
		JFrame frame = new JFrame("@title@");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(new StringPaintedCellProgressBar());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

class BackgroundTask extends SwingWorker<Integer, ProgressValue> {
	private final int lengthOfTask;
	private final Random rnd = new Random();

	protected BackgroundTask(int lengthOfTask) {
		super();
		this.lengthOfTask = lengthOfTask;
	}

	@Override
	protected Integer doInBackground() throws InterruptedException {
		int current = 0;
		int total = 0;
		while (current <= lengthOfTask && !isCancelled()) {
			total += doSomething(current);
			current++;
		}
		return total;
	}

	protected int doSomething(int current) throws InterruptedException {
		publish(new ProgressValue(lengthOfTask, current));
		int iv = rnd.nextInt(50) + 1;
		Thread.sleep(iv);
		return iv;
	}
}

record ProgressValue(Integer lengthOfTask, Integer progress) {


}

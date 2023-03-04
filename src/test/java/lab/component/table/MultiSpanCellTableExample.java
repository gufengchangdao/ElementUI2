package lab.component.table;
// Example from http://www.crionics.com/products/opensource/faq/swing_ex/SwingExamples.html
// 我修正了两个代码问题

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Enumeration;
import java.util.Vector;


/**
 * 表格合并单元格
 *
 * @version 1.0 11/26/98
 */
public class MultiSpanCellTableExample extends JFrame {
	MultiSpanCellTableExample() {
		super("Multi-Span Cell Example");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		AttributiveCellTableModel ml = new AttributiveCellTableModel(10, 6);
		// AttributiveCellTableModel ml = new AttributiveCellTableModel(10, 6) {
		// 	public Object getValueAt(int row, int col) {
		// 		return "" + row + "," + col;
		// 	}
		// };
		final CellSpan cellAtt = (CellSpan) ml.getCellAttribute();
		final MultiSpanCellTable table = new MultiSpanCellTable(ml);
		JScrollPane scroll = new JScrollPane(table);

		JButton b_one = new JButton("Combine");
		b_one.addActionListener(e -> {
			int[] columns = table.getSelectedColumns();
			int[] rows = table.getSelectedRows();
			cellAtt.combine(rows, columns);
			table.clearSelection();
			table.revalidate();
			table.repaint();
		});
		JButton b_split = new JButton("Split");
		b_split.addActionListener(e -> {
			int column = table.getSelectedColumn();
			int row = table.getSelectedRow();
			cellAtt.split(row, column);
			table.clearSelection();
			table.revalidate();
			table.repaint();
		});
		JPanel p_buttons = new JPanel();
		p_buttons.setLayout(new GridLayout(2, 1));
		p_buttons.add(b_one);
		p_buttons.add(b_split);

		Box box = new Box(BoxLayout.X_AXIS);
		box.add(scroll);
		box.add(new JSeparator(SwingConstants.HORIZONTAL));
		box.add(p_buttons);
		getContentPane().add(box);
		setSize(400, 200);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		new MultiSpanCellTableExample().setVisible(true);
	}
}

/**
 * @version 1.0 11/22/98
 */
@SuppressWarnings({"rawtypes", "unchecked"})
class AttributiveCellTableModel extends DefaultTableModel {
	protected CellAttribute cellAtt;

	public AttributiveCellTableModel() {
		this(new Vector(), 0);
	}

	public AttributiveCellTableModel(int numRows, int numColumns) {
		Vector names = new Vector(numColumns);
		names.setSize(numColumns); //这一步不能少，充当列数
		setColumnIdentifiers(names);
		dataVector = new Vector<>();
		setNumRows(numRows);
		cellAtt = new DefaultCellAttribute(numRows, numColumns);
	}

	public AttributiveCellTableModel(Vector columnNames, int numRows) {
		this.columnIdentifiers = columnNames;
		dataVector = new Vector<>();
		setNumRows(numRows);
		cellAtt = new DefaultCellAttribute(numRows, columnNames.size());
	}

	public AttributiveCellTableModel(Object[] columnNames, int numRows) {
		this(convertToVector(columnNames), numRows);
	}

	public AttributiveCellTableModel(Vector data, Vector columnNames) {
		setDataVector(data, columnNames);
	}

	public AttributiveCellTableModel(Object[][] data, Object[] columnNames) {
		setDataVector(data, columnNames);
	}

	public void setDataVector(Vector newData, Vector columnNames) {
		if (newData == null)
			throw new IllegalArgumentException("setDataVector() - Null parameter");

		this.columnIdentifiers = columnNames;
		dataVector = newData;

		cellAtt = new DefaultCellAttribute(dataVector.size(), columnIdentifiers.size());

		newRowsAdded(new TableModelEvent(this, 0, getRowCount() - 1,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}

	public void addColumn(Object columnName, Vector columnData) {
		if (columnName == null)
			throw new IllegalArgumentException("addColumn() - null parameter");
		columnIdentifiers.addElement(columnName);
		int index = 0;
		Enumeration eeration = dataVector.elements();
		while (eeration.hasMoreElements()) {
			Object value;
			if ((columnData != null) && (index < columnData.size()))
				value = columnData.get(index);
			else
				value = null;
			((Vector) eeration.nextElement()).addElement(value);
			index++;
		}

		cellAtt.addColumn();

		fireTableStructureChanged();
	}

	public void addRow(Vector rowData) {
		Vector newData = null;
		if (rowData == null) {
			newData = new Vector(getColumnCount());
		} else {
			rowData.setSize(getColumnCount());
		}
		dataVector.addElement(newData);

		cellAtt.addRow();

		newRowsAdded(new TableModelEvent(this, getRowCount() - 1, getRowCount() - 1,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}

	public void insertRow(int row, Vector rowData) {
		if (rowData == null) {
			rowData = new Vector(getColumnCount());
		} else {
			rowData.setSize(getColumnCount());
		}

		dataVector.insertElementAt(rowData, row);

		cellAtt.insertRow(row);

		newRowsAdded(new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}

	public CellAttribute getCellAttribute() {
		return cellAtt;
	}

	public void setCellAttribute(CellAttribute newCellAtt) {
		int numColumns = getColumnCount();
		int numRows = getRowCount();
		if ((newCellAtt.getSize().width != numColumns) || (newCellAtt.getSize().height != numRows)) {
			newCellAtt.setSize(new Dimension(numRows, numColumns));
		}
		cellAtt = newCellAtt;
		fireTableDataChanged();
	}
}

/**
 * 单元格属性，维护合并数据，还有每个单元格的字体、字体色、背景色
 *
 * @version 1.0 11/22/98
 */
class DefaultCellAttribute implements CellAttribute, CellSpan, CellColor, CellFont {
	//
	// !!!! CAUTION !!!!!
	// these values must be synchronized to Table data
	//
	protected int rowSize;
	protected int columnSize;
	// 记录单元格合并的数据
	// 没有合并的单元格默认为[1,1]，合并的单元格从左上角到右下角rowSpan和colSpan都递减，左上角是[0,0]
	// [row][col][rowSpan,colSpan]
	protected int[][][] span;
	protected Color[][] foreground;             // ColoredCell
	protected Color[][] background;             //
	protected Font[][] font;                   // CellFont

	public DefaultCellAttribute() {
		this(1, 1);
	}

	public DefaultCellAttribute(int numRows, int numColumns) {
		setSize(new Dimension(numColumns, numRows));
	}

	protected void initValue() {
		for (int i = 0; i < span.length; i++) {
			for (int j = 0; j < span[i].length; j++) {
				span[i][j][CellSpan.COLUMN] = 1;
				span[i][j][CellSpan.ROW] = 1;
			}
		}
	}


	//
	// CellSpan
	//
	@Override
	public int[] getSpan(int row, int column) {
		if (isOutOfBounds(row, column)) {
			return new int[]{1, 1};
		}
		return span[row][column];
	}

	@Override
	public void setSpan(int[] span, int row, int column) {
		if (isOutOfBounds(row, column)) return;
		this.span[row][column] = span;
	}

	@Override
	public boolean isVisible(int row, int column) {
		if (isOutOfBounds(row, column)) return false;
		return (span[row][column][CellSpan.COLUMN] >= 1) && (span[row][column][CellSpan.ROW] >= 1);
	}

	/**
	 * 合并单元格，如果该区域存在已经合并的单元格，则不进行任何操作。实际上就是维护 span 数组
	 *
	 * @param rows    需要合并的单元格行区间
	 * @param columns 需要合并的单元格列区间
	 */
	@Override
	public void combine(int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns)) return;
		int rowSpan = rows.length;
		int columnSpan = columns.length;
		int startRow = rows[0];
		int startColumn = columns[0];
		// 判断该区域是否有已经合并的单元格
		for (int i = 0; i < rowSpan; i++) {
			for (int j = 0; j < columnSpan; j++) {
				if ((span[startRow + i][startColumn + j][CellSpan.COLUMN] != 1)
						|| (span[startRow + i][startColumn + j][CellSpan.ROW] != 1)) {
					// 存在已经合并的单元格，这里认为不能合并，不做处理
					return;
				}
			}
		}
		// 被合并的部分随着x、y的增加，值递减，最左上角为[0,0]，右下角是[- (rowSpan -1),- (columnSpan -1)]
		for (int i = 0, ii = 0; i < rowSpan; i++, ii--) {
			for (int j = 0, jj = 0; j < columnSpan; j++, jj--) {
				span[startRow + i][startColumn + j][CellSpan.COLUMN] = jj;
				span[startRow + i][startColumn + j][CellSpan.ROW] = ii;
			}
		}
		span[startRow][startColumn][CellSpan.COLUMN] = columnSpan;
		span[startRow][startColumn][CellSpan.ROW] = rowSpan;
	}

	/**
	 * 拆分合并的单元格，也是维护 span 数组
	 */
	@Override
	public void split(int row, int column) {
		if (isOutOfBounds(row, column)) return;
		int columnSpan = span[row][column][CellSpan.COLUMN];
		int rowSpan = span[row][column][CellSpan.ROW];
		for (int i = 0; i < rowSpan; i++) {
			for (int j = 0; j < columnSpan; j++) {
				span[row + i][column + j][CellSpan.COLUMN] = 1;
				span[row + i][column + j][CellSpan.ROW] = 1;
			}
		}
	}


	//
	// ColoredCell
	//
	@Override
	public Color getForeground(int row, int column) {
		if (isOutOfBounds(row, column)) return null;
		return foreground[row][column];
	}

	@Override
	public void setForeground(Color color, int row, int column) {
		if (isOutOfBounds(row, column)) return;
		foreground[row][column] = color;
	}

	@Override
	public void setForeground(Color color, int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns)) return;
		setValues(foreground, color, rows, columns);
	}

	@Override
	public Color getBackground(int row, int column) {
		if (isOutOfBounds(row, column)) return null;
		return background[row][column];
	}

	@Override
	public void setBackground(Color color, int row, int column) {
		if (isOutOfBounds(row, column)) return;
		background[row][column] = color;
	}

	@Override
	public void setBackground(Color color, int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns)) return;
		setValues(background, color, rows, columns);
	}

	//
	// CellFont
	//
	@Override
	public Font getFont(int row, int column) {
		if (isOutOfBounds(row, column)) return null;
		return font[row][column];
	}

	@Override
	public void setFont(Font font, int row, int column) {
		if (isOutOfBounds(row, column)) return;
		this.font[row][column] = font;
	}

	@Override
	public void setFont(Font font, int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns)) return;
		setValues(this.font, font, rows, columns);
	}

	//
	// CellAttribute
	//
	@Override
	public void addColumn() {
		int[][][] oldSpan = span;
		int numRows = oldSpan.length;
		int numColumns = oldSpan[0].length;
		span = new int[numRows][numColumns + 1][2];
		System.arraycopy(oldSpan, 0, span, 0, numRows);
		for (int i = 0; i < numRows; i++) {
			span[i][numColumns][CellSpan.COLUMN] = 1;
			span[i][numColumns][CellSpan.ROW] = 1;
		}
	}

	@Override
	public void addRow() {
		int[][][] oldSpan = span;
		int numRows = oldSpan.length;
		int numColumns = oldSpan[0].length;
		span = new int[numRows + 1][numColumns][2];
		System.arraycopy(oldSpan, 0, span, 0, numRows);
		for (int i = 0; i < numColumns; i++) {
			span[numRows][i][CellSpan.COLUMN] = 1;
			span[numRows][i][CellSpan.ROW] = 1;
		}
	}

	@Override
	public void insertRow(int row) {
		int[][][] oldSpan = span;
		int numRows = oldSpan.length;
		int numColumns = oldSpan[0].length;
		span = new int[numRows + 1][numColumns][2];
		if (0 < row) {
			System.arraycopy(oldSpan, 0, span, 0, row);
		}
		System.arraycopy(oldSpan, 0, span, row + 1, numRows - row);
		for (int i = 0; i < numColumns; i++) {
			span[row][i][CellSpan.COLUMN] = 1;
			span[row][i][CellSpan.ROW] = 1;
		}
	}

	@Override
	public Dimension getSize() {
		return new Dimension(rowSize, columnSize);
	}

	@Override
	public void setSize(Dimension size) {
		columnSize = size.width;
		rowSize = size.height;
		span = new int[rowSize][columnSize][2];   // 2: COLUMN,ROW
		foreground = new Color[rowSize][columnSize];
		background = new Color[rowSize][columnSize];
		font = new Font[rowSize][columnSize];
		initValue();
	}

	protected boolean isOutOfBounds(int row, int column) {
		return (row < 0) || (rowSize <= row) || (column < 0) || (columnSize <= column);
	}

	protected boolean isOutOfBounds(int[] rows, int[] columns) {
		for (int row : rows) {
			if ((row < 0) || (rowSize <= row)) return true;
		}
		for (int column : columns) {
			if ((column < 0) || (columnSize <= column)) return true;
		}
		return false;
	}

	protected void setValues(Object[][] target, Object value, int[] rows, int[] columns) {
		for (int row : rows) {
			for (int column : columns) {
				target[row][column] = value;
			}
		}
	}
}

/**
 * @version 1.0 11/26/98
 */
class MultiSpanCellTable extends JTable {
	public MultiSpanCellTable(TableModel model) {
		super(model);
		setUI(new MultiSpanCellTableUI());
		getTableHeader().setReorderingAllowed(false);
		setCellSelectionEnabled(true);
		setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	}

	/** 根据位置获取单元格大小 */
	public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
		Rectangle sRect = super.getCellRect(row, column, includeSpacing);
		if ((row < 0) || (column < 0) ||
				(getRowCount() <= row) || (getColumnCount() <= column)) {
			return sRect;
		}

		// 被合并后单元格部分，计算合并的单元格个数
		CellSpan cellAtt = (CellSpan) ((AttributiveCellTableModel) getModel()).getCellAttribute();
		if (!cellAtt.isVisible(row, column)) {
			int temp_row = row;
			int temp_column = column;
			// 左上角第一个单元格位置
			row += cellAtt.getSpan(temp_row, temp_column)[CellSpan.ROW];
			column += cellAtt.getSpan(temp_row, temp_column)[CellSpan.COLUMN];
		}
		int[] n = cellAtt.getSpan(row, column);

		// 垂直方向
		int index = 0;
		int columnMargin = getColumnModel().getColumnMargin();
		Rectangle cellFrame = new Rectangle();
		int aCellHeight = rowHeight + rowMargin;
		cellFrame.y = row * aCellHeight;
		cellFrame.height = n[CellSpan.ROW] * aCellHeight;

		// 水平方向
		Enumeration<TableColumn> eeration = getColumnModel().getColumns();
		while (eeration.hasMoreElements()) {
			TableColumn aColumn = eeration.nextElement();
			// 这里减1是因为每个列的宽度都有一个额外的像素
			cellFrame.width = aColumn.getWidth() + columnMargin - 1;
			if (index == column) break;
			cellFrame.x += cellFrame.width;
			index++;
		}
		for (int i = 0; i < n[CellSpan.COLUMN] - 1; i++) {
			TableColumn aColumn = eeration.nextElement();
			cellFrame.width += aColumn.getWidth() + columnMargin;
		}

		if (!includeSpacing) {
			Dimension spacing = getIntercellSpacing();
			cellFrame.setBounds(cellFrame.x + spacing.width / 2,
					cellFrame.y + spacing.height / 2,
					cellFrame.width - spacing.width,
					cellFrame.height - spacing.height);
		}
		return cellFrame;
	}

	/** 根据像素位置获取单元格位置，[行索引，列索引] */
	private int[] rowColumnAtPoint(Point point) {
		int[] retValue = {-1, -1};
		int row = point.y / (rowHeight + rowMargin);
		if ((row < 0) || (getRowCount() <= row)) return retValue;

		int column = getColumnModel().getColumnIndexAtX(point.x);
		CellSpan cellAtt = (CellSpan) ((AttributiveCellTableModel) getModel()).getCellAttribute();
		if (cellAtt.isVisible(row, column)) {
			retValue[CellSpan.ROW] = row;
			retValue[CellSpan.COLUMN] = column;
			return retValue;
		}
		retValue[CellSpan.ROW] = row + cellAtt.getSpan(row, column)[CellSpan.ROW];
		retValue[CellSpan.COLUMN] = column + cellAtt.getSpan(row, column)[CellSpan.COLUMN];
		return retValue;
	}

	public int rowAtPoint(Point point) {
		return rowColumnAtPoint(point)[CellSpan.ROW];
	}

	public int columnAtPoint(Point point) {
		return rowColumnAtPoint(point)[CellSpan.COLUMN];
	}


	public void columnSelectionChanged(ListSelectionEvent e) {
		repaint();
	}

	public void valueChanged(ListSelectionEvent e) {
		int firstIndex = e.getFirstIndex();
		int lastIndex = e.getLastIndex();
		if (firstIndex == -1 && lastIndex == -1) { // Selection cleared.
			repaint();
		}
		Rectangle dirtyRegion = getCellRect(firstIndex, 0, false);
		int numCoumns = getColumnCount();
		int index = firstIndex;
		// firstIndex所在行
		for (int i = 0; i < numCoumns; i++) {
			dirtyRegion.add(getCellRect(index, i, false));
		}
		index = lastIndex;
		// lastIndex所在行
		for (int i = 0; i < numCoumns; i++) {
			dirtyRegion.add(getCellRect(index, i, false));
		}
		repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
	}

}

/**
 * @version 1.0 11/26/98
 */
class MultiSpanCellTableUI extends BasicTableUI {
	public void paint(Graphics g, JComponent c) {
		Rectangle oldClipBounds = g.getClipBounds();
		Rectangle clipBounds = new Rectangle(oldClipBounds);
		int tableWidth = table.getColumnModel().getTotalColumnWidth();
		clipBounds.width = Math.min(clipBounds.width, tableWidth);
		g.setClip(clipBounds);

		int firstIndex = table.rowAtPoint(new Point(0, clipBounds.y));
		int lastIndex = table.getRowCount() - 1;

		Rectangle rowRect = new Rectangle(0, 0,
				tableWidth, table.getRowHeight() + table.getRowMargin());
		rowRect.y = firstIndex * rowRect.height;

		for (int index = firstIndex; index <= lastIndex; index++) {
			if (rowRect.intersects(clipBounds)) {
				// 该行在重绘范围内
				paintRow(g, index);
			}
			rowRect.y += rowRect.height;
		}
		g.setClip(oldClipBounds);
	}

	// 重绘指定行，并且只重绘画笔g的裁剪范围内的单元格
	private void paintRow(Graphics g, int row) {
		Rectangle rect = g.getClipBounds();
		boolean drawn = false;

		AttributiveCellTableModel tableModel = (AttributiveCellTableModel) table.getModel();
		CellSpan cellAtt = (CellSpan) tableModel.getCellAttribute();
		int numColumns = table.getColumnCount();

		for (int column = 0; column < numColumns; column++) {
			Rectangle cellRect = table.getCellRect(row, column, true);
			int cellRow, cellColumn;
			if (cellAtt.isVisible(row, column)) {
				// 没有合并的单元格
				cellRow = row;
				cellColumn = column;
			} else {
				// 合并单元格的左上角位置
				cellRow = row + cellAtt.getSpan(row, column)[CellSpan.ROW];
				cellColumn = column + cellAtt.getSpan(row, column)[CellSpan.COLUMN];
			}
			if (cellRect.intersects(rect)) {
				drawn = true;
				paintCell(g, cellRect, cellRow, cellColumn);
			} else {
				if (drawn) break;
			}
		}
	}

	/**
	 * 重绘单元格
	 *
	 * @param g        画笔
	 * @param cellRect 单元格区域，包括合并的单元格
	 * @param row      单元格左上角行索引
	 * @param column   单元格左上角列索引
	 */
	private void paintCell(Graphics g, Rectangle cellRect, int row, int column) {
		int spacingHeight = table.getRowMargin();
		int spacingWidth = table.getColumnModel().getColumnMargin();

		// 绘制单元格的边框
		Color c = g.getColor();
		g.setColor(table.getGridColor());
		g.drawRect(cellRect.x, cellRect.y, cellRect.width - 1, cellRect.height - 1);
		g.setColor(c);

		cellRect.setBounds(cellRect.x + spacingWidth / 2, cellRect.y + spacingHeight / 2,
				cellRect.width - spacingWidth, cellRect.height - spacingHeight);

		if (table.isEditing() && table.getEditingRow() == row && table.getEditingColumn() == column) {
			// 显示编辑组件
			Component component = table.getEditorComponent();
			component.setBounds(cellRect);
			component.validate();
		} else {
			// 绘制渲染组件
			TableCellRenderer renderer = table.getCellRenderer(row, column);
			Component component = table.prepareRenderer(renderer, row, column);

			if (component.getParent() == null) {
				rendererPane.add(component);
			}
			rendererPane.paintComponent(g, component, table, cellRect.x, cellRect.y,
					cellRect.width, cellRect.height, true);
		}
	}
}


interface CellFont {
	Font getFont(int row, int column);

	void setFont(Font font, int row, int column);

	void setFont(Font font, int[] rows, int[] columns);
}


interface CellColor {
	Color getForeground(int row, int column);

	void setForeground(Color color, int row, int column);

	void setForeground(Color color, int[] rows, int[] columns);

	Color getBackground(int row, int column);

	void setBackground(Color color, int row, int column);

	void setBackground(Color color, int[] rows, int[] columns);
}

/**
 * @version 1.0 11/22/98
 */
interface CellAttribute {
	void addColumn();

	void addRow();

	/** 在指定行后插入一行(即行索引表示的行前插入) */
	void insertRow(int row);

	/** 行数、列数 */
	Dimension getSize();

	/** 设置新的行数和列数，同时合并数据也会初始化 */
	void setSize(Dimension size);
}

/**
 * @version 1.0 11/22/98
 */
interface CellSpan {
	int ROW = 0;
	int COLUMN = 1;

	/**
	 * 根据所给位置计算该位置所在的单元格的合并个数(table span)
	 *
	 * @return 一个二维数组，第一个数行所跨单元格个数，第二个是列所跨单元格个数
	 */
	int[] getSpan(int row, int column);

	void setSpan(int[] span, int row, int column);

	/**
	 * 是否是已经合并过的单元格的一部分
	 */
	boolean isVisible(int row, int column);

	/**
	 * 合并单元格，如果该区域存在已经合并的单元格，则不进行任何操作。
	 *
	 * @param rows    需要合并的单元格行区间
	 * @param columns 需要合并的单元格列区间
	 */
	void combine(int[] rows, int[] columns);

	/** 拆分合并的单元格 */
	void split(int row, int column);
}
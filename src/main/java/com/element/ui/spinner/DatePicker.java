package com.element.ui.spinner;

import com.element.color.ColorUtil;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.border.IconBorder;
import com.element.ui.svg.icon.regular.CalendarSvg;
import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.event.DateSelectionEvent;
import org.jdesktop.swingx.event.DateSelectionListener;
import org.jdesktop.swingx.prompt.BuddySupport;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedSet;
import java.util.function.Function;

/**
 * 日期选择器
 * <p>
 * 通过DatePicker提供的日期选择器，可以设置标记，是否可选、可遍历、天数、周字符串、颜色等
 * <p>
 * 这个类与JXDatePicker有些相像，JXDatePicker支持输入，这里类还没实现，但是比那个好看
 */
public class DatePicker extends JXTextField implements MouseListener, DateSelectionListener {
	private PopupFactory popupFactory;
	private Popup popup;
	/** 当前是否有弹出窗口 */
	private boolean isShow;
	/** 日历 */
	private JXMonthView monthView;
	/** 内部Date转字符串使用，不能让外部调用 */
	private Calendar calendar = Calendar.getInstance();
	private Border oldBorder;
	/** 从选择的Calendar到输入框内容的格式化器，如果不提供将使用默认的 */
	private Function<Calendar, String> formatter;

	public DatePicker() {
		init();
	}

	public DatePicker(String promptText) {
		super(promptText);
		init();
	}

	public DatePicker(String promptText, Color promptForeground) {
		super(promptText, promptForeground);
		init();
	}

	public DatePicker(String promptText, Color promptForeground, Color promptBackground) {
		super(promptText, promptForeground, promptBackground);
		init();
	}

	private void init() {
		monthView = new JXMonthView();
		// 可对日期进行标记
		// Calendar cal1 = Calendar.getInstance();
		// cal1.set(2004, 1, 1);
		// Calendar cal2 = Calendar.getInstance();
		// cal2.set(2004, 1, 5);
		// monthView.setFlaggedDates(cal1.getTime(), cal2.getTime(), new Date());

		popupFactory = new PopupFactory();
		// 设置图标
		SvgIcon icon = CalendarSvg.of(16, 16);
		icon.setColorFilter(color -> ColorUtil.changeAlpha(ColorUtil.PRIMARY, .8f));
		oldBorder = getBorder();
		setIcon(icon);
		// 不可编辑，如果要编辑的话还需要一个函数来检验输入是否合法
		setEditable(false);
		// 点击输入框展开或隐藏选择器
		addMouseListener(this);

		// 选择日期事件，用哪个都行
		// monthView.addActionListener(this);
		monthView.getSelectionModel().addDateSelectionListener(this);
		// 鼠标离开选择器时选择器隐藏
		monthView.addMouseListener(new MonthViewMouseListener());

	}

	/** 始终不可编辑 */
	@Override
	public void setEditable(boolean b) {
		super.setEditable(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (isShow) hidePopup();
		else showPopup();
	}

	public void hidePopup() {
		isShow = false;
		popup.hide();
	}

	public void showPopup() {
		isShow = true;
		Point location = getLocationOnScreen();
		Dimension size = getPreferredSize();
		Insets insets = getInsets();
		popup = popupFactory.getPopup(this, monthView, location.x, location.y + size.height + insets.bottom);
		popup.show();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	/** Calendar对文本的转换器 */
	public String calendarFormatter(Calendar calendar) {
		if (formatter != null) return formatter.apply(calendar);
		return String.format("%04d-%02d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 返回对象的拷贝版本，这表示返回对象的修改不影响该对象内容
	 *
	 * @return calendar对象的拷贝版本，如果没有选择日期则为null
	 */
	public Calendar getCalendar() {
		if ("".equals(getText())) return null;
		return (Calendar) calendar.clone();
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean show) {
		isShow = show;
	}

	public JXMonthView getMonthView() {
		return monthView;
	}

	public void setMonthView(JXMonthView monthView) {
		this.monthView = monthView;
	}

	public Function<Calendar, String> getFormatter() {
		return formatter;
	}

	/** 设置转换器，来决定Calendar对象在输入框中的表现形式，有默认的转换器，即为 */
	public void setFormatter(Function<Calendar, String> formatter) {
		this.formatter = formatter;
	}

	@Override
	public void valueChanged(DateSelectionEvent e) {
		// Date date = monthView.getSelectionDate(); //在ActionListener中使用
		SortedSet<Date> selection = e.getSelection();
		if (selection.size() == 0) return;
		Date date = selection.first(); //因为是单选，first和last是一样的
		calendar.setTime(date);
		setText(calendarFormatter(calendar));
		hidePopup();
	}

	/**
	 * 用来监听鼠标离开选择器的事件，让选择器隐藏
	 * <p>
	 * 本来想做成点击其他地方时让选择器隐藏，但是发现点击其他地方（包括按钮）没法让FocusListener监听到
	 */
	public class MonthViewMouseListener extends MouseAdapter {
		@Override
		public void mouseExited(MouseEvent e) {
			hidePopup();
		}
	}

	/** 设置左侧图标 */
	public void setIcon(SvgIcon icon) {
		removeAllBuddies();
		addBuddy(new JLabel(icon), BuddySupport.Position.LEFT);
	}
}

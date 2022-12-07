package com.element.swing.base;

import com.element.plaf.LookAndFeelFactory;
import com.element.plaf.UIDefaultsLookup;
import com.element.swing.Alignable;
import com.element.swing.AlignmentSupport;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;

/**
 * 就像JideButton与JButton相比，BaseLabel与JLabel类似，只是它用在JToolBar或CommandBar上。然而，它看起来与常规JLabel没有任何不同，
 * 除非您覆盖“JideLabel.foreground”、“JideLabel.background”或“JideLabel.font”等 UIDefaults。
 * <p>
 * BaseLabel也可以用于垂直布局。如果调用{@link #setOrientation(int)}并将其设置为{@link SwingConstants#VERTICAL}，标签上的文本和
 * 图标将垂直放置。由于CommandBar支持垂直布局，这非常适合它。您还可以通过调用{@link #setClockwise(boolean)}来控制旋转方向。默认情况下，
 * 它顺时针旋转。
 */
public class BaseLabel extends JXLabel implements Alignable, AlignmentSupport {
	private static final String uiClassID = "JideLabelUI";
	public static final String PROPERTY_CLOCKWISE = "clockwise";

	private boolean _clockwise = true;
	private int _orientation;


	public BaseLabel() {
	}

	public BaseLabel(String text) {
		super(text);
	}

	public BaseLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
	}

	public BaseLabel(Icon image) {
		super(image);
	}

	public BaseLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
	}

	public BaseLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
	}

	/**
	 * Resets the UI property to a value from the current look and feel.
	 *
	 * @see JComponent#updateUI
	 */
	@Override
	public void updateUI() {
		if (UIDefaultsLookup.get(uiClassID) == null) {
			LookAndFeelFactory.installJideExtension();
		}
		setUI(UIManager.getUI(this));
	}


	/**
	 * Returns a string that specifies the name of the L&F class that renders this component.
	 *
	 * @return the string "ButtonUI"
	 * @see JComponent#getUIClassID
	 * @see UIDefaults#getUI
	 */
	@Override
	public String getUIClassID() {
		return uiClassID;
	}

	/**
	 * The button orientation.
	 *
	 * @return the orientation.
	 */
	public int getOrientation() {
		return _orientation;
	}

	public void setOrientation(int orientation) {
		int old = _orientation;
		if (old != orientation) {
			_orientation = orientation;
			firePropertyChange(PROPERTY_ORIENTATION, old, orientation);
		}
	}

	/**
	 * return true if it supports vertical orientation.
	 *
	 * @return true if it supports vertical orientation
	 */
	public boolean supportVerticalOrientation() {
		return true;
	}

	/**
	 * return true if it supports horizontal orientation.
	 *
	 * @return true if it supports horizontal orientation
	 */
	public boolean supportHorizontalOrientation() {
		return true;
	}

	/**
	 * Checks if the rotation is clockwise.
	 *
	 * @return true or false.
	 */
	public boolean isClockwise() {
		return _clockwise;
	}

	/**
	 * Sets the rotation direction.
	 *
	 * @param clockwise true or false.
	 */
	public void setClockwise(boolean clockwise) {
		boolean old = _clockwise;
		if (clockwise != _clockwise) {
			_clockwise = clockwise;
			firePropertyChange(PROPERTY_CLOCKWISE, old, _clockwise);
		}
	}
}

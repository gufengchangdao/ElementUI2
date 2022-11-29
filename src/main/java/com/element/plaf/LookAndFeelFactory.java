/*
 * @(#)LookAndFeelFactory.java 5/28/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf;

import com.element.plaf.basic.BasicPainter;
import com.element.plaf.basic.Painter;
import com.element.plaf.eclipse.Eclipse3xMetalUtils;
import com.element.plaf.eclipse.Eclipse3xWindowsUtils;
import com.element.plaf.eclipse.EclipseMetalUtils;
import com.element.plaf.eclipse.EclipseWindowsUtils;
import com.element.plaf.office2003.Office2003Painter;
import com.element.plaf.office2003.Office2003WindowsUtils;
import com.element.plaf.office2007.Office2007WindowsUtils;
import com.element.plaf.vsnet.VsnetMetalUtils;
import com.element.plaf.vsnet.VsnetWindowsUtils;
import com.element.plaf.xerto.XertoMetalUtils;
import com.element.plaf.xerto.XertoPainter;
import com.element.plaf.xerto.XertoWindowsUtils;
import com.element.ui.icons.IconsFactory;
import com.element.ui.tabs.JideTabbedPane;
import com.element.util.SystemInfo;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * JIDE Software 创建了许多新组件，它们需要自己的 ComponentUI 类和 UIDefaults 表中的其他 UIDefaults。 LookAndFeelFactory 可以从
 * 任何现有外观中获取 UIDefaults，并添加 JIDE 组件所需的额外 UIDefaults。
 * <p>
 * 在使用任何 JIDE 组件之前，请让您调用两个 LookAndFeelFactory.installJideExtension(...) 方法之一。基本上，您首先像以前一样使用
 * UIManager 设置 L&F，然后调用 installJideExtension。有关示例，请参见下面的代码。
 * <code><pre>
 * // 安装外观...
 * LookAndFeelFactory.installJideExtension();
 * </pre></code>
 * <p>
 * {@link #installJideExtension()} 方法将检查您设置的 L&F 类型以及您使用的操作系统，并决定它将安装哪种类型的 JIDE 扩展。这是规则。
 * 操作系统：Windows Vista 或 Windows 7，L&F：Windows L&F => OFFICE2007_STYLE
 * 操作系统：带有 XP 主题的 Windows XP，L&F：Windows L&F => OFFICE2003_STYLE
 * 操作系统：任何 Windows，L&F：Windows L&F => VSNET_STYLE
 * 操作系统：Linux，L&F：任何基于 Metal L&F 的 L&F => VSNET_STYLE
 * 操作系统：Mac OS X，L&F：Aqua L&F => AQUA_STYLE
 * 操作系统：任何操作系统，L&F：Quaqua L&F => AQUA_STYLE
 * 否则=> VSNET_STYLE
 * 还有另一个 {@link #installJideExtension(int)}，它采用 int 样式参数。 你可以传入
 * <ul>
 *     <li>{@link LookAndFeelFactory#EXTENSION_STYLE_VSNET}</li>
 *     <li>{@link LookAndFeelFactory#EXTENSION_STYLE_ECLIPSE}</li>
 *     <li>{@link LookAndFeelFactory#EXTENSION_STYLE_ECLIPSE3X}</li>
 *     <li>{@link LookAndFeelFactory#EXTENSION_STYLE_OFFICE2003}</li>
 *     <li>{@link LookAndFeelFactory#EXTENSION_STYLE_OFFICE2007}</li>
 *     <li>{@link LookAndFeelFactory#EXTENSION_STYLE_XERTO}</li>
 *     <li>{@link LookAndFeelFactory#EXTENSION_STYLE_ECLIPSE_WITHOUT_MENU}</li>
 *     <li>{@link LookAndFeelFactory#EXTENSION_STYLE_ECLIPSE3X_WITHOUT_MENU}</li>
 * </ul>
 * <p>
 * 换句话说，您将选择样式，而不是让 LookAndFeelFactory 为您决定样式。请注意，没有为 AQUA_STYLE 定义常量。使用它的唯一方法是当您使用
 * Aqua L&F 或 Quaqua L&F 并调用 {@link #installJideExtension()} 方法时，该方法没有参数。
 * <p>
 * 另一种方法是调用{@link #installDefaultLookAndFeelAndExtension()} ，这将根据操作系统设置默认 L&F 并安装 JIDE 扩展。
 * <p>
 * LookAndFeelFactory 支持许多已知的 L&F。有些是 JDK 中的 L&F，例如 Metal、Windows、Aqua（仅在 Apple JDK 上）、GTK。我们还支持一
 * 些第 3 方 L&F，例如 Plastic XP 或 Plastic 3D、Tonic、A03、Synthetica 等。
 * <p>
 * 如果您使用我们未正式支持的第 3 方 L&F，您可能需要对其进行自定义。您可以使用以下两个类
 * <ul>
 *     <li>{@link LookAndFeelFactory.UIDefaultsInitializer}</li>
 *     <li>{@link LookAndFeelFactory.UIDefaultsCustomizer}</li>
 * </ul>
 * <p>
 * 让我们从 UIDefaultsCustomizer 开始。无论您尝试使用什么未知 L&F，LookAndFeelFactory 的 installJideExtension() 都会尝试安
 * 装 JIDE 组件所需的 UIDefaults。希望 JIDE 能毫无例外地在您的 L&F 上运行。但最有可能的是，它看起来不会很好。这就是为什么您需要
 * {@link LookAndFeelFactory.UIDefaultsCustomizer}来自定义 UIDefaults。
 * <p>
 * 您很可能不需要使用{@link LookAndFeelFactory.UIDefaultsInitializer} 。唯一的例外是 Synth L&F 和任何基于它的 L&F。原因是我们根据
 * 现有的众所周知的 UIDefaults 计算我们将在 JIDE 组件中使用的所有颜色。例如，我们将使用 UIManagerLookup.getColor("activeCaption")
 * 来计算我们可以在可停靠框架的标题窗格中使用的颜色。我们将使用 UIManagerLookup.getColor("control") 来计算我们可以用作 JIDE 组件背景
 * 的颜色。大多数 L&F 将填充这些 UIDefaults。但是在 Synth L&F 中，这些 UIDefaults 可能有也可能没有有效值。当您稍后调用
 * installJideExtension 时，您将在代码中以 NPE 结束。在这种情况下，您可以在 UIDefaultsInitializer 中添加那些额外的 UIDefaults。我
 * 们将在调用 installJideExtension 之前调用它，以便那些 UIDefaults 准备好供我们使用。这就是添加对 GTK L&F 和 Synthetica L&F 的支持的方式。
 * <p>
 * 创建自己的 UIDefaultsCustomizer 或 Initializer 后，您可以调用 {@link #addUIDefaultsCustomizer(UIDefaultsCustomizer)} 或
 * {@link #addUIDefaultsInitializer(UIDefaultsInitializer)} ，这将使定制器或初始化器一直被触发。如果您只想将其用于某个 L&F，则应
 * 使用{@link #registerDefaultCustomizer(String, String)}或{@link #registerDefaultInitializer(String, String)} 。
 *
 * <p>
 * 默认情况下，我们还在内部使用 UIDefaultsCustomizer 和 UIDefaultsInitializer 来提供对非标准 L&F 的支持。但是，我们查
 * 看"com.element.plaf"包下的类以获取默认定制器和初始化器。例如，对于 PlasticXPLookAndFeel，对应的定制器
 * 是“com.element.plaf.plasticxp.PlasticXPCustomizer”。我们基本上采用 L&F 名称"PlasticXP"，将其附加在"com.element.plaf"之后，
 * 使用小写字母获取包名，采用 L&F 名称，附加“Customizer”以获得类名。我们将查看 PlasticXPLookAndFeel 的超类，即 PlasticLookAndFeel。
 * PlasticLookAndFeel对应的customizer是"com.element.plaf.plastic.PlasticCustomizer"。这个搜索过程一直持续到我们找到 L&F 的所有
 * 超类。然后我们从它最顶层的超类开始，如果有的话，一个接一个地调用定制器。 src-plaf.jar 或 src-plaf-jdk7.jar 包含其中一些定制器。您可以
 * 使用此命名模式来创建定制器，这样您就不需要显式注册它们。
 * <p>
 * {@link #installJideExtension()}方法只会将额外的 UIDefaults 添加到当前的 ClassLoader。如果您的系统中有多个类加载器，您可能应该告
 * 诉 UIManager 使用名为installJideExtension的类加载器。否则，您可能会出现一些意想不到的错误。下面是如何指定类加载器。
 * <code><pre>
 * UIManager.put("ClassLoader", currentClass.getClassLoader()); // currentClass is the class where the code is.
 * LookAndFeelFactory.installDefaultLookAndFeelAndExtension(); // or installJideExtension()
 * </pre></code>
 */
public class LookAndFeelFactory implements ProductNames {
	/**
	 * Class name of Windows L&F provided in Sun JDK.
	 */
	public static final String WINDOWS_CLASSIC_LNF = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";

	/**
	 * Class name of Metal L&F provided in Sun JDK.
	 */
	public static final String METAL_LNF = "javax.swing.plaf.metal.MetalLookAndFeel";

	/**
	 * Class name of Synth L&F provided in Sun JDK.
	 */
	public static final String SYNTH_LNF = "javax.swing.plaf.synth.SynthLookAndFeel";

	/**
	 * Class name of Aqua L&F provided in Apple Mac OS X JDK.
	 */
	public static final String AQUA_LNF = "apple.laf.AquaLookAndFeel";

	/**
	 * Class name of Aqua L&F provided in Apple Mac OS X JDK. This is the new package since Java Update 6.
	 */
	public static final String AQUA_LNF_6 = "com.apple.laf.AquaLookAndFeel";

	/**
	 * Class name of Quaqua L&F.
	 */
	public static final String QUAQUA_LNF = "ch.randelshofer.quaqua.QuaquaLookAndFeel";

	/**
	 * Class name of Alloy L&F.
	 */
	public static final String ALLOY_LNF = "com.incors.plaf.alloy.AlloyLookAndFeel";

	/**
	 * Class name of Synthetica L&F.
	 */
	public static final String SYNTHETICA_LNF = "de.javasoft.plaf.synthetica.SyntheticaLookAndFeel";

	public static final String SYNTHETICA_LNF_PREFIX = "de.javasoft.plaf.synthetica.Synthetica";

	/**
	 * Class name of Plastic3D L&F.
	 */
	public static final String PLASTIC3D_LNF = "com.jgoodies.looks.plastic.Plastic3DLookAndFeel";

	/**
	 * Class name of Plastic3D L&F after JGoodies Look 1.3 release.
	 *
	 * @deprecated replaced by PLASTIC3D_LNF
	 */
	@Deprecated
	public static final String PLASTIC3D_LNF_1_3 = "com.jgoodies.looks.plastic.Plastic3DLookAndFeel";

	/**
	 * Class name of PlasticXP L&F.
	 */
	public static final String PLASTICXP_LNF = "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";

	/**
	 * Class name of Tonic L&F.
	 */
	public static final String TONIC_LNF = "com.digitprop.tonic.TonicLookAndFeel";

	/**
	 * Class name of A03 L&F.
	 */
	public static final String A03_LNF = "a03.swing.plaf.A03LookAndFeel";

	/**
	 * Class name of Darcula L&F.
	 */
	public static final String DARCULA_LNF = "com.bulenkov.darcula.DarculaLaf";

	/**
	 * Class name of Pgs L&F.
	 */
	public static final String PGS_LNF = "com.pagosoft.plaf.PgsLookAndFeel";

	/**
	 * Class name of GTK L&F provided by Sun JDK.
	 */
	public static final String GTK_LNF = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";

	/**
	 * Class name of Motif L&F provided by Sun JDK.
	 */
	public static final String MOTIF_LNF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

	/**
	 * Class name of Bizlaf L&F provided by Centigrade.
	 */
	public static final String BIZ_LNF = "de.centigrade.bizlaf.BizLookAndFeel";

	/**
	 * The name of Nimbus L&F. We didn't create a constant for Nimbus is because the package name will be changed in
	 * JDK7 release
	 */
	public static final String NIMBUS_LNF_NAME = "NimbusLookAndFeel";

	/**
	 * The same as {@link #EXTENSION_STYLE_VSNET_WITHOUT_MENU}
	 *
	 * @see #EXTENSION_STYLE_VSNET_WITHOUT_MENU
	 */
	public static final int VSNET_STYLE_WITHOUT_MENU = 0;

	/**
	 * The same as {@link #EXTENSION_STYLE_VSNET}
	 *
	 * @see #EXTENSION_STYLE_VSNET
	 */
	public static final int VSNET_STYLE = 1;

	/**
	 * The same as {@link #EXTENSION_STYLE_ECLIPSE}
	 *
	 * @see #EXTENSION_STYLE_ECLIPSE
	 */
	public static final int ECLIPSE_STYLE = 2;

	/**
	 * The same as {@link #EXTENSION_STYLE_OFFICE2003}
	 *
	 * @see #EXTENSION_STYLE_OFFICE2003
	 */
	public static final int OFFICE2003_STYLE = 3;

	/**
	 * The same as {@link #EXTENSION_STYLE_XERTO}
	 *
	 * @see #EXTENSION_STYLE_XERTO
	 */
	public static final int XERTO_STYLE = 4;

	/**
	 * The same as {@link #EXTENSION_STYLE_XERTO_WITHOUT_MENU}
	 *
	 * @see #EXTENSION_STYLE_XERTO_WITHOUT_MENU
	 */
	public static final int XERTO_STYLE_WITHOUT_MENU = 6;

	/**
	 * The same as {@link #EXTENSION_STYLE_ECLIPSE}
	 *
	 * @see #EXTENSION_STYLE_ECLIPSE
	 */
	public static final int ECLIPSE3X_STYLE = 5;

	/**
	 * The same as {@link #EXTENSION_STYLE_OFFICE2007}
	 *
	 * @see #EXTENSION_STYLE_OFFICE2007
	 */
	public static final int OFFICE2007_STYLE = 7;

	/**
	 * A style that you can use with {@link #installJideExtension(int)} method. This style is the same as VSNET_STYLE
	 * except it doesn't have menu related UIDefaults. You can only use this style if you didn't use any component from
	 * JIDE Action Framework.
	 * <p/>
	 *
	 * @see #EXTENSION_STYLE_VSNET
	 */
	public static final int EXTENSION_STYLE_VSNET_WITHOUT_MENU = 0;

	/**
	 * A style that you can use with {@link #installJideExtension(int)} method. This style mimics the visual style of
	 * Microsoft Visual Studio .NET for the toolbars, menus and dockable windows.
	 * <p/>
	 * Vsnet style is a very simple style with no gradient. Although it works on almost all L&Fs in any operating
	 * systems, it looks the best on Windows 2000 or 98, or on Windows XP when XP theme is not on. If XP theme is on, we
	 * suggest you use Office2003 style or Xerto style. Since the style is so simple, it works with a lot of the 3rd
	 * party L&F such as Tonic, Pgs, Alloy etc without causing too much noise. That's why this is also the default style
	 * for any L&Fs we don't recognize when you call {@link #installJideExtension()}, the one with out style parameter.
	 * If you would like another style to be used as the default style, you can call {@link #setDefaultStyle(int)}
	 * method.
	 * <p/>
	 * Here is the code to set to Windows L&F with Vsnet style extension.
	 * <code><pre>
	 * UIManager.setLookAndFeel(WindowsLookAndFeel.class.getName()); // you need to catch the
	 * exceptions on this call.
	 * LookAndFeelFactory.installJideExtension(LookAndFeelFactory.EXTENSION_STYLE_VSNET);
	 * </pre></code>
	 * There is a special system property "shading theme" you can use. If you turn it on using the code below, you will
	 * see a gradient on dockable frame's title pane and rounded corner and gradient on the tabs of JideTabbedPane. So
	 * if the L&F you are using uses gradient, you can set this property to true to match with your L&F. For example, if
	 * you use Plastic3D L&F, turning this property on will look better.
	 * <code><pre>
	 * System.setProperty("shadingtheme", "true");
	 * </pre></code>
	 */
	public static final int EXTENSION_STYLE_VSNET = 1;

	/**
	 * A style that you can use with {@link #installJideExtension(int)} method. This style mimics the visual style of
	 * Eclipse 2.x for the toolbars, menus and dockable windows.
	 * <p/>
	 * Eclipse style works for almost all L&Fs and on any operating systems, although it looks the best on Windows. For
	 * any other operating systems we suggest you to use EXTENSION_STYLE_XERTO or EXTENSION_STYLE_VSNET.
	 * <p/>
	 * Here is the code to set to any L&F with Eclipse style extension.
	 * <code><pre>
	 * UIManager.setLookAndFeel(AnyLookAndFeel.class.getName()); // you need to catch the
	 * exceptions
	 * on this call.
	 * LookAndFeelFactory.installJideExtension(LookAndFeelFactory.EXTENSION_STYLE_ECLIPSE);
	 * </pre></code>
	 */
	public static final int EXTENSION_STYLE_ECLIPSE = 2;

	/**
	 * A style that you can use with {@link #installJideExtension(int)} method. This style mimics the visual style of
	 * Microsoft Office2003 for the toolbars, menus and dockable windows.
	 * <p/>
	 * Office2003 style looks great on Windows XP when Windows or Windows XP L&F from Sun JDK is used. It replicated the
	 * exact same style as Microsoft Office 2003, to give your end user a familiar visual style.
	 * <p/>
	 * Here is the code to set to Windows L&F with Office2003 style extension.
	 * <code><pre>
	 * UIManager.setLookAndFeel(WindowsLookAndFeel.class.getName()); // you need to catch the
	 * exceptions on this call.
	 * LookAndFeelFactory.installJideExtension(LookAndFeelFactory.EXTENSION_STYLE_OFFICE2003);
	 * </pre></code>
	 * It works either on any other Windows such asWindows 2000, Windows 98 etc. If you are on Windows XP, Office2003
	 * style will change theme based on the theme setting in Windows Display Property. But if you are not on XP,
	 * Office2003 style will use the default gray theme only. You can force to change it using {@link
	 * Office2003Painter#setColorName(String)} method, but it won't look good as other non-JIDE components won't have
	 * the matching theme.
	 * <p/>
	 * Office2003 style doesn't work on any operating systems other than Windows mainly because the design of Office2003
	 * style is so centric to Windows that it doesn't look good on other operating systems.
	 */
	public static final int EXTENSION_STYLE_OFFICE2003 = 3;

	/**
	 * A style that you can use with {@link #installJideExtension(int)} method. This style is created by Xerto
	 * (http://www.xerto.com) which is used in their Imagery product.
	 * <p/>
	 * Xerto style looks great on Windows XP when Windows XP L&F from Sun JDK is used.
	 * <p/>
	 * Here is the code to set to Windows L&F with Xerto style extension.
	 * <code><pre>
	 * UIManager.setLookAndFeel(WindowsLookAndFeel.class.getName()); // you need to catch the
	 * exceptions on this call.
	 * LookAndFeelFactory.installJideExtension(LookAndFeelFactory.EXTENSION_STYLE_XERTO);
	 * </pre></code>
	 * Although it looks the best on Windows, Xerto style also supports Linux or Solaris if you use any L&Fs based on
	 * Metal L&F or Synth L&F. For example, we recommend you to use Xerto style as default if you use SyntheticaL&F, a
	 * L&F based on Synth. To use it, you basically replace WindowsLookAndFeel to the L&F you want to use in
	 * setLookAndFeel line above.
	 */
	public static final int EXTENSION_STYLE_XERTO = 4;

	/**
	 * A style that you can use with {@link #installJideExtension(int)} method. This style mimics the visual style of
	 * Eclipse 3.x for the toolbars, menus and dockable windows.
	 * <p/>
	 * Eclipse 3x style works for almost all L&Fs and on any operating systems, although it looks the best on Windows.
	 * For any other OS's we suggest you to use EXTENSION_STYLE_XERTO or EXTENSION_STYLE_VSNET.
	 * <code><pre>
	 * UIManager.setLookAndFeel(AnyLookAndFeel.class.getName()); // you need to catch the
	 * exceptions
	 * on this call.
	 * LookAndFeelFactory.installJideExtension(LookAndFeelFactory.EXTENSION_STYLE_ECLIPSE3X);
	 * </pre></code>
	 */
	public static final int EXTENSION_STYLE_ECLIPSE3X = 5;

	/**
	 * A style that you can use with {@link #installJideExtension(int)} method. This style is the same as XERTO_STYLE
	 * except it doesn't have menu related UIDefaults. You can only use this style if you didn't use any component from
	 * JIDE Action Framework. Please note, we only use menu extension for Xerto style when the underlying L&F is Windows
	 * L&F. If you are using L&F such as Metal or other 3rd party L&F based on Metal, XERTO_STYLE_WITHOUT_MENU will be
	 * used even you use XERTO_STYLE when calling to installJideExtension().
	 * <p/>
	 *
	 * @see #EXTENSION_STYLE_XERTO
	 */
	public static final int EXTENSION_STYLE_XERTO_WITHOUT_MENU = 6;

	/**
	 * A style that you can use with {@link #installJideExtension(int)} method. This style mimics the visual style of
	 * Microsoft Office2007 for the toolbars, menus and dockable windows.
	 * <p/>
	 * Office2007 style looks great on Windows Vista when Windows L&F from Sun JDK is used. It replicated the exact same
	 * style as Microsoft Office 2007, to give your end user a familiar visual style.
	 * <p/>
	 * Here is the code to set to Windows L&F with Office2007 style extension.
	 * <code><pre>
	 * UIManager.setLookAndFeel(WindowsLookAndFeel.class.getName()); // you need to catch the
	 * exceptions on this call.
	 * LookAndFeelFactory.installJideExtension(LookAndFeelFactory.EXTENSION_STYLE_OFFICE2007);
	 * </pre></code>
	 * <p/>
	 * Office2007 style doesn't work on any operating systems other than Windows mainly because the design of Office2003
	 * style is so centric to Windows that it doesn't look good on other operating systems.
	 * <p/>
	 * Because we use some painting code that is only available in JDK6, Office 2007 style only runs if you are using
	 * JDK6 and above.
	 */
	public static final int EXTENSION_STYLE_OFFICE2007 = 7;

	/**
	 * A style that you can use with {@link #installJideExtension(int)} method. This style is the same as
	 * EXTENSION_STYLE_ECLIPSE except it doesn't have menu related UIDefaults. You can only use this style if you didn't
	 * use any component from JIDE Action Framework.
	 * <p/>
	 *
	 * @see #EXTENSION_STYLE_ECLIPSE3X
	 */
	public static final int EXTENSION_STYLE_ECLIPSE_WITHOUT_MENU = 10;

	/**
	 * A style that you can use with {@link #installJideExtension(int)} method. This style is the same as
	 * EXTENSION_STYLE_ECLIPSE3X except it doesn't have menu related UIDefaults. You can only use this style if you
	 * didn't use any component from JIDE Action Framework.
	 * <p/>
	 *
	 * @see #EXTENSION_STYLE_ECLIPSE3X
	 */
	public static final int EXTENSION_STYLE_ECLIPSE3X_WITHOUT_MENU = 11;

	private static int _style = -1;
	private static int _defaultStyle = -1;
	private static LookAndFeel _lookAndFeel;
	private static PropertyChangeListener _listener;

	/**
	 * If installJideExtension is called, it will put an entry on UIDefaults table.
	 * UIManagerLookup.getBoolean(JIDE_EXTENSION_INSTALLLED) will return true. You can also use {@link
	 * #isJideExtensionInstalled()} to check the value instead of using UIManagerLookup.getBoolean(JIDE_EXTENSION_INSTALLLED).
	 */
	public static final String JIDE_EXTENSION_INSTALLED = "jidesoft.extensionInstalled";

	/**
	 * If installJideExtension is called, a JIDE style will be installed on UIDefaults table. If so,
	 * UIManagerLookup.getInt(JIDE_STYLE_INSTALLED) will return you the style that is installed. For example, if the
	 * value is 1, it means VSNET_STYLE is installed because 1 is the value of VSNET_STYLE.
	 */
	public static final String JIDE_STYLE_INSTALLED = "jidesoft.extensionStyle";

	/**
	 * An interface to make the customization of UIDefaults easier. This customizer will be called after
	 * {@link #installJideExtension()} is called. So if you want to further customize UIDefault, you can use this
	 * customizer to do it.
	 */
	public interface UIDefaultsCustomizer {
		void customize(UIDefaults defaults);
	}

	/**
	 * An interface to make the initialization of UIDefaults easier. This initializer will be called before
	 * {@link #installJideExtension()} is called. So if you want to initialize UIDefault before installJideExtension
	 * is called, you can use this initializer to do it.
	 */
	public interface UIDefaultsInitializer {
		void initialize(UIDefaults defaults);
	}

	private static final List<UIDefaultsCustomizer> _uiDefaultsCustomizers = new Vector<>();
	private static final List<UIDefaultsInitializer> _uiDefaultsInitializers = new Vector<>();
	private static Map<String, String> _installedLookAndFeels = new HashMap<>();
	private static boolean _loadLookAndFeelClass = true;

	public static final String LAF_INSTALLED = "installed";
	public static final String LAF_NOT_INSTALLED = "not installed";

	protected LookAndFeelFactory() {
	}

	/**
	 * Gets the default style. If you never set default style before, it will return {@link #OFFICE2003_STYLE} if you
	 * are on Windows XP, L&F is instance of Windows L&F and XP theme is on. Otherwise, it will return
	 * {@link #VSNET_STYLE}. If you set default style before, it will return whatever style you set.
	 *
	 * @return the default style.
	 */
	public static int getDefaultStyle() {
		if (_defaultStyle == -1) {
			String defaultStyle = System.getProperty("jide.defaultStyle", "-1");
			try {
				_defaultStyle = Integer.parseInt(defaultStyle);
			} catch (NumberFormatException ignored) {
			}
			if (_defaultStyle == -1) {
				int suggestedStyle;
				try {
					suggestedStyle = ((LookAndFeelFactory.getProductsUsed() & PRODUCT_ACTION) == 0) ? EXTENSION_STYLE_VSNET_WITHOUT_MENU : EXTENSION_STYLE_VSNET;
				} catch (UnsupportedOperationException e) {
					suggestedStyle = ((LookAndFeelFactory.getProductsUsed() & PRODUCT_ACTION) == 0) ? EXTENSION_STYLE_VSNET_WITHOUT_MENU : EXTENSION_STYLE_VSNET;
				}
				return suggestedStyle;
			}
		}
		return _defaultStyle;
	}

	/**
	 * Sets the default style. If you call this method to set a default style, {@link #installJideExtension()} will use
	 * it as the default style.
	 *
	 * @param defaultStyle the default style.
	 */
	public static void setDefaultStyle(int defaultStyle) {
		_defaultStyle = defaultStyle;
	}

	/**
	 * Adds additional UIDefaults JIDE needed to UIDefault table. You must call this method every time switching look
	 * and feel. And call updateComponentTreeUI() in corresponding DockingManager or DockableBarManager after this
	 * call.
	 * <pre><code>
	 *  try {
	 *      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	 *  }
	 *  catch (ClassNotFoundException e) {
	 *     e.printStackTrace();
	 *  }
	 *  catch (InstantiationException e) {
	 *     e.printStackTrace();
	 *  }
	 *  catch (IllegalAccessException e) {
	 *      e.printStackTrace();
	 *  }
	 *  catch (UnsupportedLookAndFeelException e) {
	 *      e.printStackTrace();
	 *  }
	 * <p/>
	 *  // to additional UIDefault for JIDE components
	 *  LookAndFeelFactory.installJideExtension(); // use default style VSNET_STYLE. You can change
	 * to a different style
	 * using setDefaultStyle(int style) and then call this method. Or simply call
	 * installJideExtension(style).
	 * <p/>
	 *  // call updateComponentTreeUI
	 *  frame.getDockableBarManager().updateComponentTreeUI();
	 *  frame.getDockingManager().updateComponentTreeUI();
	 * </code></pre>
	 */
	public static void installJideExtension() {
		installJideExtension(_style == -1 ? getDefaultStyle() : _style);
	}

	/**
	 * Add additional UIDefaults JIDE needed to UIDefaults table. You must call this method every time switching look
	 * and feel. And call updateComponentTreeUI() in corresponding DockingManager or DockableBarManager after this
	 * call.
	 * <pre><code>
	 *  try {
	 *      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	 *  }
	 *  catch (ClassNotFoundException e) {
	 *     e.printStackTrace();
	 *  }
	 *  catch (InstantiationException e) {
	 *     e.printStackTrace();
	 *  }
	 *  catch (IllegalAccessException e) {
	 *      e.printStackTrace();
	 *  }
	 *  catch (UnsupportedLookAndFeelException e) {
	 *      e.printStackTrace();
	 *  }
	 * <p/>
	 *  // to add additional UIDefault for JIDE components
	 *  LookAndFeelFactory.installJideExtension(LookAndFeelFactory.OFFICE2003_STYLE);
	 * <p/>
	 *  // call updateComponentTreeUI
	 *  frame.getDockableBarManager().updateComponentTreeUI();
	 *  frame.getDockingManager().updateComponentTreeUI();
	 * </code></pre>
	 *
	 * @param style the style of the extension.
	 */
	public static void installJideExtension(int style) {
		installJideExtension(UIManager.getLookAndFeelDefaults(), UIManager.getLookAndFeel(), style);
	}

	/**
	 * Checks if JIDE extension is installed. Please note, UIManager.setLookAndFeel() method will overwrite the whole
	 * UIDefaults table. So even you called {@link #installJideExtension()} method before, UIManager.setLookAndFeel()
	 * method make isJideExtensionInstalled returning false.
	 *
	 * @return true if installed.
	 */
	public static boolean isJideExtensionInstalled() {
		return UIDefaultsLookup.getBoolean(JIDE_EXTENSION_INSTALLED);
	}

	/**
	 * Installs the UIDefault needed by JIDE component to the uiDefaults table passed in.
	 *
	 * @param uiDefaults the UIDefault tables where JIDE UIDefaults will be installed.
	 * @param lnf        the LookAndFeel. This may have an effect on which set of JIDE UIDefaults we will install.
	 * @param style      the style of the JIDE UIDefaults.
	 */
	public static void installJideExtension(UIDefaults uiDefaults, LookAndFeel lnf, int style) {
		if (isJideExtensionInstalled() && _style == style && _lookAndFeel == lnf) {
			return;
		}

		workAroundSwingIssues();

		if (_listener == null) {
			_listener = evt -> {
				if ("lookAndFeel".equals(evt.getPropertyName())) {
					_style = -1;
				}
			};
		}
		UIManager.removePropertyChangeListener(_listener);
		UIManager.addPropertyChangeListener(_listener);

		_style = style;
		uiDefaults.put(JIDE_STYLE_INSTALLED, _style);

		_lookAndFeel = lnf;
		UIDefaultsInitializer[] initializers = getUIDefaultsInitializers();
		for (UIDefaultsInitializer initializer : initializers) {
			if (initializer != null) {
				initializer.initialize(uiDefaults);
			}
		}

		initialize(lnf.getClass().getName(), uiDefaults);

		if ((lnf.getClass().getName().equals(ALLOY_LNF) && isAlloyLnfInstalled())
				|| (lnf.getClass().getName().equals(PLASTIC3D_LNF) && isPlastic3DLnfInstalled())
				|| (lnf.getClass().getName().equals(PLASTICXP_LNF) && isPlasticXPLnfInstalled())
				|| (lnf.getClass().getName().equals(PGS_LNF) && isPgsLnfInstalled())
				|| (lnf.getClass().getName().equals(TONIC_LNF) && isTonicLnfInstalled())) {

			switch (style) {
				case EXTENSION_STYLE_OFFICE2007 -> {
					VsnetWindowsUtils.initComponentDefaults(uiDefaults);
					Office2003WindowsUtils.initComponentDefaults(uiDefaults);
					Office2007WindowsUtils.initComponentDefaults(uiDefaults);
					Office2007WindowsUtils.initClassDefaults(uiDefaults, false);
				}
				case EXTENSION_STYLE_OFFICE2003 -> {
					VsnetWindowsUtils.initComponentDefaults(uiDefaults);
					Office2003WindowsUtils.initComponentDefaults(uiDefaults);
					Office2003WindowsUtils.initClassDefaults(uiDefaults, false);
				}
				case EXTENSION_STYLE_VSNET, EXTENSION_STYLE_VSNET_WITHOUT_MENU -> {
					VsnetMetalUtils.initComponentDefaults(uiDefaults);
					VsnetMetalUtils.initClassDefaults(uiDefaults);
					Painter gripperPainter = (c, g, rect, orientation, state) -> Office2003Painter.getInstance().paintGripper(c, g, rect, orientation, state);

					// set all grippers to Office2003 style gripper
					uiDefaults.put("Gripper.painter", gripperPainter);
					uiDefaults.put("JideTabbedPane.gripperPainter", gripperPainter);
					uiDefaults.put("JideTabbedPane.defaultTabShape", JideTabbedPane.SHAPE_OFFICE2003);
					uiDefaults.put("JideTabbedPane.selectedTabTextForeground", UIDefaultsLookup.getColor("controlText"));
					uiDefaults.put("JideTabbedPane.unselectedTabTextForeground", UIDefaultsLookup.getColor("controlText"));
					uiDefaults.put("JideTabbedPane.foreground", UIDefaultsLookup.getColor("controlText"));
					uiDefaults.put("JideTabbedPane.light", UIDefaultsLookup.getColor("control"));
					uiDefaults.put("JideSplitPaneDivider.gripperPainter", gripperPainter);
					int products = LookAndFeelFactory.getProductsUsed();
					if ((products & PRODUCT_DOCK) != 0) {
						ImageIcon titleButtonImage = IconsFactory.getImageIcon(VsnetWindowsUtils.class, "icons/title_buttons_windows.gif"); // 10 x 10 x 8
						final int titleButtonSize = 10;

						// TODO 没有这个UI
						uiDefaults.put("DockableFrameUI", "com.element.plaf.vsnet.VsnetDockableFrameUI");
						uiDefaults.put("DockableFrameTitlePane.hideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 0, titleButtonSize, titleButtonSize));
						uiDefaults.put("DockableFrameTitlePane.unfloatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, titleButtonSize, titleButtonSize, titleButtonSize));
						uiDefaults.put("DockableFrameTitlePane.floatIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 2 * titleButtonSize, titleButtonSize, titleButtonSize));
						uiDefaults.put("DockableFrameTitlePane.autohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 3 * titleButtonSize, titleButtonSize, titleButtonSize));
						uiDefaults.put("DockableFrameTitlePane.stopAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 4 * titleButtonSize, titleButtonSize, titleButtonSize));
						uiDefaults.put("DockableFrameTitlePane.hideAutohideIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 5 * titleButtonSize, titleButtonSize, titleButtonSize));
						uiDefaults.put("DockableFrameTitlePane.maximizeIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 6 * titleButtonSize, titleButtonSize, titleButtonSize));
						uiDefaults.put("DockableFrameTitlePane.restoreIcon", IconsFactory.getIcon(null, titleButtonImage, 0, 7 * titleButtonSize, titleButtonSize, titleButtonSize));
						uiDefaults.put("DockableFrameTitlePane.buttonGap", 4); // gap between buttons
						uiDefaults.put("DockableFrame.titleBorder", new BorderUIResource(BorderFactory.createEmptyBorder(1, 0, 2, 0)));
						uiDefaults.put("DockableFrame.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 0, 0, 0)));
						uiDefaults.put("DockableFrameTitlePane.gripperPainter", gripperPainter);
					}
				}
				case EXTENSION_STYLE_ECLIPSE, EXTENSION_STYLE_ECLIPSE_WITHOUT_MENU -> {
					EclipseMetalUtils.initComponentDefaults(uiDefaults);
					EclipseMetalUtils.initClassDefaults(uiDefaults);
				}
				case EXTENSION_STYLE_ECLIPSE3X, EXTENSION_STYLE_ECLIPSE3X_WITHOUT_MENU -> {
					Eclipse3xMetalUtils.initComponentDefaults(uiDefaults);
					Eclipse3xMetalUtils.initClassDefaults(uiDefaults);
				}
				case EXTENSION_STYLE_XERTO, EXTENSION_STYLE_XERTO_WITHOUT_MENU -> {
					XertoMetalUtils.initComponentDefaults(uiDefaults);
					XertoMetalUtils.initClassDefaults(uiDefaults);
				}
			}

			if (style == EXTENSION_STYLE_XERTO || style == EXTENSION_STYLE_XERTO_WITHOUT_MENU) {
				UIDefaultsLookup.put(uiDefaults, "Theme.painter", XertoPainter.getInstance());
			} else {
				UIDefaultsLookup.put(uiDefaults, "Theme.painter", BasicPainter.getInstance());
			}
		} else if (lnf.getClass().getName().equals(MetalLookAndFeel.class.getName())) {
			switch (style) {
				case EXTENSION_STYLE_OFFICE2007, EXTENSION_STYLE_OFFICE2003, EXTENSION_STYLE_VSNET -> {
					VsnetMetalUtils.initComponentDefaults(uiDefaults);
					VsnetMetalUtils.initClassDefaultsWithMenu(uiDefaults);
				}
				case EXTENSION_STYLE_ECLIPSE -> {
					EclipseMetalUtils.initComponentDefaults(uiDefaults);
					EclipseMetalUtils.initClassDefaults(uiDefaults);
				}
				case EXTENSION_STYLE_ECLIPSE3X -> {
					Eclipse3xMetalUtils.initComponentDefaults(uiDefaults);
					Eclipse3xMetalUtils.initClassDefaults(uiDefaults);
				}
				case EXTENSION_STYLE_VSNET_WITHOUT_MENU -> {
					VsnetMetalUtils.initComponentDefaults(uiDefaults);
					VsnetMetalUtils.initClassDefaults(uiDefaults);
				}
				case EXTENSION_STYLE_XERTO, EXTENSION_STYLE_XERTO_WITHOUT_MENU -> {
					XertoMetalUtils.initComponentDefaults(uiDefaults);
					XertoMetalUtils.initClassDefaults(uiDefaults);
				}
				default -> {
				}
			}
		} else if (lnf instanceof MetalLookAndFeel) {
			switch (style) {
				case EXTENSION_STYLE_OFFICE2007, EXTENSION_STYLE_OFFICE2003, EXTENSION_STYLE_VSNET, EXTENSION_STYLE_VSNET_WITHOUT_MENU -> {
					VsnetMetalUtils.initComponentDefaults(uiDefaults);
					VsnetMetalUtils.initClassDefaults(uiDefaults);
				}
				case EXTENSION_STYLE_ECLIPSE, EXTENSION_STYLE_ECLIPSE_WITHOUT_MENU -> {
					EclipseMetalUtils.initClassDefaults(uiDefaults);
					EclipseMetalUtils.initComponentDefaults(uiDefaults);
				}
				case EXTENSION_STYLE_ECLIPSE3X, EXTENSION_STYLE_ECLIPSE3X_WITHOUT_MENU -> {
					Eclipse3xMetalUtils.initClassDefaults(uiDefaults);
					Eclipse3xMetalUtils.initComponentDefaults(uiDefaults);
				}
				case EXTENSION_STYLE_XERTO, EXTENSION_STYLE_XERTO_WITHOUT_MENU -> {
					XertoMetalUtils.initComponentDefaults(uiDefaults);
					XertoMetalUtils.initClassDefaults(uiDefaults);
				}
			}
		} else if (isAquaLnfInstalled() && ((isLnfInUse(AQUA_LNF_6) || isLnfInUse(AQUA_LNF)))
				|| (isQuaquaLnfInstalled() && isLnfInUse(QUAQUA_LNF))) {
			// use reflection since we don't deliver source code of AquaJideUtils as most users don't compile it on Mac OS X
			try {
				Class<?> aquaJideUtils = getUIManagerClassLoader().loadClass("com.element.plaf.aqua.AquaJideUtils");
				aquaJideUtils.getMethod("initComponentDefaults", UIDefaults.class).invoke(null, uiDefaults);
				aquaJideUtils.getMethod("initClassDefaults", UIDefaults.class).invoke(null, uiDefaults);
			} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException |
			         NoSuchMethodException | SecurityException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		} else {
			switch (style) {
				case EXTENSION_STYLE_OFFICE2007:
					if (SystemInfo.isWindows()) {
						VsnetWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
						Office2003WindowsUtils.initComponentDefaults(uiDefaults);
						Office2007WindowsUtils.initComponentDefaults(uiDefaults);
						Office2007WindowsUtils.initClassDefaults(uiDefaults);
					} else {
						VsnetMetalUtils.initComponentDefaults(uiDefaults);
						VsnetMetalUtils.initClassDefaults(uiDefaults);
					}
					break;
				case EXTENSION_STYLE_OFFICE2003:
					if (SystemInfo.isWindows()) {
						VsnetWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
						Office2003WindowsUtils.initComponentDefaults(uiDefaults);
						Office2003WindowsUtils.initClassDefaults(uiDefaults);
					} else {
						VsnetMetalUtils.initComponentDefaults(uiDefaults);
						VsnetMetalUtils.initClassDefaults(uiDefaults);
					}
					break;
				case EXTENSION_STYLE_ECLIPSE:
					if (SystemInfo.isWindows()) {
						EclipseWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
						EclipseWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
					} else {
						EclipseMetalUtils.initClassDefaults(uiDefaults);
						EclipseMetalUtils.initComponentDefaults(uiDefaults);
					}
					break;
				case EXTENSION_STYLE_ECLIPSE_WITHOUT_MENU:
					if (SystemInfo.isWindows()) {
						EclipseWindowsUtils.initClassDefaults(uiDefaults);
						EclipseWindowsUtils.initComponentDefaults(uiDefaults);
					} else {
						EclipseMetalUtils.initClassDefaults(uiDefaults);
						EclipseMetalUtils.initComponentDefaults(uiDefaults);
					}
					break;
				case EXTENSION_STYLE_ECLIPSE3X:
					if (SystemInfo.isWindows()) {
						Eclipse3xWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
						Eclipse3xWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
					} else {
						Eclipse3xMetalUtils.initClassDefaults(uiDefaults);
						Eclipse3xMetalUtils.initComponentDefaults(uiDefaults);
					}
					break;
				case EXTENSION_STYLE_ECLIPSE3X_WITHOUT_MENU:
					if (SystemInfo.isWindows()) {
						Eclipse3xWindowsUtils.initClassDefaults(uiDefaults);
						Eclipse3xWindowsUtils.initComponentDefaults(uiDefaults);
					} else {
						Eclipse3xMetalUtils.initClassDefaults(uiDefaults);
						Eclipse3xMetalUtils.initComponentDefaults(uiDefaults);
					}
					break;
				case EXTENSION_STYLE_VSNET:
					if (SystemInfo.isWindows()) {
						VsnetWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
						VsnetWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
					} else {
						VsnetMetalUtils.initComponentDefaults(uiDefaults);
						VsnetMetalUtils.initClassDefaults(uiDefaults);
					}
					break;
				case EXTENSION_STYLE_VSNET_WITHOUT_MENU:
					if (SystemInfo.isWindows()) {
						VsnetWindowsUtils.initClassDefaults(uiDefaults);
						VsnetWindowsUtils.initComponentDefaults(uiDefaults);
					} else {
						VsnetMetalUtils.initComponentDefaults(uiDefaults);
						VsnetMetalUtils.initClassDefaults(uiDefaults);
					}
					break;
				case EXTENSION_STYLE_XERTO:
					if (SystemInfo.isWindows()) {
						XertoWindowsUtils.initClassDefaultsWithMenu(uiDefaults);
						XertoWindowsUtils.initComponentDefaultsWithMenu(uiDefaults);
					} else {
						XertoMetalUtils.initComponentDefaults(uiDefaults);
						XertoMetalUtils.initClassDefaults(uiDefaults);
					}
					break;
				case EXTENSION_STYLE_XERTO_WITHOUT_MENU:
					if (SystemInfo.isWindows()) {
						XertoWindowsUtils.initClassDefaults(uiDefaults);
						XertoWindowsUtils.initComponentDefaults(uiDefaults);
					} else {
						XertoMetalUtils.initComponentDefaults(uiDefaults);
						XertoMetalUtils.initClassDefaults(uiDefaults);
					}
					break;
			}
		}

		uiDefaults.put(JIDE_EXTENSION_INSTALLED, Boolean.TRUE);

		customize(lnf.getClass().getName(), uiDefaults);

		UIDefaultsCustomizer[] customizers = getUIDefaultsCustomizers();
		for (UIDefaultsCustomizer customizer : customizers) {
			if (customizer != null) {
				customizer.customize(uiDefaults);
			}
		}
	}

	private static void workAroundSwingIssues() {
		Object o = UIManager.get("PopupMenu.selectedWindowInputMapBindings.RightToLeft");
		if (o instanceof Object[] mapArray) {
			for (Object item : mapArray) {
				if ("DOWN".equals(item)) {
					return; // maybe Swing fixed the bug later, no need to work around any more.
				}
			}
			Object[] newMapArray = new Object[mapArray.length + 14];
			System.arraycopy(mapArray, 0, newMapArray, 0, mapArray.length);
			int i = mapArray.length;
			newMapArray[i++] = "DOWN";
			newMapArray[i++] = "selectNext";
			newMapArray[i++] = "UP";
			newMapArray[i++] = "selectPrevious";
			newMapArray[i++] = "KP_DOWN";
			newMapArray[i++] = "selectNext";
			newMapArray[i++] = "KP_UP";
			newMapArray[i++] = "selectPrevious";
			newMapArray[i++] = "ENTER";
			newMapArray[i++] = "return";
			newMapArray[i++] = "SPACE";
			newMapArray[i++] = "return";
			newMapArray[i++] = "ESCAPE";
			newMapArray[i] = "cancel";
			UIManager.put("PopupMenu.selectedWindowInputMapBindings.RightToLeft", newMapArray);
		}
	}

	private static Map<String, String> _defaultInitializers;
	private static Map<String, String> _defaultCustomizers;

	/**
	 * Registers a UIDefaultsInitializer with a L&F. Note that you can only register one initializer for a L&F.
	 *
	 * @param lnfClassName         full class name of the L&F
	 * @param initializerClassName full class name of the UIDefaultInitializer
	 */
	public static void registerDefaultInitializer(String lnfClassName, String initializerClassName) {
		if (_defaultInitializers == null) {
			_defaultInitializers = new HashMap<>();
		}
		_defaultInitializers.put(lnfClassName, initializerClassName);
	}

	/**
	 * Unregisters a UIDefaultsInitializer for L&F.
	 *
	 * @param lnfClassName full class name of the L&F
	 */
	public static void unregisterDefaultInitializer(String lnfClassName) {
		if (_defaultInitializers != null) {
			_defaultInitializers.remove(lnfClassName);
		}
	}

	/**
	 * Clears all registered initializers.
	 */
	public static void clearDefaultInitializers() {
		if (_defaultInitializers != null) {
			_defaultInitializers.clear();
		}
	}

	/**
	 * Registers a UIDefaultsCustomizer with a L&F. Note that you can only register one customizer for a L&F.
	 *
	 * @param lnfClassName        full class name of the L&F
	 * @param customizerClassName full class name of the UIDefaultsCustomizer
	 */
	public static void registerDefaultCustomizer(String lnfClassName, String customizerClassName) {
		if (_defaultCustomizers == null) {
			_defaultCustomizers = new HashMap<>();
		}
		_defaultCustomizers.put(lnfClassName, customizerClassName);
	}

	/**
	 * Unregisters a UIDefaultCustomizer for L&F.
	 *
	 * @param lnfClassName full class name of the L&F
	 */
	public static void unregisterDefaultCustomizer(String lnfClassName) {
		if (_defaultCustomizers != null) {
			_defaultCustomizers.remove(lnfClassName);
		}
	}

	/**
	 * Clears all registered customizers.
	 */
	public static void clearDefaultCustomizers() {
		if (_defaultCustomizers != null) {
			_defaultCustomizers.clear();
		}
	}

	private static void initialize(String lnfClassName, UIDefaults uiDefaults) {
		Vector<String> lookup = new Vector<>();
		Vector<String> classLookup = new Vector<>();
		classLookup.insertElementAt(lnfClassName, 0);
		String lnf = guessLookAndFeelName(lnfClassName);
		if (lnf != null && lnf.trim().length() > 0) lookup.insertElementAt(lnf, 0);
		try {
			Class<?> clazz = Class.forName(lnfClassName);
			while (clazz != null) {
				Class<?> superclass = clazz.getSuperclass();
				if (superclass != null && LookAndFeel.class.isAssignableFrom(superclass)) {
					lnfClassName = superclass.getName();
				} else {
					break;
				}
				classLookup.insertElementAt(lnfClassName, 0);
				lnf = guessLookAndFeelName(lnfClassName);
				if (lnf != null && lnf.trim().length() > 0) lookup.insertElementAt(lnf, 0);
				clazz = superclass;
			}
		} catch (ClassNotFoundException e) {
			// ignore
		}

		for (String s : classLookup) {
			String initializer = findDefaultInitializer(s);
			if (initializer != null) {
				invokeInitialize(uiDefaults, initializer);
			}
		}

		for (String s : lookup) {
			String initializer = getDefaultInitializer(s);
			if (initializer != null) {
				invokeInitialize(uiDefaults, initializer);
			}
		}
	}

	private static void invokeInitialize(UIDefaults uiDefaults, String initializer) {
		try {
			Class<?> clazz = Class.forName(initializer);
			Object o = clazz.getConstructor().newInstance();
			Method method = o.getClass().getMethod("initialize", UIDefaults.class);
			method.invoke(o, uiDefaults);
		} catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
		         IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// ignore
		}
	}

	private static String getDefaultInitializer(String lnf) {
		return "com.element.plaf." + lnf.toLowerCase() + "." + lnf + "Initializer";
	}

	private static String findDefaultInitializer(String lnfClassName) {
		if (_defaultInitializers != null) {
			String s = _defaultInitializers.get(lnfClassName);
			return s;
		}
		return null;
	}

	private static void customize(String lnfClassName, UIDefaults uiDefaults) {
		Vector<String> lookup = new Vector<>();
		Vector<String> classLookup = new Vector<>();
		classLookup.insertElementAt(lnfClassName, 0);
		String lnf = guessLookAndFeelName(lnfClassName);
		if (lnf != null && lnf.trim().length() > 0) lookup.insertElementAt(lnf, 0);
		try {
			Class<?> clazz = Class.forName(lnfClassName);
			while (clazz != null) {
				Class<?> superclass = clazz.getSuperclass();
				if (superclass != null && LookAndFeel.class.isAssignableFrom(superclass)) {
					lnfClassName = superclass.getName();
				} else {
					break;
				}
				classLookup.insertElementAt(lnfClassName, 0);
				lnf = guessLookAndFeelName(lnfClassName);
				if (lnf != null && lnf.trim().length() > 0) lookup.insertElementAt(lnf, 0);
				clazz = superclass;
			}
		} catch (ClassNotFoundException e) {
			// ignore
		}

		for (String s : classLookup) {
			String customizer = findDefaultCustomizer(s);
			if (customizer != null) {
				invokeCustomize(uiDefaults, customizer);
			}
		}

		for (String s : lookup) {
			String customizer = getDefaultCustomizer(s);
			if (customizer != null) {
				invokeCustomize(uiDefaults, customizer);
			}
		}
	}

	private static void invokeCustomize(UIDefaults uiDefaults, String customizer) {
		try {
			Class<?> clazz = Class.forName(customizer);
			Object o = clazz.getConstructor().newInstance();
			Method method = o.getClass().getMethod("customize", UIDefaults.class);
			method.invoke(o, uiDefaults);
		} catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
		         IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// ignore
		}
	}

	private static String getDefaultCustomizer(String lnf) {
		return "com.element.plaf." + lnf.toLowerCase() + "." + lnf + "Customizer";
	}

	private static String findDefaultCustomizer(String lnfClassName) {
		if (_defaultCustomizers != null) {
			String s = _defaultCustomizers.get(lnfClassName);
			return s;
		}
		return null;
	}

	private static String guessLookAndFeelName(String lnfClassName) {
		int start = lnfClassName.lastIndexOf(".") + 1;
		if (lnfClassName.endsWith("LookAndFeel")) {
			return lnfClassName.substring(start, lnfClassName.length() - "LookAndFeel".length());
		} else if (lnfClassName.endsWith("Laf")) {
			return lnfClassName.substring(start, lnfClassName.length() - "Laf".length());
		} else if (lnfClassName.endsWith("Lnf")) {
			return lnfClassName.substring(start, lnfClassName.length() - "Lnf".length());
		}
		return null;
	}

	/**
	 * Returns whether or not the L&F is in classpath. This method will check for pre-installed L&Fs using {@link
	 * #setLnfInstalled(String, boolean)}. If the L&F is not pre-installed, we will try to use class loader to load the
	 * class to determine if the L&F is installed. If you don't want us to load the class, you can call {@link
	 * #setLoadLookAndFeelClass(boolean)} false. If so, we will solely depend on the setLnfInstalled method to determine
	 * if the L&F is installed.
	 *
	 * @param lnfName the L&F name.
	 * @return <tt>true</tt> if the L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isLnfInstalled(String lnfName) {
		String installed = _installedLookAndFeels.get(lnfName);
		if (installed != null) {
			return LAF_INSTALLED.equals(installed);
		}
		return isLoadLookAndFeelClass() && loadLnfClass(lnfName) != null;
	}

	public static ClassLoader getUIManagerClassLoader() {
		Object cl = UIManager.get("ClassLoader");
		if (cl instanceof ClassLoader) {
			return (ClassLoader) cl;
		}
		ClassLoader classLoader = LookAndFeelFactory.class.getClassLoader();
		if (classLoader == null) {
			classLoader = ClassLoader.getSystemClassLoader();
		}
		return classLoader;
	}

	/**
	 * Checks if the L&F is the L&F or a subclass of the L&F.
	 *
	 * @param lnf the full class name of the L&F (including the package).
	 * @return true or false.
	 */
	public static boolean isLnfInUse(String lnf) {
		return !(_installedLookAndFeels.containsKey(lnf)
				&& (_installedLookAndFeels.get(lnf) == null || _installedLookAndFeels.get(lnf).equals(LAF_NOT_INSTALLED)))
				&& isAssignableFrom(lnf, UIManager.getLookAndFeel().getClass());
	}

	/**
	 * Tells the LookAndFeelFactory whether a L&F is installed. We will try to instantiate the class when {@link
	 * #isLnfInstalled(String)} is called to determine if the class is in the class path. However you can call this
	 * method to tell if the L&F is available without us instantiating the class.
	 *
	 * @param lnfName   the L&F name.
	 * @param installed true or false.
	 */
	public static void setLnfInstalled(String lnfName, boolean installed) {
		_installedLookAndFeels.put(lnfName, installed ? LAF_INSTALLED : LAF_NOT_INSTALLED);
	}

	private static Class loadLnfClass(String lnfName) {
		try {
			Class clazz = getUIManagerClassLoader().loadClass(lnfName);
			Map<String, String> map = new HashMap<>(_installedLookAndFeels);
			map.put(lnfName, LAF_INSTALLED);
			_installedLookAndFeels = map;
			return clazz;
		} catch (ClassNotFoundException e) {
			Map<String, String> map = new HashMap<>(_installedLookAndFeels);
			map.put(lnfName, LAF_NOT_INSTALLED);
			_installedLookAndFeels = map;
			return null;
		}
	}

	private static boolean isAssignableFrom(String lnfName, Class cls) {
		if (lnfName.equals(cls.getName())) {
			return true;
		}
		Class cl = loadLnfClass(lnfName);
		return cl != null && cl.isAssignableFrom(cls);
	}

	/**
	 * Returns whether or not the Aqua L&F is in classpath.
	 *
	 * @return <tt>true</tt> if aqua L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isAquaLnfInstalled() {
		return isLnfInstalled(AQUA_LNF_6) || isLnfInstalled(AQUA_LNF);
	}


	/**
	 * Returns whether or not the Quaqua L&F is in classpath.
	 *
	 * @return <tt>true</tt> if Quaqua L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isQuaquaLnfInstalled() {
		return isLnfInstalled(QUAQUA_LNF);
	}

	/**
	 * Returns whether alloy L&F is in classpath
	 *
	 * @return <tt>true</tt> alloy L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isAlloyLnfInstalled() {
		return isLnfInstalled(ALLOY_LNF);
	}

	/**
	 * Returns whether Bizlaf L&F is in classpath
	 *
	 * @return <tt>true</tt> Bizlaf L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isBizlafLnfInstalled() {
		return isLnfInstalled(BIZ_LNF);
	}

	/**
	 * Returns whether GTK L&F is in classpath
	 *
	 * @return <tt>true</tt> GTK L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isGTKLnfInstalled() {
		return isLnfInstalled(GTK_LNF);
	}

	/**
	 * Returns whether Plastic3D L&F is in classpath
	 *
	 * @return <tt>true</tt> Plastic3D L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isPlastic3DLnfInstalled() {
		return isLnfInstalled(PLASTIC3D_LNF);
	}

	/**
	 * Returns whether Plastic3D L&F is in classpath
	 *
	 * @return <tt>true</tt> Plastic3D L&F is in classpath, <tt>false</tt> otherwise
	 * @deprecated replace by {@link #isPlastic3DLnfInstalled()}
	 */
	@Deprecated
	public static boolean isPlastic3D13LnfInstalled() {
		return isLnfInstalled(PLASTIC3D_LNF);
	}

	/**
	 * Returns whether PlasticXP L&F is in classpath
	 *
	 * @return <tt>true</tt> Plastic3D L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isPlasticXPLnfInstalled() {
		return isLnfInstalled(PLASTICXP_LNF);
	}

	/**
	 * Returns whether Tonic L&F is in classpath
	 *
	 * @return <tt>true</tt> Tonic L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isTonicLnfInstalled() {
		return isLnfInstalled(TONIC_LNF);
	}

	/**
	 * Returns whether A03 L&F is in classpath
	 *
	 * @return <tt>true</tt> A03 L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isA03LnfInstalled() {
		return isLnfInstalled(A03_LNF);
	}

	/**
	 * Returns whether Darcula L&F is in classpath
	 *
	 * @return <tt>true</tt> Darcula L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isDarculaLnfInstalled() {
		return isLnfInstalled(DARCULA_LNF);
	}

	/**
	 * Returns whether or not the Pgs L&F is in classpath.
	 *
	 * @return <tt>true</tt> if pgs L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isPgsLnfInstalled() {
		return isLnfInstalled(PGS_LNF);
	}

	/**
	 * Returns whether or not the Synthetica L&F is in classpath.
	 *
	 * @return <tt>true</tt> if Synthetica L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isSyntheticaLnfInstalled() {
		return isLnfInstalled(SYNTHETICA_LNF);
	}

	/**
	 * Returns whether or not the Nimbus L&F is in classpath.
	 *
	 * @return <tt>true</tt> if Nimbus L&F is in classpath, <tt>false</tt> otherwise
	 */
	public static boolean isNimbusLnfInstalled() {
		UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
		for (UIManager.LookAndFeelInfo info : infos) {
			if (info.getClassName().contains(NIMBUS_LNF_NAME)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Install the default L&F. In this method, we will look at system property "swing.defaultlaf" first. If the value
	 * is set and it's not an instance of Synth L&F, we will use it. Otherwise, we will use Metal L&F is OS is Linux or
	 * UNIX and use UIManager.getSystemLookAndFeelClassName() for other OS. In addition, we will add JIDE extension to
	 * it.
	 */
	public static void installDefaultLookAndFeelAndExtension() {
		installDefaultLookAndFeel();
		// to add additional UIDefault for JIDE components
		LookAndFeelFactory.installJideExtension();
	}

	/**
	 * Install the default L&F. In this method, we will look at system property "swing.defaultlaf" first. If the value
	 * is set and it's not an instance of Synth L&F, we will use it. Otherwise, we will use Metal L&F is OS is Linux or
	 * UNIX and use UIManager.getSystemLookAndFeelClassName() for other OS.
	 */
	public static void installDefaultLookAndFeel() {
		try {
			String lnfName = System.getProperty("swing.defaultlaf", null);
			UIManager.setLookAndFeel(Objects.requireNonNullElseGet(lnfName, UIManager::getSystemLookAndFeelClassName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets current L&F.
	 *
	 * @return the current L&F.
	 */
	public static LookAndFeel getLookAndFeel() {
		return _lookAndFeel;
	}

	/**
	 * Gets current style.
	 *
	 * @return the current style.
	 */
	public static int getStyle() {
		return _style;
	}

	/**
	 * Gets all UIDefaults customizers.
	 *
	 * @return an array of UIDefaults customizers.
	 */
	public static UIDefaultsCustomizer[] getUIDefaultsCustomizers() {
		return _uiDefaultsCustomizers.toArray(new UIDefaultsCustomizer[0]);
	}

	/**
	 * Adds your own UIDefaults customizer. You need to add it before installJideExtension() is called but the actual
	 * customize() code will be called after installJideExtension() is called.
	 * <code><pre>
	 * For example, we use "JideButton.font" as the UIDefault for the JideButton font. If you want
	 * to use another font, you can do
	 * LookAndFeelFactory.addUIDefaultsCustomizer(new LookAndFeelFactory.UIDefaultsCustomizer() {
	 *     public void customize(UIDefaults defaults) {
	 *         defaults.put("JideButton.font", whateverFont);
	 *     }
	 * });
	 * LookAndFeelFactory.installJideExtension();
	 * </pre></code>
	 *
	 * @param uiDefaultsCustomizer the UIDefaultsCustomizer
	 */
	public static void addUIDefaultsCustomizer(UIDefaultsCustomizer uiDefaultsCustomizer) {
		if (!_uiDefaultsCustomizers.contains(uiDefaultsCustomizer)) {
			_uiDefaultsCustomizers.add(uiDefaultsCustomizer);
		}
	}

	/**
	 * Removes an existing UIDefaults customizer you added before.
	 *
	 * @param uiDefaultsCustomizer the UIDefaultsCustomizer
	 */
	public static void removeUIDefaultsCustomizer(UIDefaultsCustomizer uiDefaultsCustomizer) {
		_uiDefaultsCustomizers.remove(uiDefaultsCustomizer);
	}

	/**
	 * Gets all UIDefaults initializers.
	 *
	 * @return an array of UIDefaults initializers.
	 */
	public static UIDefaultsInitializer[] getUIDefaultsInitializers() {
		return _uiDefaultsInitializers.toArray(new UIDefaultsInitializer[0]);
	}

	/**
	 * Adds your own UIDefaults initializer. This initializer will be called before installJideExtension() is called.
	 * <p/>
	 * Here is how you use it. For example, we use the color of UIDefault "activeCaption" to get the active title color
	 * which we will use for active title bar color in JIDE components. If the L&F you are using doesn't set this
	 * UIDefault, we might throw NPE later in the code. To avoid this, you call
	 * <code><pre>
	 * LookAndFeelFactory.addUIDefaultsInitializer(new LookAndFeelFactory.UIDefaultsInitializer() {
	 *     public void initialize(UIDefaults defaults) {
	 *         defaults.put("activeCaption", whateverColor);
	 *     }
	 * });
	 * UIManager.setLookAndFeel(...); // set whatever L&F
	 * LookAndFeelFactory.installJideExtension(); // install the UIDefaults needed by the JIDE
	 * components
	 * </pre></code>
	 *
	 * @param uiDefaultsInitializer the UIDefaultsInitializer.
	 */
	public static void addUIDefaultsInitializer(UIDefaultsInitializer uiDefaultsInitializer) {
		if (!_uiDefaultsInitializers.contains(uiDefaultsInitializer)) {
			_uiDefaultsInitializers.add(uiDefaultsInitializer);
		}
	}

	/**
	 * Removes an existing UIDefaults initializer you added before.
	 *
	 * @param uiDefaultsInitializer the UIDefaultsInitializer
	 */
	public static void removeUIDefaultsInitializer(UIDefaultsInitializer uiDefaultsInitializer) {
		_uiDefaultsInitializers.remove(uiDefaultsInitializer);
	}

	@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
	public static void verifyDefaults(UIDefaults table, Object[] keyValueList) {
		for (int i = 0, max = keyValueList.length; i < max; i += 2) {
			Object value = keyValueList[i + 1];
			if (value == null) {
				System.out.println("The value for " + keyValueList[i] + " is null");
			} else {
				Object oldValue = table.get(keyValueList[i]);
				if (oldValue != null) {
					System.out.println("The value for " + keyValueList[i] + " exists which is " + oldValue);
				}
			}
		}
	}

	/**
	 * Puts a list of UIDefault to the UIDefaults table. The keyValueList is an array with a key and value in pair. If
	 * the value is null, this method will remove the key from the table. If the table already has a value for the key,
	 * the new value will be ignored. This is the difference from {@link #putDefaults(UIDefaults, Object[])}
	 * method. You should use this method in {@link UIDefaultsInitializer} so that it fills in the UIDefault value only
	 * when it is missing.
	 *
	 * @param table         the ui defaults table
	 * @param keyValueArray the key value array. It is in the format of a key followed by a value.
	 */
	public static void putDefaults(UIDefaults table, Object[] keyValueArray) {
		for (int i = 0, max = keyValueArray.length; i < max; i += 2) {
			Object value = keyValueArray[i + 1];
			if (value == null) {
				table.remove(keyValueArray[i]);
			} else {
				table.putIfAbsent(keyValueArray[i], value);
			}
		}
	}

	/**
	 * Puts a list of UIDefault to the UIDefaults table. The keyValueList is an array with a key and value in pair. If
	 * the value is null, this method will remove the key from the table. Otherwise, it will put the new value in even
	 * if the table already has a value for the key. This is the difference from {@link
	 * #putDefaults(UIDefaults, Object[])} method. You should use this method in {@link
	 * UIDefaultsCustomizer} because you always want to override the existing value using the new value.
	 *
	 * @param table         the ui defaults table
	 * @param keyValueArray the key value array. It is in the format of a key followed by a value.
	 */
	public static void overwriteDefaults(UIDefaults table, Object[] keyValueArray) {
		for (int i = 0, max = keyValueArray.length; i < max; i += 2) {
			Object value = keyValueArray[i + 1];
			if (value == null) {
				table.remove(keyValueArray[i]);
			} else {
				table.put(keyValueArray[i], value);
			}
		}
	}

	private static int _productsUsed = -1;

	/**
	 * TODO 这个方法通过反射加载不同的Project类，但是jide没有公开这些Project类，尚不知道如何修改
	 *
	 * @deprecated 没有Product类，这个方法需要修改
	 */
	public static int getProductsUsed() {
		return _productsUsed == -1 ? 0 : _productsUsed;
	}

	/**
	 * As of Java 10, com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel is no longer available on macOS thus
	 * "instanceof WindowsClassicLookAndFeel" directives will result in a NoClassDefFoundError during runtime. This method
	 * was introduced to avoid this exception.
	 *
	 * @param lnf
	 * @return true if it is a WindowsClassicLookAndFeel.
	 */
	public static boolean isWindowsClassicLookAndFeel(LookAndFeel lnf) {
		if (lnf == null) {
			return false;
		} else {
			try {
				Class<?> c = Class.forName(WINDOWS_CLASSIC_LNF);
				return c.isInstance(lnf);
			} catch (ClassNotFoundException | NoClassDefFoundError ignore) {
				// if it is not possible to load the Windows LnF class, the
				// given lnf instance cannot be an instance of the Windows
				// LnF class
				return false;
			}
		}
	}

	/**
	 * Sets the products you will use. This is needed so that LookAndFeelFactory knows what UIDefault to initialize. For
	 * example, if you use only JIDE Docking Framework and JIDE Grids, you should call
	 * <code>setProductUsed(ProductNames.PRODUCT_DOCK | ProductNames.PRODUCT_GRIDS)</code> so that we don't initialize
	 * UIDefaults needed by any other products. If you use this class as part of JIDE Common Layer open source project,
	 * you should call <code>setProductUsed(ProductNames.PRODUCT_COMMON)</code>. If you want to use all JIDE products,
	 * you should call <code>setProductUsed(ProductNames.PRODUCT_ALL)</code>
	 *
	 * @param productsUsed a bit-wise OR of product values defined in {@link ProductNames}.
	 */
	public static void setProductsUsed(int productsUsed) {
		_productsUsed = productsUsed;
	}

	/**
	 * Checks if the current L&F uses decorated frames.
	 *
	 * @return true if the current L&F uses decorated frames. Otherwise false.
	 */
	public static boolean isCurrentLnfDecorated() {
		return !isLnfInstalled(SYNTHETICA_LNF) || !isLnfInUse(SYNTHETICA_LNF);
	}

	/**
	 * Gets the flag indicating if JIDE will try to load the LnF class when {@link #isLnfInstalled(String)} is invoked.
	 *
	 * @return true if JIDE will try to load the LnF class. Otherwise false
	 * @see #setLoadLookAndFeelClass(boolean)
	 * @since 3.2.0
	 */
	public static boolean isLoadLookAndFeelClass() {
		return _loadLookAndFeelClass;
	}

	/**
	 * Sets the flag indicating if JIDE will try to load the L&F class when {@link #isLnfInstalled(String)} is invoked.
	 * <p/>
	 * By default, this flag is true. However, it may cause unexpected class loading, which may be a performance issue
	 * for web start applications. If this is a concern to your application, please try to set this flag to false and
	 * invoke {@link #setLnfInstalled(String, boolean)} to make {@link #isLnfInstalled(String)} returns correct value as
	 * you wish.
	 *
	 * @param loadLookAndFeelClass the flag
	 * @since 3.2.0
	 */
	public static void setLoadLookAndFeelClass(boolean loadLookAndFeelClass) {
		_loadLookAndFeelClass = loadLookAndFeelClass;
	}

	public static boolean isMnemonicHidden() {
		return !UIManager.getBoolean("Button.showMnemonics");
	}
}

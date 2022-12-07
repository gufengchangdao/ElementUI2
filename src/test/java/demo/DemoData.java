package demo;

import com.element.ui.icons.IconsFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

/**
 * 演示伪造的数据
 */
public class DemoData {
	public static String getArticle() {
		try (InputStream inputStream = DemoData.class.getResourceAsStream("benghuai.txt")) {
			if (inputStream == null) return "benghuai.txt 文件没有找到";
			try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
				String s;
				StringBuilder b = new StringBuilder();
				while ((s = br.readLine()) != null)
					b.append(s).append("\n");
				return b.toString();
			}
		} catch (IOException e) {
			return "benghuai.txt 文件没有找到";
		}
	}

	public static final String LONG_TEXT = """
			……德莉莎，我的那些「老朋友」们，赤鸢仙人、理之律者……他们是真正的好人，一定会帮助你走出一条属于自己的道路。
			而「比安卡」，我最后的学生啊……我操弄了你的人生，规划了你的命途，一边对你付出栽培的真情，一边又把你当作棋子予取予求。你知道吗……\s
			我在最后的这十年中对你所展示的一切，不过是为了像这般寂灭之后，有人能为我立一块无字之碑罢了。
			我不需要有人能评价我，毕竟这漫长而又短暂的五百年，不过是一个男人，为了自己的一厢情愿，所能付出的一切罢了。
			他如今已经抵达了旅途的尽头——他所要完成的、所要见证的、所要救赎的……它们已经在虚数之树中生根发芽，只等待着那迷路的信使，将最后的\s
			消息在一切都结束前送达。那一刻，不会太早，也不会太晚，它会成为跨越死亡的镇魂曲，它会成为奇迹降临的赞美诗。世界将在那一刻只为了一\s
			个人而转动……让那被强加的罪孽烟消云散，让那被终结的意志继续向前。
			卑鄙，将由我带进坟墓；光明，会因你伸向未来。
			……
						
			我愚弄了友人，愚弄了至亲，愚弄了世界和它之上的规则……只为了给予那唯一真实的你，以第二次生命。
			我回来了……卡莲。
			……
						
			当一个人，正真想改变世界的时候，才会发现个人的力量是多么渺小。圣女为民众付出了一切，可换来的却是无情的镣铐和绞索。
			世界如此混沌，它既不公平，也不合理。它迫害英雄，滋养恶类，丑陋遍地，美好无存。
			呵，世界的恶意，就由恶人来斩断吧。
			这是她和明天之间的距离，这是世界对她的无情反扑，但她的信徒，绝不会因此放弃。
			……
						
			人一旦魂飞魄散，就无法再起死回生。世界允许意识匹配新的容器，却不允许容器收集消散的意识。
			想要拯救唯一的她，我只能，在过去创造出新的可能。这另一个未来，将是，属于她的时刻。
			……
						
			卡莲，活下去。
			""";

	public static final String[] NAMES = new String[]{
			"卡莲", "符华", "德丽莎", "姬子", "布洛妮娅", "芽衣", "琪亚娜", "八重樱", "丽塔", "莉莉娅", "罗莎莉娅", "希儿",
			"幽兰黛尔", "明日香", "菲谢尔", "渡鸦", "爱莉希雅", "梅比乌斯", "卡萝尔", "帕朵菲莉丝", "阿波尼亚", "伊甸", "格蕾修",
			"维尔薇", "李素裳", "玉骑士·月痕", "真我·人之律者", "螺旋·愚戏之匣", "繁星·绘世之卷", "黄金·璀耀之歌", "戒律·深罪之槛",
			"空梦·掠集之兽", "天元骑英", "缭乱星棘", "次生银翼", "薪炎之律者", "甜辣女孩", "粉色妖精小姐♪", "午夜苦艾",
			"无限·噬界之蛇", "断罪皇女", "识之律者", "明日福音", "雷之律者"
	};
	public static final int[] MNEMONICS = new int[]{
			KeyEvent.VK_M,
			KeyEvent.VK_C,
			KeyEvent.VK_O,
			KeyEvent.VK_T,
			KeyEvent.VK_N,
			KeyEvent.VK_F,
			KeyEvent.VK_S,
			KeyEvent.VK_J
	};
	public static final ImageIcon[] ICONS = new ImageIcon[]{
			IconsFactory.getImageIcon(DemoData.class, "icons/email.gif"),
			IconsFactory.getImageIcon(DemoData.class, "icons/calendar.gif"),
			IconsFactory.getImageIcon(DemoData.class, "icons/contacts.gif"),
			IconsFactory.getImageIcon(DemoData.class, "icons/tasks.gif"),
			IconsFactory.getImageIcon(DemoData.class, "icons/notes.gif"),
			IconsFactory.getImageIcon(DemoData.class, "icons/folder.gif"),
			IconsFactory.getImageIcon(DemoData.class, "icons/shortcut.gif"),
			IconsFactory.getImageIcon(DemoData.class, "icons/journal.gif")
	};

	public static DefaultListModel<String> createCountryListModel() {
		final String[] names = getCountryNames();
		final DefaultListModel<String> model = new DefaultListModel<>();
		for (String name : names) {
			model.addElement(name);
		}
		return model;
	}

	public static DefaultComboBoxModel<String> createCountryComboBoxModel() {
		final String[] names = getCountryNames();
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (String name : names) {
			model.addElement(name);
		}
		return model;
	}

	public static DefaultListModel<String> createFontListModel() {
		final String[] names = getFontNames();
		final DefaultListModel<String> model = new DefaultListModel<>();
		for (String name : names) {
			model.addElement(name);
		}
		return model;
	}

	public static DefaultComboBoxModel<String> createFontComboBoxModel() {
		final String[] names = getFontNames();
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (String name : names) {
			model.addElement(name);
		}
		return model;
	}

	public static String[] getFontNames() {
		return new String[]{
				"Agency FB", "Aharoni", "Algerian", "Andalus", "Angsana New", "AngsanaUPC", "Aparajita",
				"Arabic Typesetting", "Arial", "Arial Black", "Arial Narrow", "Arial Rounded MT Bold",
				"Arial Unicode MS", "Baskerville Old Face", "Batang", "BatangChe", "Bauhaus 93", "Bell MT",
				"Berlin Sans FB", "Berlin Sans FB Demi", "Bernard MT Condensed", "Blackadder ITC", "Bodoni MT",
				"Bodoni MT Black", "Bodoni MT Condensed", "Bodoni MT Poster Compressed", "Book Antiqua",
				"Bookman Old Style", "Bookshelf Symbol 7", "Bradley Hand ITC", "Britannic Bold", "Broadway",
				"Browallia New", "BrowalliaUPC", "Brush Script MT", "Calibri", "Californian FB", "Calisto MT", "Cambria",
				"Cambria Math", "Candara", "Castellar", "Centaur", "Century", "Century Gothic", "Century Schoolbook",
				"Chiller", "Colonna MT", "Comic Sans MS", "Consolas", "Constantia", "Cooper Black",
				"Copperplate Gothic Bold", "Copperplate Gothic Light", "Corbel", "Cordia New", "CordiaUPC", "Courier New",
				"Curlz MT", "DaunPenh", "David", "DFKai-SB", "Dialog", "DialogInput", "DilleniaUPC", "DokChampa", "Dotum",
				"DotumChe", "Ebrima", "Edwardian Script ITC", "Elephant", "Engravers MT", "Estrangelo Edessa",
				"EucrosiaUPC", "Euphemia", "Euro Sign", "FangSong", "Felix Titling", "Footlight MT Light", "Forte",
				"Franklin Gothic Book", "Franklin Gothic Demi", "Franklin Gothic Demi Cond", "Franklin Gothic Heavy",
				"Franklin Gothic Medium", "Franklin Gothic Medium Cond", "FrankRuehl", "FreesiaUPC", "Freestyle Script",
				"French Script MT", "Gabriola", "Garamond", "Gautami", "Georgia", "Gigi", "Gill Sans MT",
				"Gill Sans MT Condensed", "Gill Sans MT Ext Condensed Bold", "Gill Sans Ultra Bold",
				"Gill Sans Ultra Bold Condensed", "Gisha", "Gloucester MT Extra Condensed", "Goudy Old Style",
				"Goudy Stout", "Gulim", "GulimChe", "Gungsuh", "GungsuhChe", "Haettenschweiler", "Harlow Solid Italic",
				"Harrington", "High Tower Text", "Impact", "Imprint MT Shadow", "Informal Roman", "IrisUPC",
				"Iskoola Pota", "JasmineUPC", "Jokerman", "Juice ITC", "KaiTi", "Kalinga", "Kartika", "Khmer UI",
				"KodchiangUPC", "Kokila", "Kristen ITC", "Kunstler Script", "Lao UI", "Latha", "Leelawadee", "Levenim MT",
				"LilyUPC", "Lucida Bright", "Lucida Calligraphy", "Lucida Console", "Lucida Fax", "Lucida Handwriting",
				"Lucida Sans", "Lucida Sans Typewriter", "Lucida Sans Unicode", "Magneto", "Maiandra GD", "Malgun Gothic",
				"Mangal", "Marlett", "Matura MT Script Capitals", "Meiryo", "Meiryo UI", "Microsoft Himalaya",
				"Microsoft JhengHei", "Microsoft New Tai Lue", "Microsoft PhagsPa", "Microsoft Sans Serif",
				"Microsoft Tai Le", "Microsoft Uighur", "Microsoft YaHei", "Microsoft Yi Baiti", "MingLiU",
				"MingLiU-ExtB", "MingLiU_HKSCS", "MingLiU_HKSCS-ExtB", "Miriam", "Miriam Fixed", "Mistral",
				"Modern No. 20", "Mongolian Baiti", "Monospaced", "Monotype Corsiva", "MoolBoran", "MS Gothic",
				"MS Mincho", "MS Outlook", "MS PGothic", "MS PMincho", "MS Reference Sans Serif",
				"MS Reference Specialty", "MS UI Gothic", "MT Extra", "MV Boli", "Narkisim", "Niagara Engraved",
				"Niagara Solid", "Niamey", "NSimSun", "Nyala", "OCR A Extended", "OCR B MT", "OCR-A II",
				"Old English Text MT", "Onyx", "Palace Script MT", "Palatino Linotype", "Papyrus", "Parchment",
				"Perpetua", "Perpetua Titling MT", "Plantagenet Cherokee", "Playbill", "PMingLiU", "PMingLiU-ExtB",
				"Poor Richard", "Pristina", "QuickType", "QuickType Condensed", "QuickType II", "QuickType II Condensed",
				"QuickType II Mono", "QuickType II Pi", "QuickType Mono", "QuickType Pi", "Raavi", "Rage Italic", "Ravie",
				"Rockwell", "Rockwell Condensed", "Rockwell Extra Bold", "Rod", "Sakkal Majalla", "SansSerif",
				"Script MT Bold", "Segoe Print", "Segoe Script", "Segoe UI", "Segoe UI Light", "Segoe UI Semibold",
				"Segoe UI Symbol", "Serif", "Shonar Bangla", "Showcard Gothic", "Shruti", "SimHei", "Simplified Arabic",
				"Simplified Arabic Fixed", "SimSun", "SimSun-ExtB", "Snap ITC", "Stencil", "SWGamekeys MT", "Sylfaen",
				"Symbol", "Tahoma", "Tempus Sans ITC", "Times New Roman", "Traditional Arabic", "Trebuchet MS", "Tunga",
				"Tw Cen MT", "Tw Cen MT Condensed", "Tw Cen MT Condensed Extra Bold", "Untitled", "Utsaah", "Vani",
				"Verdana", "Vijaya", "Viner Hand ITC", "Vivaldi", "Vladimir Script", "Vodafone Rg", "Vrinda",
				"Webdings", "Wide Latin", "Wingdings", "Wingdings 2", "Wingdings 3", "ZWAdobeF"
		};
	}

	public static String[] getCountryNames() {
		return new String[]{
				"Andorra",
				"United Arab Emirates",
				"Afghanistan",
				"Antigua And Barbuda",
				"Anguilla",
				"Albania",
				"Armenia",
				"Netherlands Antilles",
				"Angola",
				"Antarctica",
				"Argentina",
				"American Samoa",
				"Austria",
				"Australia",
				"Aruba",
				"Azerbaijan",
				"Bosnia And Herzegovina",
				"Barbados",
				"Bangladesh",
				"Belgium",
				"Burkina Faso",
				"Bulgaria",
				"Bahrain",
				"Burundi",
				"Benin",
				"Bermuda",
				"Brunei Darussalam",
				"Bolivia",
				"Brazil",
				"Bahamas",
				"Bhutan",
				"Bouvet Island",
				"Botswana",
				"Belarus",
				"Belize",
				"Canada",
				"Cocos (Keeling) Islands",
				"Congo, The Democratic Republic Of The",
				"Central African Republic",
				"Congo",
				"Switzerland",
				"Cook Islands",
				"Chile",
				"Cameroon",
				"China",
				"Colombia",
				"Costa Rica",
				"Cuba",
				"Cape Verde",
				"Christmas Island",
				"Cyprus",
				"Czech Republic",
				"Germany",
				"Djibouti",
				"Denmark",
				"Dominica",
				"Dominican Republic",
				"Algeria",
				"Ecuador",
				"Estonia",
				"Egypt",
				"Western Sarara",
				"Eritrea",
				"Spain",
				"Ethiopia",
				"Finland",
				"Fiji",
				"Falkland Islands (Malvinas)",
				"Micronesia, Federated States Of",
				"Faroe Islands",
				"France",
				"Gabon",
				"United Kingdom",
				"Grenada",
				"Georgia",
				"French Guiana",
				"Ghana",
				"Gibraltar",
				"Greenland",
				"Gambia",
				"Guinea",
				"Guadeloupe",
				"Equatorial Guinea",
				"Greece",
				"South Georgia And The South Sandwich Islands",
				"Guatemala",
				"Guam",
				"Guinea-bissau",
				"Guyana",
				"Hong Kong",
				"Heard Island And Mcdonald Islands",
				"Honduras",
				"Croatia",
				"Haiti",
				"Hungary",
				"Indonesia",
				"Ireland",
				"Israel",
				"India",
				"British Indian Ocean Territory",
				"Iraq",
				"Iran, Islamic Republic Of",
				"Iceland",
				"Italy",
				"Jamaica",
				"Jordan",
				"Japan",
				"Kenya",
				"Kyrgyzstan",
				"Cambodia",
				"Kiribati",
				"Comoros",
				"Saint Kitts And Nevis",
				"Korea, Democratic People'S Republic Of",
				"Korea, Republic Of",
				"Kuwait",
				"Cayman Islands",
				"Kazakhstan",
				"Lao People'S Democratic Republic",
				"Lebanon",
				"Saint Lucia",
				"Liechtenstein",
				"Sri Lanka",
				"Liberia",
				"Lesotho",
				"Lithuania",
				"Luxembourg",
				"Latvia",
				"Libyan Arab Jamabiriya",
				"Morocco",
				"Monaco",
				"Moldova, Republic Of",
				"Madagascar",
				"Marshall Islands",
				"Macedonia, The Former Yugoslav Repu8lic Of",
				"Mali",
				"Myanmar",
				"Mongolia",
				"Macau",
				"Northern Mariana Islands",
				"Martinique",
				"Mauritania",
				"Montserrat",
				"Malta",
				"Mauritius",
				"Maldives",
				"Malawi",
				"Mexico",
				"Malaysia",
				"Mozambique",
				"Namibia",
				"New Caledonia",
				"Niger",
				"Norfolk Island",
				"Nigeria",
				"Nicaragua",
				"Netherlands",
				"Norway",
				"Nepal",
				"Niue",
				"New Zealand",
				"Oman",
				"Panama",
				"Peru",
				"French Polynesia",
				"Papua New Guinea",
				"Philippines",
				"Pakistan",
				"Poland",
				"Saint Pierre And Miquelon",
				"Pitcairn",
				"Puerto Rico",
				"Portugal",
				"Palau",
				"Paraguay",
				"Qatar",
				"Romania",
				"Russian Federation",
				"Rwanda",
				"Saudi Arabia",
				"Solomon Islands",
				"Seychelles",
				"Sudan",
				"Sweden",
				"Singapore",
				"Saint Helena",
				"Slovenia",
				"Svalbard And Jan Mayen",
				"Slovakia",
				"Sierra Leone",
				"San Marino",
				"Senegal",
				"Somalia",
				"Suriname",
				"Sao Tome And Principe",
				"El Salvador",
				"Syrian Arab Republic",
				"Swaziland",
				"Turks And Caicos Islands",
				"Chad",
				"French Southern Territories",
				"Togo",
				"Thailand",
				"Tajikistan",
				"Tokelau",
				"Turkmenistan",
				"Tunisia",
				"Tonga",
				"East Timor",
				"Turkey",
				"Trinidad And Tobago",
				"Tuvalu",
				"Taiwan, Province Of China",
				"Tanzania, United Republic Of",
				"Ukraine",
				"Uganda",
				"United States Minor Outlying Islands",
				"United States",
				"Uruguay",
				"Uzbekistan",
				"Venezuela",
				"Virgin Islands, British",
				"Virgin Islands, U.S.",
				"Viet Nam",
				"Vanuatu",
				"Wallis And Futuna",
				"Samoa",
				"Yemen",
				"Mayotte",
				"Yugoslavia",
				"South Africa",
				"Zambia",
				"Zimbabwe"
		};
	}

	static String[] QUOTE_COLUMNS_WITH_SELECTED = new String[]{"Selected", "Symbol", "Name", "Last", "Change", "Volume"};

	static Object[][] QUOTES_WITH_SELECTED = new Object[][]{
			new Object[]{false, "AA", "ALCOA INC", 32.88, 0.53, 4156200},
			new Object[]{false, "AIG", "AMER INTL GROUP", 69.53, -0.58, 4369200},
			new Object[]{false, "AXP", "AMER EXPRESS CO", 48.90, -0.35, 4103600},
			new Object[]{false, "BA", "BOEING CO", 49.14, -0.18, 3573700},
			new Object[]{false, "CA", "CITIGROUP", 44.21, -0.89, 28594900},
			new Object[]{false, "CATA", "CATERPILLAR INC", 79.40, +0.62, 1458200},
			new Object[]{false, "DDA", "DU PONT CO", 42.62, -0.14, 1832700},
			new Object[]{false, "DISA", "WALT DISNEY CO", 23.87, -0.32, 4443600},
			new Object[]{false, "GEA", "GENERAL ELEC CO", 33.37, +0.24, 31429500},
			new Object[]{false, "GMA", "GENERAL MOTORS", 43.94, -0.20, 3722100},
			new Object[]{false, "HDA", "HOME DEPOT INC", 34.33, -0.18, 5367900},
			new Object[]{false, "HONA", "HONEYWELL INTL", 35.70, +0.23, 4092100},
			new Object[]{false, "HPQA", "HEWLETT-PACKARD", 19.65, -0.25, 11003000},
			new Object[]{false, "IBMA", "INTL BUS MACHINE", 84.02, -0.11, 6880500},
			new Object[]{false, "INTCA", "INTEL CORP", 23.15, -0.23, 95177008},
			new Object[]{false, "JNJA", "JOHNSON&JOHNSON", 55.35, -0.57, 5428000},
			new Object[]{false, "JPMA", "JP MORGAN CHASE", 36.00, -0.45, 12135300},
			new Object[]{false, "KOA", "COCA COLA CO", 50.84, -0.32, 4143600},
			new Object[]{false, "MCDA", "MCDONALDS CORP", 27.91, +0.12, 6110800},
			new Object[]{false, "MMMA", "3M COMPANY", 88.62, +0.43, 2073800},
			new Object[]{false, "MOA", "ALTRIA GROUP", 48.20, -0.80, 6005500},
			new Object[]{false, "MRKA", "MERCK & CO", 44.71, -0.97, 5472100},
			new Object[]{false, "MSFTA", "MICROSOFT CP", 27.87, -0.26, 46717716},
			new Object[]{false, "PFEA", "PFIZER INC", 32.58, -1.43, 28783200},
			new Object[]{false, "PGA", "PROCTER & GAMBLE", 55.01, -0.07, 5538400},
			new Object[]{false, "SBCA", "SBC COMMS", 23.00, -0.54, 6423400},
			new Object[]{false, "UTXA", "UNITED TECH CP", 91.00, +1.16, 1868600},
			new Object[]{false, "VZA", "VERIZON COMMS", 34.81, -0.35, 4182600},
			new Object[]{false, "WMTA", "WAL-MART STORES", 52.33, -0.25, 6776700},
			new Object[]{false, "XOMA", "EXXON MOBIL", 45.32, -0.14, 7838100}
	};

	static String[] QUOTE_COLUMNS = new String[]{"Symbol", "Name", "Last", "Change", "Volume"};

	static Object[][] QUOTES = new Object[][]{
			new Object[]{"AA", "ALCOA INC", 32.88, 0.53, 4156200},
			new Object[]{"AIG", "AMER INTL GROUP", 69.53, -0.58, 4369200},
			new Object[]{"AXP", "AMER EXPRESS CO", 48.90, -0.35, 4103600},
			new Object[]{"BA", "BOEING CO", 49.14, -0.18, 3573700},
			new Object[]{"CA", "CITIGROUP", 44.21, -0.89, 28594900},
			new Object[]{"CATA", "CATERPILLAR INC", 79.40, +0.62, 1458200},
			new Object[]{"DDA", "DU PONT CO", 42.62, -0.14, 1832700},
			new Object[]{"DISA", "WALT DISNEY CO", 23.87, -0.32, 4443600},
			new Object[]{"GEA", "GENERAL ELEC CO", 33.37, +0.24, 31429500},
			new Object[]{"GMA", "GENERAL MOTORS", 43.94, -0.20, 3722100},
			new Object[]{"HDA", "HOME DEPOT INC", 34.33, -0.18, 5367900},
			new Object[]{"HONA", "HONEYWELL INTL", 35.70, +0.23, 4092100},
			new Object[]{"HPQA", "HEWLETT-PACKARD", 19.65, -0.25, 11003000},
			new Object[]{"IBMA", "INTL BUS MACHINE", 84.02, -0.11, 6880500},
			new Object[]{"INTCA", "INTEL CORP", 23.15, -0.23, 95177008},
			new Object[]{"JNJA", "JOHNSON&JOHNSON", 55.35, -0.57, 5428000},
			new Object[]{"JPMA", "JP MORGAN CHASE", 36.00, -0.45, 12135300},
			new Object[]{"KOA", "COCA COLA CO", 50.84, -0.32, 4143600},
			new Object[]{"MCDA", "MCDONALDS CORP", 27.91, +0.12, 6110800},
			new Object[]{"MMMA", "3M COMPANY", 88.62, +0.43, 2073800},
			new Object[]{"MOA", "ALTRIA GROUP", 48.20, -0.80, 6005500},
			new Object[]{"MRKA", "MERCK & CO", 44.71, -0.97, 5472100},
			new Object[]{"MSFTA", "MICROSOFT CP", 27.87, -0.26, 46717716},
			new Object[]{"PFEA", "PFIZER INC", 32.58, -1.43, 28783200},
			new Object[]{"PGA", "PROCTER & GAMBLE", 55.01, -0.07, 5538400},
			new Object[]{"SBCA", "SBC COMMS", 23.00, -0.54, 6423400},
			new Object[]{"UTXA", "UNITED TECH CP", 91.00, +1.16, 1868600},
			new Object[]{"VZA", "VERIZON COMMS", 34.81, -0.35, 4182600},
			new Object[]{"WMTA", "WAL-MART STORES", 52.33, -0.25, 6776700},
			new Object[]{"XOMA", "EXXON MOBIL", 45.32, -0.14, 7838100}
	};


	public static TreeModel createSongTreeModel() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Songs");
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		HashMap<String, DefaultMutableTreeNode> albums = new HashMap<>();

		try {
			InputStream resource = DemoData.class.getClassLoader().getResourceAsStream("Library.txt.gz");
			if (resource == null) {
				return null;
			}
			InputStream in = new GZIPInputStream(resource);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			reader.readLine(); // skip first line
			do {
				String line = reader.readLine();
				if (line == null || line.length() == 0) {
					break;
				}
				String[] values = line.split("\t");
				String songName = "";
				String albumName = "";
				if (values.length >= 1) {
					songName = values[0];
				}
				if (values.length >= 2) {
					songName += " - " + values[1];
				}
				if (values.length >= 3) {
					albumName = values[2]; // artist
				}

				DefaultMutableTreeNode treeNode = albums.get(albumName);
				if (treeNode == null) {
					treeNode = new DefaultMutableTreeNode(albumName);
					albums.put(albumName, treeNode);
					root.add(treeNode);
				}
				treeNode.add(new DefaultMutableTreeNode(songName));
			}
			while (true);
			return treeModel;
		} catch (IOException e) {
			//noinspection CallToPrintStackTrace
			e.printStackTrace();
		}
		return null;
	}

	public static TableModel createSongTableModel() {
		try {
			InputStream resource = DemoData.class.getResourceAsStream("Library.txt.gz");
			if (resource == null) {
				return null;
			}
			InputStream in = new GZIPInputStream(resource);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			Vector<Vector<Object>> data = new Vector<>();

			String columnsLine = reader.readLine(); // skip first line
			String[] columnValues = columnsLine.split("\t");
			Vector<String> columnNames = new Vector<>(Arrays.asList(columnValues));
			do {
				String line = reader.readLine();
				if (line == null || line.length() == 0) {
					break;
				}
				String[] values = line.split("\t");
				Vector<Object> lineData = new Vector<>();
				if (values.length < 1)
					lineData.add(null); // song name
				else
					lineData.add(values[0]); // song name
				if (values.length < 2)
					lineData.add(null); // artist
				else
					lineData.add(values[1]); // artist
				if (values.length < 3)
					lineData.add(null); // album
				else
					lineData.add(values[2]); // album
				if (values.length < 4)
					lineData.add(null); // genre
				else
					lineData.add(values[3]); // genre
				if (values.length < 5)
					lineData.add(null); // time
				else
					lineData.add(values[4]); // time
				if (values.length < 6)
					lineData.add(null); // year
				else
					lineData.add(values[5]); // year
				data.add(lineData);
			}
			while (true);
			return new DefaultTableModel(data, columnNames);
		} catch (IOException e) {
			//noinspection CallToPrintStackTrace
			e.printStackTrace();
		}
		return null;
	}
}

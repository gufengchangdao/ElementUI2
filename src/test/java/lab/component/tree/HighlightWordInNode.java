package lab.component.tree;

import demo.SwingTestUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Objects;

/**
 * 节点高亮匹配输入框内容
 */
public class HighlightWordInNode extends JPanel {
	private final JTextField field = new JTextField("foo");
	private final JTree tree = new JTree() {
		@Override
		public void updateUI() {
			setCellRenderer(null);
			super.updateUI();
			setCellRenderer(new HighlightTreeCellRenderer());
			EventQueue.invokeLater(HighlightWordInNode.this::fireDocumentChangeEvent);
		}
	};

	private HighlightWordInNode() {
		super(new BorderLayout());
		field.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				fireDocumentChangeEvent();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				fireDocumentChangeEvent();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				/* not needed */
			}
		});
		JPanel n = new JPanel(new BorderLayout());
		n.add(field);
		n.setBorder(BorderFactory.createTitledBorder("Search"));

		add(n, BorderLayout.NORTH);
		add(new JScrollPane(tree));
		setPreferredSize(new Dimension(320, 240));
	}

	public void fireDocumentChangeEvent() {
		TreeCellRenderer r = tree.getCellRenderer();
		if (r instanceof HighlightTreeCellRenderer) {
			HighlightTreeCellRenderer renderer = (HighlightTreeCellRenderer) r;
			String q = field.getText();
			renderer.setQuery(q);
			TreePath root = tree.getPathForRow(0);
			collapseAll(tree, root);
			if (!q.isEmpty()) {
				searchTree(tree, root, q);
			}
		}
	}

	private static void searchTree(JTree tree, TreePath path, String q) {
		TreeNode node = (TreeNode) path.getLastPathComponent();
		if (Objects.isNull(node)) {
			return;
		} else if (node.toString().startsWith(q)) {
			tree.expandPath(path.getParentPath());
		}
		if (!node.isLeaf()) {
			// Java 9: Collections.list(node.children())
			Collections.list((Enumeration<?>) node.children())
					.forEach(n -> searchTree(tree, path.pathByAddingChild(n), q));
		}
	}

	private static void collapseAll(JTree tree, TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (!node.isLeaf()) {
			// Java 9: Collections.list(node.children())
			Collections.list((Enumeration<?>) node.children())
					.forEach(n -> collapseAll(tree, parent.pathByAddingChild(n)));
		}
		tree.collapsePath(parent);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JPanel p = SwingTestUtil.init(new MigLayout());
			p.add(new HighlightWordInNode());
			SwingTestUtil.test();
		});
	}
}

class HighlightTreeCellRenderer implements TreeCellRenderer {
	private static final Color SELECTION_BGC = new Color(0xDC_F0_FF);
	private static final Highlighter.HighlightPainter HIGHLIGHT = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
	private String query;
	private final JTextField renderer = new JTextField() {
		@Override
		public void updateUI() {
			super.updateUI();
			setOpaque(true);
			setBorder(BorderFactory.createEmptyBorder());
			setForeground(Color.BLACK);
			setBackground(Color.WHITE);
			setEditable(false);
		}
	};

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		String txt = Objects.toString(value, "");
		renderer.getHighlighter().removeAllHighlights();
		renderer.setText(txt);
		renderer.setBackground(selected ? SELECTION_BGC : Color.WHITE);
		if (query != null && !query.isEmpty() && txt.startsWith(query)) {
			try {
				renderer.getHighlighter().addHighlight(0, query.length(), HIGHLIGHT);
			} catch (BadLocationException ex) {
				// should never happen
				RuntimeException wrap = new StringIndexOutOfBoundsException(ex.offsetRequested());
				wrap.initCause(ex);
				throw wrap;
			}
		}
		return renderer;
	}
}

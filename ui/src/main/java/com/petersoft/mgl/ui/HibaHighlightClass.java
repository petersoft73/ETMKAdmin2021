package com.petersoft.mgl.ui;

import com.petersoft.mgl.model.Hiba;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class HibaHighlightClass extends JPanel {
    private static Hiba hiba;
    private MainFrame frame;
    private List<Hiba> hibaList;
    private static String typedText;

    public HibaHighlightClass(List<Hiba> hibaList) {
        this.hibaList = hibaList;
        highlight();
    }

    public HibaHighlightClass(MainFrame frame, List<Hiba> hibalist) {
        this.frame = frame;
        this.hibaList = hibalist;
        this.setPreferredSize(new Dimension(300, 200));
        highlight();
    }

    private static String getHibaDisplayText(Hiba h) {
        return String.format("%s", h.getLeiras());
    }

    private ListCellRenderer<? super Hiba> createListRenderer(JTextField filterField) {
        return new DefaultListCellRenderer() {
            private Color background = new Color(0, 100, 255, 15);
            private Color defaultBackground = (Color) UIManager.get("List.background");

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (c instanceof JLabel) {
                    hiba = (Hiba) value;
                    JLabel original = (JLabel) c;
                    LabelHighlighted label = new LabelHighlighted();
                    label.setText(getHibaDisplayText(hiba));
                    label.setFont(original.getFont().deriveFont(14f).deriveFont(Font.PLAIN));
                    label.setBackground(original.getBackground());
                    label.setForeground(original.getForeground());
                    label.setOpaque(original.isOpaque());
                    label.setHorizontalTextPosition(original.getHorizontalTextPosition());
                    label.highlightText(filterField.getText());
                    return label;
                }
                return c;
            }
        };
    }

    public static Hiba getHiba() {
        return hiba;
    }

    public static String getTypedText() {
        return typedText;
    }

    public void highlight() {
        DefaultListModel<Hiba> model = new DefaultListModel<>();
        hibaList.forEach(model::addElement);
        JList<Hiba> jList = new JList<>(model);
        JListFilterDecorator decorator = JListFilterDecorator
                .decorate(jList, this::hibaFilter);
        jList.setCellRenderer(createListRenderer(decorator.getFilterField()));
        add(decorator.getContentPanel());
        setVisible(true);
    }

    private boolean hibaFilter(Hiba h, String str) {
        typedText = str;
        return getHibaDisplayText(h).toLowerCase().contains(str.toLowerCase());
    }

}

class LabelHighlighted extends JLabel {
    private List<Rectangle2D> rectangles = new ArrayList<>();
    private Color colorHighlight = Color.YELLOW;

    public void reset() {
        rectangles.clear();
        repaint();
    }

    public void highlightText(String textToHighlight) {
        if (textToHighlight == null) {
            return;
        }
        reset();

        final String textToMatch = textToHighlight.toLowerCase().trim();
        if (textToMatch.length() == 0) {
            return;
        }
        textToHighlight = textToHighlight.trim();

        final String labelText = getText().toLowerCase();
        if (labelText.contains(textToMatch)) {
            FontMetrics fm = getFontMetrics(getFont());
            float w = -1;
            final float h = fm.getHeight() - 1;
            int i = 0;
            while (true) {
                i = labelText.indexOf(textToMatch, i);
                if (i == -1) {
                    break;
                }
                if (w == -1) {
                    String matchingText = getText().substring(i,
                            i + textToHighlight.length());
                    w = fm.stringWidth(matchingText);
                }
                String preText = getText().substring(0, i);
                float x = fm.stringWidth(preText);
                rectangles.add(new Rectangle2D.Float(x, 1, w, h));
                i = i + textToMatch.length();
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (rectangles.size() > 0) {
            Graphics2D g2d = (Graphics2D) g;
            Color c = g2d.getColor();
            for (Rectangle2D rectangle : rectangles) {
                g2d.setColor(colorHighlight);
                g2d.fill(rectangle);
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.draw(rectangle);
            }
            g2d.setColor(c);
        }
        super.paintComponent(g);
    }
}

class JListFilterDecorator {
    private JPanel contentPanel;
    private JTextField filterField;

    public static <T> JListFilterDecorator decorate(JList<T> jList, BiPredicate<T, String> userFilter) {
        if (!(jList.getModel() instanceof DefaultListModel)) {
            throw new IllegalArgumentException("List model must be an instance of DefaultListModel");
        }
        DefaultListModel<T> model = (DefaultListModel<T>) jList.getModel();
        List<T> items = getItems(model);
        JTextField textField = new JTextField();
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filter();
            }

            private void filter() {
                model.clear();
                String s = textField.getText();
                for (T item : items) {
                    if (userFilter.test(item, s)) {
                        model.addElement(item);
                    }
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textField, BorderLayout.NORTH);
        JScrollPane pane = new JScrollPane(jList);
        panel.add(pane);


        JListFilterDecorator decorator = new JListFilterDecorator();
        decorator.contentPanel = panel;
        decorator.filterField = textField;
        return decorator;
    }

    private static <T> List<T> getItems(DefaultListModel<T> model) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            list.add(model.elementAt(i));
        }
        return list;
    }

    public JTextField getFilterField() {
        return filterField;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}

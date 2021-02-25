package com.petersoft.mgl.ui.highlighter;


import com.petersoft.mgl.model.Hiba;
import com.petersoft.mgl.ui.JavitasInputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class JListHighlightedFilterExample extends JDialog{
    private static Hiba hiba;
    private static String typedText;
    private static JButton visszaButton, kivalasztButton;





    public static void start(List<Hiba> hibaList) {
        DefaultListModel<Hiba> model = new DefaultListModel<>();
        hibaList.forEach(model::addElement);
        JList<Hiba> jList = new JList<>(model);
        JListFilterDecorator decorator = JListFilterDecorator
                .decorate(jList, JListHighlightedFilterExample::hibaFilter);
        jList.setCellRenderer(createListRenderer(decorator.getFilterField()));

        visszaButton = new JButton("Vissza");
        kivalasztButton = new JButton("Kiválaszt");
        JFrame frame = createFrame();
        frame.add(decorator.getContentPanel(), BorderLayout.CENTER);
        frame.add(visszaButton, BorderLayout.SOUTH);
        frame.add(kivalasztButton, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        visszaButton.addActionListener(e -> frame.dispose());
        kivalasztButton.addActionListener(e -> {
            if (jList.getSelectedIndex() != -1) {
                hiba = jList.getSelectedValue();
                typedText = hiba.getLeiras();

            } else {
                hiba = new Hiba();
                typedText = decorator.getFilterField().getText();
                hiba.setLeiras(typedText);
            }
            JavitasInputPanel.setHiba(hiba);
            JavitasInputPanel.setHibaTextArea(typedText);
//            inputPanel.setHiba(hiba);
//            inputPanel.setHibaTextArea(typedText);
          //  frame.setVisible(false);
            //  System.out.println("Hiba: " + hiba + " " + "Text: " + typedText);
            frame.dispose();
        });

        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    hiba = jList.getSelectedValue();
                    typedText = hiba.getLeiras();
                    JavitasInputPanel.setHiba(hiba);
                    JavitasInputPanel.setHibaTextArea(typedText);
                    frame.dispose();
                }
            }
        });
    }

    public static Hiba getHiba() {
        return hiba;
    }

    public static String getTypedText() {
        return typedText;
    }


    private static boolean hibaFilter(Hiba h, String textToFilter) {
        if (textToFilter.isEmpty()) {
            return true;
        }
        return getHibaDisplayText(h).toLowerCase()
                .contains(textToFilter.toLowerCase());
//        try {//Use following instead of the above line to support regex filtering..
//            return getEmployeeDisplayText(h).matches(textToFilter);
//        } catch (Exception e) {
//            return false;
//        }
    }

    private static String getHibaDisplayText(Hiba h) {
        return String.format("%s", h.getLeiras());
    }

    private static ListCellRenderer<? super Hiba> createListRenderer(JTextField filterField) {
        return new DefaultListCellRenderer() {
            private Color background = new Color(0, 100, 255, 15);
            private Color defaultBackground = (Color) UIManager.get("List.background");

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                this.setFont(this.getFont().deriveFont(14f).deriveFont(Font.PLAIN));
                Hiba h = (Hiba) value;
                String displayText = getHibaDisplayText(h);
                displayText = HtmlHighlighter.highlightText(displayText, filterField.getText());
                setText(displayText);
                if (!isSelected) {
                    setBackground(index % 2 == 0 ? background : defaultBackground);
                }
                return this;
            }
        };
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame("JList Example");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(600, 300));
        return frame;
    }
}
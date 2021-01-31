package com.petersoft.mgl.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

class HibaListDialog extends JDialog {
    private final List<String> hibaList;
    private final JList<String> hibaJList;
    private JButton kivalasztButton, visszaButton;
    private JavitasInputPanel javitasInputPanel;
    private String hibaString;


    public HibaListDialog(List<String> hibaList) {

        this.hibaList = hibaList;
        this.hibaJList = new JList(hibaList.toArray());
        setSize(1100, 300);
        setDialogHibaList();
        JPanel dialogPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        setLocationRelativeTo(null);
        dialogPanel.add(new JScrollPane(hibaJList));
        dialogPanel.setVisible(true);
        add(dialogPanel);
        setButtons();
        buttonPanel.add(visszaButton);
        buttonPanel.add(kivalasztButton);
        add(buttonPanel, BorderLayout.SOUTH);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    private void setDialogHibaList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        hibaList.forEach(e -> model.addElement(e));
        //  TableModel tableModel = new HibaTableModel(hibaList);
        hibaJList.setModel(model);
    }

    private void setButtons() {
        kivalasztButton = new JButton("Kiválasztás");
        visszaButton = new JButton("Vissza");
        kivalasztButton.addActionListener(e -> {
            hibaString = hibaJList.getSelectedValue();
            dispose();
        });




        hibaJList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //   int selectedRow = hibaJList.getSelectedRow();
                    hibaString = hibaJList.getSelectedValue();
                    dispose();
                }
            }
        });

        visszaButton.addActionListener(e -> {
            hibaString = "";
            setVisible(false);
        });
    }

    public String getSelectedHiba() {
        return this.hibaString;
    }
}

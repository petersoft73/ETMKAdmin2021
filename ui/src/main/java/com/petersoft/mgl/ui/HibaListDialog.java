package com.petersoft.mgl.ui;

import com.petersoft.mgl.model.Hiba;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class HibaListDialog extends JDialog {
    //    private final List<Hiba> hibaList;
//    private final JList<Hiba> hibaJList;
    private JButton kivalasztButton, visszaButton;
    private JavitasInputPanel javitasInputPanel;
    private Hiba hiba;
    private String typedText;


    public HibaListDialog(List<Hiba> hibaList) {

//        this.hibaList = hibaList;
//        this.hibaJList = new JList(hibaList.toArray());
        setSize(1100, 300);
        //   setDialogHibaList();
        JPanel dialogPanel = new HibaHighlightClass(hibaList);
        JPanel buttonPanel = new JPanel();
        setLocationRelativeTo(null);
        // dialogPanel.add(new JScrollPane(hibaJList));
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
        DefaultListModel<Hiba> model = new DefaultListModel<>();
        //     hibaList.forEach(e -> model.addElement(e));
        //  TableModel tableModel = new HibaTableModel(hibaList);
        //    hibaJList.setModel(model);
    }

    private void setButtons() {
        kivalasztButton = new JButton("Kiválasztás");
        visszaButton = new JButton("Vissza");
        kivalasztButton.addActionListener(e -> {
            hiba = HibaHighlightClass.getHiba() == null ? null : HibaHighlightClass.getHiba();
            typedText = hiba == null ? HibaHighlightClass.getTypedText() : hiba.getLeiras();
            dispose();
        });


//
//        hibaJList.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                if (e.getClickCount() == 2) {
//                    //   int selectedRow = hibaJList.getSelectedRow();
//                    hiba = hibaJList.getSelectedValue();
//                    dispose();
//                }
//            }
//        });

        visszaButton.addActionListener(e -> {
            setVisible(false);
        });
    }

    public Hiba getSelectedHiba() {
        return this.hiba;
    }

    public String getTypedText() {
        return typedText;
    }
}

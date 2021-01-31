package com.petersoft.mgl.ui;

import com.petersoft.mgl.model.Lepcso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

class HianyzoKarbListDialog extends JDialog {
    private final List<Lepcso> hianyzoKarbList;
    private final DefaultListModel<Lepcso> listModel = new DefaultListModel<>();
    private final JPanel buttonPanel = new JPanel();
    private Lepcso lepcso;
    private final JList<Lepcso> hianyzoList = new JList<>();
    private final KarbanTartasInputPanel karbanTartasInputPanel;

    public HianyzoKarbListDialog(KarbanTartasInputPanel karbanTartasInputPanel, List<Lepcso> hianyzoKarbList) {
        this.karbanTartasInputPanel = karbanTartasInputPanel;
        this.setTitle("El nem végzett karbantartások");

        this.hianyzoKarbList = hianyzoKarbList;

        setSize(600, 300);
        setLocationRelativeTo(null);
        setHianyzoLepcsoListDialog();
        hianyzoList.setModel(listModel);
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        JScrollPane listContainer = new JScrollPane(hianyzoList);
        listContainer.setMaximumSize(new Dimension(90, 150));
        dialogPanel.add(Box.createRigidArea(new Dimension(190, 20)));
        dialogPanel.add(listContainer, -1);
        dialogPanel.add(new JLabel(hianyzoList.getModel().getSize() + " lépcső összesen"));
        hianyzoList.addListSelectionListener(e -> lepcso = hianyzoList.getSelectedValue());
        add(dialogPanel, BorderLayout.CENTER);
        setButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        setModalityType(ModalityType.DOCUMENT_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void setButtonPanel() {
        JButton visszaButton = new JButton("Vissza");
        JButton kijeloltLepcsoButton = new JButton("Ugrás a kijelölt lépcsőhöz");
        buttonPanel.add(visszaButton);
        buttonPanel.add(kijeloltLepcsoButton);

        visszaButton.addActionListener(e -> dispose());

        kijeloltLepcsoButton.addActionListener(e -> {
            if (lepcso != null) {
                dispose();
                karbanTartasInputPanel.setLepcsoSzamComboBox(lepcso);
            }
        });

        hianyzoList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    dispose();
                    karbanTartasInputPanel.setLepcsoSzamComboBox(lepcso);
                }
            }
        });

    }

    private void setHianyzoLepcsoListDialog() {

        hianyzoKarbList.forEach(l -> listModel.addElement(l));
    }
}

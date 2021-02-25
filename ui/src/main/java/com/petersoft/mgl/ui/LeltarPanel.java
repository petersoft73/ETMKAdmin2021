package com.petersoft.mgl.ui;

import com.petersoft.mgl.model.Alkatresz;
import com.petersoft.mgl.service.AlkatreszService;
import com.petersoft.mgl.serviceimpl.AlkatreszServiceImpl;
import com.petersoft.mgl.utility.StringConstants;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LeltarPanel extends JPanel {
    private JTextField searchField;
    private JTable talalatJTable;
    private JButton visszaButton, mutasdButton;
    private MainFrame frame;
    private List<Alkatresz> alkatreszList;
    private JPanel mainPanel;
    private LeltarPanelTableModel model;

    public LeltarPanel(MainFrame frame) {
        this.frame = frame;
        initializeComponents();
        createPanel();
        setVisible(true);

    }

    private void initializeComponents() {
        mainPanel = new JPanel();
        //   mainPanel.setPreferredSize(new Dimension(800, 600));
        searchField = new JTextField();
        //  searchField.setSize(new Dimension(400,10));
        talalatJTable = new JTable();
        talalatJTable.setFont(new Font("Serif", Font.PLAIN, 14));

        visszaButton = new JButton("Vissza");
        // mutasdButton = new JButton("Mutasd!");
        AlkatreszService alkatreszService = new AlkatreszServiceImpl();
        try {
            this.alkatreszList = alkatreszService.getAlkatreszList().stream()
                    .filter(e -> e != null)
                    .sorted(Comparator.comparing(Alkatresz::getLeiras, new RuleBasedCollator(StringConstants.HUNGARIAN_COLLATOR_RULES))
                            .thenComparing(Alkatresz::getTipus))
                    .collect(Collectors.toList());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        visszaButton.addActionListener(e -> {
            setVisible(false);
            frame.showFramePanel();
        });

        model = new LeltarPanelTableModel(alkatreszList);
        talalatJTable.setModel(model);
        setTableColumnWidth();
        model.updateTable();
    }

    private void searchFromTextField(String text) {
        String searchText = text.toLowerCase();

        List<Alkatresz> talalat;
        talalat = alkatreszList.stream()
                .filter(Objects::nonNull)
                .filter(a -> (a.getLeiras() != null && a.getLeiras().toLowerCase().contains(searchText)) ||
                        (a.getLeiras() != null && a.getTipus().toLowerCase().contains(searchText)) ||
                        (a.getLeiras() != null && a.getCikkszam().toLowerCase().contains(searchText)) ||
                        (a.getLeiras() != null && a.getHely().toLowerCase().contains(searchText)))
                .collect(Collectors.toList());
        if (searchText.equals("")) talalat.clear();
        model = new LeltarPanelTableModel(talalat);
        talalatJTable.setModel(model);
        setTableColumnWidth();
        model.updateTable();
    }


    private void createPanel() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(searchField);

        JScrollPane scrollPane = new JScrollPane(talalatJTable);
        scrollPane.setPreferredSize(new Dimension(900, 600));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(Box.createHorizontalStrut(50));
        mainPanel.add(visszaButton);
        //  mainPanel.add(mutasdButton);
        mainPanel.setVisible(true);
        add(mainPanel);

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchFromTextField(searchField.getText());
            }
        });

    }

    private void setTableColumnWidth() {
        talalatJTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        talalatJTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        talalatJTable.getColumnModel().getColumn(2).setPreferredWidth(180);
        talalatJTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        talalatJTable.getColumnModel().getColumn(4).setPreferredWidth(40);
    }

    private class LeltarPanelTableModel extends AbstractTableModel {
        private final String[] colNames = {"Alkatrész megnevezés", "Típus", "Cikkszám", "Hol van?", "Darabszám"};
        private List<Alkatresz> list;

        public LeltarPanelTableModel(List<Alkatresz> list) {
            this.list = list;
        }

        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return colNames.length;
        }

        @Override
        public String getColumnName(int index) {
            return colNames[index];
        }

        @Override
        public Object getValueAt(int row, int column) {
            Alkatresz alkatresz = list.get(row);

            switch (column) {
                case 0:
                    return alkatresz.getLeiras();
                case 1:
                    return alkatresz.getTipus();
                case 2:
                    return alkatresz.getCikkszam();
                case 3:
                    return alkatresz.getHely();
                case 4:
                    return alkatresz.getDarabszam();
            }
            return null;
        }

        public void setAlkatreszList(List<Alkatresz> javitasList) {
            this.list = list;
        }

        public void updateTable() {
            fireTableDataChanged();
        }
    }
}



package com.petersoft.mgl.ui;

import com.github.lgooddatepicker.components.DatePicker;
import com.petersoft.mgl.connection.DBConnector;
import com.petersoft.mgl.dolgozo.Dolgozo;
import com.petersoft.mgl.dolgozo.DolgozoNapiRekord;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class NapiDolgozoPanel extends JPanel {
    private DolgozoNapiRekord.StatuszEnum statuszEnum;
    private DolgozoNapiRekord.StatuszEnum[] statuszEnumArray;
    private DolgozoNapiRekord.MuszakEnum muszakEnum;
    private DolgozoNapiRekord.MuszakEnum[] muszakEnumArray;
    private JComboBox<DolgozoNapiRekord.StatuszEnum> statuszEnumJComboBox = new JComboBox<>();
    private JComboBox<DolgozoNapiRekord.MuszakEnum> muszakEnumJComboBox = new JComboBox<>();
    private DatePicker munkanapPicker;
    private JButton mentesButton, visszaButton;
    private List<Dolgozo> dolgozoList;
    private List<DolgozoNapiRekord> napiRekordList = new ArrayList<>();
    private JPanel dolgozoPanel;
    private JTable dolgozoTable;
    private MainFrame frame;
    private List<DolgozoStatuszMuszak> dolgozoStatuszMuszakList;
    private DolgozoStatuszMuszak dolgozoStatuszMuszak;
    private NapiDolgozoTableModel model;
    private Dolgozo dolgozo;
    private List<DolgozoNapiRekord> dolgozoNapiRekordList;


    public NapiDolgozoPanel(MainFrame frame) {
        this.frame = frame;
        statuszEnumArray = DolgozoNapiRekord.StatuszEnum.values();
        muszakEnumArray = DolgozoNapiRekord.MuszakEnum.values();
        setDialogProperties();
        this.add(dolgozoPanel);
        setListeners();
        setVisible(true);
    }

    private void setListeners() {
        visszaButton.addActionListener(e -> {
            setVisible(false);
            frame.showFramePanel();
        });

        mentesButton.addActionListener(e -> {
            List<DolgozoNapiRekord> dolgozoNapiRekordListMentes = new ArrayList<>();
            int tableRows = dolgozoTable.getRowCount();
            int tableColumns = dolgozoTable.getColumnCount();
            for (int i = 0; i < tableRows; i++) {
                DolgozoNapiRekord napiRekord = new DolgozoNapiRekord();
                for (int j = 0; j < tableColumns; j++) {
                    if (j == 0) dolgozo = ((Dolgozo) dolgozoTable.getValueAt(i, j));
                    if (j == 1) statuszEnum = (DolgozoNapiRekord.StatuszEnum) dolgozoTable.getValueAt(i, j);
                    if (j == 2) muszakEnum = (DolgozoNapiRekord.MuszakEnum) dolgozoTable.getValueAt(i, j);
                    napiRekord.setDolgozo(dolgozo);
                    napiRekord.setStatuszEnum(statuszEnum);
                    napiRekord.setMuszakEnum(muszakEnum);
                    napiRekord.setMunkaNap(munkanapPicker.getDate());
                }
                dolgozoNapiRekordListMentes.add(napiRekord);

            }
            try {
                if (!dolgozoNapiRekordList.isEmpty()) {
                    this.dolgozoNapiRekordList.stream()
                            .forEach(napiRekord -> DBConnector.deleteNapiRekord(napiRekord));
                }
                dolgozoNapiRekordListMentes.stream()
                        .forEach(i -> DBConnector.saveDolgozoNapiRekord(i));
                JOptionPane.showMessageDialog(null, "Sikeres mentés");
                setDolgozoStatuszMuszak();
            } catch (Throwable t) {
                JOptionPane.showMessageDialog(null, t.getStackTrace());
            }
        });



    }

    private void setDialogProperties() {
        dolgozoPanel = new JPanel();
        mentesButton = new JButton("Mentés");
        visszaButton = new JButton("Vissza");
        munkanapPicker = new DatePicker();
        munkanapPicker.setDateToToday();
        setStatuszEnumJComboBox();
        setMuszakEnumJComboBox();
        dolgozoTable = new JTable();
        setDolgozoStatuszMuszak();
        munkanapPicker.addDateChangeListener(e -> setDolgozoStatuszMuszak());
        dolgozoPanel.add(munkanapPicker, BorderLayout.NORTH);
        dolgozoPanel.add(new JScrollPane(dolgozoTable), BorderLayout.CENTER);
        dolgozoPanel.add(visszaButton, BorderLayout.SOUTH);
        dolgozoPanel.add(mentesButton, BorderLayout.SOUTH);
        dolgozoPanel.setVisible(true);
    }

    private void setStatuszEnumJComboBox() {
        Arrays.stream(statuszEnumArray)
                .forEachOrdered(i ->
                        statuszEnumJComboBox.addItem(i));
    }

    private void setMuszakEnumJComboBox() {
        Arrays.stream(muszakEnumArray)
                .forEachOrdered(i -> muszakEnumJComboBox.addItem(i));
    }

    private Integer[] setMuszakSzama() {
        final LocalDate REFERENCE_DATE = LocalDate.of(2020, 1, 2);
        Map<Integer, Integer[]> muszakMap = new HashMap<>();
        muszakMap.put(0, new Integer[]{4, 1});
        muszakMap.put(1, new Integer[]{3, 4});
        muszakMap.put(2, new Integer[]{2, 3});
        muszakMap.put(3, new Integer[]{1, 2});
        int passedDays = Math.abs((int) ChronoUnit.DAYS.between(REFERENCE_DATE, munkanapPicker.getDate()));

        return muszakMap.get(passedDays % 4);
    }

    private void setDolgozoStatuszMuszak() {

        dolgozoList = DBConnector.getAllDolgozo(setMuszakSzama());
        this.dolgozoNapiRekordList = DBConnector.getDolgozoNapiRekord(munkanapPicker.getDate());
        if (dolgozoNapiRekordList.isEmpty()) {
            dolgozoStatuszMuszakList = dolgozoList.stream()
                    .map(dolgozo -> new DolgozoStatuszMuszak(
                            dolgozo,
                            DolgozoNapiRekord.StatuszEnum.ISMERETLEN,
                            DolgozoNapiRekord.MuszakEnum.ISMERETLEN))
                    .collect(Collectors.toList());
        } else {
            dolgozoStatuszMuszakList = dolgozoNapiRekordList.stream()
                    .map(napiRekord -> new DolgozoStatuszMuszak(
                            napiRekord.getDolgozo(),
                            napiRekord.getStatuszEnum(),
                            napiRekord.getMuszakEnum()))
                    .collect(Collectors.toList());
        }
        this.model = new NapiDolgozoTableModel(dolgozoStatuszMuszakList);
        dolgozoTable.setModel(model);
        TableColumn statuszModel = dolgozoTable.getColumnModel().getColumn(1);
        statuszModel.setCellEditor(new DefaultCellEditor(statuszEnumJComboBox));
        TableColumn muszakModel = dolgozoTable.getColumnModel().getColumn(2);
        muszakModel.setCellEditor(new DefaultCellEditor(muszakEnumJComboBox));
        dolgozoTable.getColumnModel().getColumn(1).setCellRenderer(new ErrorCellRenderer());
        dolgozoTable.getColumnModel().getColumn(2).setCellRenderer(new ErrorCellRenderer());
    }


    public class DolgozoStatuszMuszak {
        private Dolgozo dolgozo;
        private DolgozoNapiRekord.StatuszEnum statuszEnum;
        private DolgozoNapiRekord.MuszakEnum muszakEnum1;

        public DolgozoStatuszMuszak(Dolgozo dolgozo, DolgozoNapiRekord.StatuszEnum s, DolgozoNapiRekord.MuszakEnum muszakEnum1) {
            this.dolgozo = dolgozo;
            this.statuszEnum = s;
            this.muszakEnum1 = muszakEnum1;
        }

        public Dolgozo getDolgozo() {
            return dolgozo;
        }

        public void setDolgozo(Dolgozo dolgozo) {
            this.dolgozo = dolgozo;
        }

        public DolgozoNapiRekord.StatuszEnum getS() {
            return statuszEnum;
        }

        public void setS(DolgozoNapiRekord.StatuszEnum s) {
            this.statuszEnum = s;
        }

        public DolgozoNapiRekord.MuszakEnum getMuszakEnum1() {
            return muszakEnum1;
        }

        public void setMuszakEnum1(DolgozoNapiRekord.MuszakEnum muszakEnum1) {
            this.muszakEnum1 = muszakEnum1;
        }

        @Override
        public String toString() {
            return "DolgozoStatuszMuszak{" +
                    "d=" + dolgozo +
                    ", s=" + statuszEnum +
                    ", m=" + muszakEnum1 +
                    '}';
        }
    }

    private class NapiDolgozoTableModel extends AbstractTableModel {
        private final String[] colNames = {"Dolgozó neve", "Dolgozik?",
                "Mikor?"};
        private List<DolgozoStatuszMuszak> list = new ArrayList<>();
        private DolgozoStatuszMuszak dolgozoStatuszMuszak;

        public NapiDolgozoTableModel(List<DolgozoStatuszMuszak> dsm) {
            list.addAll(dsm);
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
        public String getColumnName(int col) {
            return colNames[col];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            this.dolgozoStatuszMuszak = list.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    dolgozo = this.dolgozoStatuszMuszak.getDolgozo();
                    return dolgozo;
                case 1:
                    return this.dolgozoStatuszMuszak.statuszEnum;
                case 2:
                    return this.dolgozoStatuszMuszak.muszakEnum1;
            }
            return null;
        }

        @Override
        public void setValueAt(Object value, int row, int column) {
            this.dolgozoStatuszMuszak = this.list.get(row);
            Dolgozo dolgozo = this.dolgozoStatuszMuszak.getDolgozo();
            if (column == 0) this.dolgozoStatuszMuszak.setDolgozo(dolgozo);
            if (column == 1) this.dolgozoStatuszMuszak.setS((DolgozoNapiRekord.StatuszEnum) value);
            if (column == 2) this.dolgozoStatuszMuszak.setMuszakEnum1((DolgozoNapiRekord.MuszakEnum) value);
            fireTableDataChanged();
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }


        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
    }

    private class ErrorCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                    row, column);

            if(value.toString().equals("Válassz...")){
                component.setBackground(Color.RED);
                mentesButton.setVisible(false);
            } else {
                component.setBackground(Color.WHITE);
                mentesButton.setVisible(true);
            }

            return component;
        }


    }


}



package com.petersoft.mgl.ui;

import com.petersoft.mgl.model.Javitas;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

class JavitasTableModel extends AbstractTableModel {
    private final String[] colNames = {"Javítás leírása", "Melyik hiba?", "Dátum"};
    private List<Javitas> javitasList;

    public JavitasTableModel(List<Javitas> javitasList) {
        this.javitasList = javitasList;

    }

    @Override
    public int getRowCount() {
        return javitasList.size();
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
        Javitas javitas = javitasList.get(row);

        switch (column) {
            case 0:
                return javitas.getLeiras();
            case 1:
                if (javitas.getHiba() != null) {
                    return javitas.getHiba().getLeiras();
                } else return "";
            case 2:
                return javitas.getJavitasKelte().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null;
    }

    public void setJavitasList(List<Javitas> javitasList) {
        this.javitasList = javitasList;
    }

    public void updateTable() {
        fireTableDataChanged();
    }


}

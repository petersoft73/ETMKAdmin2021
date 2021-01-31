package com.petersoft.mgl.ui;

import com.petersoft.mgl.model.Hiba;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

class HibaTableModel extends AbstractTableModel {
    private List<Hiba> hibaList;

    private final String[] colNames = {"Hiba leírása", "Dátum"};


    public HibaTableModel(List<Hiba> hibaList) {
        this.hibaList = hibaList;
    }

    @Override
    public int getRowCount() {
        return hibaList.size();
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
        Hiba hiba = hibaList.get(row);

        switch (column) {
            case 0:
                return hiba.getLeiras();
            case 1:
                return hiba.getDatum().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null;
    }

    public void setHibaList(List<Hiba> hibaList) {
        this.hibaList = hibaList;
    }

    public void updateTable() {
        fireTableDataChanged();
    }


}

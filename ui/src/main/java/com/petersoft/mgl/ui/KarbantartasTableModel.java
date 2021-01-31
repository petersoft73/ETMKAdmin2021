package com.petersoft.mgl.ui;

import com.petersoft.mgl.model.Hiba;
import com.petersoft.mgl.model.Karbantartas;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class KarbantartasTableModel extends AbstractTableModel {
    private List<Karbantartas> karbantartasList;
    private String[] colNames;


    public KarbantartasTableModel(List<Karbantartas> karbantartasList, String title) {
        this.karbantartasList = karbantartasList;
        colNames = new String[]{title, "Dátum"};
    }

    @Override
    public int getRowCount() {
        return karbantartasList.size();
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        Karbantartas k = karbantartasList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return k.getKarbantartasTipus();
            case 1:
                return k.getKarbantartasDatum() != null ?
                        k.getKarbantartasDatum().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
        }
        return null;
    }

    public void setHibaList(List<Karbantartas> karbantartasList) {
        this.karbantartasList = karbantartasList;
    }

    public void updateTable() {
        fireTableDataChanged();
    }
}

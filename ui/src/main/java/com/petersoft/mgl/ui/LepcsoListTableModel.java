package com.petersoft.mgl.ui;

import com.petersoft.mgl.model.Lepcso;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class LepcsoListTableModel extends AbstractTableModel {
    private List<Lepcso> lepcsoList;
    private final String[] colNames = {"Lépcső száma", "Üzemképes",
            "Érintésvédelmi érvényesség", "Helyszín", "Típus"};

    public LepcsoListTableModel() {
        this.lepcsoList = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return lepcsoList.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Lepcso lepcso = lepcsoList.get(row);
        switch (column) {
            case 0:
                return lepcso.getLepcsoSzama();
            case 1:
                return lepcso.getStatus();
            case 2:
                return lepcso.getErintesVedelem().getErvenyes();
            case 3:
                return lepcso.getLocation().getLocationName();
            case 4:
                return lepcso.getTipus().getTipusNev();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int index) {
        return colNames[index];
    }

    public void setLepcsoList(List<Lepcso> lepcsoList) {
        this.lepcsoList = lepcsoList;
    }

    public void updateTable() {
        fireTableDataChanged();
    }
}

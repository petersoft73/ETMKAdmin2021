package com.petersoft.mgl.ui;

import com.petersoft.mgl.model.Hiba;
import com.petersoft.mgl.model.Javitas;
import com.petersoft.mgl.service.HibaService;
import com.petersoft.mgl.service.JavitasService;
import com.petersoft.mgl.serviceimpl.HibaServiceImpl;
import com.petersoft.mgl.serviceimpl.JavitasServiceImpl;
import org.apache.commons.lang3.text.WordUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class SearchBarDialog extends JPanel {
  //  private JTable hibaJTable;
    private JTable javitasJTable;
    private JTextField searchTxt;
 //   private final HibaService hibaService = new HibaServiceImpl();
 //   private List<Hiba> hibaList;
    private final JavitasService javitasService = new JavitasServiceImpl();
    private List<Javitas> javitasList;
    private final MainFrame frame;


    public SearchBarDialog(MainFrame frame) throws Exception {
        this.frame = frame;
        initComponents();
        initTableSorter();
        setVisible(true);
        frame.add(this);
        bindData();
    }

    private void initTableSorter() {
     //   hibaJTable.setAutoCreateRowSorter(true);
        javitasJTable.setAutoCreateRowSorter(true);
     //   TableRowSorter<TableModel> sorterHiba = new TableRowSorter<>(hibaJTable.getModel());
        TableRowSorter<TableModel> sorterJavitas = new TableRowSorter<>(javitasJTable.getModel());
     //   hibaJTable.setRowSorter(sorterHiba);
        javitasJTable.setRowSorter(sorterJavitas);
        List<RowSorter.SortKey> sortKeysJavitas = new ArrayList<>();
        List<RowSorter.SortKey> sortKeysHiba = new ArrayList<>();
        for (int i = 0; i < javitasJTable.getModel().getColumnCount(); i++) {
            sortKeysJavitas.add(new RowSorter.SortKey(i, SortOrder.ASCENDING));
        }
     //   for (int i = 0; i < hibaJTable.getModel().getColumnCount(); i++) {
      //      sortKeysHiba.add(new RowSorter.SortKey(i, SortOrder.ASCENDING));
      //  }

   //     sorterHiba.setSortKeys(sortKeysHiba);
    //    sorterJavitas.setSortKeys(sortKeysJavitas);

//        sorterHiba.addRowSorterListener(e -> {
//            int indexNoOfColumn = 0;
//            for (int i = 0; i < hibaJTable.getRowCount(); i++) {
//                hibaJTable.setValueAt(i + 1, i, indexNoOfColumn);
//            }
//        });

        sorterJavitas.addRowSorterListener(e -> {
            int indexNoOfColumn = 0;
            for (int i = 0; i < javitasJTable.getRowCount(); i++) {
                javitasJTable.setValueAt(i + 1, i, indexNoOfColumn);
            }
        });

    }

    private void bindData() throws Exception {
     //   hibaList = hibaService.getHibaList();
        javitasList = javitasService.getJavitasList();
    }

    private void initComponents() {
    //    hibaJTable = new JTable();
        javitasJTable = new JTable();
    //    hibaJTable.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 13));
        javitasJTable.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 13));

        JPanel jPanel2 = new JPanel();
     //   JPanel hibaListPanel = new JPanel();
        JPanel javitasListPanel = new JPanel();
     //   JScrollPane hibaJScrollPane = new JScrollPane();
        JScrollPane javitasJScrollPane = new JScrollPane();
        javitasJScrollPane.setPreferredSize(new Dimension(800,400));
        searchTxt = new JTextField(30);
        JLabel searchLabel = new JLabel();
     //   hibaListPanel.add(hibaJScrollPane);
        javitasListPanel.add(javitasJScrollPane);
      //  jPanel2.add(hibaJScrollPane);
        jPanel2.add(javitasJScrollPane);
        JButton mainMenuButton = new JButton("Főmenü");
        JButton lepcsoListButton = new JButton("Lépcsőlista");

    //    hibaJScrollPane.setViewportView(hibaJTable);
        javitasJScrollPane.setViewportView(javitasJTable);

        searchTxt.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 18)); // NOI18N
        searchTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                searchTxtKeyReleased();
            }
        });

        searchLabel.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 12)); // NOI18N
        searchLabel.setForeground(new java.awt.Color(0, 0, 0));
        searchLabel.setText("Keresés/Szűrés");

        add(searchLabel, BorderLayout.NORTH);
        add(searchTxt, BorderLayout.NORTH);
        add(jPanel2, BorderLayout.CENTER);
        add(mainMenuButton, BorderLayout.SOUTH);
        add(lepcsoListButton, BorderLayout.SOUTH);
        mainMenuButton.addActionListener(e -> {
            setVisible(false);
            frame.showFramePanel();
        });

        lepcsoListButton.addActionListener(e -> {
            setVisible(false);
            try {
                frame.showLepcsoListPanel();
            } catch (Throwable exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        });

//        hibaJTable.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                JTable table = (JTable) e.getSource();
//                Point point = e.getPoint();
//                int row = table.rowAtPoint(point);
//                StringBuilder displayHiba = new StringBuilder();
//                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
//                    for (int i = 0; i < table.getModel().getColumnCount(); i++) {
//                        displayHiba.append(table.getModel().getValueAt(row, i)).append("\n\n");
//                    }
//                    JOptionPane.showMessageDialog(null,
//                            WordUtils.wrap(String.valueOf(displayHiba), 30),
//                            "Leírás",
//                            JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });

        javitasJTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                Point p = e.getPoint();
                int row = table.rowAtPoint(p);
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    StringBuilder displayJavitas = new StringBuilder();
                    for (int i = 0; i < table.getModel().getColumnCount(); i++) {
                        displayJavitas.append(table.getModel().getValueAt(row, i)).append("\n\n");
                    }
                    JOptionPane.showMessageDialog(null,
                            WordUtils.wrap(String.valueOf(displayJavitas), 30),
                            "Leírás",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }


    private void searchTxtKeyReleased() {
        searchFilter(searchTxt.getText());
    }

    private void searchFilter(String searchTerm) {
        List<Hiba> listHiba = new ArrayList<>();
        List<Javitas> listJavitas = new ArrayList<>();

//        for (Hiba h : hibaList) {
//            String hibaLeiras = h.getLeiras().toLowerCase();
//            if (hibaLeiras.contains(searchTerm.toLowerCase())) {
//                listHiba.add(h);
//            }
//        }

        for (Javitas j : javitasList) {
            String javitasLeiras = j.getLeiras().toLowerCase();
            if (javitasLeiras.contains(searchTerm.toLowerCase())) {
                listJavitas.add(j);
            } else if (j.getHiba() != null) {
                if (j.getHiba().getLeiras().toLowerCase().contains(searchTerm.toLowerCase())) {
                    listJavitas.add(j);
                }
            }
        }


//        HibaTableModelSearchBar hibaTableModel = new HibaTableModelSearchBar(listHiba);
//        this.hibaJTable.setModel(hibaTableModel);
//        hibaTableModel.fireTableDataChanged();
        JavitasTableModelSearchBar javitasTableModel = new JavitasTableModelSearchBar(listJavitas);
        this.javitasJTable.setModel(javitasTableModel);
        javitasTableModel.fireTableDataChanged();


    }


    static class HibaTableModelSearchBar extends AbstractTableModel {
        private final String[] colNames = {"Hiba leírása"};
        private final List<Hiba> hibaListTableModel;

        public HibaTableModelSearchBar(List<Hiba> hibaList) {
            this.hibaListTableModel = hibaList;
        }

        @Override
        public int getRowCount() {
            return hibaListTableModel.size();
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
            Hiba hiba = hibaListTableModel.get(row);
            switch (column) {
                case 0:
                    return hiba.getLeiras();
//                return hiba.getLepcso().getLepcsoSzama()
//                            + " :: " + hiba.getLepcso().getLocation()
//                            + " :: " + hiba.getLepcso().getTipus();
//                case 1:
//                case 2:
//                    return hiba.getDatum().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            return null;
        }

    }

    static class JavitasTableModelSearchBar extends AbstractTableModel {
        private final String[] colNames = {"Lépcső száma", "Javítás leírása", "Melyik hiba?", "Dátum", "Túr száma"};
        private final List<Javitas> javitasListTableModel;

        public JavitasTableModelSearchBar(List<Javitas> javitasList) {
            this.javitasListTableModel = javitasList;
        }

        @Override
        public int getRowCount() {
            return javitasListTableModel.size();
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
            Javitas javitas = javitasListTableModel.get(row);

            switch (column) {
                case 0:
                    return javitas.getLepcso().getLepcsoSzama()
                            + " :: " + javitas.getLepcso().getLocation()
                            + " :: " + javitas.getLepcso().getTipus();

                case 1:
                    return javitas.getLeiras();
                case 2:
                    return javitas.getHiba() != null ?
                            javitas.getHiba().getLeiras() : "";
                case 3:
                    return javitas.getJavitasKelte().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                case 4:
                    return javitas.getMuszakSzama() + ". túr";
            }
            return null;
        }

    }
}




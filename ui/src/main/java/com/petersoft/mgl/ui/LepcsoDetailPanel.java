package com.petersoft.mgl.ui;

import com.petersoft.mgl.model.Hiba;
import com.petersoft.mgl.model.Javitas;
import com.petersoft.mgl.model.Karbantartas;
import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.service.HibaService;
import com.petersoft.mgl.service.JavitasService;
import com.petersoft.mgl.service.KarbantartasService;
import com.petersoft.mgl.serviceimpl.HibaServiceImpl;
import com.petersoft.mgl.serviceimpl.JavitasServiceImpl;
import com.petersoft.mgl.serviceimpl.KarbantartasServiceImpl;
import com.petersoft.mgl.utility.StringConstants;
import org.apache.commons.lang3.text.WordUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Collator;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.apache.poi.util.LocaleID.HU;

public class LepcsoDetailPanel extends JPanel {
    private final MainFrame frame;
    private final Lepcso lepcso;
    private JButton visszaButton;
    private List<Hiba> hibaList;
    private List<Javitas> javitasList;
    private List<Karbantartas> karbantartasList;
    private JTable hibaJList;
    private JTable javitasJList;
    private JavitasTableModel javitasJTableModel;
    private HibaTableModel hibaJTableModel;
    private JPopupMenu hibaPopupMenu;
    private JPopupMenu javitasPopupMenu;
    private JPopupMenu karbantartasPopupMenu;
    private JMenuItem hibaModositItem;
    private JMenuItem ujHibaRogzitItem;
    private JMenuItem javitasRogzitItem;
    private JMenuItem ujJavitasRogzitItem;
    private JButton mainMenuButton;
    private Hiba hiba;
    private Javitas javitas;
    private JScrollPane hibaScrollPane, javitasScrollPane;
    private JMenuItem javitasModositItem;
    private JMenuItem karbantartasElvegezItem;

    public LepcsoDetailPanel(MainFrame frame, Lepcso lepcso) throws Exception {
        this.frame = frame;
        this.lepcso = lepcso;
        initializeLists();
        createPanelLayout();
        refreshTables();
        updateTables();
        initializePopupMenuHibaList();
        initializePopupMenuJavitasList();
        initializeKarbantartasPopupMenu();
        initializeListeners();
        addButtonListeners();
        frame.add(this);
        setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void initializeListeners() {

//        hibaJList.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                int row = hibaJList.rowAtPoint(e.getPoint());
//                hibaJList.getSelectionModel().setSelectionInterval(row, row);
//                if (e.getButton() == MouseEvent.BUTTON3) {
//                    hibaModositItem.setVisible(true);
//                    ujJavitasRogzitItem.setVisible(true);
//                    javitasRogzitItem.setVisible(true);
//                    hibaPopupMenu.show(hibaJList, e.getX(), e.getY());
//                }
//            }
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (lepcso != null) {
//                    if (e.getClickCount() == 2) {
//                        JOptionPane.showMessageDialog(null,
//                                WordUtils.wrap(hiba.getLeiras(), 30)
//                                        + "\n\n" +
//                                        hiba.getDatum() + "\n",
//                                "Leírás",
//                                JOptionPane.INFORMATION_MESSAGE);
//                    }
//                }
//            }
//
//
//        });


        javitasJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = javitasJList.rowAtPoint(e.getPoint());
                javitasJList.getSelectionModel().setSelectionInterval(row, row);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    ujJavitasRogzitItem.setVisible(true);
                    javitasPopupMenu.show(javitasJList, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (lepcso != null) {
                    String hibaMessage = (javitas.getHiba() == null) ? "" : javitas.getHiba().getLeiras();
                    if (e.getClickCount() == 2) {
                        JOptionPane.showMessageDialog(null,
                                WordUtils.wrap(javitas.getLeiras(), 30) + "\n\n"
                                        + javitas.getMuszakSzama() + ". túr\n"
                                        + WordUtils.wrap(hibaMessage, 30) + "\n\n"
                                        + javitas.getJavitasKelte() + "\n",
                                "Leírás", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

//        hibaScrollPane.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (e.getButton() == MouseEvent.BUTTON3) {
//                    hibaModositItem.setVisible(false);
//                    ujJavitasRogzitItem.setVisible(false);
//                    javitasRogzitItem.setVisible(false);
//                    hibaPopupMenu.show(hibaScrollPane, e.getX(), e.getY());
//                }
//            }
//        });

        javitasScrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    ujJavitasRogzitItem.setVisible(true);
                    javitasPopupMenu.show(javitasScrollPane, e.getX(), e.getY());
                }
            }
        });

        mainMenuButton.addActionListener(e -> {
            setVisible(false);
            frame.showFramePanel();
        });

//        ujHibaRogzitItem.addActionListener(e -> {
//            setVisible(false);
//            try {
//                frame.showHibaInputPanel(lepcso);
//            } catch (Throwable exception) {
//                JOptionPane.showMessageDialog(null, exception.getStackTrace());
//            }
//        });

        ujJavitasRogzitItem.addActionListener(e -> {
            setVisible(false);
            try {
                frame.showJavitasInputPanel(lepcso);
            } catch (Throwable exception) {
                JOptionPane.showMessageDialog(null, exception.getStackTrace());
            }
        });

//        hibaModositItem.addActionListener(e -> {
//            setVisible(false);
//            try {
//                frame.showHibaInputPanel(lepcso, hiba);
//            } catch (Throwable exception) {
//                JOptionPane.showMessageDialog(null, exception.getStackTrace());
//            }
//        });

        ListSelectionModel selectionModelJavitas = javitasJList.getSelectionModel();
        selectionModelJavitas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = javitasJList.getSelectedRow();
                this.javitas = javitasList.get(javitasJList.convertRowIndexToModel(selectedRow));
                String javitasString = WordUtils.wrap(javitas.getLeiras(), 30);
                String hibaString = javitas.getHiba() == null ? "" : WordUtils.wrap(javitas.getHiba().getLeiras(), 30);
                javitasJList.setToolTipText("<html>" + javitasString
                        + "<br>"
                        + "<br>"
                        + hibaString
                        + "</html>");
            }
        });

//        ListSelectionModel selectionModelHiba = hibaJList.getSelectionModel();
//        selectionModelHiba.addListSelectionListener(e -> {
//            if (!e.getValueIsAdjusting()) {
//                int selectedRow = hibaJList.getSelectedRow();
//                this.hiba = hibaList.get(hibaJList.convertRowIndexToModel(selectedRow));
//            }
//        });


        javitasRogzitItem.addActionListener(e -> {
            setVisible(false);
            try {
                frame.showJavitasInputPanel(lepcso);
            } catch (Throwable exception) {
                JOptionPane.showMessageDialog(null, exception.getStackTrace());
            }
        });

        javitasModositItem.addActionListener(e -> {
            if (javitasJList.getRowCount() != 0) {
                setVisible(false);
                try {
                    frame.showJavitasInputPanel(lepcso, javitas);
                } catch (Throwable exception) {
                    JOptionPane.showMessageDialog(null, exception.getStackTrace());
                }
            }
            setVisible(false);
            try {
                frame.showJavitasInputPanel(lepcso, javitas);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });

//        hibaJList.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                int row = hibaJList.rowAtPoint(e.getPoint());
//                hibaJList.getSelectionModel().setSelectionInterval(row, row);
//                if (e.getButton() == MouseEvent.BUTTON3) {
//                    hibaModositItem.setVisible(true);
//                    ujJavitasRogzitItem.setVisible(true);
//                    javitasRogzitItem.setVisible(true);
//                    hibaPopupMenu.show(hibaJList, e.getX(), e.getY());
//                }
//            }
//
////            @Override
////            public void mouseClicked(MouseEvent e) {
////                if (lepcso != null) {
////                    if (e.getClickCount() == 2) {
////                        JOptionPane.showMessageDialog(null,
////                                WordUtils.wrap(hiba.getLeiras(), 30)
////                                        + "\n\n" +
////                                        hiba.getDatum() + "\n");
////                    }
////                }
////            }
//
//
//        });

    }

    private void initializePopupMenuHibaList() {
//        hibaPopupMenu = new JPopupMenu();
//        hibaModositItem = new JMenuItem("Hiba módosítása");
//        ujHibaRogzitItem = new JMenuItem("Új hiba rögzítése ...");
        javitasRogzitItem = new JMenuItem("Javítás rögzítése ehhez a hibához...");
//
//        hibaPopupMenu.add(ujHibaRogzitItem);
//        hibaPopupMenu.add(hibaModositItem);
//        hibaPopupMenu.add(javitasRogzitItem);

    }

    private void initializePopupMenuJavitasList() {
        javitasPopupMenu = new JPopupMenu();
        javitasModositItem = new JMenuItem("Javítás módosítása");
        ujJavitasRogzitItem = new JMenuItem("Új javítás rögzítése");
        javitasPopupMenu.add(ujJavitasRogzitItem);
        javitasPopupMenu.add(javitasModositItem);
    }

    private void initializeKarbantartasPopupMenu() {
        karbantartasPopupMenu = new JPopupMenu();
        karbantartasElvegezItem = new JMenuItem("Karbantartás elvégzése");
        karbantartasPopupMenu.add(karbantartasElvegezItem);

        karbantartasElvegezItem.addActionListener(e -> {
            setVisible(false);
            try {
                frame.showKarbanTartasPanel(this.lepcso);
            } catch (Throwable throwable) {
                JOptionPane.showMessageDialog(null, throwable.getStackTrace());
            }
        });
    }

    private void initializeLists() {
        try {
         //   this.hibaList = new ArrayList<>();
            this.javitasList = new ArrayList<>();
            this.karbantartasList = new ArrayList<>();
         //   HibaService hibaService = new HibaServiceImpl();
            JavitasService javitasService = new JavitasServiceImpl();
            KarbantartasService karbantartasService = new KarbantartasServiceImpl();
//            this.hibaList = hibaService.getHibaList(lepcso).stream()
//                    .filter(e -> !e.getLeiras().equals(""))
//                    .collect(Collectors.toList());
            this.javitasList = javitasService.getJavitasList(lepcso);
            this.karbantartasList = karbantartasService.getKarbantartasList(lepcso);
        } catch (Throwable throwable) {
            JOptionPane.showMessageDialog(null, throwable.getStackTrace());
        }
    }

    private void refreshTables() {
        this.javitasJTableModel.setJavitasList(javitasList);
      //  this.hibaJTableModel.setHibaList(hibaList);

    }

    private void updateTables() {
     //   hibaJTableModel.updateTable();
        javitasJTableModel.updateTable();
    }

    private void createPanelLayout() {
        visszaButton = new JButton("Lépcső Lista");
        mainMenuButton = new JButton("Főmenü");

        JLabel lepcsoSzama = new JLabel(String.valueOf(lepcso.getLepcsoSzama()));
        lepcsoSzama.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel location = new JLabel(lepcso.getLocation().getLocationName());
        location.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel tipus = new JLabel(lepcso.getTipus().getTipusNev());
        tipus.setFont(new Font("SansSerif", Font.BOLD, 18));

//        hibaJList = new JTable();
//        hibaJTableModel = new HibaTableModel(hibaList);
//        hibaJList.setModel(hibaJTableModel);

        javitasJList = new JTable();
        javitasJTableModel = new JavitasTableModel(javitasList);
        javitasJList.setModel(javitasJTableModel);
        TableColumnModel columnModel = javitasJList.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(300);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setPreferredWidth(50);


        List<Karbantartas> karbListDone = karbantartasList.stream()
                .filter(k -> k.isElvegezve())
                .sorted(Comparator.comparing(Karbantartas::getKarbantartasDatum).reversed())
                .collect(Collectors.toList());

        List<Karbantartas> karbListUnDone = karbantartasList.stream()
                .filter(k -> !k.isElvegezve())
                .collect(Collectors.toList());

        JTable elvegzettKarbList = new JTable(new KarbantartasTableModel(karbListDone, "Elvégzett karbantartások"));
        JTable hianyzoKarbList = new JTable(new KarbantartasTableModel(karbListUnDone, "Hátralévő karbantartások"));


     //   hibaScrollPane = new JScrollPane(hibaJList);
        javitasScrollPane = new JScrollPane(javitasJList);
        javitasScrollPane.setPreferredSize(new Dimension(800,400));
        setJTableSorting();

        JScrollPane doneKarbListPane = new JScrollPane(elvegzettKarbList);
        JScrollPane unDoneKarbListPane = new JScrollPane(hianyzoKarbList);

        JPanel labelPanel = new JPanel();
        labelPanel.add(lepcsoSzama);
        labelPanel.add(tipus);
        labelPanel.add(location);

        JPanel firstPanel = new JPanel();
     //   firstPanel.add(hibaScrollPane);
        firstPanel.add(javitasScrollPane);

        JPanel secondPanel = new JPanel();
        secondPanel.add(doneKarbListPane);
        secondPanel.add(unDoneKarbListPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(visszaButton);
        buttonPanel.add(mainMenuButton);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(labelPanel);
        add(firstPanel);
        add(secondPanel);
        add(buttonPanel);

        hianyzoKarbList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = hianyzoKarbList.rowAtPoint(e.getPoint());
                hianyzoKarbList.getSelectionModel().setSelectionInterval(row, row);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    karbantartasElvegezItem.setVisible(true);
                    karbantartasPopupMenu.show(hianyzoKarbList, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
//                if (lepcso != null) {
//                    if (e.getClickCount() == 2) {
//                        JOptionPane.showMessageDialog(null,
//                                WordUtils.wrap(hiba.getLeiras(), 30)
//                                        + "\n\n" +
//                                        hiba.getDatum() + "\n",
//                                "Leírás",
//                                JOptionPane.INFORMATION_MESSAGE);
//                    }
//                }
            }


        });

    }

    private void addButtonListeners() {
        visszaButton.addActionListener(e -> {
            setVisible(false);
            try {
                frame.showLepcsoListPanel();
            } catch (Throwable exception) {
                JOptionPane.showMessageDialog(null, exception.getStackTrace());
            }

        });
    }

    private void setJTableSorting() {
        javitasJList.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(javitasJList.getModel());
        javitasJList.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndexJavitas = 0;
        int columnIndexHiba = 1;
        int columnIndexDatum = 2;
        sortKeys.add(new RowSorter.SortKey(columnIndexJavitas, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(columnIndexHiba, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(columnIndexDatum, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKeys);
        javitasJList.getRowSorter().toggleSortOrder(2);
        javitasJList.getRowSorter().toggleSortOrder(2);
        sorter.addRowSorterListener(e -> {
            int indexNoOfColumn = 0;
            for (int i = 0; i < javitasJList.getRowCount(); i++) {
                javitasJList.setValueAt(i + 1, i, indexNoOfColumn);
            }
        });
        Locale locale = new Locale("hu", "HU");
        Collator collator = Collator.getInstance(locale);
//            sorter.setComparator(columnIndexJavitas, Comparator.naturalOrder());
//            sorter.setComparator(columnIndexHiba, Comparator.naturalOrder());
        sorter.setComparator(columnIndexDatum, Comparator.naturalOrder());
        sorter.setComparator(columnIndexJavitas, collator);
        sorter.setComparator(columnIndexHiba, collator);
        sorter.sort();

    }


}


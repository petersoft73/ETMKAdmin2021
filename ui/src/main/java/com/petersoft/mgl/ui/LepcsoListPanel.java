package com.petersoft.mgl.ui;

import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.model.Status;
import com.petersoft.mgl.service.LepcsoService;
import com.petersoft.mgl.serviceimpl.LepcsoServiceImpl;
import com.petersoft.mgl.utility.NumberConstants;
import com.petersoft.mgl.utility.StringConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.*;

public class LepcsoListPanel extends JPanel {
    private final MainFrame frame;
    private JTable lepcsoListTable;
    private LepcsoListTableModel tableModel;
    private JPopupMenu popupMenu;
    private JButton tovabbiInfoButton;
    private JButton mainMenuButton;
    private JPanel buttonPanel;
    private JMenuItem erVedRogzitPopupItem, tovabbiInfoItem,
            hibaRogzitPopupItem, javitasRogzitPopupItem, karbElvegezPopupItem;
    private Lepcso lepcso;
    private List<Lepcso> lepcsoList = new ArrayList<>();


    public LepcsoListPanel(MainFrame frame) throws Throwable {
        this.frame = frame;
        initializeVariables();
        initializeLayout();
        updateTable();
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 30, 10, 30));
        add(new JScrollPane(lepcsoListTable), BorderLayout.CENTER);
        setPopUpMenu();
        lepcsoListTable.add(popupMenu);
        setButtonLayout();
        setListeners();
        setJTableSorting();
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void initializeVariables() throws Throwable {
        LepcsoService lepcsoService = new LepcsoServiceImpl();
        this.lepcsoList = lepcsoService.getAllLepcso();
        this.tableModel = new LepcsoListTableModel();
        this.lepcsoListTable = new JTable(tableModel);
        TableCellRenderer renderer = new CustomCellRenderer();
        lepcsoListTable.setDefaultRenderer(Object.class, renderer);
        setCellsCentered();
    }

    private void setCellsCentered() {
        lepcsoListTable.setFont(new Font("Serif", Font.BOLD, 14));
        lepcsoListTable.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 14));
        lepcsoListTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        lepcsoListTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        lepcsoListTable.getColumnModel().getColumn(2).setPreferredWidth(120);
    }

    private void setPopUpMenu() {
        popupMenu = new JPopupMenu();
        tovabbiInfoItem = new JMenuItem("További info ...");
     //   hibaRogzitPopupItem = new JMenuItem("Hiba rögzítése ...");
        javitasRogzitPopupItem = new JMenuItem("Javítás rögzítése ...");
        erVedRogzitPopupItem = new JMenuItem("Érintésvédelem rögzítése ...");
        karbElvegezPopupItem = new JMenuItem("Karbantartás elvégzése ...");
        popupMenu.add(tovabbiInfoItem);
     //   popupMenu.add(hibaRogzitPopupItem);
        popupMenu.add(javitasRogzitPopupItem);
        popupMenu.add(erVedRogzitPopupItem);
        popupMenu.add(karbElvegezPopupItem);
    }

    private void setButtonLayout() {
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(500, 50));
        tovabbiInfoButton = new JButton("Több Info ...");
        mainMenuButton = new JButton("Főmenü");
        buttonPanel.add(mainMenuButton);
        buttonPanel.add(tovabbiInfoButton);
    }

    private void setListeners() {
        mainMenuButton.addActionListener(e -> {
            setVisible(false);
            frame.showFramePanel();
        });


        ListSelectionModel selectionModel = lepcsoListTable.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = lepcsoListTable.getSelectedRow();
                this.lepcso = lepcsoList.get(lepcsoListTable.convertRowIndexToModel(selectedRow));
            }
        });

        tovabbiInfoButton.addActionListener(e -> {
            if (lepcso != null) {
                setVisible(false);
                try {
                    frame.showLepcsoDetailPanel(lepcso);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });

        lepcsoListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (lepcso != null) {
                    if (e.getClickCount() == 2) {
                        setVisible(false);
                        try {
                            frame.showLepcsoDetailPanel(lepcso);
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage());
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int row = lepcsoListTable.rowAtPoint(e.getPoint());
                lepcsoListTable.getSelectionModel().setSelectionInterval(row, row);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(lepcsoListTable, e.getX(), e.getY());
                }
            }
        });

        tovabbiInfoItem.addActionListener(e -> {
            setVisible(false);
            try {
                frame.showLepcsoDetailPanel(lepcso);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getStackTrace());
            }
        });

        erVedRogzitPopupItem.addActionListener(e -> {
            setVisible(false);
            try {
                frame.showErVedInputPanel(lepcso);
            } catch (Throwable exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        });

//        hibaRogzitPopupItem.addActionListener(e -> {
//            setVisible(false);
//            try {
//                frame.showHibaInputPanel(lepcso);
//            } catch (Throwable exception) {
//                JOptionPane.showMessageDialog(null, exception.getMessage());
//            }
//        });

        javitasRogzitPopupItem.addActionListener(e -> {
            setVisible(false);
            try {
                frame.showJavitasInputPanel(this.lepcso);
            } catch (Throwable exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        });

        karbElvegezPopupItem.addActionListener(e -> {
            setVisible(false);
            try {
                frame.showKarbanTartasPanel(this.lepcso);
            } catch (Throwable throwable) {
                JOptionPane.showMessageDialog(null, throwable.getStackTrace());
            }
        });
    }

    private void setJTableSorting() {
        lepcsoListTable.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(lepcsoListTable.getModel());
        lepcsoListTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndexLepcso = 0;
        int columnIndexUzemkepes = 1;
        int columnIndexErVed = 2;
        int columnIndexLocation = 3;
        int columnIndexTIpus = 4;
        sortKeys.add(new RowSorter.SortKey(columnIndexLepcso, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(columnIndexUzemkepes, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(columnIndexErVed, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(columnIndexLocation, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(columnIndexTIpus, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKeys);
        lepcsoListTable.getRowSorter().toggleSortOrder(1);

        sorter.addRowSorterListener(e -> {
            int indexNoOfColumn = 0;
            for (int i = 0; i < lepcsoListTable.getRowCount(); i++) {
                lepcsoListTable.setValueAt(i + 1, i, indexNoOfColumn);
            }
        });
        try {
            sorter.setComparator(columnIndexUzemkepes, Comparator.naturalOrder());
            sorter.setComparator(columnIndexLepcso, Comparator.naturalOrder());
            sorter.setComparator(columnIndexErVed, Comparator.naturalOrder());
            sorter.setComparator(columnIndexLocation, new RuleBasedCollator(StringConstants.HUNGARIAN_COLLATOR_RULES));
            sorter.setComparator(columnIndexTIpus, new RuleBasedCollator(StringConstants.HUNGARIAN_COLLATOR_RULES));
            sorter.sort();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void setTableModel(List<Lepcso> lepcsoList) {
        this.tableModel.setLepcsoList(lepcsoList);
    }

    public void updateTable() {
        this.tableModel.updateTable();
    }
}

class CustomCellRenderer implements TableCellRenderer {
    public final DefaultTableCellRenderer DEFAULT_RENDERER =
            new DefaultTableCellRenderer();
    Map<Integer, Color> cellRendererMap = new HashMap<>();

    public CustomCellRenderer() {

        DEFAULT_RENDERER.setHorizontalAlignment(SwingConstants.CENTER);
        cellRendererMap.put(0, new Color(44, 217, 36, 180));
        cellRendererMap.put(1, new Color(56, 103, 245));
        cellRendererMap.put(2, new Color(252, 219, 1, 255));
        cellRendererMap.put(3, new Color(245, 2, 2));
        cellRendererMap.put(4, new Color(111, 53, 85));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent
                (table, value, isSelected, hasFocus, row, column);
        Color foreground = table.getForeground();
        Color background = table.getBackground();
        if (!isSelected) {
            if (column == 1) {
                Status result = (Status) value;
                if (result != null) foreground = cellRendererMap.get(result.getStatusId());

            }
            if (column == 2) {
                LocalDate erVed = (LocalDate) value;
                if (erVed.minusDays(NumberConstants.ATTENTION_DAYS).isBefore(LocalDate.now())) {
                    foreground = new Color(56, 103, 245);
                }
                if (erVed.minusDays(NumberConstants.WARNING_DAYS).isBefore(LocalDate.now())) {
                    foreground = new Color(252, 219, 1, 255);
                }
                if (erVed.isBefore(LocalDate.now())) {
                    foreground = new Color(245, 2, 2);
                }
            }
        } else {
            foreground = new Color(255, 255, 255);
            background = new Color(81, 89, 163);
        }
        renderer.setForeground(foreground);
        renderer.setBackground(background);
        return renderer;
    }
}

package com.petersoft.mgl.ui;

import com.github.lgooddatepicker.components.DatePicker;
import com.petersoft.mgl.model.Karbantartas;
import com.petersoft.mgl.model.KarbantartasTipus;
import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.service.KarbantartasService;
import com.petersoft.mgl.service.LepcsoService;
import com.petersoft.mgl.serviceimpl.KarbantartasServiceImpl;
import com.petersoft.mgl.serviceimpl.LepcsoServiceImpl;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class KarbanTartasInputPanel extends JPanel {
    private final MainFrame frame;
    private final List<Lepcso> lepcsoList;
    private final JComboBox<Integer> lepcsoSzamComboBox = new JComboBox<>();
    private final DefaultComboBoxModel<Integer> comboBoxModel = new DefaultComboBoxModel<>();
    private final DatePicker datePicker = new DatePicker(); //new CustomDatePickerClass();
    private final JList<Karbantartas> karbantartasJList = new JList<>();
    private final DefaultListModel<Karbantartas> karbModel = new DefaultListModel<>();
    private final KarbantartasService karbantartasService = new KarbantartasServiceImpl();
    private JList<KarbantartasTipus> karbTipusJlist = new JList<>();
    private JPanel buttonPanel;
    private JPanel lepcsoPanel;
    private JPanel karbantartasPanel;
    private JLabel locationLabel;
    private JLabel tipusLabel;
    private Lepcso lepcso;
    private JComboBox<Integer> muszakSzamaComboBox = new JComboBox<>();

    public KarbanTartasInputPanel(MainFrame frame) throws Throwable {
        this.frame = frame;
        LepcsoService lepcsoService = new LepcsoServiceImpl();
        this.lepcsoList = lepcsoService.getAllLepcso();
        setButtonPanel();
        setLepcsoPanel();
        setKarbantartasPanel();
        setAllPanels();
    }

    public KarbanTartasInputPanel(MainFrame frame, Lepcso lepcso) throws Throwable {
        this(frame);
        setLepcsoSzamComboBox(lepcso);
    }


    public void setLepcsoSzamComboBox(Lepcso lepcso) {
        lepcsoSzamComboBox.setSelectedItem(lepcso.getLepcsoSzama());
    }


    private void setAllPanels() {
        setLayout(new BorderLayout());
        add(lepcsoPanel, BorderLayout.NORTH);
        add(karbantartasPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setButtonPanel() {
        buttonPanel = new JPanel();
        JButton mainMenuButton = new JButton("Főmenü");
        JButton visszaButton = new JButton("Lépcső Lista");
        JButton hianyzoKarbListaButton = new JButton("Hiányzó Karbantartások");
        buttonPanel.add(mainMenuButton);
        buttonPanel.add(visszaButton);
        buttonPanel.add(hianyzoKarbListaButton);

        visszaButton.addActionListener(e -> {
            setVisible(false);
            try {
                frame.showLepcsoListPanel();
            } catch (Throwable exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
            frame.setVisible(true);
        });

        mainMenuButton.addActionListener(e -> {
            setVisible(false);
            frame.showFramePanel();
            frame.setVisible(true);
        });

        hianyzoKarbListaButton.addActionListener(
                e -> new HianyzoKarbListDialog(this, karbantartasService.getHianyzoKarbList()));


    }

    private void setLepcsoPanel() {
        lepcsoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        lepcsoPanel.setVisible(true);
        JLabel lepcsoLabel = new JLabel("Lépcső száma: ");
        locationLabel = new JLabel();
        locationLabel.setText("              ");
        locationLabel.setFont(new Font("Serif", Font.BOLD, 16));
        tipusLabel = new JLabel();
        tipusLabel.setText("           ");
        tipusLabel.setFont(new Font("Serif", Font.BOLD, 16));
        lepcsoSzamComboBox.setModel(comboBoxModel);
        AutoCompleteDecorator.decorate(lepcsoSzamComboBox);
        lepcsoSzamComboBox.setEditable(false);
        lepcsoSzamComboBox.setSize(20, 10);

        fillComboBoxWithLepcsoId();

        lepcsoPanel.add(lepcsoLabel, FlowLayout.LEFT);
        lepcsoPanel.add(lepcsoSzamComboBox, FlowLayout.CENTER);
        lepcsoPanel.add(tipusLabel);
        lepcsoPanel.add(locationLabel);

        lepcsoSzamComboBox.addActionListener(e -> {
            JComboBox<Integer> cb = (JComboBox<Integer>) e.getSource();
            int lepcsoSzama = (int) cb.getSelectedItem();
            if (cb.getSelectedIndex() != -1) {
                Optional<Lepcso> result = lepcsoList.stream()
                        .filter(l -> l.getLepcsoSzama() == lepcsoSzama)
                        .findFirst();
                this.lepcso = result.orElse(null);
                setLepcsoDetailsFromComboBox(lepcso);
                setKarbTableModel(lepcso);
                karbantartasJList.setModel(karbModel);
            }

        });
    }

    private void setKarbantartasPanel() {
        karbantartasPanel = new JPanel();
        List<KarbantartasTipus> karbTipusList = karbantartasService.getKarbantartasTipusList();
        karbTipusList.sort(Comparator.comparing(KarbantartasTipus::getKarbTipusId));
        karbTipusJlist = new JList(karbTipusList.toArray());
        karbTipusJlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JTextArea karbAzonLabel = new JTextArea("Karbantartás\nazonosító: ");
        karbantartasPanel.add(karbAzonLabel);

        ListCellRenderer<Karbantartas> listCellRenderer = new KarbantartasCellRenderer();
        karbantartasJList.setCellRenderer(listCellRenderer);
        karbantartasJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        karbantartasPanel.add(karbTipusJlist);

        JPanel midButtonPanel = new JPanel();
        midButtonPanel.setLayout(new BoxLayout(midButtonPanel, BoxLayout.Y_AXIS));
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.setLayout(new BoxLayout(rightButtonPanel, BoxLayout.PAGE_AXIS));


        karbantartasPanel.add(midButtonPanel);

        JButton karbTipusHozzaAd = new JButton("Kijelöltek hozzáadása");
        JButton karbTipusEltavolit = new JButton("Kijelöltek eltávolítása");
        JButton karbElvegezButton = new JButton("Kijelöltek elvégezve");
        midButtonPanel.add(karbTipusHozzaAd);
        midButtonPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        midButtonPanel.add(karbTipusEltavolit);

        karbTipusEltavolit.addActionListener(e -> {
                    int answer = JOptionPane.showConfirmDialog(null,
                            "Eltávolítod a kijelölt elemeket?",
                            "Karbantartás törlés",
                            JOptionPane.OK_CANCEL_OPTION);
                    if (answer == JOptionPane.OK_OPTION) {
                        List<Karbantartas> selectedToRemove = karbantartasJList.getSelectedValuesList();
                        selectedToRemove.forEach(k -> {
                            karbantartasService.deleteKarbantartas(k);
                            setKarbTableModel(lepcso);
                        });
                    }
                }
        );


        karbTipusHozzaAd.addActionListener(e -> {
            List<KarbantartasTipus> karbantartasTipusList = karbTipusJlist.getSelectedValuesList();
            List<KarbantartasTipus> meglevoKarbantartas = new ArrayList<>();
            for (int i = 0; i < karbantartasJList.getModel().getSize(); i++) {
                meglevoKarbantartas.add(karbantartasJList.getModel().getElementAt(i).getKarbantartasTipus());
            }
            if (lepcso != null) {
                for (KarbantartasTipus kTipus : karbantartasTipusList) {
                        Karbantartas k = new Karbantartas();
                        k.setKarbantartasTipus(kTipus);
                        k.setLepcso(lepcso);
                        karbantartasService.addKarbantartas(k);
                }
                karbTipusJlist.clearSelection();
                setKarbTableModel(lepcso);
            }
        });

        karbElvegezButton.addActionListener(e -> {
            List<Karbantartas> listSelected = karbantartasJList.getSelectedValuesList();
            if (!listSelected.isEmpty()) {
                for (Karbantartas k : listSelected) {
                    k.setElvegezve(true);
                    k.setKarbantartasDatum(datePicker.getDate());
                    k.setMuszakSzama((int) muszakSzamaComboBox.getSelectedItem());
                    karbantartasService.updateKarbantartas(k);
                }
                setKarbTableModel(lepcso);
            }
        });
        datePicker.setDateToToday();
        datePicker.addDateChangeListener(event -> setMuszakSzamaComboBox());
        setMuszakSzamaComboBox();
        rightButtonPanel.add(new JLabel("Elvégzés dátuma:"));
        rightButtonPanel.add(Box.createRigidArea(new Dimension(20, 10)));
        rightButtonPanel.add(datePicker);
        rightButtonPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        rightButtonPanel.add(new JLabel("Túr száma:"));
        rightButtonPanel.add(muszakSzamaComboBox);
        muszakSzamaComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        muszakSzamaComboBox.setMaximumSize(new Dimension(60, 10));
        rightButtonPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        rightButtonPanel.add(karbElvegezButton);

        Border innerBorder = BorderFactory.createTitledBorder("Karbantartás");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 10, 10, 10);
        karbantartasPanel.add(new JScrollPane(karbantartasJList)).setPreferredSize(new Dimension(300, 200));
        karbantartasPanel.add(rightButtonPanel);
        karbantartasPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));


    }


    private void fillComboBoxWithLepcsoId() {
        comboBoxModel.removeAllElements();
        if (lepcsoList != null) {
            lepcsoList.forEach(i -> comboBoxModel.addElement(i.getLepcsoSzama()));
        }
    }

    private void setLepcsoDetailsFromComboBox(Lepcso lepcso) {
        locationLabel.setText(lepcso.getLocation().getLocationName());
        tipusLabel.setText(lepcso.getTipus().getTipusNev());
    }

    private void setKarbTableModel(Lepcso lepcso) {
        karbModel.clear();
        List<Karbantartas> karbantartasList = karbantartasService.getKarbantartasList(lepcso).stream()
                .sorted(Comparator.comparing(
                        Karbantartas::getKarbantartasDatum, Comparator.nullsFirst(Comparator.reverseOrder())))
                .collect(Collectors.toList());
        karbantartasList.forEach(k -> karbModel.addElement(k));
        karbantartasJList.setModel(karbModel);
    }

    private void setMuszakSzamaComboBox() {
        final LocalDate REFERENCE_DATE = LocalDate.of(2020, 1, 2);
        Map<Integer, Integer[]> muszakMap = new HashMap<>();
        muszakMap.put(0, new Integer[]{4, 1});
        muszakMap.put(1, new Integer[]{3, 4});
        muszakMap.put(2, new Integer[]{2, 3});
        muszakMap.put(3, new Integer[]{1, 2});
        int passedDays = Math.abs((int) ChronoUnit.DAYS.between(REFERENCE_DATE, datePicker.getDate()));

        muszakSzamaComboBox.setModel(new DefaultComboBoxModel<>(muszakMap.get(passedDays % 4)));
        muszakSzamaComboBox.setSelectedIndex(0);
        muszakSzamaComboBox.setSize(30, 10);
    }

    private void setMuszakFromDatePicker() {

    }

}


class KarbantartasCellRenderer extends JLabel implements ListCellRenderer<Karbantartas> {
    public KarbantartasCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Karbantartas> list,
                                                  Karbantartas value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        if (!value.isElvegezve()) {
            setForeground(new Color(0xFF0000));
        } else setForeground(new Color(0xFF0CB302, true));

        String elvegezveString = value.isElvegezve() ? "Elvégezve" : "Nincs elvégezve";
        String muszakSzamString = "";

        if (value.getMuszakSzama() != 0) {
            muszakSzamString = value.getMuszakSzama() + ". túr";
        }

        setText(value.getKarbantartasTipus().getKarbTipusKod() +
                " : : " + elvegezveString +
                " : : " + value.getKarbantartasDatum() +
                " : : " + muszakSzamString);

        return this;
    }


}


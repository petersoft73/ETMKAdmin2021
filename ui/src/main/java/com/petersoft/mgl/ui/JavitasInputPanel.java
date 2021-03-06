package com.petersoft.mgl.ui;

import com.github.lgooddatepicker.components.DatePicker;
import com.petersoft.mgl.model.Hiba;
import com.petersoft.mgl.model.Javitas;
import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.model.Status;
import com.petersoft.mgl.service.HibaService;
import com.petersoft.mgl.service.JavitasService;
import com.petersoft.mgl.service.LepcsoService;
import com.petersoft.mgl.serviceimpl.HibaServiceImpl;
import com.petersoft.mgl.serviceimpl.JavitasServiceImpl;
import com.petersoft.mgl.serviceimpl.LepcsoServiceImpl;
import com.petersoft.mgl.ui.highlighter.JListHighlightedFilterExample;
import org.apache.commons.lang3.text.WordUtils;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JavitasInputPanel extends JPanel {
    private static Hiba hiba;
    private static JTextArea hibaTextArea;
    private final MainFrame frame;
    private final HibaService hibaService;
    private final JavitasService javitasService;
    private final LepcsoService lepcsoService;
    private final List<Lepcso> lepcsoList;
    protected List<Hiba> hibaListSet;
    private DatePicker datePicker;
    private List<Javitas> javitasList;
    private List<Status> statusList;
    private Lepcso lepcso;
    //  private Hiba hiba;
    private JPanel buttonPanel;
    private JButton mainMenuButton;
    private JButton visszaButton;
    private JButton mentesButton;
    private JPanel javitasPanel;
    private JTextArea javitasLeirasField;
    private JComboBox<Status> status;
    private JPanel lepcsoPanel;
    private JLabel locationLabel;
    private JLabel tipusLabel;
    private JComboBox<Integer> lepcsoJComboBox = new JComboBox<>();
    private DefaultComboBoxModel<Integer> comboBoxModel = new DefaultComboBoxModel<>();
    private JComboBox<Integer> muszakSzamaComboBox;
    private JPanel hibaListPanel;
    private HibaListDialog hibaListDialog;
    private Javitas javitas;
    private int muszakSzama;
    private String hibaTextField;

    public JavitasInputPanel(MainFrame frame) throws Throwable {

        this.frame = frame;
        this.lepcsoService = new LepcsoServiceImpl();
        this.lepcsoList = lepcsoService.getAllLepcso();
        this.statusList = lepcsoService.getStatusList();
        this.hibaService = new HibaServiceImpl();
        this.javitasService = new JavitasServiceImpl();

        setLayout(new BorderLayout());
        setLepcsoPanel();
        datePicker.addDateChangeListener(event -> setMuszakSzamaComboBox());
        add(lepcsoPanel, BorderLayout.NORTH);
        setButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        setJavitasPanel();
        add(javitasPanel, BorderLayout.CENTER);
        setLepcsoFieldCombox();
        AutoCompleteDecorator.decorate(lepcsoJComboBox);


    }

    public JavitasInputPanel(MainFrame frame, Lepcso lepcso) throws Throwable {
        this(frame);
        this.lepcso = lepcso;
        lepcsoJComboBox.setSelectedItem(lepcso.getLepcsoSzama());

    }

    public JavitasInputPanel(MainFrame frame, Lepcso lepcso, Javitas javitas) throws Throwable {
        this(frame);
        this.lepcso = lepcso;
        this.javitas = javitas;
        lepcsoJComboBox.setSelectedItem(lepcso.getLepcsoSzama());
        javitasLeirasField.setText(javitas.getLeiras());
        datePicker.setDate(javitas.getJavitasKelte());
        javitasLeirasField.setLineWrap(true);
        javitasLeirasField.setWrapStyleWord(true);
        if (javitas.getHiba() != null) {
            this.hiba = javitas.getHiba();
            //   hibaListPanel.setText(WordUtils.wrap(javitas.getHiba().getLeiras(), 40));
        }
    }

    public JavitasInputPanel(MainFrame frame, Lepcso lepcso, Hiba hiba) throws Throwable {
        this(frame, lepcso);
        this.lepcso = lepcso;
        this.hiba = hiba;
        //  hibaListPanel.setText(WordUtils.wrap(hiba.getLeiras(), 40));

    }

    public static void setHiba(Hiba h) {
        hiba = h;
    }

    public static void setHibaTextArea(String hibaTextField) {
        hibaTextArea.setText(hibaTextField);
    }

    private void setButtonPanel() {

        buttonPanel = new JPanel();
        mainMenuButton = new JButton("Főmenü");
        visszaButton = new JButton("Lépcső Lista");
        mentesButton = new JButton("Mentés");
        buttonPanel.add(mainMenuButton);
        buttonPanel.add(visszaButton);
        buttonPanel.add(mentesButton);
    }

    private void setJavitasPanel() throws Exception {
        javitasPanel = new JPanel(new GridBagLayout());
        JLabel javitasKelteLabel = new JLabel("Javítás kelte: ");
        JLabel javitasLeirasLabel = new JLabel("Javítás leírása (max 250 karakter) ");
        JLabel hibaListLabel = new JLabel("Melyik hiba javítása?\n(Választás listából,\nvagy új hiba felvitele)");
        hibaListLabel.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 14));
        hibaListLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hibaTextArea = new JTextArea(5, 25);
        hibaTextArea.setLineWrap(true);
        hibaTextArea.setWrapStyleWord(true);
        hibaTextArea.setEditable(false);
//        hibaListField.setWrapStyleWord(true);
//        hibaListField.setLineWrap(true);
//        hibaListField.setFont(new Font("Serif", Font.BOLD, 14));
        javitasLeirasField = new JTextArea(5, 25);
        javitasLeirasField.setLineWrap(true);
        javitasLeirasField.setWrapStyleWord(true);
        javitasLeirasField.setFont(new Font("Serif", Font.BOLD, 14));
        javitasLeirasField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                return !javitasLeirasField.getText().isEmpty();
            }
        });


        hibaListLabel.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            List<Hiba> listHiba = hibaService.getHibaList();
                            JListHighlightedFilterExample.start(listHiba);
//                            new HibaListDialog(hibaService.getHibaList());
//                            hiba = HibaHighlightClass.getHiba();
//                            hibaTextField = HibaHighlightClass.getTypedText();
//                            hibaTextArea.setText(hibaTextField);
//                            System.out.println(hibaTextArea.getText());
                        //    System.out.println("Hiba: " + hiba + "HibaTextArea: " + hibaTextArea.getText());
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, exception.getStackTrace());
                        }

                    }
                }
        );

        mentesButton.addActionListener(e -> {
          //  System.out.println("Hiba: " + hiba + "HibaTextArea: " + hibaTextArea.getText());
            if (lepcso != null) {
                if (!javitasLeirasField.getText().isEmpty()) {
                    int click = JOptionPane.showConfirmDialog(null,
                            "Helyesek az adatok?\n"
                                    + lepcso.getLepcsoSzama() + "\n"
                                    + WordUtils.wrap(javitasLeirasField.getText(), 30) + "\n"
                                    + datePicker.getDateStringOrEmptyString() + "\n"
                                    + status.getSelectedItem().toString() + "\n"
                                    + muszakSzama
                                    + ". túr" + "\n"
                                    + hibaTextArea.getText(),
                            "Javítás Felvitele", JOptionPane.YES_NO_OPTION);
                    if (click == JOptionPane.YES_OPTION) {
                        lepcso.setStatus((Status) status.getSelectedItem());
                        if (hiba == null) {
                            if (!hibaTextArea.getText().isEmpty()){
                                hiba = new Hiba(hibaTextArea.getText());
                            }else hiba = null;
                        }
                        if (javitas == null) {
                            this.javitas = new Javitas(lepcso,
                                    javitasLeirasField.getText(),
                                    datePicker.getDate(),
                                    muszakSzama,
                                    hiba);
                        } else {
                            this.javitas.setLeiras(javitasLeirasField.getText());
                            this.javitas.setJavitasKelte(datePicker.getDate());
                            this.javitas.setLepcsoUzemkepes((Status) status.getSelectedItem());
                            this.javitas.setMuszakSzama(muszakSzama);
                            this.javitas.setHiba(hiba);
                        }
                        try {
                            if (hiba != null) hibaService.saveHiba(hiba);
                            javitasService.saveJavitas(this.javitas);
                            lepcsoService.saveLepcso(lepcso);
                            javitas = null;
                            hiba = null;
                            hibaTextArea.setText("");
                            JOptionPane.showMessageDialog(null, "Sikeres rögzítés");
                            //frame.showLepcsoListPanel();
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, exception.getStackTrace());
                        } catch (Throwable th) {
                            JOptionPane.showMessageDialog(null, th.getStackTrace());
                        }

                    }
                } else JOptionPane.showMessageDialog(null,
                        "A javítás mező nem lehet üres",
                        "Hiba", JOptionPane.WARNING_MESSAGE);
            } else JOptionPane.showMessageDialog(null,
                    "A lépcső száma nem lehet üres",
                    "Hiba", JOptionPane.WARNING_MESSAGE);
        });

        mainMenuButton.addActionListener(e -> {
            setVisible(false);
            frame.showFramePanel();
        });

        visszaButton.addActionListener(e -> {
            setVisible(false);
            try {
                frame.showLepcsoListPanel();
            } catch (Throwable exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        });

        JLabel uzemkepesLabel = new JLabel("A lépcső üzemképes ?");
        status = new JComboBox<>();
        status.setModel(new DefaultComboBoxModel(statusList.toArray()));

        JButton javMezoDelete = new JButton("Javítás mező törlése");
        JButton hibaMezoDelete = new JButton("Hiba mező törlése");

        GridBagConstraints gc = new GridBagConstraints();

        ////// Javitas leiras

        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        javitasPanel.add(javitasLeirasLabel, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        javitasPanel.add(new JScrollPane(javitasLeirasField), gc);

        gc.gridx = 2;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        javitasPanel.add(javMezoDelete, gc);

        //////////////////////// Javitas kelte ////////

        gc.gridy = 1;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        javitasPanel.add(javitasKelteLabel, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        javitasPanel.add(datePicker, gc);

        ///////////    Hibalista Click on Label /////////

        gc.gridy = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        javitasPanel.add(hibaListLabel, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        javitasPanel.add(hibaTextArea, gc);

        gc.gridx = 2;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        javitasPanel.add(hibaMezoDelete, gc);

        ////////////////////     Muszak Szama ComboBox ///////

        gc.gridy = 3;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        javitasPanel.add(new JLabel("Túr Száma: "), gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        javitasPanel.add(muszakSzamaComboBox, gc);

        ////////////////////     Uzemkepes Radio Button ///////

        gc.gridy = 4;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        javitasPanel.add(uzemkepesLabel, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        javitasPanel.add(status, gc);

        javMezoDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javitasLeirasField.setText("");
            }
        });

        hibaMezoDelete.addActionListener(e -> {
            hibaTextArea.setText("");
            hiba = null;
        });

        Border innerBorder = BorderFactory.createTitledBorder("Javítás bevitele");
        Border outerBorder = BorderFactory.createEmptyBorder(50, 50, 50, 50);
        javitasPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
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
        lepcsoJComboBox.setModel(comboBoxModel);
        AutoCompleteDecorator.decorate(lepcsoJComboBox);
        lepcsoJComboBox.setEditable(false);
        lepcsoJComboBox.setSize(20, 10);
        fillComboBoxWithLepcsoId();
        datePicker = new DatePicker();
        lepcsoJComboBox.setSelectedIndex(-1);
        Integer[] muszakSzamArray = {1, 2, 3, 4, 5};
        muszakSzamaComboBox = new JComboBox<>(muszakSzamArray);
        muszakSzamaComboBox.setEditable(false);
        datePicker.addDateChangeListener(e -> setMuszakSzamaComboBox());
        datePicker.setDateToToday();
        //   setMuszakSzamaComboBox();
        //   muszakSzamaComboBox.setSelectedIndex(0);
        //  muszakSzamaComboBox.setSelectedIndex(0);
        //  muszakSzama = 1;
        setListenerMuszakComboBox();
        lepcsoPanel.add(lepcsoLabel, FlowLayout.LEFT);
        lepcsoPanel.add(lepcsoJComboBox, FlowLayout.CENTER);
        lepcsoPanel.add(tipusLabel);
        lepcsoPanel.add(locationLabel);
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
        muszakSzama = (int) muszakSzamaComboBox.getSelectedItem();
        muszakSzamaComboBox.setSize(30, 10);
    }

    private void fillComboBoxWithLepcsoId() {
        comboBoxModel.removeAllElements();
        if (lepcsoList != null) {
            lepcsoList.forEach(i -> comboBoxModel.addElement(i.getLepcsoSzama()));
        }
    }

    private void setLepcsoFieldCombox() {
        lepcsoJComboBox.addActionListener(e -> {
            JComboBox<Integer> cb = (JComboBox<Integer>) e.getSource();
            int lepcsoSzama = (int) cb.getSelectedItem();
            if (cb.getSelectedIndex() != -1) {
                Optional<Lepcso> result = lepcsoList.stream()
                        .filter(l -> l.getLepcsoSzama() == lepcsoSzama)
                        .findFirst();
                this.lepcso = result.orElse(null);
                setLepcsoDetailsFromComboBox(lepcso);
            }
        });


    }

    private void setLepcsoDetailsFromComboBox(Lepcso lepcso) {
        locationLabel.setText(lepcso.getLocation().getLocationName());
        tipusLabel.setText(lepcso.getTipus().getTipusNev());
        status.setSelectedItem(lepcso.getStatus());
    }

    private void setListenerMuszakComboBox() {
        muszakSzamaComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                muszakSzama = (int) muszakSzamaComboBox.getSelectedItem();
            }
        });
    }


}





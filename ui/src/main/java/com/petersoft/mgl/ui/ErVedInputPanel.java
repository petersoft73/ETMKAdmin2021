package com.petersoft.mgl.ui;

import com.github.lgooddatepicker.components.DatePicker;
import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.service.LepcsoService;
import com.petersoft.mgl.serviceimpl.LepcsoServiceImpl;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ErVedInputPanel extends JPanel {
    private final List<Lepcso> lepcsoList;
    private final LepcsoService lepcsoService;
    private final DatePicker datePicker1;
    private final DatePicker datePicker2;
    private Lepcso lepcso;
    private JComboBox<Integer> lepcsoJComboBox;
    private DefaultComboBoxModel<Integer> comboBoxModel = new DefaultComboBoxModel<>();
    private JLabel locationLabel;
    private JLabel tipusLabel;
    //  private JFormattedTextField erVedErvenyesField, erVedKeltField;
    private JTextField erVedIdField;
    private JPanel lepcsoPanel, erVedPanel, buttonPanel;
    private JButton visszaButton, mentesButton, mainMenuButton;

    public ErVedInputPanel(MainFrame frame) throws Throwable {
        this.lepcsoService = new LepcsoServiceImpl();
        this.lepcsoList = lepcsoService.getAllLepcso();
        this.datePicker1 = new DatePicker();
        this.datePicker2 = new DatePicker();
        datePicker1.setDate(LocalDate.now());

        setLayout(new BorderLayout());
        setLepcsoPanel();
        add(lepcsoPanel, BorderLayout.NORTH);
        setErVedPanel();
        add(erVedPanel, BorderLayout.CENTER);
        setButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        frame.add(this);
        AutoCompleteDecorator.decorate(lepcsoJComboBox);

        datePicker1.addDateChangeListener(e -> datePicker2.setDate(datePicker1.getDate().plusYears(3)));


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


        mentesButton.addActionListener(e -> {
            if (lepcsoJComboBox.getSelectedIndex() != -1
                    && !erVedIdField.getText().isEmpty()) {
                //   erVedKeltField.setText(datePicker1.getDateStringOrEmptyString());
                //   erVedErvenyesField.setText(datePicker2.getDateStringOrEmptyString());
                int click = JOptionPane.showConfirmDialog(null,
                        "Helyesek az adatok?\n" + erVedIdField.getText() + "\n"
                                + datePicker1.getDate() + "\n"
                                + datePicker2.getDate(),
                        "Érintésvédelem", JOptionPane.YES_NO_OPTION);
                if (click == JOptionPane.YES_OPTION) {
                    try {
                        lepcso.getErintesVedelem().setJegyzokonyvSzam(erVedIdField.getText());
                        lepcso.getErintesVedelem().setErvenyes(datePicker2.getDate());
                        lepcso.getErintesVedelem().setKelt(datePicker1.getDate());
                        lepcsoService.saveLepcso(lepcso);
                        JOptionPane.showMessageDialog(null, "Sikeres mentés");
                    } catch (Throwable ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Egy vagy több mező nincs kitöltve"
                        , "Hiba", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        visszaButton.addActionListener(e -> {
            setVisible(false);
            try {
                frame.showLepcsoListPanel();
            } catch (Throwable exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        });
        setVisible(true);

        mainMenuButton.addActionListener(e -> {
            setVisible(false);
            frame.showFramePanel();
        });
    }


    public ErVedInputPanel(MainFrame frame, Lepcso lepcso) throws Throwable {
        this(frame);
        lepcsoJComboBox.setSelectedItem(lepcso.getLepcsoSzama());
        setLepcsoDetailsFromComboBox(lepcso);

    }

    static LocalDate parseDate(String text) {
        return LocalDate.parse(text);
    }

    private void setLepcsoDetailsFromComboBox(Lepcso lepcso) {
        locationLabel.setText(lepcso.getLocation().getLocationName());
        tipusLabel.setText(lepcso.getTipus().getTipusNev());
        datePicker2.setDate(lepcso.getErintesVedelem().getErvenyes());
        erVedIdField.setText(lepcso.getErintesVedelem().getJegyzokonyvSzam());
        datePicker1.setDate(lepcso.getErintesVedelem().getKelt());
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
        lepcsoJComboBox = new JComboBox<>();
        lepcsoJComboBox.setModel(comboBoxModel);
        lepcsoJComboBox.setEditable(false);
        lepcsoJComboBox.setSize(20, 10);
        fillComboBoxWithLepcsoId();
        lepcsoJComboBox.setSelectedIndex(-1);

        lepcsoPanel.add(lepcsoLabel, FlowLayout.LEFT);
        lepcsoPanel.add(lepcsoJComboBox, FlowLayout.CENTER);
        lepcsoPanel.add(tipusLabel);
        lepcsoPanel.add(locationLabel);
    }

    private void setErVedPanel() {
        erVedPanel = new JPanel(new GridBagLayout());
        JLabel ervedKelteLabel = new JLabel("Jegyzőkönyv kelte: ");
        JLabel erVedErvenyesLabel = new JLabel("Érvényesség: ");
        JLabel erVedIdLabel = new JLabel("Jegyzőkönyv száma: ");
        //     erVedKeltField = new JFormattedTextField();
        //    erVedKeltField.setFont(new Font("Serif", Font.BOLD, 14));
        //  erVedKeltField.setInputVerifier(new DateParseVerifier());
        //     erVedErvenyesField = new JFormattedTextField();
        //    erVedErvenyesField.setFont(new Font("Serif", Font.BOLD, 14));
        //   erVedErvenyesField.setInputVerifier(new DateParseVerifier());
        erVedIdField = new JTextField(16);
        erVedIdField.setFont(new Font("Serif", Font.BOLD, 14));

        GridBagConstraints gc = new GridBagConstraints();

        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        erVedPanel.add(erVedIdLabel, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        erVedPanel.add(erVedIdField, gc);

        //////////////////////// ErVed kelte ////////

        gc.gridy = 1;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        erVedPanel.add(ervedKelteLabel, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        erVedPanel.add(datePicker1, gc);

        ////////////////////     ErVed Ervenyes ///////

        gc.gridy = 2;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        erVedPanel.add(erVedErvenyesLabel, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        erVedPanel.add(datePicker2, gc);

        Border innerBorder = BorderFactory.createTitledBorder("Érintésvédelem");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 10, 10, 10);
        erVedPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));


    }

    private void fillComboBoxWithLepcsoId() {
        comboBoxModel.removeAllElements();
        if (lepcsoList != null) {
            lepcsoList.forEach(i -> comboBoxModel.addElement(i.getLepcsoSzama()));
        }
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
}


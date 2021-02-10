package com.petersoft.mgl.ui;

import com.github.lgooddatepicker.components.DatePicker;
import com.petersoft.mgl.model.Hiba;
import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.model.Status;
import com.petersoft.mgl.service.HibaService;
import com.petersoft.mgl.service.LepcsoService;
import com.petersoft.mgl.serviceimpl.HibaServiceImpl;
import com.petersoft.mgl.serviceimpl.LepcsoServiceImpl;
import org.apache.commons.lang3.text.WordUtils;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class HibaInputPanel extends JPanel {
    private final MainFrame frame;
    private final List<Lepcso> lepcsoList;
    private final HibaService hibaService;
    private final DatePicker datePicker;
    private Hiba hiba;
    private Lepcso lepcso;
    private JComboBox<Integer> lepcsoJComboBox;
    private JLabel locationLabel;
    private JLabel tipusLabel;
    private List<Status> statusList;
    private JComboBox<Status> status;
    private JFormattedTextField hibaKeltField;
    private JTextArea hibaLeirasField;
    private JPanel lepcsoPanel, hibaPanel, buttonPanel;
    private JButton visszaButton, mentesButton, mainMenuButton;

    public HibaInputPanel(MainFrame frame) throws Throwable {
        this.frame = frame;
        LepcsoService lepcsoService = new LepcsoServiceImpl();
        hibaService = new HibaServiceImpl();
        this.lepcsoList = lepcsoService.getAllLepcso();
        this.statusList = lepcsoService.getStatusList();
        this.datePicker = new DatePicker();
        datePicker.setDate(LocalDate.now());
        setLayout(new BorderLayout());
        setLepcsoPanel();
        add(lepcsoPanel, BorderLayout.NORTH);
        setHibaPanel();
        add(hibaPanel, BorderLayout.CENTER);
        setButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        frame.add(this);
        AutoCompleteDecorator.decorate(lepcsoJComboBox);
        setLepcsoFieldCombox();
        setListeners();
    }

    public HibaInputPanel(MainFrame frame, Lepcso lepcso) throws Throwable {
        this(frame);
        this.lepcso = lepcso;
        lepcsoJComboBox.setSelectedItem(lepcso.getLepcsoSzama());
        setLepcsoDetailsFromComboBox(lepcso);
    }

    public HibaInputPanel(MainFrame frame, Lepcso lepcso, Hiba hiba) throws Throwable {
        this(frame, lepcso);
        this.hiba = hiba;
        setHibaFields(hiba);
    }

    private void setHibaFields(Hiba hiba) {
      //  hibaKeltField.setText(hiba.getDatum().toString());
      //  datePicker.setDate(hiba.getDatum());
        hibaLeirasField.append(hiba.getLeiras());

    }

    private void setListeners() {
        mentesButton.addActionListener(e -> {
            if (lepcsoJComboBox.getSelectedIndex() != -1
                    && !hibaLeirasField.getText().isEmpty()) {
                hibaKeltField.setText(datePicker.getDateStringOrEmptyString());
                int click = JOptionPane.showConfirmDialog(null,
                        "Helyesek az adatok?\n"
                                + lepcso.getLepcsoSzama() + "\n"
                                + WordUtils.wrap(hibaLeirasField.getText(), 30) + "\n"
                                + hibaKeltField.getText(),
                        "Hiba Felvitele", JOptionPane.YES_NO_OPTION);
                if (click == JOptionPane.YES_OPTION) {
                    lepcso.setStatus((Status) status.getSelectedItem());
                    if (hiba == null) {
                        hiba = new Hiba();
                    }
                    hiba.setLeiras(hibaLeirasField.getText());
                 //   hiba.setDatum(datePicker.getDate());
                 //   hiba.setLepcso(lepcso);
                    try {
                        hibaService.saveHiba(hiba);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                    setVisible(false);
                    try {
                        frame.showLepcsoListPanel();
                    } catch (Throwable exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
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

        mainMenuButton.addActionListener(e -> {
            setVisible(false);
            frame.showFramePanel();
        });

    }

    private void setLepcsoFieldCombox() {
        lepcsoJComboBox.addActionListener(e -> {
            String typedText = ((JTextField) lepcsoJComboBox.getEditor().getEditorComponent()).getText();
            System.out.println("Typed Text: " + typedText);
            if (!typedText.isEmpty() && typedText.matches("^[0-9]*$")) {
                if (!typedText.isEmpty() && typedText.matches("^[0-9]{4,}$")) {
                    int lepcsoSzama = Integer.parseInt(typedText);
                    Optional<Lepcso> result = lepcsoList.stream()
                            .filter(l -> l.getLepcsoSzama() == lepcsoSzama)
                            .findFirst();
                    lepcso = result.orElse(null);
                    if (lepcso != null) {
                        setLepcsoDetailsFromComboBox(lepcso);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Nem létező lépcsőszám",
                                "Hiba", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        }




        private void fillComboBoxWithLepcsoId () {
            if (lepcsoList != null) {
                for (Lepcso value : lepcsoList) {
                    lepcsoJComboBox.addItem(value.getLepcsoSzama());
                }
            }
        }

        private void setButtonPanel () {
            buttonPanel = new JPanel();
            mainMenuButton = new JButton("Főmenü");
            visszaButton = new JButton("Lépcső Lista");
            mentesButton = new JButton("Mentés");
            buttonPanel.add(mainMenuButton);
            buttonPanel.add(visszaButton);
            buttonPanel.add(mentesButton);
        }

        private void setHibaPanel () {
            hibaPanel = new JPanel(new GridBagLayout());
            JLabel hibaKelteLabel = new JLabel("Hiba kelte: ");
            JLabel hibaLeirasLabel = new JLabel("Hiba leírása (max 250 karakter) ");
            hibaKeltField = new JFormattedTextField(datePicker.getComponentDateTextField());
            hibaKeltField.setFont(new Font("Serif", Font.BOLD, 14));
            hibaKeltField.setInputVerifier(new DateParseVerifier());
            hibaLeirasField = new JTextArea(6, 40);
            hibaLeirasField.setLineWrap(true);
            hibaLeirasField.setWrapStyleWord(true);
            hibaLeirasField.setFont(new Font("Serif", Font.BOLD, 14));
            hibaLeirasField.setInputVerifier(new InputVerifier() {
                @Override
                public boolean verify(JComponent input) {
                    return !hibaLeirasField.getText().isEmpty();
                }
            });

            JLabel uzemkepesLabel = new JLabel("A lépcső üzemképes ?");
            status = new JComboBox<>();
            status.setModel(new DefaultComboBoxModel(statusList.toArray()));

            GridBagConstraints gc = new GridBagConstraints();

            ////// Hiba leiras

            gc.gridy = 0;

            gc.weightx = 1;
            gc.weighty = 0.1;

            gc.gridx = 0;
            gc.fill = GridBagConstraints.NONE;
            gc.anchor = GridBagConstraints.LINE_END;
            gc.insets = new Insets(0, 0, 0, 5);
            hibaPanel.add(hibaLeirasLabel, gc);

            gc.gridx = 1;
            gc.insets = new Insets(0, 0, 0, 0);
            gc.anchor = GridBagConstraints.LINE_START;
            hibaPanel.add(new JScrollPane(hibaLeirasField), gc);

            //////////////////////// Hiba kelte ////////

            gc.gridy = 1;
            gc.weightx = 1;
            gc.weighty = 0.1;
            gc.gridx = 0;
            gc.anchor = GridBagConstraints.LINE_END;
            gc.insets = new Insets(0, 0, 0, 5);
            hibaPanel.add(hibaKelteLabel, gc);

            gc.gridx = 1;
            gc.insets = new Insets(0, 0, 0, 0);
            gc.anchor = GridBagConstraints.LINE_START;
            hibaPanel.add(datePicker, gc);

            ////////////////////     Uzemkepes Combo Box ///////

            gc.gridy = 2;
            gc.weightx = 1;
            gc.weighty = 0.1;
            gc.gridx = 0;
            gc.anchor = GridBagConstraints.LINE_END;
            gc.insets = new Insets(0, 0, 0, 5);
            hibaPanel.add(uzemkepesLabel, gc);

            gc.gridx = 1;
            gc.insets = new Insets(0, 0, 0, 0);
            gc.anchor = GridBagConstraints.LINE_START;
            hibaPanel.add(status, gc);

            Border innerBorder = BorderFactory.createTitledBorder("Hiba bevitele");
            Border outerBorder = BorderFactory.createEmptyBorder(50, 50, 50, 50);
            hibaPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        }

        private void setLepcsoPanel () {
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
            lepcsoJComboBox.setEditable(true);
            lepcsoJComboBox.setSize(20, 10);
            fillComboBoxWithLepcsoId();
            lepcsoJComboBox.setSelectedIndex(-1);

            lepcsoPanel.add(lepcsoLabel, FlowLayout.LEFT);
            lepcsoPanel.add(lepcsoJComboBox, FlowLayout.CENTER);
            lepcsoPanel.add(tipusLabel);
            lepcsoPanel.add(locationLabel);
        }

        private void setLepcsoDetailsFromComboBox (Lepcso lepcso){
            locationLabel.setText(lepcso.getLocation().getLocationName());
            tipusLabel.setText(lepcso.getTipus().getTipusNev());
            status.setSelectedItem(lepcso.getStatus());
        }
    }




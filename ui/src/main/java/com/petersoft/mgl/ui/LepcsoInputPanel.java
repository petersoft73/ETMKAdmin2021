package com.petersoft.mgl.ui;

import com.petersoft.mgl.model.*;
import com.petersoft.mgl.service.LepcsoService;
import com.petersoft.mgl.serviceimpl.LepcsoServiceImpl;
import com.petersoft.mgl.utility.StringConstants;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("rawtypes")
public class LepcsoInputPanel extends JPanel {
    private final MainFrame frame;
    private final LepcsoService lepcsoService;
    private JButton saveButton;
    private JButton cancelButton, mainMenuButton;
    private JComboBox<Lepcso> lepcsoSzamField;
    private JComboBox<Location> locationField;
    private JComboBox<Tipus> tipusField;
    private JComboBox<Status> statusField;
    private Location location;
    private Tipus tipus;
    private Lepcso lepcso;
    private Status status;
    private JButton deleteButton;

    public LepcsoInputPanel(MainFrame frame) throws Throwable {
        this.frame = frame;
        lepcsoService = new LepcsoServiceImpl();
        initVariables();
        refreshContent();
        initButtons();
        initPanel();
        initActionListeners();
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    private void initActionListeners() {
        saveButton.addActionListener(e -> {
            System.out.println(lepcsoSzamField.getSelectedIndex());
            JOptionPane.showMessageDialog(null,
                    "Lépcsőszám: " + lepcsoSzamField.getSelectedItem().toString() +
                            "\nHelyszín: " + locationField.getSelectedItem().toString() +
                            "\nTípus: " + tipusField.getSelectedItem().toString() +
                            "\nÁllapot: " + statusField.getSelectedItem().toString());
            if (lepcsoSzamField.getSelectedIndex() == -1) {
                if (lepcsoSzamField.getSelectedItem().toString()
                        .matches("^[1-9][0-9]*$")) {
                    this.lepcso = new Lepcso();
                    lepcso.setLepcsoSzama(Integer.parseInt((String) lepcsoSzamField.getSelectedItem()));
                    lepcso.setErintesVedelem(new ErintesVedelem(
                            LocalDate.now(), LocalDate.now()));
                    lepcso.setStatus((Status) statusField.getSelectedItem());
                    try {
                        saveLepcso();
                    } catch (Throwable exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                    JOptionPane.showMessageDialog(null, "Sikeresen létrehozva");

                } else {
                    JOptionPane
                            .showMessageDialog(null,
                                    "A lépcső száma nem tartalmazhat betűket és nem kezdődhet 0-val.",
                                    "Hiba",
                                    JOptionPane.ERROR_MESSAGE);
                }
            } else {
                try {
                    saveLepcso();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Nem sikerult a mentes\n" + ex.getMessage());
                }
            }


            try {
                refreshContent();
            } catch (Throwable exception) {
                exception.printStackTrace();
            }
            lepcso.setLocation(location);
            lepcso.setTipus(tipus);


        });

        cancelButton.addActionListener(e ->
                resetFields()
        );

        mainMenuButton.addActionListener(e -> {
            setVisible(false);
            frame.showFramePanel();
        });

        locationField.addActionListener(e -> {
            JComboBox<Location> cb = (JComboBox) e.getSource();
            if (cb.getSelectedIndex() == -1) {
                location = new Location();
                location.setLocationName(cb.getEditor().getItem().toString());
            } else {
                location = (Location) cb.getSelectedItem();
            }
        });

        tipusField.addActionListener(e -> {
            JComboBox<Tipus> cb = (JComboBox) e.getSource();
            if (cb.getSelectedIndex() == -1) {
                tipus = new Tipus();
                tipus.setTipusNev(cb.getEditor().getItem().toString());
            } else tipus = (Tipus) cb.getSelectedItem();
        });

        lepcsoSzamField.addActionListener(e -> {
            JComboBox<Lepcso> cb = (JComboBox) e.getSource();
            if (cb.getSelectedIndex() != -1) {
                lepcso = (Lepcso) cb.getSelectedItem();
                tipusField.setSelectedItem(lepcso.getTipus());
                locationField.setSelectedItem(lepcso.getLocation());
                statusField.setSelectedItem(lepcso.getStatus());
            }
        });

        statusField.addActionListener(e -> {
            JComboBox<Status> cb = (JComboBox<Status>) e.getSource();
            status = (Status) cb.getSelectedItem();
        });

        deleteButton.addActionListener(e -> {
            if (lepcsoSzamField.getSelectedIndex() != -1) {
                lepcso = (Lepcso) lepcsoSzamField.getSelectedItem();
                try {
                    lepcsoService.deleteLepcso(lepcso);
                    refreshContent();
                    JOptionPane.showMessageDialog(null, "Sikeresen törölve");
                } catch (Throwable ex) {
                    try {
                        lepcsoService.rollback();
                    } catch (Throwable exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                    JOptionPane.showMessageDialog(null, "Nem lehet torolni ezt a lepcsot");
                }
            }
        });
    }

    private void initButtons() {
        saveButton = new JButton("Lépcsőkar Mentése");
        cancelButton = new JButton("Alaphelyzet");
        mainMenuButton = new JButton("Főmenü");
        deleteButton = new JButton("Lépcsőkar Törlése");
    }

    private void initVariables() {
        locationField = new JComboBox<>();
        locationField.setEditable(true);
        this.tipusField = new JComboBox<>();
        tipusField.setEditable(true);
        this.lepcsoSzamField = new JComboBox<>();
        lepcsoSzamField.setEditable(true);
        statusField = new JComboBox<>();
        statusField.setEditable(false);
        AutoCompleteDecorator.decorate(lepcsoSzamField);
        AutoCompleteDecorator.decorate(locationField);
        AutoCompleteDecorator.decorate(tipusField);
        AutoCompleteDecorator.decorate(statusField);
    }


    private void initPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel fieldPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        fieldPanel.add(lepcsoSzamField, BorderLayout.NORTH);
        fieldPanel.add(locationField, BorderLayout.CENTER);
        fieldPanel.add(tipusField, BorderLayout.SOUTH);
        fieldPanel.add(statusField);
        fieldPanel.add(deleteButton);
        fieldPanel.add(saveButton);
        this.add(fieldPanel);
        this.add(buttonPanel);
//        buttonPanel.add(deleteButton);
//        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(mainMenuButton);
    }

    private void refreshContent() throws Throwable {
        List<Lepcso> lepcsoList = lepcsoService.getAllLepcso();
        lepcsoList.sort(Comparator.comparing(Lepcso::getLepcsoSzama));
        List<Location> locationList = lepcsoService.getLocationList();
        List<Tipus> tipusList = lepcsoService.getTipusList();
        List<Status> statusList = lepcsoService.getStatusList();
        try {
            locationList.sort(Comparator.comparing(Location::getLocationName, new RuleBasedCollator(StringConstants.HUNGARIAN_COLLATOR_RULES)));
            tipusList.sort(Comparator.comparing(Tipus::getTipusNev, new RuleBasedCollator(StringConstants.HUNGARIAN_COLLATOR_RULES)));
            statusList.sort(Comparator.comparing(Status::getStatusId));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        locationField.setModel(new DefaultComboBoxModel(locationList.toArray()));
        tipusField.setModel(new DefaultComboBoxModel(tipusList.toArray()));
        lepcsoSzamField.setModel(new DefaultComboBoxModel(lepcsoList.toArray()));
        statusField.setModel(new DefaultComboBoxModel(statusList.toArray()));
    }

    private void saveLepcso() throws Throwable {
        lepcso.setLocation(location);
        lepcso.setTipus(tipus);
        lepcso.setStatus(status);
        lepcsoService.saveLepcso(lepcso);
        resetFields();
    }

    private void resetFields() {
        lepcsoSzamField.setSelectedIndex(-1);
        locationField.setSelectedIndex(0);
        tipusField.setSelectedIndex(0);
        statusField.setSelectedIndex(0);
    }
}

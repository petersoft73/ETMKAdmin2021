package com.petersoft.mgl.ui;

import com.github.lgooddatepicker.components.DatePicker;
import com.petersoft.mgl.model.Javitas;
import com.petersoft.mgl.model.Karbantartas;
import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.service.JavitasService;
import com.petersoft.mgl.service.KarbantartasService;
import com.petersoft.mgl.service.LepcsoService;
import com.petersoft.mgl.serviceimpl.JavitasServiceImpl;
import com.petersoft.mgl.serviceimpl.KarbantartasServiceImpl;
import com.petersoft.mgl.serviceimpl.LepcsoServiceImpl;
import net.sf.jasperreports.engine.JRException;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

public class LepcsoTortenetDialog extends JDialog {
    private MainFrame frame;
    private DatePicker datePickerStartDate;
    private DatePicker datePickerEndDate;
    private JPanel jelentesPanel;
    private JRadioButton javitasRadioButton, karbantartasRadioButton;
    private JButton okButton, visszaButton;
    private JComboBox<Lepcso> lepcsoJComboBox;
    private Lepcso lepcso;


    public LepcsoTortenetDialog(MainFrame frame) {
        this.frame = frame;
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(600, 200);
        setLayout(new BorderLayout());
        setVisible(true);
        datePickerStartDate = new DatePicker();
        datePickerStartDate.setDate(LocalDate.now().minusMonths(1));
        datePickerEndDate = new DatePicker();
        datePickerEndDate.setDateToToday();
        setLayout();
        add(jelentesPanel);
        setListeners();
    }

    private void setLayout() {
        lepcsoJComboBox = new JComboBox<>();
        try {
            setLepcsoJComboBox();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        jelentesPanel = new JPanel();
        jelentesPanel.setVisible(true);
        javitasRadioButton = new JRadioButton("javítás");
        karbantartasRadioButton = new JRadioButton("karbantartás");
        okButton = new JButton("Mutasd!");
        visszaButton = new JButton("Vissza a Jelentés menübe");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(javitasRadioButton);
        buttonGroup.add(karbantartasRadioButton);
        javitasRadioButton.doClick();
        jelentesPanel.add(lepcsoJComboBox);
        jelentesPanel.add(javitasRadioButton);
        jelentesPanel.add(karbantartasRadioButton);
        jelentesPanel.add(datePickerStartDate);
        jelentesPanel.add(datePickerEndDate);
        jelentesPanel.add(visszaButton);
        jelentesPanel.add(okButton);
    }

    private void setLepcsoJComboBox() throws Throwable {
        LepcsoService service = new LepcsoServiceImpl();
        List<Lepcso> lepcsoList = service.getAllLepcso();
        lepcsoJComboBox.setModel(new DefaultComboBoxModel(lepcsoList.toArray()));
        AutoCompleteDecorator.decorate(lepcsoJComboBox);
        lepcsoJComboBox.setSelectedIndex(-1);
    }

    private void setListeners() {
        lepcsoJComboBox.addActionListener(e -> {
            JComboBox<Lepcso> cb = (JComboBox<Lepcso>) e.getSource();
            if (cb.getSelectedIndex() != -1) {
                lepcso = (Lepcso) cb.getSelectedItem();
            }
        });

        okButton.addActionListener(e -> {
            if (lepcsoJComboBox.getSelectedItem() != null) {
                if (javitasRadioButton.isSelected()) {
                    JavitasService service = new JavitasServiceImpl();
                    List<Javitas> javitasIntervalList = service.getJavitasInterval(
                            (Lepcso) lepcsoJComboBox.getSelectedItem(),
                            datePickerStartDate.getDate(), datePickerEndDate.getDate()
                    );
                    try {
                        Reports.javitasIntervalReport(
                                javitasIntervalList,
                                lepcso.getLepcsoSzama(),
                                datePickerStartDate.getDate(),
                                datePickerEndDate.getDate());
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    } catch (JRException jrException) {
                        jrException.printStackTrace();
                    }
                } else {
                    KarbantartasService karbantartasService = new KarbantartasServiceImpl();
                    System.out.println(lepcso.getLepcsoSzama());
                    List<Karbantartas> karbantartasIntervalList = karbantartasService.getKarbantartasInterval(
                            lepcso,
                            datePickerStartDate.getDate(),
                            datePickerEndDate.getDate()
                    );
                    try {
                        Reports.karbantartasIntervalReport(karbantartasIntervalList,
                                lepcso.getLepcsoSzama(),
                                datePickerStartDate.getDate(),
                                datePickerEndDate.getDate()

                        );
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    } catch (JRException jrException) {
                        jrException.printStackTrace();
                    }
                }
            }
        });

        visszaButton.addActionListener(e -> {
            dispose();
            frame.showJelentesPanel();
        });
    }

}

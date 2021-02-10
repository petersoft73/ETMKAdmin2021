package com.petersoft.mgl.ui;

import com.github.lgooddatepicker.components.DatePicker;
import com.petersoft.mgl.connection.DBConnector;
import com.petersoft.mgl.dto.JavitasDTO;
import com.petersoft.mgl.dto.KarbantartasDTO;
import com.petersoft.mgl.model.Karbantartas;
import com.petersoft.mgl.service.KarbantartasService;
import com.petersoft.mgl.serviceimpl.KarbantartasServiceImpl;
import net.sf.jasperreports.engine.JRException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NapiJelentesDialog extends JDialog {
    private JPanel jelentesPanel;
    private DatePicker datePicker;
    private JRadioButton javitasRadioButton, karbantartasRadioButton;
    private JButton okButton, visszaButton;
    private MainFrame frame;

    public NapiJelentesDialog(MainFrame frame) {
        this.frame = frame;
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout());
        setVisible(true);
        datePicker = new DatePicker();
        datePicker.setDateToToday();
        setButtons();
        setPanelLayout();
        add(jelentesPanel);
        setListeners();
    }

    private void setButtons() {
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
    }

    private void setPanelLayout() {
        jelentesPanel.add(javitasRadioButton);
        jelentesPanel.add(karbantartasRadioButton);
        jelentesPanel.add(datePicker);
        jelentesPanel.add(visszaButton);
        jelentesPanel.add(okButton);
    }

    private void setListeners() {
        okButton.addActionListener(e -> {
            if (javitasRadioButton.isSelected()) {
                List<JavitasDTO> dailyReportList = DBConnector.getJavitasList().stream()
                        .filter(l -> l.getJavitasKelte().isEqual(datePicker.getDate()))
                        .map(j -> new JavitasDTO(
                                j.getLepcso().getLepcsoSzama(),
                                j.getLeiras(),
                                j.getMuszakSzama(),
                                j.getJavitasKelte()))
                        .collect(Collectors.toList());

                try {
                    Reports.dailyReportJavitas(dailyReportList);
                } catch (FileNotFoundException | JRException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            } else {
                KarbantartasService karbService = new KarbantartasServiceImpl();
                java.util.List<Karbantartas> karbantartasList = karbService
                        .getKarbantartasByDate(datePicker.getDate());
                List<KarbantartasDTO> karbantartasDailyList = karbantartasList.stream()
                        .map(karb -> new KarbantartasDTO(
                                karb.getLepcso().getLepcsoSzama(),
                                karb.getKarbantartasTipus().getKarbTipusId(),
                                karb.getMuszakSzama(),
                                karb.isElvegezve(),
                                karb.getKarbantartasDatum()))
                        .sorted(Comparator.comparing(KarbantartasDTO::getLepcsoSzama)
                                .thenComparing(KarbantartasDTO::getKarbantartasDatum))
                        .collect(Collectors.toList());
                try {
                    Reports.dailyReportKarbantartas(karbantartasDailyList);
                } catch (FileNotFoundException | JRException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });


        visszaButton.addActionListener(e -> {
            dispose();
            frame.showJelentesPanel();
        });
    }
}


package com.petersoft.mgl.ui;

import com.github.lgooddatepicker.components.DatePicker;
import com.petersoft.mgl.dto.JavitasDTO;
import com.petersoft.mgl.model.Javitas;
import com.petersoft.mgl.service.JavitasService;
import com.petersoft.mgl.serviceimpl.JavitasServiceImpl;
import net.sf.jasperreports.engine.JRException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class IdoszakiJelentesDialog extends JDialog {
    private JPanel jelentesPanel;
    private DatePicker datePickerStartDate, datePickerEndDate;
    private JRadioButton javitasRadioButton, karbantartasRadioButton;
    private JButton okButton, visszaButton;
    private MainFrame frame;


    public IdoszakiJelentesDialog(MainFrame frame) {
        this.frame = frame;
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);
        setVisible(true);
        datePickerStartDate = new DatePicker();
        datePickerStartDate.setDate(LocalDate.now().minusMonths(1));
        datePickerEndDate = new DatePicker();
        datePickerEndDate.setDateToToday();
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
        jelentesPanel.add(datePickerStartDate);
        jelentesPanel.add(datePickerEndDate);
        jelentesPanel.add(visszaButton);
        jelentesPanel.add(okButton);
    }

    private void setListeners() {
        visszaButton.addActionListener(e -> {
            dispose();
            frame.showJelentesPanel();
        });

        okButton.addActionListener(e -> {
            if (javitasRadioButton.isSelected()) {
                JavitasService service = new JavitasServiceImpl();
                List<Javitas> javitasIdoszakosList = service.getIdoszakosJavitas(
                        datePickerStartDate.getDate(), datePickerEndDate.getDate());
                List<JavitasDTO> javitasDTOList = javitasIdoszakosList.stream()
                        .map(j -> new JavitasDTO(j.getLepcso().getLepcsoSzama(),
                                j.getLeiras(),
                                j.getMuszakSzama(),
                                j.getJavitasKelte()))
                        .sorted(Comparator.comparing(JavitasDTO::getJavitasKelte)
                                .thenComparing(JavitasDTO::getLepcso_lepcsoSzama))
                        .collect(Collectors.toList());
                try {
                    Reports.idoszakosJavitas(javitasDTOList,
                            datePickerStartDate.getDate(),
                            datePickerEndDate.getDate());
                } catch (FileNotFoundException | JRException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

            } else {

            }
        });
    }
}

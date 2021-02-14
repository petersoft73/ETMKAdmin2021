package com.petersoft.mgl.ui;

import com.github.lgooddatepicker.components.DatePicker;
import com.petersoft.mgl.connection.DBConnector;
import com.petersoft.mgl.dto.JavitasDTO;
import com.petersoft.mgl.dto.KarbantartasDTO;
import com.petersoft.mgl.model.Javitas;
import com.petersoft.mgl.model.Karbantartas;
import com.petersoft.mgl.model.Lepcso;
import com.petersoft.mgl.service.JavitasService;
import com.petersoft.mgl.service.KarbantartasService;
import com.petersoft.mgl.serviceimpl.JavitasServiceImpl;
import com.petersoft.mgl.serviceimpl.KarbantartasServiceImpl;
import net.sf.jasperreports.engine.JRException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

            }
            if (karbantartasRadioButton.isSelected()) {
                KarbantartasService karbantartasService = new KarbantartasServiceImpl();
                List<Karbantartas> karbantartasList = karbantartasService.getIdoszakiKarbantartasList(
                        datePickerStartDate.getDate(), datePickerEndDate.getDate()
                );
                List<KarbantartasDTO> dtoList = karbantartasDTOList(karbantartasList);
                try {
                    Reports.idoszakosKarbantartas(dtoList,
                            datePickerStartDate.getDate(),
                            datePickerEndDate.getDate());
                } catch (FileNotFoundException | JRException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
    }

    private List<KarbantartasDTO> karbantartasDTOList(List<Karbantartas> karbantartasList) {
        List<Karbantartas> ujList = karbantartasList.stream()
                .sorted(Comparator.comparing(Karbantartas::getKarbantartasDatum)
                        .thenComparing(k -> k.getLepcso().getLepcsoSzama())
                        .thenComparing(k -> k.getKarbTipusId()))
                .collect(Collectors.toList());

        Map<LocalDate, Map<Lepcso, List<Karbantartas>>> localDateListMap = ujList.stream()
                .collect(Collectors.groupingBy(Karbantartas::getKarbantartasDatum
                        , Collectors.groupingBy(Karbantartas::getLepcso, Collectors.toList())));
        List<KarbantartasDTO> karbantartasDTOList = new ArrayList<>();
        LocalDate date = null;
        Lepcso lepcso = null;
        Karbantartas karbantartas = null;
        KarbantartasDTO dto = null;
        for (Map.Entry<LocalDate, Map<Lepcso, List<Karbantartas>>> dateMapEntry : localDateListMap.entrySet()) {
            date = dateMapEntry.getKey();
            for (Map.Entry<Lepcso, List<Karbantartas>> lepcsoListEntry : dateMapEntry.getValue().entrySet()) {
                StringBuilder sb = new StringBuilder();
                lepcso = lepcsoListEntry.getKey();
                for (Karbantartas k : lepcsoListEntry.getValue()) {
                    karbantartas = k;
                    sb.append("4.1.").append(k.getKarbTipusId()).append(" ");
                }
                dto = new KarbantartasDTO(lepcso.getLepcsoSzama(), sb.toString(), date, karbantartas.getMuszakSzama());
                karbantartasDTOList.add(dto);
            }
        }
        karbantartasDTOList = karbantartasDTOList.stream().sorted(Comparator.comparing(KarbantartasDTO::getKarbantartasDatum)
        .thenComparing(KarbantartasDTO::getLepcsoSzama)).collect(Collectors.toList());
//        karbantartasDTOList.forEach(i -> System.out.println(i.getKarbantartasDatum() + " " + i.getLepcsoSzama() +
//                " " + i.getKarbTipusLeiras() + " " + i.getMuszakSzama()));



        return karbantartasDTOList;
    }
}

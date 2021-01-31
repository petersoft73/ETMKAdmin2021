package com.petersoft.mgl.ui;

import com.github.lgooddatepicker.components.DatePicker;
import com.petersoft.mgl.dto.KarbantartasDTO;
import com.petersoft.mgl.model.Karbantartas;
import com.petersoft.mgl.service.KarbantartasService;
import com.petersoft.mgl.serviceimpl.KarbantartasServiceImpl;
import net.sf.jasperreports.engine.JRException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class HianyzoKarbListReportDialog extends JDialog {
    private MainFrame frame;
    private DatePicker datePickerStartDate;
    private DatePicker datePickerEndDate;
    private JButton okButton;
    private JButton visszaButton;
    private JPanel jelentesPanel;


    public HianyzoKarbListReportDialog(MainFrame frame) {
        this.frame = frame;
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(600, 200);
        setLayout(new BorderLayout());
        setVisible(true);
        setJelentesPanel();
        add(jelentesPanel);
        setListeners();
    }

    private void setJelentesPanel() {
        jelentesPanel = new JPanel();
        jelentesPanel.setVisible(true);
        okButton = new JButton("Mutasd!");
        visszaButton = new JButton("Vissza a Jelentés menübe");
        jelentesPanel.add(datePickerStartDate = new DatePicker());
        jelentesPanel.add(datePickerEndDate = new DatePicker());
        jelentesPanel.add(okButton);
        jelentesPanel.add(visszaButton);
        datePickerEndDate.setDateToToday();
        LocalDate intervalStartDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        datePickerStartDate.setDate(intervalStartDate);
    }

    private void setListeners() {
        okButton.addActionListener(e -> {
            KarbantartasService karbService = new KarbantartasServiceImpl();
            List<Karbantartas> karbList = karbService.getReportHianyzoKarbList
                    (datePickerStartDate.getDate().atTime(0, 0), datePickerEndDate.getDate().atTime(0, 0));
            testStream(karbList);

            try {
                Reports.hianyzoKarbListReport
                        (testStream(karbList), datePickerStartDate.getDate(), datePickerEndDate.getDate());
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (JRException jrException) {
                jrException.printStackTrace();
            }
        });

        visszaButton.addActionListener(e -> {
            dispose();
            frame.showJelentesPanel();
        });
    }

    private List<KarbantartasDTO> testStream(List<Karbantartas> karbantartasList) {
        Map<Integer, List<String>> map;
        map = karbantartasList.stream()
                .collect(Collectors.groupingBy(
                        k -> (k.getLepcso().getLepcsoSzama()),
                        Collectors.mapping(
                                p -> p.getKarbantartasTipus().getKarbTipusKod(),
                                Collectors.toList()
                        )
                ));
        Map<Integer, List<String>> result = new TreeMap<>(map);
        List<KarbantartasDTO> finalList = new ArrayList<>();

        for (Integer key : result.keySet()) {
            String karbtipusLeiras = "";
            for (String s : result.get(key)) {
                karbtipusLeiras += s + " ";
            }
            KarbantartasDTO karbDTO = new KarbantartasDTO(key, karbtipusLeiras);
            finalList.add(karbDTO);
        }

        return finalList;
    }
}

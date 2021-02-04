package com.petersoft.mgl.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class JelentesPanel extends JPanel {
    private JButton napiJelentesButton;
    private JButton lepcsoTortenetButton;
    private JButton hianyzoKarbantartasButton;
    private JButton mainMenuButton, idoszakiJelentesButton;
    private MainFrame frame;

    public JelentesPanel(MainFrame frame) {

        this.frame = frame;
        //  setSize(300,200);
        Border innerBorder = BorderFactory.createTitledBorder("Jelentések");
        Border outerBorder = BorderFactory.createEmptyBorder(100, 100, 100, 100);
        this.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtons();
        setListeners();
        setVisible(true);

    }

    private void setButtons() {
        frame.setLocationRelativeTo(null);
        napiJelentesButton = new JButton("Napi Jelentés...");
        lepcsoTortenetButton = new JButton("Lépcsőkar történet...");
        hianyzoKarbantartasButton = new JButton("Hiányzó Karbantartások...");
        mainMenuButton = new JButton("Vissza a Főmenübe");
        idoszakiJelentesButton = new JButton("Időszaki jelentés");
        add(napiJelentesButton);
        add(idoszakiJelentesButton);
        add(lepcsoTortenetButton);
        add(hianyzoKarbantartasButton);
        add(mainMenuButton);
    }

    private void setListeners() {
        mainMenuButton.addActionListener(e -> {
            setVisible(false);
            frame.showFramePanel();
        });

        napiJelentesButton.addActionListener(e -> {
            new NapiJelentesDialog(frame);
        });

        idoszakiJelentesButton.addActionListener(e -> {
            new IdoszakiJelentesDialog(frame);
        });

        lepcsoTortenetButton.addActionListener(e -> {
            new LepcsoTortenetDialog(frame);
        });

        hianyzoKarbantartasButton.addActionListener(e -> {
            new HianyzoKarbListReportDialog(frame);
        });
    }
}


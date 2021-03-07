package com.petersoft.mgl.app;

import com.petersoft.mgl.ui.MainFrame;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            SwingUtilities.invokeLater(MainFrame::new);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Hiba",
                    e.getLocalizedMessage(),
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }
}

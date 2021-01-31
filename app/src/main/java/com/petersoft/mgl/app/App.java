package com.petersoft.mgl.app;

import com.petersoft.mgl.ui.MainFrame;

import javax.swing.*;
import java.util.TimeZone;

public class App {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            System.out.println(TimeZone.getDefault());
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

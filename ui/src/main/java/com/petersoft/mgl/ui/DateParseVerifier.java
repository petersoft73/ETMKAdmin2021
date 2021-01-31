package com.petersoft.mgl.ui;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeParseException;

class DateParseVerifier extends InputVerifier {


    @Override
    public boolean verify(JComponent input) {
//        try {
//            JFormattedTextField jtf = (JFormattedTextField) input;
//            String dateInput = jtf.getText();
//            ErVedInputPanel.parseDate(dateInput);
//            return true;
//        } catch (DateTimeParseException e) {
//            Toolkit.getDefaultToolkit().beep();
//            JOptionPane.showMessageDialog(null, "Hibás dátum\nKérlek ellenőrizd!\nÉÉÉÉ-HH-NN",
//                    "Hiba", JOptionPane.INFORMATION_MESSAGE);
//
//        }
        return false;
    }


}

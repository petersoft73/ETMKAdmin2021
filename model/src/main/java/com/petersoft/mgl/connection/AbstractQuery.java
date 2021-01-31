package com.petersoft.mgl.connection;

import javax.swing.*;

public class AbstractQuery {

    public static void open() {
        try {
            EntityManagerHandler.INSTANCE.open();
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(
                    null,
                    e.getStackTrace(),
                    "Hiba",
                    JOptionPane.INFORMATION_MESSAGE);
            throw e;
        }

    }

    public static void shuttingdown() {
        try {
            EntityManagerHandler.INSTANCE.shutdown();
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(
                    null,
                    e.getStackTrace(),
                    "Hiba",
                    JOptionPane.INFORMATION_MESSAGE);
            throw e;
        }
    }
}
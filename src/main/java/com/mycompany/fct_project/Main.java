package com.mycompany.fct_project;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ConfigDialogLogin login = new ConfigDialogLogin(null, true);
                login.setVisible(true);
            }
        });
    }
}
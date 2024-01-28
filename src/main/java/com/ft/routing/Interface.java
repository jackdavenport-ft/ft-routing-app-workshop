/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
*
* @author Jack Davenport
*
****************************************************************/
package com.ft.routing;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class Interface extends JFrame {
    
    protected Interface() {
        super("FutureTech Routing Workshop");

        JLabel text = new JLabel("Hello world!");
        text.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));
        
        add(text, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo((Component)null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLookAndFeel();
        setVisible(true);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Unable to set look and feel");
            e.printStackTrace();
        }
    }

}

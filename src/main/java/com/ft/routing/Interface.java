/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Interface extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger(Interface.class);
    
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

        LOGGER.info("window open");
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOGGER.warn("Unable to set look and feel", e);
        }
    }

}

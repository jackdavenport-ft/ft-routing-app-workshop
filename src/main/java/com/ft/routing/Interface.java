/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ft.routing.ui.MessagePanel;

public class Interface extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger(Interface.class);

    private final MessagePanel messagePanel;
    
    protected Interface() {
        super("FutureTech Routing Workshop");
        getContentPane().setPreferredSize(new Dimension(700, 275));

        this.messagePanel = new MessagePanel();
        getContentPane().add(this.messagePanel, BorderLayout.CENTER);
        
        // getContentPane().setBackground(Color.red);

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
            LOGGER.warn("Unable to set look and feel", e);
        }
    }

}

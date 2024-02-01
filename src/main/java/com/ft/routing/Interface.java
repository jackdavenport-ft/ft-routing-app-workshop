/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ft.routing.ui.InfoPanel;
import com.ft.routing.ui.MessagePanel;

public class Interface extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger(Interface.class);

    private final MessagePanel messagePanel;

    private boolean closeRequested = false;
    
    protected Interface() {
        super("FutureTech Routing Workshop");
        getContentPane().setPreferredSize(new Dimension(600, 275));
        setMinimumSize(getContentPane().getPreferredSize());

        this.messagePanel = new MessagePanel();
        getContentPane().add(this.messagePanel, BorderLayout.CENTER);

        // create sidebar of window
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.LINE_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 10));
        sidebar.setBackground(Color.yellow);
        getContentPane().add(sidebar, BorderLayout.EAST);

        InfoPanel infoPanel = new InfoPanel();
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(infoPanel);
        
        // getContentPane().setBackground(Color.red);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Interface.this.closeRequested = true;
            }
        });

        pack();
        setLocationRelativeTo((Component)null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

    public boolean isCloseRequested() {
        return this.closeRequested;
    }

}

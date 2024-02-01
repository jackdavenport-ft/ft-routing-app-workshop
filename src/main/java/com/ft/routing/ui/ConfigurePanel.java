/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.ui;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class ConfigurePanel extends JPanel {
    
    private final JButton incomingButton;
    private final JButton outgoingButton;
    private final JButton forwardedButton;

    public ConfigurePanel() {
        setLayout(new GridLayout(4, 1));
        setBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                "Configure"
            )
        );

        // create button components
        JButton editRoutingButton = new JButton("Edit Routing");
        this.incomingButton = new JButton();
        this.outgoingButton = new JButton();
        this.forwardedButton = new JButton();

        setInboxCount(0);
        setOutboxCount(0);
        setForwardedCount(0);
        
        // left align everything
        editRoutingButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.incomingButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.outgoingButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.forwardedButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        // construct ui
        add(editRoutingButton);
        add(this.incomingButton);
        add(this.outgoingButton);
        add(this.forwardedButton);
    }

    public void setInboxCount(int count) {
        this.incomingButton.setText(String.format("Inbox (%d)", count));
    }

    public void setOutboxCount(int count) {
        this.outgoingButton.setText(String.format("Outbox (%d)", count));
    }

    public void setForwardedCount(int count) {
        this.forwardedButton.setText(String.format("Forwarded (%d)", count));
    }

}

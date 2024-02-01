/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.ui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.ft.routing.Interface;

public class ConfigurePanel extends JPanel implements ActionListener {
    
    private final JButton incomingButton;
    private final JButton outgoingButton;
    private final JButton forwardedButton;

    private final Interface parent;

    public ConfigurePanel(Interface parent) {
        this.parent = parent;

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

        // register actions
        editRoutingButton.setActionCommand("edit_routes");
        editRoutingButton.addActionListener(this);
        
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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "edit_routes":
                new RoutingDialog(this.parent);
                break;
            default:
                break;
        }
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

/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.ft.routing.Interface;
import com.ft.routing.messaging.Mailbox;
import com.ft.routing.messaging.Mailbox.MessageDirection;

public class MailboxDialog extends JDialog {
    
    protected MailboxDialog(Interface parent, Mailbox mailbox, MessageDirection direction) {
        setDialogTitle(direction);
        setMinimumSize(new Dimension(500, 400));
        setPreferredSize(getMinimumSize());
        setLayout(new BorderLayout());

        // configure and show dialog
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void setDialogTitle(MessageDirection direction) {
        String dirString = direction.name()
            .substring(0,1).toUpperCase() + 
            direction.name()
            .substring(1).toLowerCase();
        setTitle(dirString + " messages");
    }

}

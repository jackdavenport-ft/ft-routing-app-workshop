/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.ui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.ft.routing.App;
import com.ft.routing.server.Util;

public class InfoPanel extends JPanel {
    
    public InfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                "Information"
            )
        );

        // create label components
        JLabel usernameHeader = new JLabel("Username");
        JLabel usernameLabel = new JLabel(App.getUsername());
        JLabel ipAddressHeader = new JLabel("IP Address");
        JLabel ipAddressLabel = new JLabel(Util.getLocalIpAddress());

        // make headers bold
        Font font = usernameHeader.getFont();
        font = font.deriveFont(font.getStyle() | Font.BOLD);
        usernameHeader.setFont(font);
        ipAddressHeader.setFont(font);

        // align everything left
        usernameHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ipAddressHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        ipAddressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // construct ui
        add(usernameHeader);
        add(usernameLabel);
        add(ipAddressHeader);
        add(ipAddressLabel);
    }

}

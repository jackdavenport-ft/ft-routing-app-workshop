/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.ft.routing.Interface;
import com.ft.routing.client.RouteTable;

public class RoutingDialog extends JDialog implements ActionListener {

    private final JTable table;
    private final JButton addButton;
    private final JButton removeButton;

    public RoutingDialog(Interface parent) {
        setMinimumSize(new Dimension(500, 400));
        setPreferredSize(getMinimumSize());
        setLayout(new BorderLayout());

        this.table = new JTable(new RoutingTableModel());
        JScrollPane scrollPane = new JScrollPane(this.table);
        add(scrollPane, BorderLayout.CENTER);

        // create action toolbar above table
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new FlowLayout());

        this.addButton = new JButton("Add");
        this.addButton.setActionCommand("add_item");
        this.addButton.addActionListener(this);

        this.removeButton = new JButton("Remove");
        this.removeButton.setEnabled(false);
        this.removeButton.setActionCommand("remove_item");
        this.removeButton.addActionListener(this);
        
        actionsPanel.add(this.addButton);
        actionsPanel.add(this.removeButton);
        add(actionsPanel, BorderLayout.NORTH);

        // add event handlers to table
        this.table.getSelectionModel().addListSelectionListener((e) -> {
            if(!e.getValueIsAdjusting()) {
                RoutingDialog.this.removeButton.setEnabled(table.getSelectedRow() > -1);
            }
        });

        // configure and show dialog
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "remove_item":
                if(this.table.getSelectedRow() > -1) {
                    Entry<String,String> entry = RouteTable.getRouteEntry(this.table.getSelectedRow());
                    RouteTable.removeAddress(entry.getKey());
                    this.table.getSelectionModel().clearSelection();
                }
                break;
            default:
                break;
        }
    }

}

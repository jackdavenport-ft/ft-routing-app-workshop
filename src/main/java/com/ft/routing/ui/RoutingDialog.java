/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.ft.routing.Interface;
import com.ft.routing.client.RouteTable;
import com.ft.routing.server.Util;

public class RoutingDialog extends JDialog implements ActionListener {

    private final JTable table;
    private final JButton addButton;
    private final JButton removeButton;

    private final RoutingTableModel model;

    public RoutingDialog(Interface parent) {
        setTitle("Edit route table");
        setMinimumSize(new Dimension(500, 400));
        setPreferredSize(getMinimumSize());
        setLayout(new BorderLayout());

        this.model = new RoutingTableModel();
        this.table = new JTable(this.model);
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
            case "add_item":
                new AddDialog(this);
                break;
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

    public class AddDialog extends JDialog implements ActionListener {

        private final JTextField usernameField;
        private final JTextField addressField;
        private final JButton addButton;
        private final JLabel errorLabel;

        private final RoutingTableModel model;

        private AddDialog(RoutingDialog parent) {
            this.model = parent.model;
            setTitle("Add new route");
            setMinimumSize(new Dimension(300, 200));
            setPreferredSize(getMinimumSize());

            // create inputs and labels
            JPanel form = new JPanel();
            form.setLayout(new GridLayout(3, 2));
            form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel usernameLabel = new JLabel("Username");
            JLabel addressLabel = new JLabel("IP Address");
            this.usernameField = new JTextField();
            this.addressField = new JTextField();
            this.errorLabel = new JLabel();
            this.addButton = new JButton("Add");

            this.addButton.setEnabled(false);
            this.addButton.addActionListener(this);
            this.errorLabel.setForeground(Color.red);

            form.add(usernameLabel);
            form.add(this.usernameField);
            form.add(addressLabel);
            form.add(this.addressField);
            form.add(this.errorLabel);
            form.add(this.addButton);
            setContentPane(form);

            // add key listeners to inputs to control button state
            KeyListener keyListener = new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    boolean usernameValue = !AddDialog.this.usernameField.getText().trim().isEmpty();
                    boolean addressValue  = !AddDialog.this.addressField.getText().trim().isEmpty();
                    AddDialog.this.addButton.setEnabled(usernameValue && addressValue);
                }
            };
            this.usernameField.addKeyListener(keyListener);
            this.addressField.addKeyListener(keyListener);

            // add listener to give control back to parent
            parent.setAlwaysOnTop(false);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    parent.table.repaint();
                    parent.setAlwaysOnTop(true);
                }
            });

            // configure and show dialog
            pack();
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setAlwaysOnTop(true);
            setModal(true);
            setModalityType(ModalityType.APPLICATION_MODAL);
            setLocationRelativeTo(parent);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = this.usernameField.getText().trim();
            String address = this.addressField.getText().trim();
            if(!Util.isValidIpFormat(address)) {
                this.errorLabel.setText("Invalid IP address!");
                return;
            }
            RouteTable.setAddress(username, address);
            this.model.refreshTable();
            this.dispose();
        }

    }

}

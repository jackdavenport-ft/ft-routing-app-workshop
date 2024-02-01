/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.ft.routing.Interface;

public class RoutingDialog extends JDialog {

    public RoutingDialog(Interface parent) {
        setMinimumSize(new Dimension(500, 400));
        setPreferredSize(getMinimumSize());
        setLayout(new BorderLayout());

        DefaultListModel<String> testModel = new DefaultListModel<>();
        testModel.addElement("hello");
        testModel.addElement("world");
        testModel.addElement("this");
        testModel.addElement("is");
        testModel.addElement("routing");
        testModel.addElement("baby");

        JList<String> list = new JList<>(testModel);
        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

}

/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ft.routing.App;
import com.ft.routing.Interface;
import com.ft.routing.client.Client;
import com.ft.routing.client.SendResult;
import com.ft.routing.messaging.Message;

public class MessagePanel extends JPanel implements KeyListener, ActionListener {

    private final JTextField recipientField;
    private final JTextArea messageField;
    private final JButton sendButton;

    private final Interface parent;

    public MessagePanel(Interface parent) {
        this.parent = parent;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // init all components
        JLabel recipientLabel = new JLabel("Recipient");
        JLabel messageLabel = new JLabel("Message");
        this.recipientField = new JTextField();
        this.messageField = new JTextArea();
        this.sendButton = new JButton("Send");

        // left align everything
        recipientLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.recipientField.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.messageField.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.sendButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.recipientField.setPreferredSize(new Dimension(400, this.recipientField.getPreferredSize().height));
        this.recipientField.setMaximumSize(this.recipientField.getPreferredSize());
        this.recipientField.addKeyListener(this);
        this.messageField.setMaximumSize(new Dimension(400, 150));
        this.messageField.addKeyListener(this);
        this.sendButton.setEnabled(false);

        // add action listener to button
        this.sendButton.addActionListener(this);

        // construct ui
        add(recipientLabel);
        add(this.recipientField);
        add(messageLabel);
        add(this.messageField);
        add(this.sendButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Message message = new Message(this.messageField.getText(), App.getUsername(), this.recipientField.getText());
        SendResult result = Client.sendMessage(message);
        if(result.isSuccess()) {
            this.messageField.setText("");
            this.recipientField.setText("");
            App.getMailbox().delegateMessage(message);
            this.parent.onMailboxUpdate(App.getMailbox());
        } else {
            JOptionPane.showMessageDialog(null, result.getErrorMessage(), "Couldn't send message!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        boolean recipientValue = this.recipientField.getText().length() > 0;
        boolean messageValue = this.messageField.getText().length() > 0;
        this.sendButton.setEnabled(recipientValue && messageValue);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}
    
}

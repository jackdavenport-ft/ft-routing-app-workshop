package com.ft.routing.ui;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.ft.routing.messaging.Mailbox;
import com.ft.routing.messaging.Mailbox.MessageDirection;
import com.ft.routing.messaging.Message;

public class MailboxTableModel implements TableModel {

    private static final String[] HEADER_LABELS = { "Content", "Sender", "Recipient" };

    private final Mailbox mailbox;
    private final MessageDirection type;
    private final Set<Message> messages;
    private final List<Message> messageList;

    private final Set<TableModelListener> listeners = new HashSet<>();

    protected MailboxTableModel(Mailbox mailbox, MessageDirection type) {
        this.mailbox = mailbox;
        this.type = type;
        this.messages = mailbox.getMessages(type);
        this.messageList = this.messages.stream().collect(Collectors.toList());
    }

    @Override
    public int getRowCount() {
        return this.messages.size();
    }

    @Override
    public int getColumnCount() {
        return HEADER_LABELS.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return HEADER_LABELS[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Message message = this.messageList.get(rowIndex);
        return columnIndex == 0 ? message.getContent() : columnIndex == 1 ? message.getSender() : message.getTarget();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

    @Override
    public void addTableModelListener(TableModelListener l) {
        this.listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        this.listeners.remove(l);
    }
    


}

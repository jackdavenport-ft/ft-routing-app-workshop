/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.messaging;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.ft.routing.App;
import com.ft.routing.ui.ConfigurePanel;

public class Mailbox {
    
    private final Set<Message> inbox     = new HashSet<>();
    private final Set<Message> outbox    = new HashSet<>();
    private final Set<Message> forwarded = new HashSet<>();

    private final Set<MailboxEventListener> eventListeners = new HashSet<>();

    public MessageDirection delegateMessage(Message message) {
        MessageDirection type = MessageDirection.FORWARDED;
        if(message.getTarget().equals(App.getUsername())) {
            // target is the user, message is coming in
            this.inbox.add(message);
            type = MessageDirection.INBOX;
        } else if(message.getSender().equals(App.getUsername())) {
            // sender is the user, message is going out
            this.outbox.add(message);
            type = MessageDirection.OUTBOX;
        } else {
            // neither matches the user, we must be forwarding this one
            this.forwarded.add(message);
        }
        this.notifyListeners(message, type);
        return type;
    }

    public Set<Message> getMessages(MessageDirection direction) {
        switch(direction) {
            case INBOX: return this.inbox;
            case OUTBOX: return this.outbox;
            case FORWARDED: return this.forwarded;
            default: return Collections.emptySet();
        }
    }

    public void updateUi(ConfigurePanel panel) {
        panel.setInboxCount(inbox.size());
        panel.setOutboxCount(outbox.size());
        panel.setForwardedCount(forwarded.size());
    }

    public void addEventListener(MailboxEventListener l) {
        this.eventListeners.add(l);
    }

    public void removeEventListener(MailboxEventListener l) {
        this.eventListeners.remove(l);
    }

    private void notifyListeners(Message message, MessageDirection type) {
        for(MailboxEventListener l : this.eventListeners) {
            l.messageAdded(message, type);
        }
    }
    
    public enum MessageDirection {
        INBOX,
        OUTBOX,
        FORWARDED
    }

    public interface MailboxEventListener {
        void messageAdded(Message message, MessageDirection type);
    }

}

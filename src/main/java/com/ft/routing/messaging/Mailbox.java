/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.messaging;

import java.util.HashSet;
import java.util.Set;

import com.ft.routing.App;
import com.ft.routing.ui.ConfigurePanel;

public class Mailbox {
    
    private final Set<Message> inbox     = new HashSet<>();
    private final Set<Message> outbox    = new HashSet<>();
    private final Set<Message> forwarded = new HashSet<>();

    public MessageDirection delegateMessage(Message message) {
        if(message.getTarget().equals(App.getUsername())) {
            // target is the user, message is coming in
            this.inbox.add(message);
            return MessageDirection.INBOX;
        } else if(message.getSender().equals(App.getUsername())) {
            // sender is the user, message is going out
            this.outbox.add(message);
            return MessageDirection.OUTBOX;
        } else {
            // neither matches the user, we must be forwarding this one
            this.forwarded.add(message);
            return MessageDirection.FORWARD;
        }
    }

    public void updateUi(ConfigurePanel panel) {
        panel.setInboxCount(inbox.size());
        panel.setOutboxCount(outbox.size());
        panel.setForwardedCount(forwarded.size());
    }

    public enum MessageDirection {
        INBOX,
        OUTBOX,
        FORWARD
    }

}

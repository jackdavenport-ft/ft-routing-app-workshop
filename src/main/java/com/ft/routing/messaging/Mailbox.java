package com.ft.routing.messaging;

import java.util.ArrayList;
import java.util.List;

import com.ft.routing.App;
import com.ft.routing.ui.ConfigurePanel;

public class Mailbox {
    
    private final List<Message> inbox     = new ArrayList<>();
    private final List<Message> outbox    = new ArrayList<>();
    private final List<Message> forwarded = new ArrayList<>();

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

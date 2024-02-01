/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.messaging;

public class Message {
    
    private final String content;
    private final String sender;
    private final String target;

    public Message(String content, String sender, String target) {
        this.content = content;
        this.sender  = sender;
        this.target  = sender;
    }

    public String getContent() {
        return this.content;
    }

    public String getSender() {
        return this.sender;
    }

    public String getTarget() {
        return this.target;
    }

}

/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.client;

import java.io.DataOutputStream;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ft.routing.App;
import com.ft.routing.messaging.Message;
import com.ft.routing.server.Server;

public class Client {
    
    private static final Logger LOGGER = LogManager.getLogger(Client.class);

    public static boolean sendMessage(Message message) {
        if(RouteTable.isEmpty()) {
            LOGGER.warn("Cannot send message, there are no routes defined");
            return false;
        }
        if(message.getTarget().equals(App.getUsername())) {
            LOGGER.warn("Refusing to send message, cannot send message to yourself");
            return false;
        }

        // here's the magic of how this works
        // if we have an entry for the recipient we send it directly to them
        // but if we don't, we'll just forward it to a random person in our
        // table, and eventually it should get to the right person
        String targetUsername = message.getTarget();
        String targetAddress = RouteTable.getAddress(targetUsername);
        if(!RouteTable.hasAddressFor(targetUsername)) {
            targetAddress = RouteTable.getRandomAddress();
        }

        try {
            // open connection to server and write message data
            Socket socket = new Socket(targetAddress, Server.PORT);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(message.getSender());
            dos.writeUTF(message.getTarget());
            dos.writeUTF(message.getContent());

            // clean up resources
            dos.close();
            socket.close();
            return true;
        } catch (Exception e) {
            LOGGER.error("Error while sending message!", e);
            return false;
        }
    }

}

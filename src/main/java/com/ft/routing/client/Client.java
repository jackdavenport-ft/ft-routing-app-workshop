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
        if(message.getTarget().equals(App.getUsername())) {
            LOGGER.warn("Refusing to send message, cannot send message to yourself");
            return false;
        }

        String address = "localhost"; // TODO: get IP from routing table

        try {
            // open connection to server and write message data
            Socket socket = new Socket(address, Server.PORT);
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

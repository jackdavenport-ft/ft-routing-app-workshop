/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.client;

import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ft.routing.App;
import com.ft.routing.messaging.Message;
import com.ft.routing.server.Server;

public class Client {

    public static final Logger LOGGER = LogManager.getLogger(Client.class);
    private static final int TIMEOUT_MS = 3000;

    public static SendResult sendMessage(Message message) {
        if(RouteTable.isEmpty()) {
            return new SendResult(false, "Cannot send message, there are no routes defined");
        }
        if(message.getTarget().equals(App.getUsername())) {
            return new SendResult(false, "You cannot send messages to yourself!");
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
            // open connection to server
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(targetAddress, Server.PORT), TIMEOUT_MS);
            socket.setSoTimeout(TIMEOUT_MS);

            // write message data
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(message.getSender());
            dos.writeUTF(message.getTarget());
            dos.writeUTF(message.getContent());

            // clean up resources
            dos.close();
            socket.close();
            return new SendResult(true, null);
        } catch (Exception e) {
            LOGGER.error("Error while sending message!", e);
            if(e instanceof SocketTimeoutException) return new SendResult(false, "Connection timed out, is the IP address correct?");
            else if(e.getMessage().contains("Connection refused")) return new SendResult(false, "Connection was refused, is the IP address correct?");
            else return new SendResult(false, e.getMessage());
        }
    }

}

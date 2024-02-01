/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ft.routing.Interface;
import com.ft.routing.messaging.Mailbox;
import com.ft.routing.messaging.Message;
import com.ft.routing.messaging.Mailbox.MessageDirection;

public class Server implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    public static final int PORT = 5678;
    private static final int TIMEOUT_MS = 3000;

    private ServerSocket serverSocket;
    private Thread serverThread;
    private boolean running = true;

    private final Mailbox mailbox;
    private final Interface ui;

    public Server(Mailbox mailbox, Interface ui) {
        this.mailbox = mailbox;
        this.ui = ui;
        this.serverThread = new Thread(this, "Routing Server");
        this.serverThread.start();
    }

    @Override
    public void run() {
        // create the server socket
        try {
            this.serverSocket = new ServerSocket(PORT);
            LOGGER.info("Server open on localhost:{}", PORT);
        } catch (IOException e) {
            LOGGER.fatal("Failed to start server", e);
        }

        // accept new connections
        while(this.running) {
            try {
                Socket socket = this.serverSocket.accept();
                socket.setSoTimeout(TIMEOUT_MS);
                this.readMessage(socket);
                socket.close();
            } catch (IOException e) {
                if(!this.running) break;
                LOGGER.error("Error while handling connection", e);
            }
        }
    }

    private void readMessage(Socket socket) {
        try {
            // read message data from socket
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String sender  = dis.readUTF();
            String target  = dis.readUTF();
            String content = dis.readUTF();
            Message message = new Message(content, sender, target);
            MessageDirection direction = this.mailbox.delegateMessage(message);

            // handle the message depending on the direction
            switch (direction) {
                case OUTBOX:
                    LOGGER.info("message sent to user");
                case FORWARD:
                    LOGGER.info("message forwarded to user");
                case INBOX:
                default:
                    break;
            }

            // update the UI
            this.ui.onMailboxUpdate(this.mailbox);
        } catch (IOException e) {
            LOGGER.warn("Error reading message from socket", e);
        }
    }
    
    public void closeServer() {
        try {
            LOGGER.info("Stopping server...");
            this.running = false;
            this.serverSocket.close();
            this.serverThread.join(TIMEOUT_MS);
            LOGGER.info("Server thread terminated");
        } catch (IOException e) {
            LOGGER.error("Error while closing server", e);
        } catch (InterruptedException e) {
            LOGGER.warn("Error while stopping server thread", e);
        }
    }

}

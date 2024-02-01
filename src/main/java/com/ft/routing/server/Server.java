/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    public static final int PORT = 5678;
    private static final int TIMEOUT_MS = 3000;

    private ServerSocket serverSocket;
    private Thread serverThread;
    private boolean running = true;

    public Server() {
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
            } catch (IOException e) {
                if(!this.running) break;
                LOGGER.error("Error while handling connection", e);
            }
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

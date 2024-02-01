/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ft.routing.messaging.Mailbox;
import com.ft.routing.server.Server;
import com.ft.routing.server.Util;

public class App {

    private static final Logger LOGGER = LogManager.getLogger(App.class);

    private static String username;
    private static Interface ui;
    private static Server server;
    private static Mailbox mailbox;

    public static void main(String[] args) {
        username = Util.generateUsername();
        ui = new Interface();
        mailbox = new Mailbox();
        server = new Server(mailbox, ui);

        LOGGER.info("Username: {}", username);

        while(!ui.isCloseRequested()) {
            try {
                Thread.sleep(20L);
            } catch (InterruptedException e) {
                // don't care
            }
        }

        close();
    }

    private static void close() {
        server.closeServer();
        ui.dispose();
        System.exit(0);
    }

    public static String getUsername() {
        return username;
    }
}

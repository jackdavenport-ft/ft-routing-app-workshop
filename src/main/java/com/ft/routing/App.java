/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ft.routing.server.Util;

public class App {

    private static final Logger LOGGER = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        String username = Util.generateUsername();
        Interface ui = new Interface();

        LOGGER.info(username);
    }
}

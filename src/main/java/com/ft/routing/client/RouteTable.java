/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.client;

import java.util.HashMap;
import java.util.Map;

import com.ft.routing.server.Util;

public class RouteTable {
    
    private static final Map<String, String> routeTable = new HashMap<>();

    public static void setAddress(String username, String address) {
        routeTable.put(username, address);
    }

    public static String getAddress(String username) {
        return routeTable.get(username);
    }

    public static boolean hasAddressFor(String username) {
        return routeTable.containsKey(username);
    }

    public static String getRandomAddress() {
        return Util.randomItem(routeTable.values());
    }

    public static boolean isEmpty() {
        return routeTable.isEmpty();
    }

}

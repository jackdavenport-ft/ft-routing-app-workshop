/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ft.routing.server.Util;

public class RouteTable {
    
    private static final Map<String, String> routeTable = new HashMap<>();
    private static final List<Entry<String, String>> routeList = new ArrayList<>();

    public static void setAddress(String username, String address) {
        routeTable.put(username, address);
        rebuildRouteList();
    }

    public static void removeAddress(String username) {
        routeTable.remove(username);
        rebuildRouteList();
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

    public static int getRouteCount() {
        return routeList.size();
    }

    public static Entry<String, String> getRouteEntry(int index) {
        return routeList.get(index);
    }

    private static void rebuildRouteList() {
        routeList.clear();
        routeList.addAll(routeTable.entrySet());
    }

}

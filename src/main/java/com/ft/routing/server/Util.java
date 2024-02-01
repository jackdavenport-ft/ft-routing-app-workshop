/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {

    private static final Logger LOGGER   = LogManager.getLogger(Util.class);
    private static final String IP_REGEX = "[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+";

    public static final Random RANDOM = new Random();

    // thanks chatgpt!
    private static final String[] FIRST_NAMES = {
        "apple", "banana", "orange", "grape", "kiwi",
        "chocolate", "coffee", "sunset", "ocean", "mountain",
        "guitar", "piano", "happiness", "laughter", "silence",
        "firefly", "whisper", "velvet", "raindrop", "serendipity",
        "umbrella", "galaxy", "synergy", "breeze", "harmony",
        "jasmine", "cinnamon", "mystique", "zen", "jubilant"
    };
    private static final String[] LAST_NAMES = {
        "engineer", "scientist", "designer", "manager", "analyst",
        "specialist", "developer", "representative", "admin", "associate",
        "writer", "doctor", "counsel", "accountant", "trainer",
        "planner", "electrician", "chef", "nurse", "architect",
        "assistant", "technician", "artist", "officer", "therapist",
        "librarian", "consultant", "coordinator", "producer", "driver"
    };
    private static final int NUMBER_RANGE = 20;

    // methods

    public static String generateUsername() {
        return String.format("%s_%s_%d",
            randomItem(FIRST_NAMES),
            randomItem(LAST_NAMES),
            RANDOM.nextInt(NUMBER_RANGE)
        );
    }

    private static String randomItem(String[] array) {
        return array[RANDOM.nextInt(array.length)];
    }

    // source: https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration<InetAddress> ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    if(isValidIpAddress(i)) return i.getHostAddress();
                }
            }
            LOGGER.warn("Couldn't find a suitable local IP address");
            return "Cannot determine IP";
        } catch (Exception e) {
            LOGGER.error("Failed to get local IP address", e);
            return "Error";
        }
        
    }

    private static boolean isValidIpAddress(InetAddress address) {
        if(!address.isSiteLocalAddress()) return false;
        return Pattern.matches(IP_REGEX, address.getHostAddress());
    }

}

/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.server;

import java.util.Random;

public class Util {

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

}

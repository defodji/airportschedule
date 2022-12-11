package com.boeing.airportschedule.util;

import java.util.Random;

public class Utils {
    final static Random RANDOM = new Random();

    public static int getRandomDelay() {
        // between -15 and 44
        return RANDOM.nextInt(60)-15;
    }
}

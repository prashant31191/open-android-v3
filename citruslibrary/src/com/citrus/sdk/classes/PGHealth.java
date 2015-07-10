package com.citrus.sdk.classes;

/**
 * Created by salilgodbole on 10/07/15.
 */
public enum PGHealth {
    GOOD, BAD;

    public static PGHealth getPGHealth(String health) {
        int healthInInt = Integer.parseInt(health);
        if (healthInInt > 50) {
            return GOOD;
        } else {
            return BAD;
        }
    }
}

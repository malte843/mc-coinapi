package de.malte.coinapi.utils;

import java.util.UUID;

public class Util {
    public static String convertUUID(UUID uuid) {
        return uuid.toString().replaceAll("-", "");
    }
    public static UUID convertUUID(String uuid) {
        return UUID.fromString(
                uuid.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5")
        );
    }
}

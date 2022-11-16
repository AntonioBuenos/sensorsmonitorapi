package by.smirnov.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UUIDGenerator {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}

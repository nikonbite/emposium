package me.nikonbite.emposium.util;

public class Logger {
    public static final String PREFIX = "Emposium // ";

    public static void log(String message) {
        System.out.println(message);
    }

    public static void log(String prefix, String message) {
        System.out.println(prefix + message);
    }

    public static void logf(String message, Object... args) {
        System.out.printf(message + "%n", args);
    }
}
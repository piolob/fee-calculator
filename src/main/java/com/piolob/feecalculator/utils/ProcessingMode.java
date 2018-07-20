package com.piolob.feecalculator.utils;

public enum ProcessingMode {
    INMEMORY_MODE("inmemoryMode"),
    STREAM_MODE("streamMode");

    String name;

    ProcessingMode(String name) {
        this.name = name;
    }
}

package org.gamehouse.challenge.core;

import java.io.IOException;

import static org.gamehouse.challenge.core.Application.Cache;

public class Backup {

    public static void save() throws IOException {
        Cache.save();
        GameHistory.save();
    }

    public static void restore() throws IOException, ClassNotFoundException {
        Cache.restore();
        GameHistory.restore();
    }
}

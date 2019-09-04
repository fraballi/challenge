package org.gamehouse.challenge.views;

import org.gamehouse.challenge.core.Application;
import org.gamehouse.challenge.core.Backup;

import java.io.IOException;

public class SaveGameScreen implements Screen {

    @Override
    public void show() throws IOException, InterruptedException {
        save();
    }

    private void save() throws IOException, InterruptedException {
        if (Application.isEmpty()) {
            Application.error("Application must be initialized. Create characters, matches and explore.");
            return;
        }

        Backup.save();
        Application.success("Backup made successfully");
    }
}

package org.gamehouse.challenge.views;

import org.gamehouse.challenge.core.Application;
import org.gamehouse.challenge.core.Backup;

public class RestoreGameScreen implements Screen {

    @Override
    public void show() throws Exception {
        restore();
    }

    private void restore() throws Exception {
        Backup.restore();
        Application.success("Backup restored successfully");
    }
}

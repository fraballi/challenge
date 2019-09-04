package org.gamehouse.challenge.views;

import org.gamehouse.challenge.core.Application;

import java.util.Objects;

public interface Screen {
    void show() throws Exception;

    default void next(Screen screen) throws Exception {
        Application.clear();
        Objects.requireNonNull(screen).show();
    }
}


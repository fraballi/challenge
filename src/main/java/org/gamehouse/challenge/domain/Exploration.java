package org.gamehouse.challenge.domain;

import org.gamehouse.challenge.core.Application;
import org.gamehouse.challenge.core.AsciiArt;
import org.gamehouse.challenge.core.Judge;

import java.io.Serializable;
import java.util.Objects;

public final class Exploration implements Serializable {

    private static final long serialVersionUID = 2261548440079308388L;

    private final Character character;

    private Exploration(Character character) {
        Objects.requireNonNull(character);

        this.character = character;
    }

    public static Exploration create(Character character) {
        return new Exploration(character);
    }

    public void discover() throws InterruptedException {

        AsciiArt.printTextArt("Infinite and Beyond!", AsciiArt.ART_SIZE_MINI, AsciiArt.AsciiArtFont.ART_FONT_DIALOG_INPUT, "@");
        Thread.sleep(2000);

        Judge.claimPrize(character);

        Application.updateHistory(this);
    }

    public Character getCharacter() {
        return character;
    }
}

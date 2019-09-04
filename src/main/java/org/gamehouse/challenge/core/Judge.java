package org.gamehouse.challenge.core;

import org.gamehouse.challenge.domain.Character;
import org.gamehouse.challenge.domain.Skill;

import java.util.LinkedList;
import java.util.Queue;

public class Judge {

    private static Queue<Skill> vault = new LinkedList<>();

    public static void claimPrize(Character character) {
        if (vault.isEmpty())
            request();

        character.improve(vault.poll());
    }

    private static void request() {
        Application.generatePrizes(vault);
    }
}

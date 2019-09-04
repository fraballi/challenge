package org.gamehouse.challenge.views;

import org.gamehouse.challenge.core.Application;
import org.gamehouse.challenge.domain.Character;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public class CharacterDetailsScreen implements Screen {

    private final Character character;

    CharacterDetailsScreen(Character character) {
        this.character = character;
    }

    @Override
    public void show() {
        String name = character.getName();
        String actions = character.getActions().stream().map(Enum::toString).collect(joining(", "));
        String weapons = character.getWeapons().stream().map(Character.Weapon::toString).collect(joining(", "));
        String power = String.valueOf(character.getPower());

        String damageColumn = "Damage";
        String actionsColumn = "Actions";
        int separators = 3;
        int actionLength = actions.length() > actionsColumn.length() ? actions.length() : actionsColumn.length();
        int damageLength = power.length() > damageColumn.length() ? power.length() : damageColumn.length();
        int totalLength = IntStream.of(name.length(), weapons.length(), actionLength, damageLength).sum();


        String leftAlignFormat = "\t|%-" + name.length() + "s|%-" + weapons.length() + "s|%-" + actionLength + "s|%-" + damageLength + "s|%n";
        final StringBuilder segment = new StringBuilder();
        IntStream.range(0, totalLength + separators).forEach(i -> segment.append("-"));

        System.out.format("\t+" + segment + "+ %n");
        System.out.format(leftAlignFormat, "Name", "Weapons", actionsColumn, damageColumn);
        System.out.format("\t+" + segment + "+ %n");

        System.out.format(leftAlignFormat, name, weapons, actions, power);

        System.out.format("\t+" + segment + "+ %n");

        System.out.println(String.format("%s", Application.Console.ANSI_RESET));
    }
}

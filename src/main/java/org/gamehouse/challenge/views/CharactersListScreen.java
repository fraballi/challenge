package org.gamehouse.challenge.views;

import org.gamehouse.challenge.domain.Character;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;
import static org.gamehouse.challenge.core.Application.Console;

public class CharactersListScreen implements Screen {

    private final List<Character> characters;

    CharactersListScreen(List<Character> characters) {
        Objects.requireNonNull(characters);
        if (characters.isEmpty()) throw new IllegalArgumentException("Empty character collection");

        this.characters = characters;
    }

    @Override
    public void show() {

        final int maxNameLength = characters.stream().mapToInt(c -> c.getName().length()).max().getAsInt();
        final int maxWeaponsLength = characters.stream().mapToInt(c -> c.getWeapons().size()).max().getAsInt();
        final int maxActionsLength = characters.stream().mapToInt(c -> c.getActions().size()).max().getAsInt();

        String leftAlignFormat = "\t| %-" + maxNameLength + "s | %-" + maxWeaponsLength + "s | %-" + maxActionsLength + "s | %n";

        System.out.format("\t+------------------------------------------------------+%n");
        System.out.format(leftAlignFormat, "ID", "Name", "Weapons", "Actions");
        System.out.format("\t+------------------------------------------------------+%n");

        for (int i = 0; i < characters.size(); i++) {
            final Character character = characters.get(i);
            String actions = character.getActions().stream().map(Enum::toString).collect(joining(", "));
            String weapons = character.getWeapons().stream().map(Character.Weapon::toString).collect(joining(", "));
            System.out.format(leftAlignFormat, i + 1, character.getName(), weapons, actions);
        }

        System.out.format("\t+------------------------------------------------------+%n");

        System.out.println(String.format("%s", Console.ANSI_RESET));
    }
}

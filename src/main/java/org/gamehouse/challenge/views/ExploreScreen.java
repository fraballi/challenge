package org.gamehouse.challenge.views;

import org.gamehouse.challenge.core.Application;
import org.gamehouse.challenge.core.Menu.Option;
import org.gamehouse.challenge.dao.CharacterDAO;
import org.gamehouse.challenge.dao.ExplorationDAO;
import org.gamehouse.challenge.domain.Character;
import org.gamehouse.challenge.domain.Exploration;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;

public class ExploreScreen implements Screen {

    private final Scanner IN = new Scanner(System.in);
    private List<Character> CHARACTERS = CharacterDAO.list();
    private Predicate<String> characterPattern = (input) -> {
        Integer index;
        try {
            index = Integer.valueOf(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return index > 0 && index <= CHARACTERS.size();
    };

    private Character character;

    @Override
    public void show() throws Exception {
        create();
    }

    private void create() throws Exception {
        selectCharacter();
    }

    private void selectCharacter() throws Exception {
        if (CHARACTERS.isEmpty()) {
            Application.error("You must create a character first");
            Application.view(Option.CHARACTER_CREATION);

        } else {
            System.out.println("\n\tSelect a character to go explore (Press 's' = Save, 'r' = Reset): ");
            next(new CharactersListScreen(CHARACTERS));

            String entry;
            if (character != null) {
                Application.input("Adventure selected: Press 's' / 'r': ");
                entry = IN.next().trim();
                if (entry.equalsIgnoreCase("s")) {
                    if (character == null) {
                        Application.error("You must select a character first");
                        selectCharacter();
                    } else {
                        saveExploration();
                        return;
                    }
                }
                if (entry.equalsIgnoreCase("r"))
                    resetExploration();
            }

            Application.input("Adventurer: ");
            entry = IN.next().trim();
            if (!characterPattern.test(entry))
                Application.error("You must select a character first");
            else
                character = CHARACTERS.get(Integer.valueOf(entry) - 1);

            selectCharacter();
        }
    }

    private void resetExploration() throws Exception {
        create();
    }

    private void saveExploration() throws Exception {
        Objects.requireNonNull(character);

        final Exploration exploration = Exploration.create(character);
        ExplorationDAO.addExploration(exploration);
        Application.success("Exploration created successfully!");

        next(new CharacterDetailsScreen(exploration.getCharacter()));

        exploration.discover();

        System.out.println("REWARDS !");
        next(new CharacterDetailsScreen(exploration.getCharacter()));

    }
}

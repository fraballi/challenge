package org.gamehouse.challenge.views;

import org.gamehouse.challenge.core.Application;
import org.gamehouse.challenge.core.Menu.Option;
import org.gamehouse.challenge.dao.CharacterDAO;
import org.gamehouse.challenge.dao.MatchDAO;
import org.gamehouse.challenge.domain.Character;
import org.gamehouse.challenge.domain.Match;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;

public class MatchScreen implements Screen {

    private final Scanner IN = new Scanner(System.in);
    private List<Character> CHARACTERS = CharacterDAO.list();
    private Predicate<String> matchPattern = (input) -> {
        Integer index;
        try {
            index = Integer.valueOf(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return index > 0 && index <= CHARACTERS.size();
    };

    @Override
    public void show() throws Exception {
        create();
    }

    private void create() throws Exception {
        System.out.println("\n\tCreate new match: ");
        selectCharacters();
    }

    private void selectCharacters() throws Exception {
        if (CHARACTERS.size() < Match.Builder.MATCH_SIZE) {
            Application.error("Insufficient opponents for a match. Please, create (2) characters first.");
            Application.view(Option.CHARACTER_CREATION);
        } else
            setOpponents(new Match.Builder());
    }

    private void setOpponents(Match.Builder builder) throws Exception {

        System.out.println("\n\tSelect (2) characters (Press 's' = Save, 'r' = Reset): ");

        String entry;
        if (builder.isFull()) {

            Application.input("Match is full (2 characters). Press 's' / 'r': ");
            entry = IN.next().trim();

            if (entry.equalsIgnoreCase("s"))
                saveMatch(builder);

            if (entry.equalsIgnoreCase("r"))
                resetMatch(builder);

        } else {

            next(new CharactersListScreen(CHARACTERS));

            Application.input("Opponent: ");
            entry = IN.next().trim();

            if (!matchPattern.test(entry)) {
                Application.error("You must select an opponent");
                setOpponents(builder);
            } else {

                Character character = CHARACTERS.get(Integer.valueOf(entry) - 1);
                builder.addOpponent(character);
                setOpponents(builder);
            }
        }
    }

    private void resetMatch(Match.Builder builder) throws Exception {
        builder.reset();
        create();
    }

    private void saveMatch(Match.Builder builder) throws Exception {
        Objects.requireNonNull(builder);

        final Match match = builder.build();
        MatchDAO.addMatch(match);
        Application.success("Match created successfully!");

        next(new CharactersListScreen(match.getOpponents()));

        match.fight();

        System.out.println("WINNER !");
        next(new CharacterDetailsScreen(match.getWinner()));

    }
}

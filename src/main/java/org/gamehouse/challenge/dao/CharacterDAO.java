package org.gamehouse.challenge.dao;

import org.gamehouse.challenge.core.Application;
import org.gamehouse.challenge.domain.Character;

import java.util.ArrayList;
import java.util.List;

public class CharacterDAO {

    public static void addCharacter(Character character) {
        Application.Cache.addCharacter(character);
    }

    public static List<Character> list() {
        return new ArrayList<>(Application.Cache.getCharacters());
    }
}

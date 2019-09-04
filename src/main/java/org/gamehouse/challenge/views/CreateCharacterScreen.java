package org.gamehouse.challenge.views;

import org.gamehouse.challenge.core.Application;
import org.gamehouse.challenge.core.Armory;
import org.gamehouse.challenge.dao.CharacterDAO;
import org.gamehouse.challenge.domain.Character;
import org.gamehouse.challenge.domain.Character.Action;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;

import static org.gamehouse.challenge.domain.Character.Builder;
import static org.gamehouse.challenge.domain.Character.Weapon;

public class CreateCharacterScreen implements Screen {

    private final Scanner IN = new Scanner(System.in);
    private static final Map<Integer, Armory> WEAPONS = new HashMap<>();

    static {
        WEAPONS.put(1, Armory.AX);
        WEAPONS.put(2, Armory.SWORD);
        WEAPONS.put(3, Armory.MAGIC);
        WEAPONS.put(4, Armory.HAMMER);
        WEAPONS.put(5, Armory.SPEAR);
        WEAPONS.put(6, Armory.CHAIN);
        WEAPONS.put(7, Armory.ROPE);
    }

    private static Predicate<String> weaponsPattern = (input) -> {
        Integer value;
        try {
            value = Integer.valueOf(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return WEAPONS.containsKey(value);
    };

    private static Predicate<String> actionsPattern = (input) -> {
        Integer value;
        try {
            value = Integer.valueOf(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return Action.set().stream().anyMatch(a -> a.getIndex() == value);
    };

    @Override
    public void show() throws Exception {
        create();
    }

    private void create() throws Exception {
        System.out.println("\n\tCreate a character:\t");
        setName();
    }

    private void setName() throws Exception {
        Application.input("\tName: ");

        Builder builder;
        String entry = IN.next().trim();
        if (entry.length() == 0)
            setName();
        else {
            builder = new Builder(entry);
            setWeapon(builder);
        }
    }

    private void setWeapon(Builder builder) throws Exception {

        System.out.println("\tSelect a weapon: ");
        WEAPONS.forEach((k, v) -> System.out.println(String.format("\t\t%s - %s", k, v)));
        Application.input("Weapon: ");

        String entry = IN.next().trim();
        if (entry.length() == 0 || !weaponsPattern.test(entry)) {
            Application.error("You must select a weapon first");
            setWeapon(builder);
        } else {
            final Armory armory = WEAPONS.get(Integer.valueOf(entry));
            builder.weapon(Weapon.of(armory.name(), armory.getDamage()));
            setActions(builder);
        }
    }

    private void setActions(Builder builder) throws Exception {
        System.out.println("\n\tSelect available actions (Press 's' = Save): ");
        Action.set().forEach(a -> System.out.println(String.format("\t\t%s - %s", a.getIndex(), a.name())));
        Application.input("Action: ");

        String entry = IN.next().trim();
        if (entry.equalsIgnoreCase("s"))
            saveCharacter(builder);
        else {
            if (!actionsPattern.test(entry)) {
                if (!builder.hasActions()) {
                    Application.error("You must select at least one Action");
                    setActions(builder);
                }
            } else {
                Action.set().stream().filter(a -> a.getIndex() == Integer.valueOf(entry)).findFirst().ifPresent(builder::addAction);
                setActions(builder);
            }
        }
    }

    private void saveCharacter(Builder builder) throws Exception {
        Objects.requireNonNull(builder);

        final Character character = builder.build();
        CharacterDAO.addCharacter(character);
        Application.success("Character created successfully!");

        next(new CharacterDetailsScreen(character));
    }
}

package org.gamehouse.challenge.core;

import org.gamehouse.challenge.views.*;

import java.util.EnumSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.gamehouse.challenge.core.Application.display;

public class Menu {

    static Function<String, Boolean> checkPattern = Option.MENU_PATTERN::test;

    static void print() {

        Application.clear();

        printOptions();

        Application.input("Option: ");
    }

    private static void printOptions() {

        final int maxLength = Option.set().stream().mapToInt(o -> o.getCaption().length()).max().getAsInt();

        StringBuilder sb = new StringBuilder("\t" + Application.APP_NAME.toUpperCase() + "\t\n\n");
        Option.set().forEach(o -> {
            sb.append('\t').append(o);
            IntStream.range(0, maxLength - o.getCaption().length()).forEach(i -> sb.append(' '));
            sb.append('\n');
        });

        sb.append("\n\n");
        System.out.print(sb);
    }

    public enum Option {

        CHARACTER_CREATION("1", "Create a character"),
        EXPLORATION("2", "Explore"),
        FIGHTING("3", "Fight"),
        RESUMING_GAME("4", "Resume game"),
        SAVE_GAME("5", "Save game"),
        QUIT("q", "Quit");


        private final String index;
        private final String caption;
        private static Predicate<String> MENU_PATTERN = Pattern.compile(createPattern()).asPredicate();


        Option(String index, String caption) {
            this.index = index;
            this.caption = caption;
        }

        public String getIndex() {
            return index;
        }

        public String getCaption() {
            return caption;
        }

        public static EnumSet<Option> set() {
            return EnumSet.allOf(Option.class);
        }

        private static String createPattern() {
            final String content = set().stream().map(Option::getIndex).collect(Collectors.joining("|"));
            return String.format("^(%s)$", content);
        }

        @Override
        public String toString() {
            return String.format("%s - %s", index, caption);
        }

    }

    static class Handler {

        private final Option option;

        private Handler(Option option) {
            this.option = option;
        }

        static Handler create(Option option) {
            return new Handler(option);
        }

        void call() throws Exception {

            switch (option) {
                case CHARACTER_CREATION:
                    display(new CreateCharacterScreen());
                    break;
                case EXPLORATION:
                    display(new ExploreScreen());
                    break;
                case FIGHTING:
                    display(new MatchScreen());
                    break;
                case RESUMING_GAME:
                    display(new RestoreGameScreen());
                    break;
                case SAVE_GAME:
                    display(new SaveGameScreen());
                    break;
                case QUIT:
                    Application.exit();
                    return;
            }

            Application.menu();
        }
    }
}

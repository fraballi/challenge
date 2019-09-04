package org.gamehouse.challenge.core;

import org.gamehouse.challenge.core.Menu.Handler;
import org.gamehouse.challenge.core.Menu.Option;
import org.gamehouse.challenge.domain.Character;
import org.gamehouse.challenge.domain.Exploration;
import org.gamehouse.challenge.domain.Match;
import org.gamehouse.challenge.domain.Skill;
import org.gamehouse.challenge.views.Screen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static org.gamehouse.challenge.core.Menu.checkPattern;

public class Application {

    private static final Scanner IN = new Scanner(System.in);

    static final String APP_NAME = "Challenge";
    private static final String APP_WELCOME = "welcome.txt";
    private static final String FIGHT_LOGO = "images/crusader-black.png";
    private static final String APP_CACHE_BACKUP = "app_cache.ser";
    static final String APP_HISTORY_BACKUP = "app_history.ser";
    private static final int PRIZES_MAX_SIZE = 10;

    private static Consumer<String> render = (index) -> {
        Option.set().stream().filter(o -> o.getIndex().equals(index)).findFirst().ifPresent(option -> {
            try {
                view(option);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    };

    private static void rules() throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getResource(APP_WELCOME)))) {
            reader.lines().forEach(l -> {
                try {
                    Thread.sleep(300);
                    System.out.println("\t" + l);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Thread.sleep(5000);
    }

    private Application() {
    }

    public static void start() throws Exception {
        welcome();
        menu();
    }

    static void menu() throws InterruptedException {
        clear();

        Menu.print();

        String entry = IN.next().trim();
        if (!checkPattern.apply(entry))
            error("Invalid entry. Please, check menu options");
        else
            render.accept(entry);
    }

    public static void input(String caption) {
        Objects.requireNonNull(caption);

        System.out.print("\t" + caption);
    }

    public static void view(Option option) throws Exception {
        Handler.create(option).call();
    }

    static void display(Screen screen) throws Exception {
        clear();
        screen.show();
    }

    public static void success(String message) throws InterruptedException {
        Objects.requireNonNull(message);

        clear();

        String output = String.format("\t%s%s Congratulations: %s\t", Console.ANSI_BG_GREEN, Console.ANSI_BLACK, message);
        System.out.format("%-" + output.length() + "s%n", output);
        Thread.sleep(2000);

        clear();
    }

    public static void error(String message) throws InterruptedException {
        Objects.requireNonNull(message);

        clear();

        String output = String.format("\t%s%s Error: %s \t", Console.ANSI_BG_RED, Console.ANSI_WHITE, message);
        System.out.format("%-" + output.length() + "s%n", output);
        Thread.sleep(1500);

        clear();
    }

    private static void welcome() throws Exception {

        clear();

        logo();
        Thread.sleep(1000);

        rules();
    }

    public static void clear() {
        System.out.println(Console.ANSI_RESET);
        System.out.flush();
    }

    static void exit() {
        System.out.println("Exit");
    }

    private static void logo() {
        AsciiArt.printTextArt(APP_NAME, AsciiArt.ART_SIZE_MINI, AsciiArt.AsciiArtFont.ART_FONT_MONO, "@");
    }

    public static void fightLogo() throws IOException {
        BufferedImage image = ImageIO.read(Application.getResource(FIGHT_LOGO));
        System.out.print(AsciiArt.convert(image, false));
    }


    private static InputStream getResource(String resource) {
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(resource));
    }

    public static void updateHistory(Match match) {
        Application.Cache.removeMatch(match);
    }

    public static void updateHistory(Exploration exploration) {
        Application.Cache.addExploration(exploration);
    }

    public static boolean isEmpty() {
        return Cache.isEmpty();
    }

    static void generatePrizes(Queue<Skill> prizes) {
        final Random rnd = new Random();
        IntStream.range(0, PRIZES_MAX_SIZE).forEach(i -> {
            Skill skill = Skill.create("skill-" + i, rnd.nextDouble() * .10, rnd.nextDouble() * .15);
            prizes.add(skill);
        });
    }

    public static class Console {
        public static final String ANSI_RESET = "\u001B[0m";

        public static final String ANSI_BLACK = "\u001B[30m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_WHITE = "\u001B[37m";

        public static final String ANSI_BRIGHT_BLACK = "\u001B[90m";
        public static final String ANSI_BRIGHT_RED = "\u001B[91m";
        public static final String ANSI_BRIGHT_GREEN = "\u001B[92m";
        public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";
        public static final String ANSI_BRIGHT_BLUE = "\u001B[94m";
        public static final String ANSI_BRIGHT_PURPLE = "\u001B[95m";
        public static final String ANSI_BRIGHT_CYAN = "\u001B[96m";
        public static final String ANSI_BRIGHT_WHITE = "\u001B[97m";

        static final String[] FOREGROUNDS = {
                ANSI_BLACK, ANSI_RED, ANSI_GREEN, ANSI_YELLOW,
                ANSI_BLUE, ANSI_PURPLE, ANSI_CYAN, ANSI_WHITE,
                ANSI_BRIGHT_BLACK, ANSI_BRIGHT_RED, ANSI_BRIGHT_GREEN, ANSI_BRIGHT_YELLOW,
                ANSI_BRIGHT_BLUE, ANSI_BRIGHT_PURPLE, ANSI_BRIGHT_CYAN, ANSI_BRIGHT_WHITE
        };

        public static final String ANSI_BG_BLACK = "\u001B[40m";
        public static final String ANSI_BG_RED = "\u001B[41m";
        public static final String ANSI_BG_GREEN = "\u001B[42m";
        public static final String ANSI_BG_YELLOW = "\u001B[43m";
        public static final String ANSI_BG_BLUE = "\u001B[44m";
        public static final String ANSI_BG_PURPLE = "\u001B[45m";
        public static final String ANSI_BG_CYAN = "\u001B[46m";
        public static final String ANSI_BG_WHITE = "\u001B[47m";

        public static final String ANSI_BRIGHT_BG_BLACK = "\u001B[100m";
        public static final String ANSI_BRIGHT_BG_RED = "\u001B[101m";
        public static final String ANSI_BRIGHT_BG_GREEN = "\u001B[102m";
        public static final String ANSI_BRIGHT_BG_YELLOW = "\u001B[103m";
        public static final String ANSI_BRIGHT_BG_BLUE = "\u001B[104m";
        public static final String ANSI_BRIGHT_BG_PURPLE = "\u001B[105m";
        public static final String ANSI_BRIGHT_BG_CYAN = "\u001B[106m";
        public static final String ANSI_BRIGHT_BG_WHITE = "\u001B[107m";

        static final String[] BACKGROUNDS = {
                ANSI_BG_BLACK, ANSI_BG_RED, ANSI_BG_GREEN, ANSI_BG_YELLOW,
                ANSI_BG_BLUE, ANSI_BG_PURPLE, ANSI_BG_CYAN, ANSI_BG_WHITE,
                ANSI_BRIGHT_BG_BLACK, ANSI_BRIGHT_BG_RED, ANSI_BRIGHT_BG_GREEN, ANSI_BRIGHT_BG_YELLOW,
                ANSI_BRIGHT_BG_BLUE, ANSI_BRIGHT_BG_PURPLE, ANSI_BRIGHT_BG_CYAN, ANSI_BRIGHT_BG_WHITE};

        public static void main(String[] args) {

            System.out.println("\n  Default text\n");

            for (String fg : FOREGROUNDS) {
                for (String bg : BACKGROUNDS)
                    System.out.print(fg + bg + "  TEST  ");
                System.out.println(ANSI_RESET);
            }

            System.out.println(ANSI_RESET + "\n  Back to default.\n");

        }
    }

    public static class Cache {
        private static Set<Character> characters = new HashSet<>();
        private static Set<Match> matches = new HashSet<>();
        private static List<Exploration> explorations = new ArrayList<>();

        private Cache() {
        }

        public static Set<Character> getCharacters() {
            return Collections.unmodifiableSet(characters);
        }

        public static Set<Match> getMatches() {
            return Collections.unmodifiableSet(matches);
        }

        public static boolean isEmpty() {
            return characters.isEmpty() && matches.isEmpty() && explorations.isEmpty();
        }

        public static void addCharacter(Character character) {
            Objects.requireNonNull(character);

            characters.add(character);
        }

        public static void addMatch(Match match) {
            Objects.requireNonNull(match);

            matches.add(match);
        }

        static void removeMatch(Match match) {
            Objects.requireNonNull(match);

            GameHistory.addMatch(match);
            matches.remove(match);
        }

        public static void addExploration(Exploration exploration) {
            Objects.requireNonNull(exploration);

            explorations.add(exploration);
        }

        static void removeExploration(Exploration exploration) {
            Objects.requireNonNull(exploration);

            GameHistory.addExploration(exploration);
            explorations.remove(exploration);
        }

        static void save() throws IOException {

            try (FileOutputStream fos = new FileOutputStream(APP_CACHE_BACKUP); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                {
                    List<Character> charList = new ArrayList<>(characters);
                    List<Match> matchesList = new ArrayList<>(matches);
                    List<Exploration> exploreList = new ArrayList<>(explorations);

                    oos.writeObject(charList);
                    oos.writeObject(matchesList);
                    oos.writeObject(exploreList);
                }
            }
        }

        static void restore() throws IOException, ClassNotFoundException {
            try (FileInputStream fis = new FileInputStream(APP_CACHE_BACKUP); ObjectInputStream ois = new ObjectInputStream(fis)) {
                {
                    Object charList = ois.readObject();
                    if (charList instanceof List<?> && !((List<?>) charList).isEmpty()) {
                        if (((List<?>) charList).get(0) instanceof Character) {
                            for (Object obj : (List<?>) charList)
                                characters.add((Character) obj);
                        }
                    }

                    Object matchesList = ois.readObject();
                    if (matchesList instanceof List<?> && !((List<?>) matchesList).isEmpty()) {
                        if (((List<?>) matchesList).get(0) instanceof Match) {
                            for (Object obj : (List<?>) matchesList)
                                matches.add((Match) obj);
                        }
                    }

                    Object exploreList = ois.readObject();
                    if (exploreList instanceof List<?> && !((List<?>) exploreList).isEmpty()) {
                        if (((List<?>) exploreList).get(0) instanceof Exploration) {
                            for (Object obj : (List<?>) exploreList)
                                explorations.add((Exploration) obj);
                        }
                    }
                }
            }
        }
    }
}

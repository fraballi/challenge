package org.gamehouse.challenge.core;

import org.gamehouse.challenge.domain.Exploration;
import org.gamehouse.challenge.domain.Match;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.gamehouse.challenge.core.Application.APP_HISTORY_BACKUP;

class GameHistory {

    private static List<Match> matches = new ArrayList<>();
    private static List<Exploration> explorations = new ArrayList<>();

    static void addMatch(Match match) {
        Objects.requireNonNull(match);

        matches.add(match);
    }

    static void addExploration(Exploration exploration) {
        Objects.requireNonNull(exploration);

        explorations.add(exploration);
    }

    static void save() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(new File(APP_HISTORY_BACKUP)); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            List<Match> matchesList = new ArrayList<>(matches);
            List<Exploration> exploreList = new ArrayList<>(explorations);

            oos.writeObject(matchesList);
            oos.writeObject(exploreList);
        }
    }

    static void restore() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(APP_HISTORY_BACKUP); ObjectInputStream ois = new ObjectInputStream(fis)) {
            {
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
package org.gamehouse.challenge.dao;

import org.gamehouse.challenge.core.Application;
import org.gamehouse.challenge.domain.Match;

public class MatchDAO {

    public static void addMatch(Match match) {
        Application.Cache.addMatch(match);
    }
}

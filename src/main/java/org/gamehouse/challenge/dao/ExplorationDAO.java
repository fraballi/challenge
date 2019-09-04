package org.gamehouse.challenge.dao;

import org.gamehouse.challenge.core.Application;
import org.gamehouse.challenge.domain.Exploration;

public class ExplorationDAO {

    public static void addExploration(Exploration exploration){
        Application.Cache.addExploration(exploration);
    }
}

package org.gamehouse.challenge.domain;

import org.gamehouse.challenge.core.Application;
import org.gamehouse.challenge.core.Judge;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public final class Match implements Serializable {

    private static final long serialVersionUID = -5997135334677641468L;

    private enum State {
        STARTED, FINISHED
    }

    private final Set<Character> opponents;
    private Character winner;
    private State state;

    private Match(Builder builder) {
        this.opponents = builder.opponents;
    }

    public List<Character> getOpponents() {
        final List<Character> opponents = new ArrayList<>(this.opponents);
        return Collections.unmodifiableList(opponents);
    }

    public State getState() {
        return state;
    }

    public Character getWinner() {
        return winner;
    }


    public void fight() throws InterruptedException, IOException {

        state = State.STARTED;

        Application.fightLogo();
        Thread.sleep(2000);

        Character c1 = getOpponents().get(0);
        Character c2 = getOpponents().get(1);
        winner = c1.compareTo(c2) > 0 ? c1 : c2;

        Judge.claimPrize(winner);

        state = State.FINISHED;
        updateHistory();
    }

    private void updateHistory() {
        Application.updateHistory(this);
    }

    public static class Builder {

        public static final int MATCH_SIZE = 2;
        private Set<Character> opponents;

        public Builder() {
            this.opponents = new HashSet<>(MATCH_SIZE);
        }

        public Builder addOpponent(Character character) {
            Objects.requireNonNull(character);

            if (!isFull())
                this.opponents.add(character);

            return this;
        }

        public boolean isFull() {
            return this.opponents.size() == MATCH_SIZE;
        }

        public void reset() {
            this.opponents = new HashSet<>(MATCH_SIZE);
        }

        public Match build() {
            return new Match(this);
        }
    }
}

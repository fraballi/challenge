package org.gamehouse.challenge.domain;

import java.util.Objects;

public final class Skill implements Damage, Comparable<Skill> {

    private final String name;
    private final double strength;
    private final double experience;

    private Skill(String name, double strength, double experience) {

        this.name = name;
        this.strength = strength;
        this.experience = experience;
    }

    public static Skill create(String name, double strength, double experience) {
        Objects.requireNonNull(name);
        if (strength <= 0 || experience <= 0)
            throw new IllegalArgumentException("Strength and experience must be greater than 0");

        return new Skill(name, strength, experience);
    }

    public String getName() {
        return name;
    }

    public double getExperience() {
        return experience;
    }

    public double getStrength() {
        return strength;
    }

    @Override
    public double factor() {
        return strength * experience;
    }

    @Override
    public int compareTo(Skill o) {
        return Double.compare(factor(), o.factor());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Skill)) return false;

        return this.name.equals(((Skill) obj).getName()) &&
                Double.compare(this.experience, ((Skill) obj).getExperience()) == 0 &&
                Double.compare(this.strength, ((Skill) obj).getStrength()) == 0;
    }

    @Override
    public int hashCode() {
        int result = 31 * name.hashCode() + Double.hashCode(experience);
        result = 31 * result + Double.hashCode(strength);
        return result;
    }
}

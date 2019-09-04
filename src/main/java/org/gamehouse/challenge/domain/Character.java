package org.gamehouse.challenge.domain;

import java.io.Serializable;
import java.util.*;

public class Character implements Comparable<Character>, Serializable {

    private static final long serialVersionUID = -4470825200928327978L;

    public void improve(Skill skill) {
        this.getCondition().improve(skill);
    }

    private enum Size {
        SMALL(1), MEDIUM(3), LARGE(5);

        private final int value;

        Size(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static EnumSet<Action> set() {
            return EnumSet.allOf(Action.class);
        }
    }

    public enum Action {
        MOVE(1), RUN(2), JUMP(3), FLIGHT(4), SWIM(5), ATTACK(6);

        private int index;

        Action(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public static EnumSet<Action> set() {
            return EnumSet.allOf(Action.class);
        }
    }

    private static class Condition implements Damage, Serializable {

        private static final long serialVersionUID = 4557149110219053218L;

        private static final double BLOOD_NORMAL = 100D;
        private static final double STRENGTH_NORMAL = 10D;
        private static final double EXPERIENCE_NORMAL = 10D;

        private final double blood;
        private double strength;
        private double experience;

        private Condition(double blood, double strength, double experience) {

            this.blood = blood;
            this.strength = strength;
            this.experience = experience;
        }

        static Condition newCondition() {
            return new Condition(BLOOD_NORMAL, STRENGTH_NORMAL, EXPERIENCE_NORMAL);
        }

        static Condition newCondition(double bonus) {
            return new Condition(BLOOD_NORMAL + bonus, STRENGTH_NORMAL + bonus, EXPERIENCE_NORMAL + bonus);
        }

        public static Condition of(double blood, double strength, double experience) {
            return new Condition(blood, strength, experience);
        }

        public void improve(Skill skill) {
            Objects.requireNonNull(skill);

            if (skill.getStrength() > 0)
                this.strength += skill.getStrength();
            if (skill.getExperience() > 0)
                this.experience += skill.getExperience();
        }

        @Override
        public double factor() {
            return blood * strength * experience;
        }
    }

    public static class Weapon implements Damage, Comparable<Weapon>, Serializable {

        private static final long serialVersionUID = 4749485171290719224L;

        private final String name;
        private final double damage;

        Weapon(String name, double damage) {
            this.name = name;
            this.damage = damage;
        }

        public static Weapon of(String name, double damage) {
            return new Weapon(name, damage);
        }

        String getName() {
            return name;
        }

        @Override
        public double factor() {
            return damage;
        }

        @Override
        public int compareTo(Weapon o) {
            return Double.compare(factor(), o.factor());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Weapon)) return false;

            return name.equals(((Weapon) obj).getName()) &&
                    Double.compare(damage, ((Weapon) obj).damage) == 0;
        }

        @Override
        public int hashCode() {
            return 31 * name.hashCode();
        }

        @Override
        public String toString() {
            return String.format("%s [Damage: %s]", this.name, this.damage);
        }
    }

    private final String name;
    private final Size size;
    private final Condition condition;

    private final Set<Action> actions;
    private final Set<Skill> skills;
    private final Set<Weapon> weapons;

    private Weapon selectedWeapon;

    private Character(Builder builder) {
        this.name = builder.name;
        this.size = builder.size;
        this.condition = builder.condition;
        this.actions = builder.actions;
        this.skills = builder.skills;
        this.selectedWeapon = builder.selectedWeapon;
        this.weapons = builder.weapons;
    }

    public static class Builder {

        private final String name;
        private final Size size;
        private final Condition condition = Condition.newCondition();

        private final Set<Action> actions = new HashSet<>();
        private final Set<Skill> skills = new TreeSet<>();
        private final Set<Weapon> weapons = new TreeSet<>();

        private Weapon selectedWeapon;

        public Builder(String name) {
            Objects.requireNonNull(name);

            this.name = name;
            this.size = Size.SMALL;
        }

        public Builder(String name, Size size) {
            Objects.requireNonNull(name);
            Objects.requireNonNull(size);

            this.name = name;
            this.size = size;
        }

        public Builder weapon(Weapon weapon) {
            Objects.requireNonNull(weapon);
            this.selectedWeapon = weapon;
            this.weapons.add(weapon);
            return this;
        }

        public Builder addAction(Action action) {
            Objects.requireNonNull(action);

            this.actions.add(action);
            return this;
        }

        public Builder addActions(Set<Action> actions) {
            Objects.requireNonNull(actions);

            this.actions.addAll(actions);
            return this;
        }

        public Builder addWeapons(Set<Weapon> weapons) {
            this.weapons.addAll(weapons);
            return this;
        }

        public Character build() {
            return new Character(this);
        }

        public boolean hasActions() {
            return !actions.isEmpty();
        }
    }

    public String getName() {
        return name;
    }

    private Size getSize() {
        return size;
    }

    public Set<Action> getActions() {
        return Collections.unmodifiableSet(actions);
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public Set<Weapon> getWeapons() {
        return Collections.unmodifiableSet(weapons);
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setSelectedWeapon(Weapon weapon) {
        Objects.requireNonNull(weapon);
        if (!this.weapons.contains(weapon))
            throw new NoSuchElementException("Weapon not found in private arsenal");

        this.selectedWeapon = weapon;
    }

    public Condition getCondition() {
        return condition;
    }

    public double getPower() {
        double skillsSum = skills.stream().mapToDouble(Skill::factor).sum();
        return size.getValue() * condition.factor() * (skillsSum > 0 ? skillsSum : 1);
    }

    @Override
    public int compareTo(Character o) {
        return Double.compare(this.getPower(), o.getPower());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Character)) return false;

        return this.getName().equals(((Character) obj).getName()) &&
                this.getSize().equals(((Character) obj).getSize()) &&
                this.actions.equals(((Character) obj).getActions()) &&
                this.skills.equals(((Character) obj).getSkills()) &&
                this.weapons.equals(((Character) obj).getWeapons());
    }

    @Override
    public int hashCode() {

        int result = getName().hashCode();
        result = 31 * result + getSize().hashCode();
        result = 31 * result + actions.stream().map(Action::hashCode).reduce(1, (a, b) -> a.hashCode() * b.hashCode());
        result = 31 * result + (int) skills.stream().mapToDouble(Skill::hashCode).reduce(1, (a, b) -> a * b);
        result = 31 * result + (int) weapons.stream().mapToDouble(Weapon::hashCode).reduce(1, (a, b) -> a * b);

        return result;
    }

    @Override
    public String toString() {
        return String.format("[ Nm: %s, Sz:%s, Pw: %s ]", this.getName(), this.getSize(), this.getPower());
    }
}
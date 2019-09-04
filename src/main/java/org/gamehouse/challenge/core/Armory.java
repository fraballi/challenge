package org.gamehouse.challenge.core;

import java.util.EnumSet;

public enum Armory {
    AX(.45), SWORD(.4), MAGIC(.35), HAMMER(.3), SPEAR(.25), CHAIN(.2), ROPE(.15);

    private final double damage;

    Armory(double damage) {
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    public static EnumSet<Armory> set() {
        return EnumSet.allOf(Armory.class);
    }

    @Override
    public String toString() {
        return String.format("%s [Damage: %s]", this.name(), this.damage);
    }
}

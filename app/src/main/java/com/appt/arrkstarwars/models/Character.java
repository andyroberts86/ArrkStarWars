package com.appt.arrkstarwars.models;

import java.util.Date;
import java.util.Objects;

public class Character {
    private final String name;
    private final Integer height;
    private final Integer mass;
    private final Date created;

    public Character(String name, Integer height, Integer mass, Date created) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getMass() {
        return mass;
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(name, character.name) &&
                Objects.equals(height, character.height) &&
                Objects.equals(mass, character.mass) &&
                Objects.equals(created, character.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, mass, created);
    }
}

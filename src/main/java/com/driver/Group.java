package com.driver;

import java.util.Objects;

public class Group {
    private String name;
    private int numberOfParticipants;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return numberOfParticipants == group.numberOfParticipants && name.equals(group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numberOfParticipants);
    }
}

package by.bsu.mss.kazlova.ds1.lab1.entity;

import java.util.Objects;

public class Team {
    private int id;
    private String name;
    private String coach;

    public Team(String name, String coach) {
        this.name = name;
        this.coach = coach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return
                Objects.equals(getName(), team.getName()) &&
                Objects.equals(getCoach(), team.getCoach());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCoach());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    @Override
    public String toString() {
        return "Team{" +
                ", name='" + name + '\'' +
                ", coach='" + coach + '\'' +
                '}';
    }
}

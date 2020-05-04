package by.bsu.mss.kazlova.ds1.lab1.entity;

import javafx.beans.Observable;

import java.util.Date;
import java.util.Objects;

public class Game  {
    String team1;
    String team2;
    Date date;
    Result result;
    int id;

   public enum Result
    {
        NONE,
        WIN,
        DRAW,
        LOSE
    }

    public Game(String team1, String team2, Date date, Result result) {
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return
                getTeam1() == game.getTeam1() &&
                getTeam2() == game.getTeam2() &&
                Objects.equals(getDate(), game.getDate()) &&
                getResult() == game.getResult();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeam1(), getTeam2(), getDate(), getResult());
    }

    @Override
    public String toString() {
        return "Game{" +
                "team1=" + team1 +
                ", team2=" + team2 +
                ", date=" + date +
                ", result=" + result +
                '}';
    }
}

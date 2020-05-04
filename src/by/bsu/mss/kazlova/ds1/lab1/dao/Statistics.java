package by.bsu.mss.kazlova.ds1.lab1.dao;

import by.bsu.mss.kazlova.ds1.lab1.controller.Controller;
import by.bsu.mss.kazlova.ds1.lab1.entity.Game;
import by.bsu.mss.kazlova.ds1.lab1.entity.Team;

import java.util.Date;

public class Statistics {
    public static int[] count(Date from, Date to, String team) {
        int wins = 0;
        int draws = 0;
        int fails = 0;

        try {
            for (Game cur : Controller.controller.getGamesList()) {
                if (cur.getTeam1().equals(team)) {
                    if (cur.getDate().after(from) && cur.getDate().before(to)) {
                        if (cur.getResult() == Game.Result.WIN) wins++;
                        if (cur.getResult() == Game.Result.DRAW) draws++;
                        if (cur.getResult() == Game.Result.LOSE) fails++;
                    }
                }

                if (cur.getTeam2().equals(team)) {
                    if (cur.getDate().after(from) && cur.getDate().before(to)) {
                        if (cur.getResult() == Game.Result.WIN) fails++;
                        if (cur.getResult() == Game.Result.DRAW) draws++;
                        if (cur.getResult() == Game.Result.LOSE) wins++;
                    }
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return new int[]{wins, draws, fails};
    }
}

package by.bsu.mss.kazlova.ds1.lab1;

import by.bsu.mss.kazlova.ds1.lab1.controller.Controller;
import by.bsu.mss.kazlova.ds1.lab1.dao.Dao;
import by.bsu.mss.kazlova.ds1.lab1.dao.DaoException;
import by.bsu.mss.kazlova.ds1.lab1.entity.Game;
import by.bsu.mss.kazlova.ds1.lab1.entity.GameValidator;
import by.bsu.mss.kazlova.ds1.lab1.entity.Team;

import java.lang.invoke.VarHandle;
import java.util.Date;

public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/bsu_mss_lab1";
    private static final String user = "root";
    private static final String password = "12345678";

    public static void main(String[] args){

       //in case of time_zone excpn "set global time_zone = '-3:00';" in mysql
        Dao dao=Dao.getInstance();
        try {
            dao.connect(url, user, password);
          System.out.println(Controller.controller.getGamesList().size());
        } catch (DaoException e){
            e.printStackTrace();
        }

        Team testTeam=new Team("Vitiaz", "Prokopenia");
      //Game testGame=new Game(1, 2, new Date(2020, 3, 3), Game.Result.DRAW);

/*
        try {
            dao.writeGame(testGame);
        } catch (DaoException e){
            e.printStackTrace();
        }

*/

    }
}

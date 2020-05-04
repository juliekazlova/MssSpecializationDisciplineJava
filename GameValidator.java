package by.bsu.mss.kazlova.ds1.lab1.entity;

import by.bsu.mss.kazlova.ds1.lab1.dao.Dao;

public class GameValidator {

    public static boolean validate(Game game){
        if(game.getDate()==null||game.getResult()==null||game.team1==null||game.team2==null) {
            System.out.println("Validator: null field value!");
            return false;
        }
        if (game.getTeam1().equals(game.getTeam2()))
        return false;
        if(!Dao.getInstance().teamExists(game.getTeam1()))
        {
            System.out.println("Validator: team 1 doesn't exist!");
            return false;
        }
        if(!Dao.getInstance().teamExists(game.getTeam2()))
        {
            System.out.println("Validator: team 2 doesn't exist!");
            return false;
        }
            return true;
    }
}

package by.bsu.mss.kazlova.ds1.lab1.dao;

import by.bsu.mss.kazlova.ds1.lab1.entity.Game;
import by.bsu.mss.kazlova.ds1.lab1.entity.GameValidator;
import by.bsu.mss.kazlova.ds1.lab1.entity.Team;
import com.mysql.cj.jdbc.NonRegisteringDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao {
    Connection connection;
    Driver driver;
    private Statement statement;
    private static ResultSet rs;
    private static Dao instance=new Dao();
    private Dao() {
    }

    public static Dao getInstance(){
        return instance;
    }

    public boolean connect(String url, String user, String password) throws DaoException{
        try {
            driver = new NonRegisteringDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DaoException();
        }
        System.out.println("Connection established...");
        return true;
    }

    public boolean writeTeam(Team team) throws DaoException{
        StringBuilder sb=new StringBuilder("INSERT INTO teams(name, coach) VALUES ('");
        sb.append(team.getName()).append("', '").append(team.getCoach()).append("');");
        try {
            statement.execute(sb.toString());
        } catch (SQLException e){
            e.printStackTrace();
            throw new DaoException();
        }
        return true;
    }

    public boolean writeGame(Game game) throws DaoException{
        if(!GameValidator.validate(game))
        {
            throw new DaoException();
        }
        StringBuilder sb=new StringBuilder("INSERT INTO games(team_1, team_2, date, result) VALUES ('");
       sb.append(getTeamIdByName(game.getTeam1())).append("', '").
               append(getTeamIdByName(game.getTeam2())).append("', STR_TO_DATE('").
               append(game.getDate().getDate()).append("-").
               append(game.getDate().getMonth()).append("-").
               append(game.getDate().getYear()).append("', '%d-%m-%Y'), '").
               append(game.getResult().ordinal()).append("');");
        try {
            statement.execute(sb.toString());
        } catch (SQLException e){
            e.printStackTrace();
            throw new DaoException();
        }
        return true;
    }

    public int getTeamIdByName(String teamName){
        int id=-1;
        try{
            Statement stmt = connection.createStatement();
            String query = "SELECT id FROM bsu_mss_lab1.teams where name='"+teamName+"';";
            //Executing the query
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    public boolean teamExists(String name){
       int id=-1;
        try{
            Statement stmt = connection.createStatement();
            String query = "SELECT id FROM bsu_mss_lab1.teams where name='"+name+"';";
            //Executing the query
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
               id = rs.getInt(1);
            }
            if (id!=-1) return true;
            else return false;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public String teamNameById (int teamId){
        String name=null;
        try{
            Statement stmt = connection.createStatement();
            String query = "SELECT name FROM bsu_mss_lab1.teams where id="+teamId+";";
            //Executing the query
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                name = rs.getString(1);
            }
            if (name==null)
            {
                System.out.println("Dao: no team with id "+teamId);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return name;
    }

    public  ObservableList<Game> readGames() throws DaoException{
        String query = "select id, team_1, team_2, date, result from games";
        List<Game> gamesList=new ArrayList<>();
        try {
            rs = statement.executeQuery(query);
            Game game;
            while (rs.next()) {
                int gameId=rs.getInt(1);
                int id_1 = rs.getInt(2);
                int id_2 = rs.getInt(3);
                java.sql.Date sqlDate = rs.getDate(4);
                java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                int result = rs.getInt(5);
                System.out.printf("team1: %s, team2: %s, date: %s, result %s %n", teamNameById(id_1),teamNameById( id_2), utilDate, result);
                game=new Game(teamNameById(id_1),teamNameById( id_2), utilDate, Game.Result.values()[result]);
                game.setId(gameId);
                if(GameValidator.validate(game)) {
                    System.out.println("Valid");
                    gamesList.add(game);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new DaoException();
        }
        return  FXCollections.observableArrayList(gamesList);
    }

    public ObservableList<Team> readTeams() throws DaoException{
        String query = "select name, coach from teams";
        List<Team> teamsList=new ArrayList<>();
        try {
            rs = statement.executeQuery(query);
            while (rs.next()) {
                String name=rs.getString(1);
                String coach=rs.getString(2);
                System.out.printf( name +" - "+ coach+"\n");

                    teamsList.add(new Team(name, coach));
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Dao: readTeams error!");
            throw new DaoException();
        }
        return  FXCollections.observableArrayList(teamsList);
    }

    public void deleteGame(Game game){
        StringBuilder sb=new StringBuilder("delete from games where id="+game.getId()+";");
        try{
            Statement stmt = connection.createStatement();
            //Executing the query
          statement.execute(sb.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteTeam(Team team){
        StringBuilder sb=new StringBuilder("delete from teams where id="+getTeamIdByName(team.getName())+";");
        try{
            Statement stmt = connection.createStatement();
            statement.execute(sb.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}

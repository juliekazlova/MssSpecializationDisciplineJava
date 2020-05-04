package by.bsu.mss.kazlova.ds1.lab1.controller;

import by.bsu.mss.kazlova.ds1.lab1.dao.Dao;
import by.bsu.mss.kazlova.ds1.lab1.dao.DaoException;
import by.bsu.mss.kazlova.ds1.lab1.entity.Game;
import by.bsu.mss.kazlova.ds1.lab1.entity.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class Controller  {
    public static Controller controller=new Controller();
    private static final String url = "jdbc:mysql://localhost:3306/bsu_mss_lab1";
    private static final String user = "root";
    private static final String password = "12345678";
    Dao dao=Dao.getInstance();

    private Controller(){    }

    public Controller getInstance(){
        return controller;
    }

    public void connectToDB() throws DaoException{
        //in case of time_zone excpn "set global time_zone = '-3:00';" in mysql
        Dao dao=Dao.getInstance();
        try {
            dao.connect(url, user, password);
        } catch (DaoException e){
            e.printStackTrace();
            throw  new DaoException();
        }
    }

    public ObservableList<Game> getGamesList() throws DaoException{
        return dao.readGames();
    }
    public ObservableList<Team> getTeamsList() throws DaoException{
        return dao.readTeams();
    }

    public ObservableList<String> getTeamsNamesList() throws DaoException{
        List<String> list =new ArrayList<>();

        for(Team cur: getTeamsList()){
            list.add(cur.getName());
        }
        return FXCollections.observableArrayList(list);
    }

}


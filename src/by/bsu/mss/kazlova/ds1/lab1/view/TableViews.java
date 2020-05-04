package by.bsu.mss.kazlova.ds1.lab1.view;

import by.bsu.mss.kazlova.ds1.lab1.controller.Controller;
import by.bsu.mss.kazlova.ds1.lab1.dao.Dao;
import by.bsu.mss.kazlova.ds1.lab1.dao.DaoException;
import by.bsu.mss.kazlova.ds1.lab1.dao.Statistics;
import by.bsu.mss.kazlova.ds1.lab1.entity.Game;
import by.bsu.mss.kazlova.ds1.lab1.entity.Team;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Date;

public class TableViews extends Application {
    Button teamButton=new Button("Delete team");
    Button gamesButton=new Button("Delete game");
    @Override
    public void start(Stage stage) throws Exception {
        Controller controller=Controller.controller;
        controller.connectToDB();
        TableView<Team> teamTable = new TableView <Team>();

        TableView.TableViewSelectionModel<Team> teamTableSelectionModel = teamTable.getSelectionModel();
        teamTableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
        TableColumn<Team, String> teamNameCol = new TableColumn<Team, String>("Team Name");
        teamNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Team, String> teamCoachCol = new TableColumn<Team, String>("Coach");
        teamCoachCol.setCellValueFactory(new PropertyValueFactory<>("coach"));
        teamTable.getColumns().addAll(teamNameCol, teamCoachCol);

        TableView<Game> gameTable = new TableView <>();
        TableView.TableViewSelectionModel<Game> gameTableSelectionModel = gameTable.getSelectionModel();
        gameTableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
        TableColumn<Game, String> gameTeam1Col = new TableColumn<Game, String>("Team 1 Name");
        gameTeam1Col.setCellValueFactory(new PropertyValueFactory<>("team1"));
        TableColumn<Game, String> gameTeam2Col = new TableColumn<Game, String>("Team 2 Name");
        gameTeam2Col.setCellValueFactory(new PropertyValueFactory<>("team2"));
        TableColumn<Game, Date> gameDateCol = new TableColumn<Game, Date>("Date");
        gameDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        gameDateCol.setPrefWidth(200);
        TableColumn<Game, Game.Result> gameResultCol = new TableColumn<>("Result for team 1");
        gameResultCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        gameResultCol.setPrefWidth(130);
        gameTable.getColumns().addAll(gameTeam1Col, gameTeam2Col, gameDateCol, gameResultCol);

        BorderPane root = new BorderPane();
        //root.setPadding(new Insets(5));
        TabPane tabPane = new TabPane();
        Tab teamsTab = new Tab();
        teamsTab.setText("Teams");
        BorderPane teamsPane=new BorderPane();
        teamsPane.setCenter(teamTable);
        teamsPane.setBottom(teamButton);
        teamsTab.setContent(teamsPane);
        Tab gamesTab = new Tab();
        gamesTab.setText("Games");
        BorderPane gamesPane=new BorderPane();
        gamesPane.setCenter(gameTable);
        gamesPane.setBottom(gamesButton);
        gamesTab.setContent(gamesPane);



        Tab addNewGameTab=new Tab("New Game");
        VBox newGameVBox=new VBox();
        HBox teamChoosingHBox=new HBox();
        ComboBox<String> team1CB=new ComboBox<>();
        team1CB.setPromptText("team 1");
        team1CB.setItems(controller.getTeamsNamesList());
        ComboBox<String> team2CB=new ComboBox<>();
        team2CB.setPromptText("team 2");
        team2CB.setItems(controller.getTeamsNamesList());
        teamChoosingHBox.getChildren().addAll(team1CB, team2CB);
        HBox dateInputForNewGameHBox=new HBox();
        TextField yearTF=new TextField("year");
        TextField monthTF=new TextField("month");
        TextField dayTF=new TextField("day");
        dateInputForNewGameHBox.getChildren().addAll(yearTF, monthTF, dayTF);
        ComboBox<Game.Result> resultCB=new ComboBox<>();
        resultCB.setPromptText("result");
        resultCB.setItems( FXCollections.observableArrayList(
                Game.Result.LOSE,
            Game.Result.DRAW,
            Game.Result.WIN));
        Button addNewGameButton=new Button("Add game");

        newGameVBox.getChildren().addAll(teamChoosingHBox, dateInputForNewGameHBox, resultCB, addNewGameButton);
        newGameVBox.setAlignment(Pos.TOP_CENTER);
        teamChoosingHBox.setAlignment(Pos.CENTER);
        dateInputForNewGameHBox.setAlignment(Pos.CENTER);
        addNewGameTab.setContent(newGameVBox);

        Tab addNewTeamTab=new Tab("Add team");
        VBox addNewTeamVBox=new VBox();
        HBox nameHBox=new HBox();
        Label teamNameLabel=new Label("Name: ");
        TextField teamName=new TextField("team name");
        nameHBox.getChildren().addAll(teamNameLabel, teamName);
        nameHBox.setAlignment(Pos.CENTER);
        HBox coachHBox=new HBox();
        Label teamCoachLabel=new Label("Coach: ");
        TextField coachName=new TextField("team coach");
        coachHBox.setAlignment(Pos.CENTER);
        coachHBox.getChildren().addAll(teamCoachLabel, coachName);
        Button addNewTeamButton=new Button("add");
        addNewTeamVBox.getChildren().addAll(nameHBox, coachHBox, addNewTeamButton);
        addNewTeamVBox.setAlignment(Pos.TOP_CENTER);
        addNewTeamTab.setContent(addNewTeamVBox);


        Tab statisticsTab=new Tab("Statistics");
        VBox statisticsVBox=new VBox();
        ComboBox<String> teamStatisticsCB=new ComboBox<>();
        teamStatisticsCB.setPromptText("Chose team");
        teamStatisticsCB.setItems(controller.getTeamsNamesList());

        HBox dateFromStatisticsHBox=new HBox();
        Label dateFromStatisticsLabel=new Label("From:");
        TextField yearTFStat=new TextField("year");
        TextField monthTFStat=new TextField("month");
        TextField dayTFStat=new TextField("day");
        dateFromStatisticsHBox.getChildren().addAll(dateFromStatisticsLabel, yearTFStat, monthTFStat, dayTFStat);


        HBox dateToStatisticsHBox=new HBox();
        Label dateToStatisticsLabel=new Label("To:");
        TextField yearTFStatTo=new TextField("year");
        TextField monthTFStatTo=new TextField("month");
        TextField dayTFStatTo=new TextField("day");
        dateToStatisticsHBox.getChildren().addAll(dateToStatisticsLabel, yearTFStatTo, monthTFStatTo, dayTFStatTo);

        Button getStatisticsButton=new Button("Get statistics");
        dateFromStatisticsHBox.setAlignment(Pos.CENTER);
        dateToStatisticsHBox.setAlignment(Pos.CENTER);
        statisticsVBox.getChildren().addAll(teamStatisticsCB, dateFromStatisticsHBox, dateToStatisticsHBox, getStatisticsButton);
        statisticsVBox.setAlignment(Pos.TOP_CENTER);
        statisticsTab.setContent(statisticsVBox);


        tabPane.getTabs().addAll(teamsTab, gamesTab, addNewTeamTab, addNewGameTab, statisticsTab);

        root.setCenter(tabPane);

        gamesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Game selectedGame=gameTableSelectionModel.getSelectedItem();
                Dao.getInstance().deleteGame(selectedGame);
                try {
                    gameTable.setItems(Controller.controller.getGamesList());
                } catch (DaoException e){
                    e.printStackTrace();
                }


            }
        });

        teamButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Team selectedTeam=teamTableSelectionModel.getSelectedItem();
                Dao.getInstance().deleteTeam(selectedTeam);
                try {
                    teamTable.setItems(Controller.controller.getTeamsList());
                    gameTable.setItems(Controller.controller.getGamesList());
                    team1CB.setItems(controller.getTeamsNamesList());
                    team2CB.setItems(controller.getTeamsNamesList());
                    teamStatisticsCB.setItems(controller.getTeamsNamesList());

                } catch (DaoException e){
                    e.printStackTrace();
                }
            }
        });

        addNewGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Game game = new Game(team1CB.getValue(),
                            team2CB.getValue(),
                            new Date(Integer.parseInt(yearTF.getText()),
                                    Integer.parseInt(monthTF.getText()),
                                    Integer.parseInt(dayTF.getText())),
                            resultCB.getValue());

                    try {
                        Dao.getInstance().writeGame(game);
                        gameTable.setItems(controller.getGamesList());
                    } catch (DaoException e) {
                        e.printStackTrace();
                        throw new NumberFormatException();
                    }

                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("New game error!");
                    alert.setHeaderText("Please, check input information!");
                    alert.showAndWait();
                }

            }
        });

        addNewTeamButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                           if(Dao.getInstance().teamExists(teamName.getText())||
                                   teamName.getText().equals("team name")||
                                   coachName.getText().equals("team coach")) {
                               System.out.println("Wrong data for new team - "+teamName.getText()+" - "+coachName.getText());
                               throw new NumberFormatException();
                           }
                           Team team=new Team(teamName.getText(), coachName.getText());
                           System.out.println(team.toString());

                    try {
                        Dao.getInstance().writeTeam(team);
                        teamTable.setItems(controller.getTeamsList());
                        team1CB.setItems(controller.getTeamsNamesList());
                        team2CB.setItems(controller.getTeamsNamesList());
                        teamStatisticsCB.setItems(controller.getTeamsNamesList());

                    } catch (DaoException e) {
                        e.printStackTrace();
                        throw new NumberFormatException();
                    }

                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("New team error!");
                    alert.setHeaderText("Please, check input information!");
                    alert.showAndWait();
                }
            }
        });

        getStatisticsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String team=teamStatisticsCB.getValue();
                Date from=null;
                Date to=null;

                try {
                    from = new Date(
                            (yearTFStat.getText().equals("year")) ? -1900 :
                                    Integer.parseInt(yearTFStat.getText()) - 1900,

                            (monthTFStat.equals("month")) ? 0 :
                                    Integer.parseInt(monthTFStat.getText()) - 1,

                            (dayTFStat.equals("day")) ? 1 :
                                    Integer.parseInt(dayTFStat.getText()));

                    to = new Date(Integer.parseInt(yearTFStatTo.getText()) - 1900,
                            Integer.parseInt(monthTFStatTo.getText()) - 1,
                            Integer.parseInt(dayTFStatTo.getText()));
                    int[] statistics= Statistics.count(from, to, team);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Statistics");
                    alert.setHeaderText("Results for "+team+":");
                    alert.setContentText(" Win number: "+statistics[0]+
                            "\n Draw number: "+statistics[1]+
                            "\n Lose number: "+statistics[2]);

                    alert.showAndWait();
                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Statistics error!");
                    alert.setHeaderText("Please, check input information!");
                    alert.showAndWait();
                }

            }
        });


        stage.setTitle("Teams");

        Scene scene = new Scene(root, 500, 300);
        stage.setScene(scene);
        stage.show();
        gameTable.setItems(controller.getGamesList());
        teamTable.setItems(Controller.controller.getTeamsList());


    }
    public static void main(String[] args) {
        launch(args);
    }


}

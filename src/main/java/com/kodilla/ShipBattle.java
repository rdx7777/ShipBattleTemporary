package com.kodilla;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ShipBattle extends Application {

    private Image imageBack = new Image("ocean_ready_with_grid.jpg");

    public static void main(String[] args) {
        launch(args);
    }

    public static void example(int type, int column, int row) {
        String typeString = "";
        if (type == 0) {
            typeString = "EMPTY";
        }
        if (type == 1) {
            typeString = "MAST";
        }
        if (type == -1) {
            typeString = "HIT";
        }
        System.out.println("Clicked on " + typeString + " field in column " + column + " and in row " + row);
    }

    // creating top labels
    GameLabel userInterfaceLabel = new GameLabel(378, 54, "user-interface_background.jpg");
    GameLabel playerScoreLabel = new GameLabel(54, 40, "score_background.jpg");
    GameLabel computerScoreLabel = new GameLabel(54, 40, "score_background.jpg");
    GameLabel playerNameLabel = new GameLabel(135, 40, "name_background.jpg");
    GameLabel computerNameLabel = new GameLabel(135, 40, "name_background.jpg");

    // creating bottom buttons
    GameButton newGameButton = new GameButton(108, 40, "New game");
    GameButton setShipButton = new GameButton(108, 40, "Set ship");
    GameButton startGameButton = new GameButton(108, 40, "Start");
    GameButton helpButton = new GameButton(108, 40, "Help");
    GameButton exitGameButton = new GameButton(108, 40, "Exit");
    GameButton areYouSureExitGameButton = new GameButton(135, 40, "Are you sure?");
    GameButton cancelExitGameButton = new GameButton(135, 40, "Cancel");

    // creating objects used in the game
    Ship ship_4_1 = new Ship("Ship 4-masts (1)", new ArrayList<>());
    Ship ship_3_1 = new Ship("Ship 3-masts (1)", new ArrayList<>());
    Ship ship_3_2 = new Ship("Ship 3-masts (2)", new ArrayList<>());
    Ship ship_2_1 = new Ship("Ship 2-masts (1)", new ArrayList<>());
    Ship ship_2_2 = new Ship("Ship 2-masts (2)", new ArrayList<>());
    Ship ship_2_3 = new Ship("Ship 2-masts (3)", new ArrayList<>());
    Ship ship_1_1 = new Ship("Ship 1-masts (1)", new ArrayList<>());
    Ship ship_1_2 = new Ship("Ship 1-masts (2)", new ArrayList<>());
    Ship ship_1_3 = new Ship("Ship 1-masts (3)", new ArrayList<>());
    Ship ship_1_4 = new Ship("Ship 1-masts (4)", new ArrayList<>());

    // creating ships container
    ShipsContainer shipsContainer = new ShipsContainer();

    @Override
    public void start(Stage primaryStage) throws Exception {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true,
                true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageBack, BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        Insets insets = new Insets(28,81,40, 81);
        grid.setPadding(insets);
        grid.setBackground(background);

        // setting size of columns in the grid
        int columnSizes[] = {27, 27, 27, 27, 27, 27, 27, 27, 27, 27,
                81, 27,
                27, 27, 27, 27, 27, 27, 27, 27, 27, 27};
        for(int i = 0; i<columnSizes.length; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(columnSizes[i])); }

        // setting size of rows in the grid
        int rowSizes[] = {54, 14, 40, 14, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 40, 40};
        for(int i = 0; i<rowSizes.length; i++) {
            grid.getRowConstraints().add(new RowConstraints(rowSizes[i])); }

        // setting top labels and adding to the grid
        userInterfaceLabel.setAlignment(Pos.CENTER);
        userInterfaceLabel.setPadding(new Insets(5, 0, 0, 5));
        userInterfaceLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        userInterfaceLabel.setText("Hello! You are welcome!");

        playerScoreLabel.setAlignment(Pos.CENTER);
        playerScoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        playerScoreLabel.setText("0");

        computerScoreLabel.setAlignment(Pos.CENTER);
        computerScoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        computerScoreLabel.setText("0");

        playerNameLabel.setAlignment(Pos.CENTER);
        playerNameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        playerNameLabel.setText("Player");

        computerNameLabel.setAlignment(Pos.CENTER);
        computerNameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        computerNameLabel.setText("Computer");

        grid.add(playerScoreLabel, 8, 2);
        grid.add(computerScoreLabel, 12, 2);
        grid.add(playerNameLabel, 0, 2);
        grid.add(computerNameLabel, 17, 2);
        grid.add(userInterfaceLabel, 5, 0);

        // preparing and adding set of ships to ships container
        ArrayList<Ship> shipCollection = new ArrayList<>(Arrays.asList(ship_4_1, ship_3_1, ship_3_2, ship_2_1, ship_2_2, ship_2_3,
                ship_1_1, ship_1_2, ship_1_3, ship_1_4));

        shipsContainer.addShipsToContainer(shipCollection);

        // ********** TEMPORARY ONLY **********
        for(Map.Entry entry : shipsContainer.getSetOfShips().entrySet()) { // temporary check of creating and using ships container
            System.out.println(entry.getKey());
        }
        // ************************************

        // creating main objects
        PlayerBoard playerBoard = new PlayerBoard(grid, shipsContainer);
        ComputerBoard computerBoard = new ComputerBoard(grid);

        // setting buttons to handle "exit" choice
        areYouSureExitGameButton.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        areYouSureExitGameButton.setTextFill(Color.RED);
        cancelExitGameButton.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        // setting actions for "New game" button
        newGameButton.setOnAction(event -> {
            System.out.println("New game starts here.");
            userInterfaceLabel.setAlignment(Pos.TOP_LEFT);
            userInterfaceLabel.setPadding(new Insets(5, 0, 0, 5));
            userInterfaceLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 12));
            userInterfaceLabel.setText("Now build your ships. \n" +
                    "FIRST build one 4-masts ship, then two 3-masts ships, \n" +
                    "then three 2-masts ships and finally four 1-mast ships.");
//            setShipButton.setDisable(true);
            playerBoard.createPlayerBoard();
            playerBoard.setEmptyPlayerBoard();
            playerBoard.setShipMastOnControlSquareField();
            setShipButton.setOnAction(event1 -> { // TEMPORARY ONLY !!!!!!!!!!!!!!!!!!!!!!!!!
                // NEED setting ship object etc.
                playerBoard.setFirstMastOfShipChecker(true);
            });
            computerBoard.createComputerBoard();
            computerBoard.setEmptyComputerBoard();
            // nie trzeba blokować planszy komputera, ponieważ obiekty na niej utworzone
            // już po wywołaniu metody obsługującej eventy dla ControlSquare nie są objęte tą obsługą
            newGameButton.setDisable(true);

            helpButton.setOnAction(event1 -> {
                System.out.println("Help when player has started new game.");
                userInterfaceLabel.setAlignment(Pos.TOP_LEFT);
                userInterfaceLabel.setPadding(new Insets(5, 0, 0, 5));
                userInterfaceLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 12));
                userInterfaceLabel.setText("To build your ship click on any field on Player board.\n" +
                        "When ship is built, press \"Set ship\" button to accept it.\n" +
                        "When all ships are built, press \"Start\" to start your game.");
                helpButton.setDisable(true);
            });

        });

        // setting actions for "Set ship" button
        setShipButton.setOnAction(event -> System.out.println("Player accepts their ship built on the board"));
        setShipButton.setDisable(true);

        // setting actions for "Start game" button
        startGameButton.setOnAction(event -> System.out.println("Starting game"));
        startGameButton.setDisable(true);

        // setting actions for "Help" button
        helpButton.setOnAction(event -> {
            System.out.println("Help for player wanted to start new game.");
            userInterfaceLabel.setAlignment(Pos.CENTER_LEFT);
            userInterfaceLabel.setPadding(new Insets(7, 0, 8, 5));
            userInterfaceLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 12));
            userInterfaceLabel.setText("Press \"New game\" button to start a new game.\n" +
                    "Press \"Exit\" button to end the game.");
        });

        // setting actions for "Exit" button
        exitGameButton.setOnAction(event -> {
            System.out.println("Exit game");
            grid.add(areYouSureExitGameButton, 5, 8);
            grid.add(cancelExitGameButton, 12, 8);
        });

        // setting actions for "Are you sure?" button
        areYouSureExitGameButton.setOnAction(event -> {
            System.out.println("Player confirmed their decision about exit game.");
            Platform.exit();
        });

        // setting actions for "Cancel" button
        cancelExitGameButton.setOnAction(event -> {
            System.out.println("Player's changed their mind about exit game.");
            grid.getChildren().remove(areYouSureExitGameButton);
            grid.getChildren().remove(cancelExitGameButton);
        });

        // adding bottom buttons to grid
        grid.add(newGameButton, 0, 15);
        grid.add(setShipButton, 5, 15);
        grid.add(startGameButton, 10, 15);
        grid.add(helpButton, 13, 15);
        grid.add(exitGameButton, 18, 15);

        Scene scene = new Scene(grid, 810, 540, Color.BLACK);

        primaryStage.setTitle("Ship Battle");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

    }

}


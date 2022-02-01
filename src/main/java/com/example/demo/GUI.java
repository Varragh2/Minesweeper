package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    static boolean firstClick;
    static HBox board;
    static Scene scene;
    /**
     * isChord == true: when one mouse is down and another mouse is pressed or both buttons are pressed
     * is Chord == false:
     */
    static boolean isChord = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

        board = createBoard(16, 30, 99);

        Button reset = new Button("reset");
        HBox menu = new HBox(reset);
        menu.setAlignment(Pos.CENTER);
        VBox ui = new VBox(menu, board);
        reset.setOnMouseClicked( event -> {
            //Does not work correctly!!!!
            ui.getChildren().remove(board);
            board = createBoard(16, 30, 99);
            ui.getChildren().add(board);
        });

        scene = new Scene(ui);

        scene.getStylesheets().add(String.valueOf(GUI.class.getResource("style.css")));

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public HBox createBoard(int height, int width, int mines){
        //board is the HBox that contains all the tiles
        firstClick = true;
        HBox board = new HBox();
        board.setPadding(new Insets(5));
        for (int x = 0; x < width; x++) {
            VBox row = new VBox();
            for (int y = 0; y < height; y++) {
                Tile tile = new Tile();

                //Testing code
                tile.numTiles = new int[]{x, y};

                transformTile(tile);
                putNearbyTiles(tile, board, row, x, y);
                row.getChildren().add(tile);
            }
            board.getChildren().add(row);
        }
        scatterMines(board, mines);
        return board;
    }

    public void transformTile(Tile tile){
        //method for initializing tiles
        tile.setMinSize(50, 50);
    }
    private void putNearbyTiles(Tile tile, HBox board, VBox row, int x, int y) {
        if (y > 0){
            //Only if y - 1 > 0
            //So get() doesn't throw an IndexOutOfBoundsException
            tile.putNearbyTile("NORTH", (Tile)row.getChildren().get(y - 1));
        }

        if (x == 0){
            //If there is no previous row, return
            return;
        }
        //get previous row
        VBox prevRow = (VBox) board.getChildren().get(x - 1);
        //System.out.println("prevRow: " + prevRow.getChildren().size());

        if (prevRow.getChildren().size() > 2 && y > 0)
        {
            //y - 1 needs to be at least 0
            //There must be at least two items in prevRow to get(y - 1)
            tile.putNearbyTile("NORTH_EAST", (Tile)prevRow.getChildren().get(y - 1));
        }
        if (y > -1) {
            //y has to be 0 or greater
            tile.putNearbyTile("EAST", (Tile) prevRow.getChildren().get(y));
        }

        if (prevRow.getChildren().size() - 1 > y){
            //Only if prevRow has enough children
            tile.putNearbyTile("SOUTH_EAST", (Tile)prevRow.getChildren().get(y + 1));
        }

    }
/**
    void handleEvents(Tile tile) {

        /**
         * Handles mouse events for all Tiles
         *
        tile.setOnMousePressed(
                (event) -> {
                    if (event.getButton() == MouseButton.SECONDARY){
                        tile.setText("*");
                    }

                }
        );

        tile.setOnMouseClicked(
            (event) -> {
                if (event.getButton() == MouseButton.PRIMARY){
                    //This line is the same as a for each loop with a not null if statement inside
                    //tile.getNearbyTiles().values().stream().filter(Objects::nonNull).forEach(a -> a.setText("X"));
                    if (firstClick){
                        tile.clicked(firstClick);
                        firstClick = false;
                    }

                    tile.setDisable(true);
                    tile.setText("o");
                }
            }
        );

//        tile.setOnMouseReleased(
//                event -> {
//
//                    tile.getNearbyTiles().values().stream().filter(Objects::nonNull).forEach(a -> a.setText("O"));
//                }
//        );
    }
 */

    private void scatterMines(HBox board, int mines) {
        int width = board.getChildren().size();
        VBox temp = (VBox)board.getChildren().get(0);
        int height = temp.getChildren().size();
        for (int i = 0; i < mines; i++) {
            //Generate two random numbers, for the x and y positions of mine
            //The while loop ensures that the code does not place mines on tiles that are already mines
            while (true) {
                Random rand = new Random();
                int y = rand.nextInt(height);
                int x = rand.nextInt(width);
                VBox vBox = (VBox) board.getChildren().get(x);
                Tile tile = (Tile) vBox.getChildren().get(y);
                if (tile.number != -1) {
                    //Only updateMine if the tile is not already mine
                    tile.updateMine(-1);
                    break;
                }
            }
        }
    }

    static int minesMoved = 0;
    public static void moveMine(Tile tile) {

        System.out.println("Mines moved: " + minesMoved);
        if (tile.number == -1){
            tile.updateMine(1);
        }

        if (minesMoved == 0){
            minesMoved++;
            //only on the first iteration if the first tile clicked is mine override it's number to 0
            if (tile.number == -1){
                tile.number = 0;
            }
            AtomicInteger numNearbyMines = new AtomicInteger();
            tile.getNearbyMines().values().forEach( tile1 -> {
                numNearbyMines.getAndIncrement();
                //tile1.number = tile1.getNearbyMines().size();
                moveMine(tile1);
            });
            if (numNearbyMines.get() == 0 && tile.number != -1){
                return;
            }
        }



        //Generate two random numbers, for the x and y positions of mine
        //The while loop ensures that the code does not place mines on tiles that are already mines
        while (true) {
            Random rand = new Random();
            VBox temp = (VBox) board.getChildren().get(0);
            int y = rand.nextInt(temp.getChildren().size());
            int x = rand.nextInt(board.getChildren().size());
            VBox vBox = (VBox) board.getChildren().get(x);
            Tile mine = (Tile) vBox.getChildren().get(y);
            if (mine.number != -1 && !mine.isShown) {
                //Only updateMine if the tile is not already mine or not already shown
                mine.updateMine(-1);
                testNumMines();
                return;
            }
        }
    }

    public static void testNumMines(){
        //Prints the number of mines on the board, will be useful later
        //Is currently used for testing
        VBox temp = (VBox) board.getChildren().get(0);
        int numMines = 0;
        for (int i = 0; i < board.getChildren().size(); i++) {
            VBox row = (VBox) board.getChildren().get(i);
            for (int j = 0; j < temp.getChildren().size(); j++) {
                Tile integer = (Tile) row.getChildren().get(j);
                if (integer.number == -1){
                    numMines += 1;
                }
            }
        }
        System.out.println("Total mines: " + numMines);
    }

    public static ArrayList<Tile> getMines(HBox board){
        ArrayList<Tile> mines = new ArrayList<>();
        VBox temp = (VBox) board.getChildren().get(0);
        for (int x = 0; x < board.getChildren().size(); x++) {
            VBox row = (VBox) board.getChildren().get(x);
            for (int y = 0; y < temp.getChildren().size(); y++) {
                Tile tile = (Tile) row.getChildren().get(y);
                if (tile.number == -1){
                    mines.add(tile);
                }
            }
        }
        return mines;
    }


    public static void gameOver(Tile tile) {
        ArrayList<Tile> mines = getMines(board);
        mines.forEach( mine -> {
            mine.setId("mine");
            mine.setText("X");
        });
        tile.setId("first-mine");
        tile.setText("(X)");
        System.out.println("You died");
    }









}




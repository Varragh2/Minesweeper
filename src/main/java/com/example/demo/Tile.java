package com.example.demo;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Tile extends Button {

    //neabyTiles stores all 8 adjacent Tiles and their direction
    private HashMap<String, Tile> nearbyTiles = new HashMap<>();
    public static final String[] DIRECTIONS = {"NORTH", "NORTH_EAST", "EAST", "SOUTH_EAST", "SOUTH", "SOUTH_WEST", "WEST", "NORTH_WEST"};


    /**
     * number == -1: this tile is mine
     * number == 0: this tile is empty, there are no mines adjacent
     * number >=0 && <= 8: there are number mines adjacent to this tile
     */
    int number = 0;
    boolean isFlag = false;
    boolean isShown = false;


    public static final EventType<MouseDragEvent> mouseChordEvent = new EventType<>(MouseDragEvent.ANY, "CHORD_PRESSED");
    public static final EventType<MouseEvent> mouseChordReleased = new EventType<>(MouseEvent.ANY, "CHORD_RELEASED");

    //for testing
    int[] numTiles;

    public Tile (){
        this.initialize();
    }
    public Tile (String var1){
        super(var1);
        this.initialize();
    }
    public Tile (String var1, Node var2){
        //Every instance of Tile has a NearbyTile instance
        super(var1, var2);
        this.initialize();
    }

    protected void initialize(){
        this.nearbyTiles.put("NORTH", null);
        this.nearbyTiles.put("NORTH_EAST", null);
        this.nearbyTiles.put("EAST", null);
        this.nearbyTiles.put("SOUTH_EAST", null);
        this.nearbyTiles.put("SOUTH", null);
        this.nearbyTiles.put("SOUTH_WEST", null);
        this.nearbyTiles.put("WEST", null);
        this.nearbyTiles.put("NORTH_WEST", null);

        setId("tile");

        Event chordReleased = new Event(mouseChordReleased);

        setOnMousePressed( event -> {
            System.out.println("setOnMousePressed button: " + event.getButton());
            if (event.getButton() == MouseButton.SECONDARY && !event.isPrimaryButtonDown()) {
                //If right click is pressed and left button isn't down, flag
                //and set GUI.isChord to true, which means that right click was just down
                GUI.isChord = true;
                if (!isShown) {
                    setFlag(!isFlag);
                }
                return;
            }
            if (event.isPrimaryButtonDown() && !event.isSecondaryButtonDown()) {
                GUI.isChord = true;
            }

            if (event.isPrimaryButtonDown() && event.isSecondaryButtonDown()) {
                /**
                 * Sudo code for setChordPressed() event
                 * Fire: event should begin when right click down and left click pressed, which should still place a flag; or left click down and right click pressed
                 * Target: should initiate a full drag and send inputs to every tile that is hovered over, showing the nearby chordable tiles and the tile
                 * Released: only if released on a tile that is shown, chord
                 */
                //MouseDragEvent mouseChordPressed = new MouseDragEvent(this, null, mouseChordEvent, event.getX(), event.getY(), event.getScreenX(), event.getScreenY(), event.getButton(), event.getClickCount(), event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown(), event.isPrimaryButtonDown(), event.isMiddleButtonDown(), event.isSecondaryButtonDown(), event.isBackButtonDown(), event.isForwardButtonDown(), event.isSynthesized(), event.isPopupTrigger(), event.getPickResult(), event.getSource());
                //fireEvent(mouseChordPressed);
                System.out.println("setOnMousePressed works when left and right click are down");
                System.out.println(((Tile)event.getSource()).getText());
                //Chording Code:
                //highlight all nearby Tiles that aren't shown or flags and this Tile
                GUI.isChord = true;
                ( (Tile) event.getSource()).getNearbyChordable().values().forEach(tile -> {
                    tile.setId("shown");
                });
            }
        });

        setOnDragDetected( event -> {
            if (event.isPrimaryButtonDown()) {
                ((Tile) event.getSource()).startFullDrag();
            }
            if (event.isSecondaryButtonDown()) {
                ((Tile) event.getSource()).startFullDrag();
            }
            if (event.isPrimaryButtonDown() && event.isSecondaryButtonDown()) {
                ((Tile) event.getSource()).startFullDrag();
            }
        });

        setOnMouseDragEntered( event -> {
            if (event.isPrimaryButtonDown()) {
                ( (Tile) event.getSource()).setId("shown");
            }
            if (event.isPrimaryButtonDown() && event.isSecondaryButtonDown()) {
                System.out.println(( (Tile) event.getSource()).getNearbyChordable().values());
                ( (Tile) event.getSource()).getNearbyChordable().values().forEach( tile -> {
                    tile.setId("shown");
                });
            }
        });
        setOnMouseDragExited( event -> {
            if (event.isPrimaryButtonDown()) {
                ( (Tile) event.getSource()).setId("tile");
            }
            if (event.isPrimaryButtonDown() && event.isSecondaryButtonDown()) {
                ( (Tile) event.getSource()).getNearbyChordable().values().stream().filter( tile -> !tile.isShown).forEach( tile -> {
                    tile.setId("tile");
                });
            }
        });
        /*
        setOnMouseDragReleased( event -> {
            if (event.getButton() == MouseButton.PRIMARY && GUI.isChord) {
                //If I release a left click and right click was just down
                //chord
                //Implement chording
                System.out.println("setOnMousePressed works when left and right click are released");
                GUI.isChord = false;
                int nearbyFlags = getNearbyTiles().values().stream().filter(tile -> tile.isFlag).toList().size();
                System.out.println("Nearby flags: " + nearbyFlags);
                if (this.number == nearbyFlags) {
                    getNearbyChordable().values().forEach(Tile::show);
                }
//                else {
//                    getNearbyChordable().values().forEach( tile -> tile.setId("tile"));
//                }
            }
            if (event.getButton() == MouseButton.PRIMARY && !GUI.isChord) {
                System.out.println("setOnMouseDragReleased input");
                if (this.isFlag) {
                    return;
                }
                if (GUI.firstClick){
                    GUI.firstClick = false;
                    //If it is the first click, move any mines nearby
                    GUI.moveMine(this);
                    //otherwise, show this tile
                }
                this.show();
            }
        });
         */
        setOnMouseReleased( event -> {

            System.out.println("setOnMouseReleased button: " + event.getButton());
            if (event.getButton().equals(MouseButton.PRIMARY) && !event.isSecondaryButtonDown()) {
                //If I release the left button and the right button isn't down or was just down
                //If this isn't the first click show this tile
                if (this.isFlag) {
                    return;
                }
                if (GUI.firstClick){
                    GUI.firstClick = false;
                    //If it is the first click, move any mines nearby
                    GUI.moveMine(this);
                    //otherwise, show this tile
                }
                this.show();
            }
            if (event.getButton() == MouseButton.PRIMARY && event.isSecondaryButtonDown()) {
                //If I release a left click and right click is down chord
                //Implement chording
                System.out.println("setOnMousePressed works when left click is released and right click was pressed or just pressed");
                GUI.isChord = false;
                int nearbyFlags = getNearbyTiles().values().stream().filter(tile -> tile.isFlag).toList().size();
                System.out.println("Nearby flags: " + nearbyFlags);
                if (this.number == nearbyFlags) {
                    getNearbyChordable().values().forEach(Tile::show);
                }
                else {
                    getNearbyChordable().values().forEach( tile -> tile.setId("tile"));
                }
            }
            if (event.getButton() == MouseButton.SECONDARY && event.isPrimaryButtonDown()) {
                //Chord released needs to chord and free primary button from being down
                //If I release a right click and left click is down chord
                //Implement chording
                System.out.println("setOnMousePressed works when left click is released and right click was pressed or just pressed");
                GUI.isChord = false;
                int nearbyFlags = getNearbyTiles().values().stream().filter(tile -> tile.isFlag).toList().size();
                System.out.println("Nearby flags: " + nearbyFlags);
                if (this.number == nearbyFlags) {
                    getNearbyChordable().values().forEach(Tile::show);
                }
                else {
                    getNearbyChordable().values().forEach( tile -> tile.setId("tile"));
                }
            }
        });
    }

    public Tile getNearbyTile(String str) {
        return nearbyTiles.get(str);
    }
    public HashMap<String, Tile> getNearbyMines() {
        HashMap<String, Tile> result = new HashMap<>();
        for (Map.Entry<String, Tile> tile : getNearbyTiles().entrySet()){
            if (tile.getValue().number == -1){
                result.put(tile.getKey(), tile.getValue());
            }
        }
        return result;
    }
    public  HashMap<String, Tile> getNearbyChordable() {
        //Return all nearby tiles that can be chorded including this tile
        HashMap<String, Tile> result = new HashMap<>();
        if (!isFlag && !isShown) {
            result.put("THIS", this);
        }
        for (Map.Entry<String, Tile> tile : nearbyTiles.entrySet()) {
            if (tile.getValue() == null) {
                continue;
            }
            if (tile.getValue().isShown) {
                continue;
            }
            if(!tile.getValue().isFlag) {
                result.put(tile.getKey(), tile.getValue());
            }
        }
        return result;

    }
    public HashMap<String, Tile> getNearbyTiles() {
        //Return all non null nearby tiles
        HashMap<String, Tile> result = new HashMap<>();
        for (Map.Entry<String, Tile> tile : nearbyTiles.entrySet()){
            if (tile.getValue() != null){
                result.put(tile.getKey(), tile.getValue());
            }
        }
        return result;
    }

    public void setFlag(boolean flag) {
        //If true set this tile to a flag otherwise reset it to a tile
        if (flag){
            setText("*");
            isFlag = true;
            setId("flag");
        }
        else{
            setText("");
            isFlag = false;
            setId("tile");
        }
    }

    public void putNearbyTile(String str, Tile tile) {
        this.nearbyTiles.put(str, tile);

        if (tile.getNearbyTile(opposite(str)) == null){
            tile.putNearbyTile(opposite(str), this);
            //System.out.println("putNearbyTile: " + tile.getNearbyTile(opposite(str)).numTiles[0]+ " , " + tile.getNearbyTile(opposite(str)).numTiles[1]);
        }
    }

    private String opposite(String str) {
        //Returns the opposite of the inputted direction
        return switch (str) {
            case "NORTH" -> "SOUTH";
            case "NORTH_EAST" -> "SOUTH_WEST";
            case "EAST" -> "WEST";
            case "SOUTH_EAST" -> "NORTH_WEST";
            case "SOUTH" -> "NORTH";
            case "SOUTH_WEST" -> "NORTH_EAST";
            case "WEST" -> "EAST";
            case "NORTH_WEST" -> "SOUTH_EAST";
            default -> null;
        };
    }

    public void updateMine(int number) {
        if (isShown) {
            throw new RuntimeException("called updateMine on an already shown Tile");
        }
        //If number is -1 then this Tile is mine
        if (number == -1){
            //changes this Tile to a mine and updates all nearby Tiles
            this.number = number;
            for (Tile tile : nearbyTiles.values()){
                if (tile != null && tile.number != -1)
                {
                    tile.number += 1;
                }
            }
        }
        else if (number == 1) {
            //undoes making this Tile a mine and updates all nearby Tiles accordingly
            this.number = getNearbyMines().size();
            setText("");
            getNearbyTiles().values().forEach(tile -> {
                if (tile.number > 0) {
                    tile.number -= 1;
                }
                //This is just so I can see the changes on the board
//                if (tile.number == 0) {
//                    tile.setText("");
//                } else if (tile.number > 0) {
//                    tile.setText(Integer.toString(tile.number));
//                }
            });
        }
    }

    public void show() {
        //Method for showing tile
        //If the tile is already shown there is no need to show it again
        if (isShown){
            return;
        }
        setId("shown");
        isShown = true;
        if (number == -1){
            GUI.gameOver(this);
        }
        else if (number == 0){
            //if tile is empty(number == 0), none of the tiles nearby are mines
            //So it is safe to call show on every nearby tile
            nearbyTiles.values().stream().filter(Objects::nonNull).forEach( tile -> {
                if (tile.number != -1){
                    tile.show();
                }
            });
        }
        else if (number > 0 && number <= 8){
            setText(Integer.toString(number));
            nearbyTiles.values().stream().filter(Objects::nonNull).forEach( tile -> {
                if (tile.number == 0){
                    tile.show();
                }
            });
        }
    }

    }

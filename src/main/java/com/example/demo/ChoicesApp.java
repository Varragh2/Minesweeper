package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class ChoicesApp extends Application {

    private final ChoiceBox<Pair<String,String>> assetClass = new ChoiceBox<>();
    //An example of ChoiceBox
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Adds a new label
        Label label = new Label("Asset Class:");
        assetClass.setPrefWidth(200);
        Button saveButton = new Button("Save");

        HBox hbox = new HBox(
                label,
                assetClass,
                saveButton);
        hbox.setSpacing( 10 );
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.setPadding( new Insets(90) );

        Scene scene = new Scene(hbox);

        initChoice();

        saveButton.setOnMouseEntered(
                //I handled my own event
                (evt) -> {
                    System.out.println("Mouse Entered!");
                }
        );
        saveButton.setOnAction(
                //This is how you handle an event
                //When the button is pressed, print something to the screen
                (evt) -> {
                    System.out.println("saving " + assetClass.getValue() + "\n" + evt.getSource().getClass());
                }
        );

        primaryStage.setTitle("ChoicesApp");
        primaryStage.setScene( scene );
        primaryStage.show();

    }
    private final static Pair<String, String> EMPTY_PAIR = new Pair<>("", "");

    private void initChoice() {

        List<Pair<String,String>> assetClasses = new ArrayList<>();
        assetClasses.add( new Pair("Equipment", "20000"));
        assetClasses.add( new Pair("Furniture", "21000"));
        assetClasses.add( new Pair("Investment", "22000"));
        assetClasses.add( new Pair("Tables", "22900"));

        assetClass.setConverter( new StringConverter<Pair<String,String>>() {
            @Override
            public String toString(Pair<String, String> pair) {
                return pair.getKey();
            }

            @Override
            public Pair<String, String> fromString(String string) {
                return null;
            }
        });

        assetClass.getItems().add( EMPTY_PAIR );
        assetClass.getItems().addAll( assetClasses );
        assetClass.setValue( EMPTY_PAIR );

    }

    }
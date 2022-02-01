package com.example.demo;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.skin.LabelSkin;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TransformApp extends Application {

    private Parent createContent() {
        Rectangle box = new Rectangle(100, 50, Color.BLUE);

        transform(box);

        return new Pane(box);
    }

    private void transform(Rectangle box) {
        box.setTranslateX(100);
        box.setTranslateY(200);

        box.setScaleX(1.5);
        box.setScaleY(1.5);

        box.setRotate(30);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //stage.setScene(new Scene(createContent(), 500, 300, Color.BLUEVIOLET));
        Label a = new Label("HI");
        Label c = new Label("Hello");
        c.setMinSize(20, 20);
        LabelSkin b = new LabelSkin(c);

        //a.setSkin(b);

        VBox vbox = new VBox(a);
        stage.setScene(new Scene(vbox));
        stage.show();
        c.setSkin(a.getSkin());
        vbox.getChildren().add(c);

        System.out.println(c);
        System.out.println(a.getSkin());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Test extends Label {


}


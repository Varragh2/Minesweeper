package com.example.demo;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
        Function<String, String> f = String::toUpperCase;
        Consumer<List<String> > c = s -> s.forEach( a -> System.out.println(a));

        HashMap<String, Consumer<String> > table = new HashMap<>();
        table.put("yes", s -> System.out.println(s));
        table.get("yes").accept("hi");

//        System.out.println(f.apply("what to -"));
//
//        List<String> list = new ArrayList<>();
//        list.add("table");
//        list.add("what");
//        c.accept(list);
//        System.out.println("Which Greek god is the goddess of corn, grain and harvest? Demeter(A) Dionysus(B) Poseidon(C) Pan(D)");
    }

    public static void main(String[] args) {
        launch();
    }
}
package com.filevault;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.InputStream;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        Button close = new Button("Close");
        StackPane root = new StackPane();
        Scene scene = new Scene(root,600,400, Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("FileVault");
        Image icon = new Image("file:E:\\Intellij Projects\\FileVaultApp\\src\\resources\\fileVaultLogo.png");
        stage.getIcons().add(icon);
        stage.show();

    }
}

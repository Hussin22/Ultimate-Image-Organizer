package com.example.image_oragnizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ImagesApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ImagesApplication.class.getResource("Home_Ui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ultimate Image Organizer");
        stage.getIcons().add(new Image(getClass().getResourceAsStream
                ("/com/example/image_oragnizer/Imgs/Logo.png")));uuu
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
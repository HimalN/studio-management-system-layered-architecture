package lk.ijse.shadowStudio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;

import java.awt.*;

import static java.awt.SystemColor.window;

public class Appinitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/login_form.fxml"));
        Scene scene = new Scene(parent);
        // Load the icon image
        Image icon = new Image(getClass().getResourceAsStream("/icon/windowIcon.png"));
        // Set the taskbar icon
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.show();




    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxlibrary;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Melnikov
 */
public class JavaFxLibrary extends Application {
    private Button button;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        HomeController homeController = loader.getController();
        Scene scene = new Scene(root);
        //Подключаем каскадную таблицу стилей из пакета javafxlibrary
        //scene.getStylesheets().add(getClass().getResource("home.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/javafxlibrary/home.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

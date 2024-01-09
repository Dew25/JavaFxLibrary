/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxlibrary;

import about.AboutController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Melnikov
 */
public class HomeController implements Initializable {
    
    
    @FXML
    private AnchorPane content;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showAboutScene();
        
    }    
    private void showAboutScene(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/about/about.fxml"));
            VBox vbAbout = loader.load();
            AboutController aboutController = loader.getController();
            
            
            content.getChildren().clear();
            content.getChildren().add(vbAbout);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Нет /about/about.fxml", ex);
        }
    }
    
}

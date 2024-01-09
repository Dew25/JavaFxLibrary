/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package about;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class AboutController implements Initializable {
    @FXML
    private VBox vbAbout;
    @FXML
    private Label topLabel;
    @FXML
    private Label buttonLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Scene scene = vbAbout.getScene();
        topLabel.setPrefWidth(scene.getWidth());
        buttonLabel.setPrefWidth(scene.getWidth());
    }    
    
}

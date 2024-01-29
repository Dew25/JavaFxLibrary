/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.profile;

import entity.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javax.persistence.EntityManager;
import tools.PassEncrypt;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class ProfileController implements Initializable {
    @FXML private Label lbInfo;
    @FXML private TextField tfFirstname;
    @FXML private TextField tfLastname;
    @FXML private TextField tfLogin;
    @FXML private TextField tfPassword;
    @FXML private Button btUpdateProfile;
    

    private EntityManager em;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbInfo.setText("");
        tfFirstname.setText(javafxlibrary.JavaFxLibrary.currentUser.getFirstname());
        tfLastname.setText(javafxlibrary.JavaFxLibrary.currentUser.getLastname());
        tfLogin.setText(javafxlibrary.JavaFxLibrary.currentUser.getLogin());
        tfPassword.setText("");
        tfLogin.setDisable(true);
        btUpdateProfile.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               clickUpdateProfile();
            }
        });
        btUpdateProfile.setOnMouseClicked(event -> {
           if (event.getButton() == MouseButton.PRIMARY) {
               clickUpdateProfile();
           }
       });
    }    

    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    public void clickUpdateProfile() {
        User user = em.find(User.class,javafxlibrary.JavaFxLibrary.currentUser.getId());
        user.setFirstname(tfLastname.getText());
        user.setLastname(tfFirstname.getText());
        if(!tfPassword.getText().isEmpty()){
            PassEncrypt pe = new PassEncrypt();
            user.setPassword(pe.getEncryptPassword(tfPassword.getText(),pe.getSalt()));
        }
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
            javafxlibrary.JavaFxLibrary.currentUser=user;
            tfFirstname.setText(javafxlibrary.JavaFxLibrary.currentUser.getFirstname());
            tfLastname.setText(javafxlibrary.JavaFxLibrary.currentUser.getLastname());
            tfLogin.setText(javafxlibrary.JavaFxLibrary.currentUser.getLogin());
            tfPassword.setText("");
            if(lbInfo.getStyleClass().contains("error-text")){
                    lbInfo.getStyleClass().remove("error-text");
                }
                if(!lbInfo.getStyleClass().contains("info-text")){
                    lbInfo.getStyleClass().add("info-text");
                }
            lbInfo.setText("Профиль пользователя изменен");
            lbInfo.getStyleClass().add("info-text");
        } catch (Exception e) {
            if(!lbInfo.getStyleClass().contains("error-text")){
                    lbInfo.getStyleClass().add("error-text");
                }
            if(!lbInfo.getStyleClass().contains("info-text")){
                lbInfo.getStyleClass().remove("info-text");
            }
            lbInfo.setText("Профиль изменить не удалось");
            lbInfo.getStyleClass().add("error-text");
            
        }
    }
    
}

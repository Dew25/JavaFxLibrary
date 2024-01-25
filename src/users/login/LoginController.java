/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.login;

import entity.User;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javax.persistence.EntityManager;
import tools.PassEncrypt;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class LoginController implements Initializable {

    private EntityManager em;
    @FXML private TextField tfLogin;
    @FXML private PasswordField pfPassword;
    @FXML private Label lbInfo;
    @FXML private Button btLogin;
    
    
    
    
    @FXML void clickLogin(){
        PassEncrypt pe = new PassEncrypt();
        User user = new User();
        List<User> listUsers = em.createQuery("SELECT u FROM User u").getResultList();
        boolean isLogin = false;
        for (int i = 0; i < listUsers.size(); i++) {
            user = listUsers.get(i);
            if(user.getLogin().equals(tfLogin.getText()) && user.getPassword().equals(pe.getEncryptPassword(pfPassword.getText(),pe.getSalt()))){
                javafxlibrary.JavaFxLibrary.currentUser = user;
                isLogin = true;
                break;
            }
        }
        if(!isLogin){
            lbInfo.setText("Нет такого кользователея или неправильный пароль");
        }else{
           lbInfo.setText(String.format("Привет %s %s, добро пожаловть!", user.getFirstname(), user.getLastname()));
        }
        tfLogin.setText("");
        pfPassword.setText("");
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Обработчик события для TextField
        pfPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btLogin.fire();
            }
        });

        // Обработчик события для Button
        btLogin.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               btLogin.fire();
            }
        });

    }    

    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.em;
    }
    public void setInfo(String message){
        this.lbInfo.setText(message);
    }
}

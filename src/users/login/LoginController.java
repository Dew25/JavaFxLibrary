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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class LoginController implements Initializable {

    private EntityManager em;
    @FXML private TextField tfLogin;
    @FXML private TextField tfPassword;
    @FXML private Label lbInfo;
    @FXML private MenuItem miListBooks;
    
    
    
    @FXML void clickLogin(){
        User user = new User();
        List<User> listUsers = em.createQuery("SELECT u FROM User u").getResultList();
        boolean isLogin = false;
        for (int i = 0; i < listUsers.size(); i++) {
            user = listUsers.get(i);
            if(user.getLogin().equals(tfLogin.getText()) && user.getPassword().equals(tfPassword.getText())){
                javafxlibrary.JavaFxLibrary.currentUser = user;
                isLogin = true;
            }
        }
        if(!isLogin){
            lbInfo.setText("Нет такого кользователея или неправильный пароль");
        }else{
           lbInfo.setText(String.format("Привет %s %s, добро пожаловть!", user.getFirstname(), user.getLastname()));
        }
        tfLogin.setText("");
        tfPassword.setText("");
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.em;
    }
    
}

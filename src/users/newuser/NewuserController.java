/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.newuser;

import entity.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class NewuserController implements Initializable {
    private EntityManager em;
    @FXML private Label lbInfo;
    @FXML private TextField tfFirstname;
    @FXML private TextField tfLastname;
    @FXML private TextField tfLogin;
    @FXML private TextField tfPassword;
    @FXML private Button btAddNewUser;
    
    @FXML public void clickAddNewUser(){
        User user = new User();
        user.setFirstname(tfFirstname.getText());
        user.setLastname(tfLastname.getText());
        user.setLogin(tfLogin.getText());
        user.setPassword(tfPassword.getText());
        user.getRoles().add(javafxlibrary.JavaFxLibrary.roles.USER.toString());
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            tfFirstname.setText("");
            tfLastname.setText("");
            tfLogin.setText("");
            tfPassword.setText("");
            lbInfo.setText(String.format("Пользоватеуль %s добавлен", user.getLogin()));
        } catch (Exception e) {
            lbInfo.setText("Пользователя добавить не удалось, возможно такой логин уже зарегистрирован");
            System.out.println("Записать пользовател в базу не удалось: "+e);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import entity.User;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class AdminpaneController implements Initializable {

    private EntityManager em;
    private User selectedUser;
    private Enum selectedRole;
    @FXML private ComboBox cbUsers;
    @FXML private ComboBox cbRoles;
    @FXML private Label lbInfo;
    
    
    public void setCbUsers(){
       
//        List<User> users = getEntityManager().createQuery("SELECT u FROM User u").getResultList();
        cbUsers.setItems(FXCollections.observableArrayList(getEntityManager().createQuery("SELECT u FROM User u").getResultList()));
        cbUsers.setCellFactory(new Callback<ListView<User>,ListCell<User>>(){
            @Override
            public ListCell<User> call(ListView<User> param) {
                return new ListCell<User>() {
                    @Override
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);

                        if (user == null || empty) {
                            setText(null);
                        } else {
                            setText(
                                user.getFirstname()
                                +" "
                                +user.getLastname()
                                +" ("
                                +user.getLogin()
                                +")" 
                                + " - роли " 
                                + Arrays.toString(user.getRoles().toArray())
                            );
                            
                        }
                    }
                };
            }
        });
        cbUsers.setButtonCell(new ListCell<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (user == null || empty) {
                    setText(null);
                } else {
                    setText(user.getFirstname()
                                +" "
                                +user.getLastname()
                                +" ("
                                +user.getLogin()
                                +")" 
                                + " - роли " 
                                + Arrays.toString(user.getRoles().toArray()));
                }
            }
        });
        cbUsers.setOnAction(event->{
            this.selectedUser = (User) cbUsers.getValue();
            //System.out.println(selectedUser.toString());
            lbInfo.setText("");
        });
    }
    public void setCbRoles(){
        cbRoles.setItems(FXCollections.observableArrayList(javafxlibrary.JavaFxLibrary.roles.values()));
        //Настройка механизма отображения в ComboBox
        cbRoles.setCellFactory(param -> new ListCell<javafxlibrary.JavaFxLibrary.roles>(){
            @Override
            protected void updateItem(javafxlibrary.JavaFxLibrary.roles item,boolean empty){
                super.updateItem(item, empty);
                if(item==null || empty){
                    setText(null);
                }else{
                    setText(item.toString());
                }
            }
        });
        cbRoles.setButtonCell(new ListCell<javafxlibrary.JavaFxLibrary.roles>() {
            @Override
            protected void updateItem(javafxlibrary.JavaFxLibrary.roles item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
        cbRoles.setOnAction(event->{
            this.selectedRole = (javafxlibrary.JavaFxLibrary.roles) cbRoles.getValue();
            //System.out.println(selectedRole.toString());
            lbInfo.setText("");
        });
    }
    @FXML public void clickButtonAddRole(){
        if(!this.selectedUser.getRoles().contains(this.selectedRole.toString())){
            this.selectedUser.getRoles().add(this.selectedRole.toString());
            this.changeRole();
        }
    }
    @FXML public void clickButtonRemoveRole(){
        if(this.selectedUser.getLogin().equals("admin")){
            lbInfo.setText("Изменение невозможно!");
            return;
        }
        if(this.selectedUser.getRoles().contains(this.selectedRole.toString())){
            this.selectedUser.getRoles().remove(this.selectedRole.toString());
            this.changeRole();
        }
    }
    private void changeRole(){
        getEntityManager().getTransaction().begin();
        getEntityManager().merge(this.selectedUser);
        getEntityManager().getTransaction().commit();
        setCbUsers();
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
        return em;
    }

    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxlibrary;

import entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tools.PassEncrypt;

/**
 *
 * @author Melnikov
 */
public class JavaFxLibrary extends Application {
    public static enum roles {ADMINISTRATOR, MANAGER, USER};
    public static User currentUser;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private final EntityManager em;
    private Stage primaryStage;
    
    

    public JavaFxLibrary() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JavaFxLibraryPU");
        em = emf.createEntityManager();
    }
   
    @Override
    public void start(Stage primaryStage) throws Exception {
        createSuperUser();
        setPrimaryStage(primaryStage);
        this.primaryStage.setTitle("JKTVFXLibrary");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        HomeController homeController = loader.getController();
        homeController.setApp(this);
        homeController.showAboutScene();
        Scene scene = new Scene(root, WIDTH,HEIGHT);
        //Подключаем каскадную таблицу стилей из пакета javafxlibrary
        //scene.getStylesheets().add(getClass().getResource("home.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/javafxlibrary/home.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void createSuperUser(){
        try {
            getEntityManager().createQuery("SELECT u FROM User u WHERE u.login = :login")
                    .setParameter("login", "admin")
                    .getSingleResult();
        } catch (Exception e) {
             PassEncrypt pe = new PassEncrypt();
             User userAdmin = new User();
             userAdmin.setFirstname("Juri");
             userAdmin.setLastname("Melnikov");
             userAdmin.setLogin("admin");
             userAdmin.setPassword(pe.getEncryptPassword("12345",pe.getSalt()));
             userAdmin.getRoles().add(roles.ADMINISTRATOR.toString());
             userAdmin.getRoles().add(roles.MANAGER.toString());
             userAdmin.getRoles().add(roles.USER.toString());
             getEntityManager().getTransaction().begin();
             getEntityManager().persist(userAdmin);
             getEntityManager().getTransaction().commit();
             
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
}

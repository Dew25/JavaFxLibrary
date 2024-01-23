/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxlibrary;

import books.book.BookController;
import books.newbook.NewbookController;
import entity.Book;
import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import users.login.LoginController;
import users.newuser.NewuserController;

/**
 *
 * @author Melnikov
 */
public class HomeController implements Initializable {
   
    @FXML
    private VBox vbContent;
    private JavaFxLibrary app;
    private String infoMessage;
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        showAboutScene();
        
    }    
    public void showAboutScene(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/about/about.fxml"));
            BorderPane bpAboutRoot = loader.load();
            bpAboutRoot.setPrefWidth(JavaFxLibrary.WIDTH);
            bpAboutRoot.setPrefHeight(JavaFxLibrary.HEIGHT);
            this.vbContent.getChildren().clear();
            vbContent.getChildren().add(bpAboutRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Нет /about/about.fxml", ex);
        }
    }
    @FXML public void mbShowAddNewBook(){
        if(javafxlibrary.JavaFxLibrary.currentUser == null || !javafxlibrary.JavaFxLibrary.currentUser.getRoles()
                .contains(javafxlibrary.JavaFxLibrary.roles.MANAGER.toString())){
            this.infoMessage="Для этого действия вы должны быть менеджером. Авторизуйтесь";
            this.mbShowLonginForm();
            return;
        }
        this.app.getPrimaryStage().setTitle("JKTVFXLibrary-добавить новую книгу");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/books/newbook/newbook.fxml"));
        
        try {
            VBox vbNewBookRoot = loader.load();
            vbNewBookRoot.setPrefHeight(JavaFxLibrary.HEIGHT);
            vbNewBookRoot.setPrefWidth(JavaFxLibrary.WIDTH);
            NewbookController newbookController = loader.getController();
            newbookController.setEntityManager(getApp().getEntityManager());
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbNewBookRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Невозможно заргузить newbookRoot", ex);
        }
    }
    @FXML public void mbShowAddNewUser(){
        this.app.getPrimaryStage().setTitle("JKTVFXLibrary-регистрация пользователя");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/users/newuser/newuser.fxml"));
        
        try {
            VBox vbNewUserRoot = loader.load();
            vbNewUserRoot.setPrefHeight(JavaFxLibrary.HEIGHT);
            vbNewUserRoot.setPrefWidth(JavaFxLibrary.WIDTH);
            NewuserController newuserController = loader.getController();
            newuserController.setEntityManager(getApp().getEntityManager());
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbNewUserRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Невозможно заргузить vbNewUserRoot", ex);
        }
    }
    @FXML public void mbShowLonginForm(){
        this.app.getPrimaryStage().setTitle("JKTVFXLibrary-вход пользователя");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/users/login/login.fxml"));
        
        try {
            VBox vbLoginRoot = loader.load();
            vbLoginRoot.setPrefHeight(JavaFxLibrary.HEIGHT);
            vbLoginRoot.setPrefWidth(JavaFxLibrary.WIDTH);
            LoginController loginController = loader.getController();
            loginController.setEntityManager(getApp().getEntityManager());
            loginController.setInfo(this.infoMessage);
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbLoginRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Невозможно заргузить vbLoginRoot", ex);
        }
    }
    
    @FXML public void mbShowListBooks(){
        this.app.getPrimaryStage().setTitle("JKTVFXLibrary-список книг");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/books/listbooks/listbooks.fxml"));
        
        try {
            HBox hbListBooksRoot = loader.load();
            hbListBooksRoot.getChildren().clear();
//            ListbooksController listbooksController = loader.getController();
            List<Book> listBooks = getApp().getEntityManager().createQuery("SELECT b FROM Book b").getResultList();
            for (int i = 0; i < listBooks.size(); i++) {
                Book book = listBooks.get(i);
                FXMLLoader bookLoader = new FXMLLoader();
                bookLoader.setLocation(getClass().getResource("/books/book/book.fxml"));
                ImageView ivCoverBookRoot = bookLoader.load();
                BookController bookController = bookLoader.getController();
                bookController.setApp(app);
                ivCoverBookRoot.setCursor(Cursor.OPEN_HAND);
                ivCoverBookRoot.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        bookController.showBook(book);
                    }
                });
                Image image = new Image(new ByteArrayInputStream(book.getCover()));
                ivCoverBookRoot.setImage(image);
                hbListBooksRoot.getChildren().add(ivCoverBookRoot);
            }
            vbContent.getChildren().clear();
            vbContent.getChildren().add(hbListBooksRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Невозможно заргузить /books/book/book.fxml", ex);
        }
    }

    void setApp(JavaFxLibrary app) {
        this.app = app;
    }

    public JavaFxLibrary getApp() {
        return app;
    }
    
}

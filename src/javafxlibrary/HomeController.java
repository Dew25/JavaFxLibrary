/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxlibrary;

import about.AboutController;
import books.listbooks.ListbooksController;
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
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

/**
 *
 * @author Melnikov
 */
public class HomeController implements Initializable {
   
    @FXML
    private VBox vbContent;
    private JavaFxLibrary app;
    private  Screen screen = Screen.getPrimary();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        showAboutScene();
        
    }    
    public void showAboutScene(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/about/about.fxml"));
            BorderPane bpAboutRoot = loader.load();
            Rectangle2D bounds = screen.getVisualBounds();
            bpAboutRoot.setPrefWidth(JavaFxLibrary.WIDTH);
            bpAboutRoot.setPrefHeight(JavaFxLibrary.HEIGHT);
            this.vbContent.getChildren().clear();
            vbContent.getChildren().add(bpAboutRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Нет /about/about.fxml", ex);
        }
    }
    @FXML public void mbShowAddNewBook(){
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
    @FXML public void mbShowListBooks(){
        this.app.getPrimaryStage().setTitle("JKTVFXLibrary-список книг");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/books/listbooks/listbooks.fxml"));
        
        try {
            HBox hbListBooksRoot = loader.load();
            ListbooksController listbooksController = loader.getController();
            List<Book> listBooks = getApp().getEntityManager().createQuery("SELECT b FROM Book b").getResultList();
            for (int i = 0; i < listBooks.size(); i++) {
                Book book = listBooks.get(i);
                ImageView ivCoverBook = FXMLLoader.load(getClass().getResource("/books/book/book.fxml"));
                Image image = new Image(new ByteArrayInputStream(book.getCover()));
                ivCoverBook.setImage(image);
                hbListBooksRoot.getChildren().add(ivCoverBook);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.book;

import entity.Book;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxlibrary.JavaFxLibrary;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class BookController implements Initializable {
    private JavaFxLibrary app;
    private Stage bookWindow;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void showBook(Book book) {
       this.bookWindow = new Stage();
       bookWindow.initModality(Modality.WINDOW_MODAL);
       bookWindow.initOwner(getApp().getPrimaryStage());
       Image image = new Image(new ByteArrayInputStream(book.getCover()));
       ImageView ivCover = new ImageView(image);
       ivCover.setId("big_book_cover");
       VBox vbBook = new VBox();
       vbBook.setMinWidth(Double.MAX_VALUE);
       vbBook.setAlignment(Pos.CENTER);
       vbBook.getChildren().add(ivCover);
       HBox hbButtons = new HBox();
       hbButtons.setAlignment(Pos.CENTER);
       hbButtons.setSpacing(10);
       hbButtons.setPadding(new Insets(20,20,20,20));
       Button btOk = new Button("Читать");
       Button btCansel = new Button("Закрыть");
       // Обработчик события для Button
       btCansel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               bookWindow.close();
            }
        });
       btOk.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                takeOnBook(book);
            }
        });
       hbButtons.getChildren().addAll(btOk,btCansel);
       vbBook.getChildren().add(hbButtons);
       Scene scene = new Scene(vbBook,550,700);
       scene.getStylesheets().add(getClass().getResource("/books/book/book.css").toExternalForm());
       bookWindow.setScene(scene);
       bookWindow.show();
        
    }
    private void takeOnBook(Book book){
        System.out.println(String.format("Выдаем книгу \"%s\" читателю %s %s",book.getTitle(), javafxlibrary.JavaFxLibrary.currentUser.getFirstname(), javafxlibrary.JavaFxLibrary.currentUser.getLastname()));
        bookWindow.close();
    }

    public JavaFxLibrary getApp() {
        return app;
    }

    public void setApp(JavaFxLibrary app) {
        this.app = app;
    }
    
}

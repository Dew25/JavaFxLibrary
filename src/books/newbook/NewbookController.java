/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books.newbook;

import entity.Book;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import org.imgscalr.Scalr;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class NewbookController implements Initializable {
    private EntityManager em;
    private File selectedFile;
    @FXML private TextField tfTitle;
    @FXML private Button btSelectCover;
    

    public NewbookController() {
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    @FXML
    public void selectCover(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбор изображения");
        selectedFile = fileChooser.showOpenDialog(new Stage());
        btSelectCover.setText("Выбран файл: "+ selectedFile.getName());
        btSelectCover.setDisable(true);
    }
    @FXML
    public void clickAddNewBook(){
        Book book = new Book();
        book.setTitle(tfTitle.getText());
        try {
            //Добавляем в Library проекта библиотеку imgscalr-lib.jar (находим в Интернете)
            // Получаем нужный формат изображения из selectedFile
            // Преобразуем размер изображения к ширине в 400 px 
            // Преобразуем тип в byte[] и инициируем book.setCover(...);
            BufferedImage biBookCover = ImageIO.read(selectedFile);
            BufferedImage biScaledBookCover = Scalr.resize(biBookCover, Scalr.Mode.FIT_TO_WIDTH,400);
            ByteArrayOutputStream baos = new ByteArrayOutputStream ();
            ImageIO.write (biScaledBookCover, "png", baos);
            book.setCover(baos.toByteArray());
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            //сообщить об успешном добавлении книги
        } catch (IOException ex) {
            Logger.getLogger(NewbookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        btSelectCover.setText("Выбрать обложку");
        btSelectCover.setDisable(false);
        tfTitle.setText("");
        
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
        
    
}

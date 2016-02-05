package rocks.huwi.isbnreader;

import javafx.beans.binding.ObjectBinding;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private InformationRetriever informationRetriever = new InformationRetriever();
    private Persistor persistor = new Persistor();
    private Book book = new Book();

    //@FXML private Spinner spinner_runningnumber;
    @FXML
    private TextField textfield_runningnumber;
    @FXML
    private TextField textfield_isbn;
    @FXML
    private TextField textfield_isbn10;
    @FXML
    private TextField textfield_isbn13;
    @FXML
    private TextField textfield_author;
    @FXML
    private TextField textfield_title;
    @FXML
    private TextField textfield_publisher;
    @FXML
    private TextField textfield_seller;
    @FXML
    private TextField textfield_listprice;
    @FXML
    private TextField textfield_sellingprice;
    @FXML
    private ProgressBar progressbar_retrievingInformation;
    @FXML
    private ImageView imageview_cover;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageview_cover.setImage(new Image("http://www.designsbybethann.com/pictures/Flowers/none%20flowers.jpg"));
        //imageview_cover.imageProperty().get().

        this.bindBook(book);
        //SpinnerValueFactory.IntegerSpinnerValueFactory isvf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,999999);
        //spinner_runningnumber.setValueFactory(isvf);
    }

    private void unbindBook(Book book) {
        textfield_isbn10.textProperty().unbindBidirectional(book.Isbn10Property());
        textfield_isbn13.textProperty().unbindBidirectional(book.Isbn13Property());
        textfield_author.textProperty().unbindBidirectional(book.AuthorProperty());
        textfield_title.textProperty().unbindBidirectional(book.TitleProperty());
        textfield_publisher.textProperty().unbindBidirectional(book.PublisherProperty());
        textfield_seller.textProperty().unbindBidirectional(book.SellerProperty());
        textfield_listprice.textProperty().unbindBidirectional(book.ListPriceProperty());
        textfield_sellingprice.textProperty().unbindBidirectional(book.SellingPriceProperty());
        imageview_cover.imageProperty().unbind();
    }

    private void bindBook(Book book) {
        //spinner_runningnumber. .bindBidirectional(book.RunningNumberProperty());
        //spinner_runningnumber.valueProperty().
        //textfield_isbn.textProperty().bind(book.RunningNumberProperty());
        textfield_isbn10.textProperty().bindBidirectional(book.Isbn10Property());
        textfield_isbn13.textProperty().bindBidirectional(book.Isbn13Property());
        textfield_author.textProperty().bindBidirectional(book.AuthorProperty());
        textfield_title.textProperty().bindBidirectional(book.TitleProperty());
        textfield_publisher.textProperty().bindBidirectional(book.PublisherProperty());
        textfield_seller.textProperty().bindBidirectional(book.SellerProperty());
        textfield_listprice.textProperty().bindBidirectional(book.ListPriceProperty());
        textfield_sellingprice.textProperty().bindBidirectional(book.SellingPriceProperty());

        ObjectBinding<Image> coverImageBinding = new ObjectBinding<Image>() {
            { bind(book.CoverURLProperty()); }
            protected Image computeValue() {
                logger.info("computing binding for cover URL");
                if (book.CoverURLProperty().get() == null)
                {
                    return null;
                }

                String coverURL = book.CoverURLProperty().get();
                logger.info("Setting image '{}'",coverURL);
                Image newImage = new Image(coverURL);
                return newImage;
            }
        };

        imageview_cover.imageProperty().bind(coverImageBinding);
    }

    @FXML
    protected void retrieveInformation(ActionEvent event) {
        Book lastBook = book;
        String isbn = textfield_isbn.getText();

        Task task = new Task<Book>() {
            @Override public Book call() {

                updateProgress(0.5, 1.0);
                try {
                    book = informationRetriever.retrieveBook(isbn);
                    book.setSeller(lastBook.getSeller());
                } catch (FileNotFoundException e){
                    logger.error("Book with given ISBN {} not found", isbn, e);
                    book = null;
                } catch (IOException e) {
                    logger.error("Error while retrieving book information", e);
                    book = null;
                }
                updateProgress(1.0, 1.0);

                return book;
            }
        };

        this.progressbar_retrievingInformation.progressProperty().bind(task.progressProperty());
        new Thread(task).start();

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                Book book = (Book)task.getValue();

                if (book != null) {
                    unbindBook(lastBook);
                    bindBook(book);

                    if (textfield_seller.textProperty().get() == null || textfield_seller.textProperty().get().isEmpty()) {
                        textfield_seller.requestFocus();
                    } else {
                        textfield_sellingprice.requestFocus();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error :-(");
                    alert.setHeaderText("Retrieving book information failed.");
                    alert.setContentText("The ISBN might be incorrect.");

                    alert.showAndWait();
                }
            }
        });
    }

    @FXML
    protected void saveBook(ActionEvent event) {
        try {
            this.persistor.writeCSV(this.book, "books.csv");

            Book lastBook = this.book;
            this.book = new Book();
            this.book.setSeller(lastBook.getSeller());

            unbindBook(lastBook);
            bindBook(book);

            this.textfield_isbn.setText("");
            this.textfield_isbn.requestFocus();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error :-(");
            alert.setHeaderText("Saving the book failed.");
            alert.setContentText(e.toString());

            alert.showAndWait();
            logger.error("Error while saving book", e);
        }
    }

}
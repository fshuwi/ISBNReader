package rocks.huwi.isbnreader;

import javafx.beans.binding.ObjectBinding;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    @FXML
    private Spinner spinner_runningnumber;
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
    @FXML
    private CheckBox checkbox_isstudent;
    @FXML
    private Button button_savebook;
    @FXML
    private Button button_retrieveinformation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory.IntegerSpinnerValueFactory integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999999);
        spinner_runningnumber.setValueFactory(integerSpinnerValueFactory);

        button_savebook.defaultButtonProperty().bind(button_savebook.focusedProperty());
        button_retrieveinformation.defaultButtonProperty().bind(button_retrieveinformation.focusedProperty());

        book.setRunningNumber(RunningNumberGenerator.getNextRunningNumber());
        this.bindBook(book);
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
        checkbox_isstudent.selectedProperty().unbindBidirectional(book.IsStudentProperty());
        spinner_runningnumber.getValueFactory().valueProperty().unbindBidirectional(book.RunningNumberProperty());
        imageview_cover.imageProperty().unbind();
    }

    private void bindBook(Book book) {
        textfield_isbn10.textProperty().bindBidirectional(book.Isbn10Property());
        textfield_isbn13.textProperty().bindBidirectional(book.Isbn13Property());
        textfield_author.textProperty().bindBidirectional(book.AuthorProperty());
        textfield_title.textProperty().bindBidirectional(book.TitleProperty());
        textfield_publisher.textProperty().bindBidirectional(book.PublisherProperty());
        textfield_seller.textProperty().bindBidirectional(book.SellerProperty());
        textfield_listprice.textProperty().bindBidirectional(book.ListPriceProperty());
        textfield_sellingprice.textProperty().bindBidirectional(book.SellingPriceProperty());
        checkbox_isstudent.selectedProperty().bindBidirectional(book.IsStudentProperty());
        spinner_runningnumber.getValueFactory().valueProperty().bindBidirectional(book.RunningNumberProperty());

        ObjectBinding<Image> coverImageBinding = new ObjectBinding<Image>() {
            {
                bind(book.CoverURLProperty());
            }

            protected Image computeValue() {
                logger.info("computing binding for cover URL");
                if (book.CoverURLProperty().get() == null) {
                    return null;
                }

                String coverURL = book.CoverURLProperty().get();
                logger.info("Setting image '{}'", coverURL);
                Image newImage = new Image(coverURL);
                return newImage;
            }
        };

        imageview_cover.imageProperty().bind(coverImageBinding);
    }

    private void transferValues(Book fromBook, Book toBook) {
        toBook.setSeller(fromBook.getSeller());
        toBook.setStudent(fromBook.getStudent());
        toBook.setRunningNumber(fromBook.getRunningNumber());
    }

    @FXML
    protected void retrieveInformation(ActionEvent event) {
        Book lastBook = book;
        String isbn = textfield_isbn.getText();

        Task task = new Task<Book>() {
            @Override
            public Book call() {

                updateProgress(0.5, 1.0);
                try {
                    book = informationRetriever.retrieveBook(isbn);

                    transferValues(lastBook, book);
                } catch (FileNotFoundException e) {
                    logger.error("Book with given ISBN {} not found (or some problem with the server)", isbn, e);
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
                Book book = (Book) task.getValue();

                if (book != null) {
                    unbindBook(lastBook);
                    bindBook(book);

                    if (textfield_seller.textProperty().get() == null || textfield_seller.textProperty().get().isEmpty()) {
                        textfield_seller.requestFocus();
                    } else {
                        textfield_sellingprice.requestFocus();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error :-(");
                    alert.setHeaderText("Retrieving book information failed.");
                    alert.setContentText("The ISBN might be incorrect (or some problem with the server).");

                    alert.showAndWait();
                }
            }
        });
    }

    @FXML
    protected void saveBook(ActionEvent event) {
        logger.info(this.spinner_runningnumber.getValueFactory().valueProperty().get().toString());
        try {
            this.persistor.writeCSV(this.book, "books.csv");

            Book lastBook = this.book;
            this.book = new Book();
            transferValues(lastBook, book);
            this.book.setRunningNumber(RunningNumberGenerator.getNextRunningNumber());

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
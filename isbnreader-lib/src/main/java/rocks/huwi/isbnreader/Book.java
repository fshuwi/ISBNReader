package rocks.huwi.isbnreader;

import javafx.beans.property.*;

public class Book {

    private IntegerProperty runningNumber = new SimpleIntegerProperty();
    private StringProperty seller = new SimpleStringProperty();
    private BooleanProperty isStudent = new SimpleBooleanProperty();
    private StringProperty author = new SimpleStringProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty publisher = new SimpleStringProperty();
    private StringProperty isbn10 = new SimpleStringProperty();
    private StringProperty isbn13 = new SimpleStringProperty();
    private StringProperty coverURL = new SimpleStringProperty();
    private StringProperty sellingPrice = new SimpleStringProperty();
    private StringProperty listPrice = new SimpleStringProperty();

    public IntegerProperty RunningNumberProperty() {
        return runningNumber;
    }

    @Override
    public String toString() {
        return "Book{" +
                "runningNumber=" + runningNumber +
                ", seller=" + seller +
                ", isStudent=" + isStudent +
                ", author=" + author +
                ", title=" + title +
                ", publisher=" + publisher +
                ", isbn10=" + isbn10 +
                ", isbn13=" + isbn13 +
                ", coverURL=" + coverURL +
                ", sellingPrice=" + sellingPrice +
                ", listPrice=" + listPrice +
                '}';
    }

    public StringProperty SellerProperty() {
        return seller;
    }

    public BooleanProperty IsStudentProperty() {
        return isStudent;
    }

    public StringProperty AuthorProperty() {
        return author;
    }

    public StringProperty TitleProperty() {
        return title;
    }

    public StringProperty PublisherProperty() {
        return publisher;
    }

    public StringProperty Isbn10Property() {
        return isbn10;
    }

    public StringProperty Isbn13Property() {
        return isbn13;
    }

    public StringProperty CoverURLProperty() {
        return coverURL;
    }

    public StringProperty SellingPriceProperty() {
        return sellingPrice;
    }

    public StringProperty ListPriceProperty() {
        return listPrice;
    }


    public int getRunningNumber() {
        return runningNumber.get();
    }

    public void setRunningNumber(int runningNumber) {
        this.runningNumber.set(runningNumber);
    }

    public String getSeller() {
        return seller.get();
    }

    public void setSeller(String seller) {
        this.seller.set(seller);
    }

    public Boolean getStudent() {
        return isStudent.get();
    }

    public void setStudent(Boolean student) {
        isStudent.set(student);
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getPublisher() {
        return publisher.get();
    }

    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
    }

    public String getIsbn10() {
        return isbn10.get();
    }

    public void setIsbn10(String isbn10) {
        this.isbn10.set(isbn10);
    }

    public String getIsbn13() {
        return isbn13.get();
    }

    public void setIsbn13(String isbn13) {
        this.isbn13.set(isbn13);
    }

    public String getSellingPrice() {
        return sellingPrice.get();
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice.set(sellingPrice);
    }

    public String getListPrice() {
        return listPrice.get();
    }

    public void setListPrice(String listPrice) {
        this.listPrice.set(listPrice);
    }

    public String getCoverURL() {
        return coverURL.get();
    }

    public void setCoverURL(String coverURL) {
        this.coverURL.set(coverURL);
    }
}

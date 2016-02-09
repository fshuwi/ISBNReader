package rocks.huwi.isbnreader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Persistor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String convertToCSV(Book book) {

        return book.getRunningNumber() + ";"
                + (book.getSellingPrice() != null ? book.getSellingPrice() : "") + ";"
                + (book.getSeller() != null ? book.getSeller() : "") + ";"
                + (book.getStudent() != null ? book.getStudent() : "") + ";"
                + (book.getTitle() != null ? book.getTitle() : "") + ";"
                + (book.getAuthor() != null ? book.getAuthor() : "") + ";"
                + (book.getPublisher() != null ? book.getPublisher() : "") + ";"
                + (book.getIsbn10() != null ? book.getIsbn10() : "") + ";"
                + (book.getIsbn13() != null ? book.getIsbn13() : "") + ";"
                + (book.getListPrice() != null ? book.getListPrice() : "")
                + "\n";
    }

    private String getCsvHeader() {
        return "Laufnummer;"
                + "Verkaufspreis;"
                + "Verkäufer;"
                + "istStudent;"
                + "Titel;"
                + "Autor;"
                + "Verlag;"
                + "ISBN10;"
                + "ISBN13;"
                + "Listenpreis\n";
    }

    public void writeCSV(Book book, String filename) throws IOException {
        logger.info("Saving book {} to {}", book, filename);

        File file = new File(filename);
        Boolean isFileExisting = file.exists();

        final BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));

        if (isFileExisting == false) {
            writer.write(this.getCsvHeader());
        }

        writer.write(this.convertToCSV(book));
        writer.close();
    }

}
package rocks.huwi.isbnreader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class BookFileWriter {
    private int runningNumber;
    private String sellingPrice;
    private String filename;
    private String seller;
    private Boolean isStudent;

    /*
    private String isStudentString()
    {
        if (this.isStudent)
        {
            return "1";
        }else{
            return "0";
        }
    }*/

    public BookFileWriter() {
        this.runningNumber = 1;
        this.isStudent = false;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

    public boolean getIsStudent() {
        return this.isStudent;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setSellingPrice(String ownPrice) {
        this.sellingPrice = ownPrice;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String convertBookToCSV(final HashMap<String, String> book) {
        return String.valueOf(this.runningNumber) + ";"
                + this.sellingPrice + ";"
                + this.seller + ";"
                + this.getIsStudent() + ";"
                + book.get("Title") + ";"
                + book.get("Author") + ";"
                + book.get("Publisher") + ";"
                + book.get("ISBN10") + ";"
                + book.get("ISBN13") + ";"
                + book.get("List Price") + "\n";
    }

    public String getCsvHeader()
    {
        return "Laufnummer;Verkaufspreis;Verkäufer;istStudent;Titel;Autor;Verlag;ISBN10;ISBN13;Listenpreis\n";
    }

    public int getRunningNumber() {
        return this.runningNumber;
    }

    public void setRunningNumber(int runningNumber) {
        this.runningNumber = runningNumber;
    }

    public void writeBookAsCSV(HashMap<String, String> book) throws IOException {
        File file = new File(this.filename);
        Boolean isFileExisting = file.exists();

        final BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename, true));

        if (isFileExisting) {
            writer.write(this.getCsvHeader());
        }

        writer.write(this.convertBookToCSV(book));
        writer.close();

        ++this.runningNumber;
    }
}

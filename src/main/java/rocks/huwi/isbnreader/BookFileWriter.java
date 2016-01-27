package rocks.huwi.isbnreader;

import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.HashMap;

// 
// Decompiled by Procyon v0.5.30
// 

public class BookFileWriter
{
    private int runningNumber;
    private String ownPrice;
    private String controlValues;
    private String filename;
    private String verkaufer;
    private String tmpFile;
    private String student;
    
    public void setStudent(final String student) {
        this.student = student;
    }
    
    public String getTmpFile() {
        return this.tmpFile;
    }
    
    public void setVerkaufer(final String verkaufer) {
        this.verkaufer = verkaufer;
    }
    
    public void setOwnPrice(final String ownPrice) {
        this.ownPrice = ownPrice;
    }
    
    public String getFilename() {
        return this.filename;
    }
    
    public void setFilename(final String filename) {
        this.filename = filename;
    }
    
    public String getControlValues(final HashMap<String, String> book) {
        return this.controlValues = String.valueOf(this.runningNumber) + ";" + this.ownPrice + ";" + this.verkaufer + ";" + this.student + ";" + book.get("Title") + ";" + book.get("Author") + ";" + book.get("Publisher") + ";" + book.get("ISBN10") + ";" + book.get("List Price") + "\n";
    }
    
    public int getRunningNumber() {
        return this.runningNumber;
    }
    
    public void setRunningNumber(final int runningNumber) {
        this.runningNumber = runningNumber;
    }
    
    public BookFileWriter() {
        this.runningNumber = 0;
        this.student = "0";
        this.tmpFile = "";
    }
    
    public void writeBookData(final HashMap<String, String> book) throws IOException {
        final File file = new File(this.filename);
        if (!file.exists()) {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename, true));
            writer.write("Laufnummer;Eigener Preis;Verk\u00e4ufer;Student;Titel;Autor;Verlag;ISBN10\n");
            writer.write(String.valueOf(this.runningNumber) + ";" + this.ownPrice + ";" + this.verkaufer + ";" + this.student + ";" + book.get("Title") + ";" + book.get("Author") + ";" + book.get("Publisher") + ";" + book.get("ISBN10") + ";" + "\n");
            ++this.runningNumber;
            writer.close();
        }
        final BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename, true));
        writer.write(String.valueOf(this.runningNumber) + ";" + this.ownPrice + ";" + this.verkaufer + ";" + this.student + ";" + book.get("Title") + ";" + book.get("Author") + ";" + book.get("Publisher") + ";" + book.get("ISBN10") + ";" + book.get("List Price") + "\n");
        ++this.runningNumber;
        writer.close();
    }
}

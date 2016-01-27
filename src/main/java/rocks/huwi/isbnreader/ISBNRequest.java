package rocks.huwi.isbnreader;

import java.io.IOException;
import java.net.URLConnection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

// 
// Decompiled by Procyon v0.5.30
// 

public class ISBNRequest
{
    private HashMap<String, String> book;
    
    public ISBNRequest() {
        this.book = new HashMap<String, String>();
    }
    
    public HashMap<String, String> getISBN(final String ISBN) throws IOException {
        final URL url = new URL("http://www.openisbn.org/download/" + ISBN + ".txt");
        final URLConnection yc = url.openConnection();
        final BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
        String[] attrib = null;
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            attrib = inputLine.split(": ");
            final String s;
            switch ((s = attrib[0]).hashCode()) {
                case -2126344299: {
                    if (!s.equals("ISBN10")) {
                        continue;
                    }
                    this.book.put(attrib[0], attrib[1]);
                    continue;
                }
                case -843595300: {
                    if (!s.equals("Publisher")) {
                        continue;
                    }
                    this.book.put(attrib[0], attrib[1]);
                    continue;
                }
                case 65299351: {
                    if (!s.equals("Cover")) {
                        continue;
                    }
                    this.book.put(attrib[0], attrib[1]);
                    continue;
                }
                case 80818744: {
                    if (!s.equals("Title")) {
                        continue;
                    }
                    this.book.put(attrib[0], attrib[1]);
                    continue;
                }
                case 865137479: {
                    if (!s.equals("List Price")) {
                        continue;
                    }
                    this.book.put(attrib[0], attrib[1]);
                    continue;
                }
                case 1972506027: {
                    if (!s.equals("Author")) {
                        continue;
                    }
                    this.book.put(attrib[0], attrib[1]);
                    continue;
                }
                default: {
                    continue;
                }
            }
        }
        in.close();
        return this.book;
    }
    
    public void resetBook() {
        this.book.clear();
    }
}

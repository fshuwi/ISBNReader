package rocks.huwi.isbnreader;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class BookRetriever {
    private HashMap<String, String> book;

    public BookRetriever() {
        this.book = new HashMap<String, String>();
    }

    public HashMap<String, String> retrieveBook(String ISBN) throws IOException {
        URL url = new URL("http://www.openisbn.org/download/" + ISBN + ".txt");
        URLConnection urlConnection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            String[] splitKeyValues = inputLine.split(": ");
            String key = splitKeyValues[0];
            String value = splitKeyValues.length >= 2 ? splitKeyValues[1] : "";

            switch (key) {
                case "ISBN10":
                case "ISBN13":
                case "Publisher":
                case "Cover":
                case "Title":
                case "List Price":
                case "Author":
                {
                    this.book.put(key, value);
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

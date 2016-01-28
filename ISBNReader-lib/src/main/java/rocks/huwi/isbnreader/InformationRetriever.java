package rocks.huwi.isbnreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class InformationRetriever {

    public Book retrieveBook(String isbn) throws IOException {
        Book book = new Book();

        URL url = new URL("http://www.openisbn.org/download/" + isbn + ".txt");
        URLConnection urlConnection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            String[] splitKeyValues = inputLine.split(": ");
            String key = splitKeyValues[0];
            String value = splitKeyValues.length >= 2 ? splitKeyValues[1] : "";

            switch (key) {
                case "ISBN10":
                    book.setIsbn10(value);
                    break;

                case "ISBN13":
                    book.setIsbn13(value);
                    break;

                case "Publisher":
                    book.setPublisher(value);
                    break;

                case "Cover":
                    book.setCover(value);
                    break;

                case "Title":
                    book.setTitle(value);
                    break;

                case "List Price":
                    book.setListPrice(value);
                    break;

                case "Author":
                    book.setAuthor(value);
                    break;

                default:
                    continue;
            }
        }

        reader.close();
        return book;
    }
}

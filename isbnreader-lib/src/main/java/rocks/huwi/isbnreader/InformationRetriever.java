package rocks.huwi.isbnreader;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class InformationRetriever {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * openisbn.com seems to need http://www.openisbn.org/isbn/XYZ access first before retrieving the download URL.
     * This method does this.
     *
     * @param isbn ISBN of the book to access
     */
    private void dummyRead(String isbn) {
        String urlString = "http://www.openisbn.org/isbn/" + isbn + "/";
        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            urlConnection.getInputStream();
        } catch (Exception e) {
            logger.error("Error in accessing {}", urlString, e);
        }
    }

    /**
     * Retrieve information of an book with given ISBN
     *
     * @param isbn ISBN of the book to receive information for
     * @return Book with information
     * @throws IOException
     */
    public Book retrieveBook(String isbn) throws IOException {
        logger.info("Retrieving Information for ISBN {}", isbn);
        Book book = new Book();

        logger.info("Access dummy URL for generation for ISBN {}", isbn);
        this.dummyRead(isbn);

        URL url = new URL("http://www.openisbn.org/download/" + isbn + ".txt");
        logger.info("Download real Information for ISBN {} from {}", isbn, url.toString());
        URLConnection urlConnection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            String[] splitKeyValues = inputLine.split(": ", 2);
            String key = splitKeyValues[0];
            String value = splitKeyValues.length >= 2 ? splitKeyValues[1] : "";

            switch (key) {
                case "ISBN10":
                    book.setIsbn10(value.trim());
                    break;

                case "ISBN13":
                    book.setIsbn13(value.trim());
                    break;

                case "Publisher":
                    book.setPublisher(value.trim());
                    break;

                case "Cover":
                    // openisbn.com places spaces around the URL
                    book.setCoverURL(value.trim());
                    break;

                case "Title":
                    book.setTitle(value.trim());
                    break;

                case "List Price":
                    book.setListPrice(value.trim());
                    break;

                case "Author":
                    // openisbn.com ends the authors attribute with ", "
                    book.setAuthor(StringUtils.stripEnd(value, ", "));
                    break;

                default:
                    continue;
            }
        }

        reader.close();
        return book;
    }
}

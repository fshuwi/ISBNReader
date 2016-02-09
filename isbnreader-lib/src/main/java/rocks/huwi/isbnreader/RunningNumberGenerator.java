package rocks.huwi.isbnreader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.function.Function;

public class RunningNumberGenerator {
    private static final Logger logger = LoggerFactory.getLogger(RunningNumberGenerator.class);

    /**
     * extract the running number from a CSV line
     */
    private static Function<String, Integer> extractRunningNumber = (line) -> {
        String p = line.split(";")[0].trim();
        try {
            int num = Integer.parseInt(p);

            return num;
        } catch (Exception e) {
            return -1;
        }
    };

    /**
     * @return get the next running number for a book
     */
    public static int getNextRunningNumber() {
        try {
            InputStream is = new FileInputStream(new File("books.csv"));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int maxRunningNumber = br.lines()
                    .skip(1)
                    .map(extractRunningNumber)
                    .mapToInt(i -> i)
                    .max()
                    .getAsInt();

            logger.info("Maximum running number is {}", maxRunningNumber);

            int nextRunningNumber = maxRunningNumber + 1;

            if (nextRunningNumber >= 1) {
                return nextRunningNumber;
            } else {
                return 1;
            }
        } catch (FileNotFoundException ex) {
            logger.info("File not found, using 1 as next running number.");
            return 1;
        }
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        // make a function that reads the file
        // could try getting the indexOf for each # line by line to store in a hash for future use
        // each row could have an array list of values in the hash
        // somehow need to keep track of the columns that go with the rows
        // column key row value to show where each # is located
        // if there are no rows or column hash key is null, then move 0 to the top

        // to do this maybe making an array from a range line by line to get the column keys and use a counter for each row
        // scanning character by character might be best
    }
    public static String fileReader(String file) {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
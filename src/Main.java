import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap hash = fileReader("day14smallinput.txt");
        if (hash == null) {
            System.out.println('h');
        }

        // make a function that reads the file
        // could try getting the indexOf for each # line by line to store in a hash for future use
        // each row could have an array list of values in the hash
        // somehow need to keep track of the columns that go with the rows
        // column key row value to show where each # is located
        // if there are no rows or column hash key is null, then move 0 to the top

        // to do this maybe making an array from a range line by line to get the column keys and use a counter for each row
        // scanning character by character might be best
    }
    public static HashMap fileReader(String file) {
        HashMap<Integer, ArrayList<Integer>> hash = new HashMap<>();
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            int rowCount = 0;
            int index = 0;

            while ((line = bufferedReader.readLine()) != null) {
                String array = line;
                for (int j = 0; j < array.length(); j++) {
                    // if we are adding key value for the first time,  we check if the key is there then put in new column and arraylist
                    if (!hash.containsKey(rowCount)) {
                        hash.put(rowCount, new ArrayList<>());
                    }
                    else {
                        // get the index position of one # at a time and update j to that index position
                        if (index == 0) {
                            index = array.indexOf('#');
                        }
                        if (index == -1) {
                            break;
                        }
                        else {
                            hash.get(rowCount).add(index);
                            index = array.indexOf('#', index + 1);
                        }
                        // should be indexOf of a substring

                        // add value if it is not - 1
                        // get the array list from the column count, and add the current char index to the array
                    }
                }
                index = 0;
                rowCount++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return hash;
    }
}
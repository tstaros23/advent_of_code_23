import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap hash = fileReader("day14smallinput.txt");
    }
    public static HashMap fileReader(String file) {
        HashMap<Integer, ArrayList<Integer>> hash = new HashMap<>();
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            int rowCount = 0;
            int index = 0;

            while ((line = bufferedReader.readLine()) != null) {
                // read file line by line using buffering
                String array = line;
                for (int j = 0; j < array.length(); j++) {
                    // if  adding key value for the first time,  check if the key is there then put in new row number and arraylist
                    if (!hash.containsKey(rowCount)) {
                        hash.put(rowCount, new ArrayList<>());
                    }
                    else {
                        // if the index is 0 then get the first occurence of the #
                        if (index == 0) {
                            index = array.indexOf('#');
                        }
                        // if no # are found then break the iteration
                        if (index == -1) {
                            break;
                        }
                        // otherwise add the index to the arraylist associated with the row
                        else {
                            hash.get(rowCount).add(index);
                            // get the next index starting from where the previous index position left off
                            // by using a substring and starting place in the indexOf function
                            index = array.indexOf('#', index + 1);
                        }
                    }
                }
                // reassign index back to zero after each iteration of rows and increase row count
                index = 0;
                rowCount++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return hash;
    }
}
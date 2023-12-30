import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap cubedHash = cubedShapedRocksHash("day14smallerinput.txt");
        HashMap roundHash = roundShapedRocksHash(cubedHash);
    }
    // refactor file reader hash so indexOf values are keys and the arraylists hold rows to make process easier
    // later on to make 0 hash of updated index positions

    // each time I get the indexOf values line by line, check if the indexOf value already exists in the hash keys or not
    // if not then add it to the hash and a new arrayList as the value
    // otherwise, do previous logic to get each indexOf and add the current row number to the arraylist
    public static HashMap cubedShapedRocksHash(String file) {
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
                    // get the first index of during the first iteration
                    if (index == 0) {
                        index = array.indexOf('#');
                    }
                    // if no # are found then break the iteration
                    if (index == -1) {
                        break;
                    }
                    // index is found at this point, so check if the key exists, if it doesn't, add it and new arraylist
                    else if (!hash.containsKey(index)){
                        hash.put(index, new ArrayList<>());
                    }
                    // otherwise, add the row to the arraylist associated with the key
                    else {
                        hash.get(index).add(rowCount);
                        // get the next index starting from where the previous index position left off
                        // by using a substring and starting place in the indexOf function
                        index = array.indexOf('#', index + 1);
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
    // update index hash so that each 0 moves as North as possible stopping under # if applicable
    public static HashMap roundShapedRocksHash(HashMap<Integer, ArrayList<String>> map) {
        HashMap<Integer, ArrayList<Integer>> hash = new HashMap<>();
        // using almost the same logic as above we can get the index positions of each 0, column
        // use the index position to find the key value from the map hash being passed in the params
        // find the highest value from the array list and subtract 1 to get the row for the 0 rock
        // the map hash must also be updated so that row where the 0 is moved to, is added to the array list
        return hash;
    }
    // third step is to take each row number from key value arraylist and multiply it by the arraylist length to get
    // the column total to total in the end for the answer
}
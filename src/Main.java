import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        HashMap cubedHash = new HashMap<Integer, ArrayList<Integer>>();
        HashMap roundHash = new HashMap<Integer, ArrayList<Integer>>();
        HashMap shiftedHash = cubedShapedRocksHash("day14smallerinput.txt", cubedHash, roundHash);
    }
    // need to pass in two hashes to cubedShapedRocksHash to create the hashes for 0 and # where the columns and rows index positions are stored
    // or make a hash of hashes. keys for outer hash being round 0 and # rocks
    /**
     * changes cubed and rounded hashes in place, so they contain columns and rows in their hashes by storing the indices
     * @param file txt file of shaped and round rocks in a matrix
     * @param cubedHash hash containing indices where the columns are keys and values are array list of index positions of #
     * @param roundHash hash containing indices where the columns are keys and values are array list of index positions of 0
     *
     */
    public static HashMap<Integer, ArrayList<Integer>>  cubedShapedRocksHash(String file, HashMap<Integer,ArrayList<Integer>> cubedHash, HashMap<Integer,ArrayList<Integer>> roundHash) {
        HashMap<Integer, ArrayList<Integer>> finalHash = new HashMap<>();
        try (FileReader fileReader = new FileReader(file);

             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            int lineCount = 0;


            while ((line = bufferedReader.readLine()) != null) {
                int index = 0;
                // read file line by line using buffering
                createHash(line,cubedHash, index,'#', lineCount);
                createHash(line,roundHash, index, 'O', lineCount);
                // reassign index back to zero after each iteration of rows and increase row count
                lineCount++;
            }
            finalHash = shiftedRocksHash(cubedHash,roundHash,lineCount);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return finalHash;
    }

    /**
     * Helper method that stores the columns and corresponding rows while a file is read to record occurrence for any character passed into the parameters
     * @param line current line of txt file
     * @param hash hash used to store the data
     * @param index index of current character in the string that gets updated when indexOf is called on the string
     * @param character character that data is being collected for
     */
    // need rowCount, index, string, hash
    public static void createHash(String line, HashMap<Integer, ArrayList<Integer>> hash, int index, char character, int lineCount) {
        String array = line;
        int columnLength = array.length();
        for (int j = 0; j < columnLength; j++) {
            // if we are on the first column, store key as -1 and the new array list holds in the cubed hash.
            // complexity is O(1) so will just add this key value pair to both hashes
            // not ideal storage but will do the job for now. Might refactor so the second value is the line Count but not necessary
            if (j == 0) {
                hash.put(-1, new ArrayList<>());
                hash.get(-1).add(line.length());
            }
            // get the first index of during the first iteration through the line
            if (index == 0) {
                index = array.indexOf(character);
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
            if (hash.containsKey(index)) {
                hash.get(index).add(lineCount);
                // get the next index starting from where the previous index position left off
                // by using a substring and starting place in the indexOf function
                index = array.indexOf(character, index + 1);
            }
        }
    }
    // create new hash so that each 0 moves as North as possible stopping under # if applicable. keys are columns and values are rows
    public static HashMap shiftedRocksHash(HashMap<Integer, ArrayList<Integer>> cubedHash, HashMap<Integer, ArrayList<Integer>> roundHash, int fileLength ) {
        HashMap<Integer, ArrayList<Integer>> hash = new HashMap<>();
        // using almost the same logic as above we can get the index positions of each 0, column
        // use the index position to find the key value from the map hash being passed in the params
        // get total number of columns from the cubed hash
        int lineLength = cubedHash.get(-1).get(0);
        // start by iterating through each column
        for (int i = 0; i < lineLength; i++) {
            if (cubedHash.get(i) != null && roundHash.get(i) != null) {
                // if 'O' is in the column and # is in the column then move 'O' one row under the # row index in the new hash
                // get highest value from cubed array list. add 1 then place it in the new hash after doing next step
                // need to iterate over all the rows
                if (!hash.containsKey(i)) {
                    hash.put(i, new ArrayList<>());
                }
                // go # by # row by row and see if there is a null spot below the current and then if there is a 0 between
                // current # row and the next # row
                for (int j = 0; j < cubedHash.get(i).size(); j++) {
                    // save both array list values of # and 0
                    Set<Integer> cubedKeySet = cubedHash.keySet();
                    ArrayList<Integer> cubedRowValues = new ArrayList<>(cubedKeySet);

                    Set<Integer> roundKeySet = cubedHash.keySet();
                    ArrayList<Integer> roundRowValues = new ArrayList<>(roundKeySet);
                    // create variable to store current cube row and round row
                    int currentCubedRow = cubedRowValues.get(j);
                    // break loop once 0 is empty
                    if (roundRowValues.isEmpty())
                        break;
                    // check if there is a null spot one row below first #, then check if there is a 0 row > current #
                    // and less than next # row or 0 row if they are not null
                    if (cubedHash.get(currentCubedRow - 1) == null) {
                        // if there is a next cubed row, make sure it is > than current 0 row
                        if (cubedRowValues.get(j + 1) != null) {
                            if (roundRowValues.get(0) < cubedRowValues.get(j+1)) {
                                // if it is then it is in the middle of two #s and there is space under the # above so shift
                                hash.get(i).add(fileLength - currentCubedRow + 1);
                            }
                        }
                        // 0 will be removed once compared to current # row and next # row range
                    }
                }
                if (hash.containsKey(i)){

                }
                // rowTotal (lineCount) - row equals new row
                // reverse the column rows for new hash
            }
            // if there are no # in the column but a 0 is present
            // put the
            else if (roundHash.get(i) != null){
                if (!hash.containsKey(i)) {
                    hash.put(i, new ArrayList<>());
                }
                if (hash.containsKey(i)) {
                    hash.get(i).add(fileLength);
                }
            }

        }
        // find the highest value from the array list and subtract 1 to get the row for the 0 rock
        // the map hash must also be updated so that row where the 0 is moved to, is added to the array list
        return hash;
    }
    // third step is to take each row number from key value arraylist and multiply it by the arraylist length to get
    // the column total to total in the end for the answer
}
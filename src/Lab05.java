import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class Lab05 {

    /* Global variables */
    public static HashMap<String, ArrayList<String>> dictionary = new HashMap<>();
    public static int NUM_CHARS = 256;
    public static ArrayList<String> result = new ArrayList<>();
    private static String testing[] = {"plekic", "diapers", "teardrop", "nameless", "allergy", "deepak", "impressions", "restrain", "calligraphy", "nepal", "stale", "parliaments", "sucrose", "persist", "disintegration"};

    /* Main method that runs algorithm */
    public static void main(String args[]) {
        //for all words, read word, sort, and insert into hashmap
        readWords();

        for(int i=0; i<testing.length; i++) {
            //get a word and search for it
            result = searchWord(testing[i]);

            //print list of anagrams
            if (result != null) {
                printList(testing[i], result);
            }
        }

        //exit/done
    }

    /* Method that prints a row of the output table. */
    private static void printList(String input, ArrayList<String> anagrams) {
        System.out.print("Anagrams for " + input + ":\t\t\t");
        for (int i = 0; i < anagrams.size(); i++) {
            System.out.print(anagrams.get(i)+ "\t");
        }
        System.out.println();
    }

    /* Method that searches for the word in the dictionary. */
    public static ArrayList<String> searchWord(String word) {
        ArrayList<String> returnList = dictionary.get(sortString(word));
        return returnList;
    }

    /* Method that reads from the file and loads the dictionary */
    public static void readWords() {
        Path path = FileSystems.getDefault().getPath("/usr/share/dict/", "words");
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1);
            String line;
            String word;
            String sorted;

            while ((line = reader.readLine()) != null) {
                word = line.trim();
                sorted = sortString(word);

                if (dictionary.containsKey(sorted)) {
                    ArrayList<String> anagrams = dictionary.get(sorted);
                    anagrams.add(line);
                    dictionary.put(sorted, anagrams);
                } else {
                    ArrayList<String> anagrams = new ArrayList<>();
                    anagrams.add(line);
                    dictionary.put(sorted, anagrams);
                }

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Method that sorts the strings in alphabetical order. */
    private static String sortString(String unsortedInput) {
        int[] charCounts = new int[NUM_CHARS]; //array to count the number of characters
        StringBuilder sortedInput = new StringBuilder();

        for (int i = 0; i < unsortedInput.length(); i++) {
            charCounts[(int) unsortedInput.charAt(i)]++;
        }

        for (int i = 0; i < NUM_CHARS; i++) {
            for (int j = 0; j < charCounts[i]; j++) {
                sortedInput.append((char) i);
            }
        }

        return sortedInput.toString();
    }
}

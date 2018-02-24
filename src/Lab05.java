import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class Lab05 {
    //Global variables
    public static HashMap<String, ArrayList<String>> dictionary = new HashMap<>();
    public static String filePath = "/usr/share/dict/words";
    public static int NUM_CHARS = 256;
    public static ArrayList<String> result = new ArrayList<>();


    public static void main(String args[]) {
        //for all words, read word, sort, and insert into hashmap
        readWords(filePath);

        //get a word and search for it
        result = searchWord("diapers");

        //print list of anagrams
        if (result != null) {
            printList(result);
        }

        //exit/done
    }

    private static void printList(ArrayList<String> anagrams) {
        System.out.println("List size " + anagrams.size());
        for (int i = 0; i < anagrams.size(); i++) {
            System.out.println(anagrams.get(i));
        }
    }

    public static ArrayList<String> searchWord(String word) {
        ArrayList<String> returnList = dictionary.get(sortString(word));
        return returnList;
    }

    public static void readWords(String filePath) {
        Path path = FileSystems.getDefault().getPath("/usr/share/dict/", "words");
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.US_ASCII);
            String line;
            String word;
            String sorted;

            while ((line = reader.readLine()) != null) {
//                if(line.trim().equals("praised"))
//                    System.out.println("found");

                //for(int i=0; i<10; i++){
                word = line.trim();
                sorted = sortString(word);
                //System.out.println(sortedLine);
                //System.out.println(dictionary.containsKey(sortedLine));
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

    /* Method has been tested for correctness. */
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

        //System.out.println(sortedInput);
        return sortedInput.toString();
    }
}

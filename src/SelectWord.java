import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

// Class that reads the words from the WORDS.txt and randomly selects a word for the game
public class SelectWord {

    public String word;
    public int letters;
    private HashMap<String, Integer> wordsInfo;
    ArrayList<String> stringArrayList;

    private int NUMBER_OF_WORDS;

    Random rand = new Random();

    public SelectWord(){
        defineNumberOfWordsInFile();
        gatherInfoFromFile();

        word = selectWordFromFile();
        letters = word.length();
    }

    // Select randomly a word from the list
    private String selectWordFromFile(){
        int i = rand.nextInt(NUMBER_OF_WORDS);
        return stringArrayList.get(i);
    }

    // Gather the information from the file and store them in a list
    private void gatherInfoFromFile(){
        wordsInfo = new HashMap<>();
        stringArrayList = new ArrayList<>();

        try {
            FileReader reader = new FileReader("WORDS.txt");
            int data;
            String temp = "";

            // Read the first line
            do{
                data = reader.read();
            } while((char) data != '\n');
            data = reader.read();

            // Having get rid of the first line, read the words
            while(data != -1) {
                if(((char) data >= 'A' && (char) data <= 'Z') || ((char) data >= 'a' && (char) data <= 'z')){
                    temp += String.valueOf((char) data).toUpperCase();
                }
                else if((char) data == '\n'){
                    // Add an extra line in WORDS.txt so that the last char is '\n' and word is added in the list
                    stringArrayList.add(temp);
                    temp = "";
                }

                data = reader.read();
            }

            reader.close();
            System.out.println(stringArrayList.toString());

        } catch (IOException e) { e.printStackTrace();}

        // Now organise the info in the map
        for(String x : stringArrayList){
            wordsInfo.put(x, x.length());
        }

        System.out.println(wordsInfo.toString());
    }

    // Read the number of the words from the file
    private void defineNumberOfWordsInFile(){
        try {
            FileReader reader = new FileReader("WORDS.txt");
            String temp = "";
            int data = reader.read();

            do {
                temp += String.valueOf((char) data);
                data = reader.read();
            } while ((char) data != 'K');

            NUMBER_OF_WORDS = Integer.parseInt(temp);
        } catch (IOException e) { e.printStackTrace(); }

        System.out.println(NUMBER_OF_WORDS);
    }

    @Override
    public String toString() {
        return "\nWord: " + word + ", letters: " + letters + ", number of words: " + NUMBER_OF_WORDS + "\n";
    }
}

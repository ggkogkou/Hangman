import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;

// Class that controls the game flow and creates the string to display after each round
public class PlayHangman {

    private Scanner scanner;
    private SelectWord selectWord;

    private String word;
    private int letters;

    private int lives = 6;

    private TreeMap<Integer, String> map; // Map that contains the letters {key: position, value: letter}
    private TreeMap<Integer, String> treeMap; // Map to add the letters that have been used

    private HashSet<String> lettersSet; // Set with the letters of the word
    private HashSet<String> lettersUsed; // Set to add the letters that have been used

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor: Init the variables and store the info in the Collections and start the game via playTheGame method
    //-----------------------------------------------------------------------------------------------------------------
    public PlayHangman(){
        scanner = new Scanner(System.in);
        selectWord = new SelectWord();

        word = selectWord.word;
        letters = selectWord.letters;

        // Add all the letters of the selected word in the set
        lettersSet = new HashSet<>();
        for(int i=0; i<letters; i++){
            lettersSet.add(word.substring(i, i+1));
        }
        System.out.println(lettersSet.toString());

        // Map that contains the letters {key: position, value: letter}
        map = new TreeMap<>();
        for(int i=0; i<letters; i++){
            map.put(i, word.substring(i, i+1));
        }
        System.out.println(map.toString());

        // Map to add the letters that have been used
        treeMap = new TreeMap<>();

        // Set to add the letters that have been used
        lettersUsed = new HashSet<>();

        System.out.println(selectWord.toString());
        System.out.println(map.toString());
        System.out.println(lettersSet.toString());

        for(int i=0; i<letters; i++){
            if(i != letters-1) displayString += "-";
            else displayString += "- ";
        }
    }

    // This method is only for the console based game
    private void playTheGame(){
        // The string that will store the user input
        String input;

        // Declare the string that will display the gallows as created in the next method
        String temp = "";
        for(int i=0; i<letters; i++){
            if(i != letters-1) temp += "-";
            else temp += "- ";
        }
        System.out.println();

        // Game loop
        while(lives > 0){
            //Check if word is found
            isWordFound(treeMap, map);

            // Select a valid letter if game is played in the console / terminal
            input = getValidLetterFromConsole();

            // Add it to the used words set
            lettersUsed.add(input);

            // If the word contains the letter
            if(lettersSet.contains(input)){
                for(int i=0; i<letters; i++){
                    // If the char at each position is the same with the char give as input
                    // add the position in the treemap {key: position, value: letter}
                    if(word.charAt(i) == input.charAt(0)){
                        treeMap.put(i, input);
                        temp = createString(map.size(), treeMap);
                    }
                }
            }
            else {
                lives--;
                System.out.println("Lives left: " + lives);
            }

            System.out.println(temp);
        }

        // By this point the player has lost all of his lives
        System.out.println("\n" + "--------------- DEFEAT -------------------" + "\n");
    }

    // Create the string to display
    private String createString(int letters, TreeMap<Integer, String> treeMap){
        String temp = "";

        for(int i=0; i<letters; i++){
            if(treeMap.containsKey(i) && i != letters-1) temp += treeMap.get(i) + " ";
            else if(treeMap.containsKey(i) && i == letters-1) temp += treeMap.get(i);
            else if(!treeMap.containsKey(i) && i != letters-1) temp += "- ";
            else temp += "-";
        }

        return temp;
    }

    // Check if the player found the word
    private void isWordFound(TreeMap<Integer, String> treeMap, TreeMap<Integer, String> map){
        if(treeMap.size() == map.size()){
            System.out.println("\n" + "--------------- VICTORY -------------------");
            System.exit(0);
        }
        return;
    }

    // Method for checking the validity of the input given by the user
    // All characters are converted in upper case form
    // used only if the game is played in the console
    private String getValidLetterFromConsole(){
        String input;

        do{
            System.out.print("Select a letter: ");
            input = scanner.next();
            input = input.toUpperCase();
        } while(input.length() > 1 || (input.charAt(0) >= '0' && input.charAt(0) <= '9') || lettersUsed.contains(input));

        return input;
    }

    public void nextRound(String input){
        // Declare the string that will display the gallows as created in the next method
        String temp = "";
        for(int i=0; i<letters; i++){
            if(i != letters-1) temp += "-";
            else temp += "- ";
        }
        System.out.println();

        // Add it to the used words set
        lettersUsed.add(input);

        // If the word contains the letter
        if(lettersSet.contains(input)){
            for(int i=0; i<letters; i++){
                // If the char at each position is the same with the char give as input
                // add the position in the treemap {key: position, value: letter}
                if(word.charAt(i) == input.charAt(0)){
                    wasLetterCorrect = true;
                    treeMap.put(i, input);
                    temp = createString(map.size(), treeMap);
                }
            }
        }
        else {
            wasLetterCorrect = false;
            lives--;
            System.out.println("Lives left: " + lives);
        }

        displayString = createString(letters, treeMap);

        System.out.println(temp);
    }

    // The string of the word we are looking for
    @Override
    public String toString() {
        return createString(map.size(), map);
    }

    public boolean wordFound(){
        if(treeMap.size() == map.size()){
            System.out.println("\n" + "--------------- VICTORY -------------------");
            return true;
        }
        return false;
    }

    public int getLives(){
        return lives;
    }

    public Boolean wasLetterCorrect;
    public String displayString = "";
}

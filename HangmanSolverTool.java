import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HangmanSolverTool {

    ArrayList<Character> bannedLetters;

    public HangmanSolverTool(ArrayList<Character> bannedLetters) {
        this.bannedLetters = bannedLetters;
    }

    public void start() {

        Map<Integer, Character> lettersAndIndexOfInput;
        Scanner scanner;
        String confirmedLetters;
        scanner = new Scanner(System.in);

        System.out.println("Enter the letters that you know and the ones you do not know as: \"_\"");
        System.out.println("Example: c___ee. For the word \"coffee\" if you knew it has \"c\" and \"e\".");
        confirmedLetters = scanner.nextLine().toUpperCase();

        lettersAndIndexOfInput = getLettersAndIndexOfInput(confirmedLetters);
        if (confirmedLetters.length() < 2){
            System.out.println("Word needs to be at least two letters long");
            start();
        }
        else {
            try {
                printMatchingWords(confirmedLetters, lettersAndIndexOfInput);
            } catch (IOException e) {
                throw new RuntimeException("Something went wrong with using the txt file.");
            }
        }
        scanner.close();
    }
    private void printMatchingWords(String confirmedLetters, Map<Integer, Character> lettersAndIndexOfInput) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Dictionary file path goes here."));
        Map<Character, Integer> totalLettersOfAllWords = new HashMap<>();

        for (String currentWord = reader.readLine(); currentWord != null; currentWord = reader.readLine()){
            if (currentWord.equals(confirmedLetters) || !confirmedLetters.contains("_")){
                System.out.println("Your word has been solved!!");
                System.exit(1);
            }
            else if (lettersAndIndexOfInput.size() > 0){
                if (matchesLettersAtIndex(currentWord, lettersAndIndexOfInput, confirmedLetters, totalLettersOfAllWords)){
                    System.out.println(currentWord);
                }
            }
            else {
                if(matchesWordsWithLength(currentWord, confirmedLetters, totalLettersOfAllWords)){
                    System.out.println(currentWord);
                }
            }

        }


        mostLetters(totalLettersOfAllWords, confirmedLetters, lettersAndIndexOfInput);
        reader.close();
    }


    private Map<Integer, Character> getLettersAndIndexOfInput(String confirmedLetters){
        Map<Integer, Character> lettersAndIndexOfInput = new HashMap<>();
        for (int i = 0; i < confirmedLetters.length(); i++){
            if (confirmedLetters.charAt(i) != '_'){
                lettersAndIndexOfInput.put(i, confirmedLetters.charAt(i));
            }
        }
        return lettersAndIndexOfInput;
    }
    private boolean matchesWordsWithLength(String currentWord, String confirmedLetters, Map<Character, Integer> totalLettersOfAllWords){

        if (currentWord.length() != confirmedLetters.length()){
            return false;
        }

        addLettersToList(currentWord, confirmedLetters, totalLettersOfAllWords);
        return true;
    }
    private void addLettersToList(String currentWord, String confirmedLetters, Map<Character, Integer> totalLettersOfAllWords){
        Set<Character> addedLetters = new HashSet<>();
        for (int i = 0; i < currentWord.length(); i++){

            int count = totalLettersOfAllWords.getOrDefault(currentWord.charAt(i), 0);

            if (!addedLetters.contains(currentWord.charAt(i)) && confirmedLetters.charAt(i) == '_' &&
                    !confirmedLetters.contains(String.valueOf(currentWord.charAt(i)))){

                addedLetters.add(currentWord.charAt(i));
                totalLettersOfAllWords.put(currentWord.charAt(i), count + 1);
            }
        }
    }
    private boolean matchesLettersAtIndex(String currentWord, Map<Integer, Character> lettersAndIndexOfInput, String confirmedLetters,
                                                 Map<Character, Integer> totalLettersOfAllWords) {


        for (Map.Entry<Integer, Character> entry : lettersAndIndexOfInput.entrySet()){
            int index = entry.getKey();
            char letter = entry.getValue();

            if (currentWord.length() != confirmedLetters.length() || currentWord.charAt(index) != letter){
                return false;
            }
        }

        addLettersToList(currentWord, confirmedLetters, totalLettersOfAllWords);
        return true;
    }
    private void mostLetters(Map<Character, Integer> totalLettersOfAllWords, String confirmedLetters,
                                    Map<Integer, Character> lettersAndIndexOfInput) throws IOException {
        if (totalLettersOfAllWords.size() == 0){
            System.out.println("This word does not exist.");
        }
        else {
            Scanner scanner = new Scanner(System.in);
//            values are templates for the real value
            int max = 0;
            char mostLetter = 'a';
            boolean wordExists = false;

            for (Map.Entry<Character, Integer> entry : totalLettersOfAllWords.entrySet()){

                if (entry.getValue() > max && !bannedLetters.contains(entry.getKey())){
                    max = entry.getValue();
                    mostLetter = entry.getKey();
                    wordExists = true;
                }
            }
            if (totalLettersOfAllWords.size() == 0 || !wordExists){
                System.out.println("Word does not exist");
                System.exit(1);
            }


            feedbackOnComputerGuess(totalLettersOfAllWords,mostLetter,confirmedLetters,scanner,lettersAndIndexOfInput);
            scanner.close();
        }

    }
    private void feedbackOnComputerGuess(Map<Character, Integer> totalLettersOfAllWords, char mostLetter, String confirmedLetters,
                                         Scanner scanner, Map<Integer, Character> lettersAndIndexOfInput) throws IOException {

        System.out.println("Mathematically speaking " + mostLetter + " is the best next guess");
        System.out.println("Was this guess correct? (Y/N)");
        String answer = scanner.nextLine().toUpperCase();
        if (answer.equals("Y")){
            newInput(confirmedLetters, lettersAndIndexOfInput, totalLettersOfAllWords, mostLetter);
        }
        else if(answer.equals("N")){
            totalLettersOfAllWords.remove(mostLetter);
            bannedLetters.add(mostLetter);
            mostLetters(totalLettersOfAllWords, confirmedLetters, lettersAndIndexOfInput);
        }
        else {
            mostLetters(totalLettersOfAllWords, confirmedLetters, lettersAndIndexOfInput);
        }
    }

    private void newInput(String confirmedLetters, Map<Integer, Character> lettersAndIndexOfInput,
                                 Map<Character, Integer> totalLettersOfAllWords, char mostLetter) throws IOException {
        String newInput;

        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the new letter in its place. must contain " + mostLetter + ".");
            System.out.println("Previous input was: " + confirmedLetters);
            newInput = scanner.nextLine().toUpperCase();

        }while (!inputRequirements(newInput, lettersAndIndexOfInput, confirmedLetters, totalLettersOfAllWords, mostLetter));
        lettersAndIndexOfInput = getLettersAndIndexOfInput(newInput);

        printMatchingWords(newInput, lettersAndIndexOfInput);
    }
    private boolean inputRequirements(String input, Map<Integer, Character> lettersAndIndexOfInput,
                                             String confirmedLetters, Map<Character, Integer> totalLettersOfAllWords, char mostFrequentLetter){
        String mostletterString = String.valueOf(mostFrequentLetter);


        for (int i = 0; i < input.length(); i++){
            if (!lettersAndIndexOfInput.containsKey(i)){
                if (input.charAt(i) != '_' && input.charAt(i) != mostFrequentLetter){
                    return false;
                }
            }
        }
        if (!matchesLettersAtIndex(input, lettersAndIndexOfInput, confirmedLetters, totalLettersOfAllWords) || !input.contains(mostletterString)){
            return false;
        }
        return true;
    }

}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class hangmanSolverTool {
    public static void main(String[] args) throws IOException {
        ArrayList<Character> bannedLetters = new ArrayList<>();
        start(bannedLetters);
    }

    private static void start(ArrayList<Character> bannedLetters) throws IOException {

        Map<Integer, Character> lettersAndIndexOfInput;
        Scanner scanner;
        String confirmedLetters;


        scanner = new Scanner(System.in);
        System.out.println("Enter the letters that you know and the ones you do not know as _");
        System.out.println("Example H_ll_");
        confirmedLetters = scanner.nextLine().toUpperCase();

        lettersAndIndexOfInput = getLettersAndIndexOfInput(confirmedLetters);
        if (lettersAndIndexOfInput.size() == 0){
            System.out.println("You need to know at least one letter.");
        }




        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Haaima Computers\\Desktop\\dictionary.txt"));
        Map<Character, Integer> totalLettersOfAllWords = new HashMap<>();


        for (String currentWord = reader.readLine(); currentWord != null; currentWord = reader.readLine()){
            if (currentWord.equals(confirmedLetters)){
                System.out.println(currentWord);
                System.out.println("Word is already solved");
                System.exit(1);
            }
            else if (matchesLettersAtIndex(currentWord, lettersAndIndexOfInput, confirmedLetters, totalLettersOfAllWords)){
                System.out.println(currentWord);
            }

        }

        mostLetters(totalLettersOfAllWords, bannedLetters, confirmedLetters, lettersAndIndexOfInput);

        scanner.close();
    }

    private static Map<Integer, Character> getLettersAndIndexOfInput(String confirmedLetters){
        Map<Integer, Character> lettersAndIndexOfInput = new HashMap<>();
        for (int i = 0; i < confirmedLetters.length(); i++){
            if (confirmedLetters.charAt(i) != '_'){
                lettersAndIndexOfInput.put(i, confirmedLetters.charAt(i));
            }
        }
//        System.out.println(lettersAndIndexOfInput);
        return lettersAndIndexOfInput;
    }
    private static boolean matchesLettersAtIndex(String currentWord, Map<Integer, Character> lettersAndIndexOfInput, String confirmedLetters,
                                                 Map<Character, Integer> totalLettersOfAllWords) {

        ArrayList<Character> addedLetters = new ArrayList<>();
        for (Map.Entry<Integer, Character> entry : lettersAndIndexOfInput.entrySet()){
            int index = entry.getKey();
            char letter = entry.getValue();

            if (currentWord.length() != confirmedLetters.length() || currentWord.charAt(index) != letter){
                return false;
            }

        }

        for (int i = 0; i < currentWord.length(); i++){

            int count = totalLettersOfAllWords.getOrDefault(currentWord.charAt(i), 0);

            if (!addedLetters.contains(currentWord.charAt(i)) && confirmedLetters.charAt(i) == '_' &&
                    !confirmedLetters.contains(String.valueOf(currentWord.charAt(i)))){

                addedLetters.add(currentWord.charAt(i));
                totalLettersOfAllWords.put(currentWord.charAt(i), count + 1);
            }


        }

        return true;
    }
    private static void mostLetters(Map<Character, Integer> totalLettersOfAllWords, ArrayList<Character> bannedLetters, String confirmedLetters,
                                    Map<Integer, Character> lettersAndIndexOfInput) throws IOException {
        if (totalLettersOfAllWords.size() == 0){
            System.out.println("This word does not exist.");
        }
        else {
            Scanner scanner = new Scanner(System.in);
//            values are templates for the real value
            int max = 0;
            char mostLetter = 'a';


            for (Map.Entry<Character, Integer> entry : totalLettersOfAllWords.entrySet()){

                if (entry.getValue() > max && !bannedLetters.contains(entry.getKey())){
                    max = entry.getValue();
                    mostLetter = entry.getKey();
                }
            }

            System.out.println("Mathematically speaking " + mostLetter + " is the best next guess");

            System.out.println("Was this guess correct? (Y/N)");
            String awnser = scanner.nextLine().toUpperCase();
            if (awnser.equals("Y")){
                newWordWithKnowLetters(confirmedLetters, lettersAndIndexOfInput);
                start(bannedLetters);
            }
            else if(awnser.equals("N")){
                totalLettersOfAllWords.remove(mostLetter);
                bannedLetters.add(mostLetter);
                mostLetters(totalLettersOfAllWords, bannedLetters, confirmedLetters, lettersAndIndexOfInput);
            }
            else {
                mostLetters(totalLettersOfAllWords, bannedLetters, confirmedLetters, lettersAndIndexOfInput);
            }
            scanner.close();
        }

    }

    private static void newWordWithKnowLetters(String confirmedLetters, Map<Integer, Character> lettersAndIndexOfInput) {
        String newInput;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the new letter in its place");
            System.out.println("Previous input was: " + confirmedLetters);
            newInput = scanner.nextLine().toUpperCase();

            for (Map.Entry<Integer, Character> entry : lettersAndIndexOfInput.entrySet()){
                int index = entry.getKey();
                int character = entry.getValue();

            }


        }while (newInput.length() != confirmedLetters.length());
    }
}

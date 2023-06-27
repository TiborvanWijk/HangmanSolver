import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class hangmanSolverTool {
    public static void main(String[] args) throws IOException {
        Map<Integer, Character> lettersAndIndexOfInput = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the letters that you know and the ones you do not know as _");
        System.out.println("Example H_ll_");
        String confirmedLetters = scanner.nextLine().toUpperCase();

        lettersAndIndexOfInput = getLettersAndIndexOfInput(confirmedLetters);

        System.out.println("You need to know at least one letter");


        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Haaima Computers\\Desktop\\dictionary.txt"));

        Map<Character, Integer> totalLettersOfAllWords = new HashMap<>();


        for (String currentWord = reader.readLine(); currentWord != null; currentWord = reader.readLine()){
            if (matchesLettersAtIndex(currentWord, lettersAndIndexOfInput, confirmedLetters, totalLettersOfAllWords)){
                System.out.println(currentWord);
            }
        }

        mostLetters(totalLettersOfAllWords);




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
        if (lettersAndIndexOfInput.size() != 0){
            for (Map.Entry<Integer, Character> entry : lettersAndIndexOfInput.entrySet()){
                int index = entry.getKey();
                char letter = entry.getValue();

                if (currentWord.length() != confirmedLetters.length() || currentWord.charAt(index) != letter){
                    return false;
                }

            }
        }
        else {

        }

        for (int i = 0; i < currentWord.length(); i++){

            int count = totalLettersOfAllWords.getOrDefault(currentWord.charAt(i), 0);
            if (!addedLetters.contains(currentWord.charAt(i)) && confirmedLetters.charAt(i) == '_'){
                addedLetters.add(currentWord.charAt(i));
                totalLettersOfAllWords.put(currentWord.charAt(i), count + 1);
            }


        }
        return true;
    }
    private static void mostLetters(Map<Character, Integer> totalLettersOfAllWords){

        int max = 0;
        char mostLetter = 'a';

        for (Map.Entry<Character, Integer> entry : totalLettersOfAllWords.entrySet()){

            if (entry.getValue() > max){
                max = entry.getValue();
                mostLetter = entry.getKey();
            }
        }

        System.out.println("Mathematically speaking " + mostLetter + " is the best next guess");

        }
    }


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\tibor\\Desktop\\dictionary.txt"));



        for (String currentWord = reader.readLine(); currentWord != null; currentWord = reader.readLine()){
            for (int i = 0; i < lettersAndIndexOfInput.size(); i++){

//                System.out.println(i);
//                System.out.println(lettersAndIndexOfInput.get(i));
//                System.out.println(currentWord.indexOf(i));
//                && currentWord.length() == confirmedLetters.length()

                if (lettersAndIndexOfInput.get(i) == currentWord.charAt(lettersAndIndexOfInput.)){
                    System.out.println(currentWord);
                }
            }
        }
    }
//    String currentWord;
//        while ((currentWord = reader.readLine()) != null) {
//        boolean wordMatches = true;
//
//        for (Map.Entry<Integer, Character> entry : lettersAndIndexOfInput.entrySet()) {
//            int index = entry.getKey();
//            char letter = entry.getValue();
//
//            if (currentWord.charAt(index) != letter) {
//                wordMatches = false;
//                break;
//            }
//        }
//
//        if (wordMatches) {
//            System.out.println(currentWord);
//        }
//    }
//
//        reader.close();
//}

    private static Map<Integer, Character> getLettersAndIndexOfInput(String confirmedLetters){
        Map<Integer, Character> lettersAndIndexOfInput = new HashMap<>();
        for (int i = 0; i < confirmedLetters.length(); i++){
            if (confirmedLetters.charAt(i) != '_'){
                lettersAndIndexOfInput.put(i, confirmedLetters.charAt(i));
            }
        }
        System.out.println(lettersAndIndexOfInput);
        return lettersAndIndexOfInput;
    }
    private static void chechLetters(){

    }
}

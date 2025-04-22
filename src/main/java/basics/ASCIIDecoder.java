package basics;
import java.util.ArrayList;

public class ASCIIDecoder {

    /*
     * The 2D array "sentences" contain a set of decimal ASCII code we want you
     * to translate. Each sub-element of this array is a different sentence.
     * Ex : if we pass this array : [ ["72", "101", "108", "108", "111"], ["87", "111", "114", "108", "100"]]
     * to your decode method, you should return : [ "Hello", "World" ].
     *
     * Forbidden characters are passed as an array of int.
     * Each element of this array correspond to the decimal ASCII code
     * of a forbidden character OR null if there's no forbidden character
     * If you encounter one of these forbidden character
     * you must ignore it when you translate your sentence.
     *
     * Use the StringBuilder class and its method appendCodePoint(int) to translate the ASCII code.
     *
     * You should NEVER return null or an array containing null.
     */
    public static String [] decode(int[] forbidden, String[][] sentences){
        StringBuilder[] list = new StringBuilder[sentences.length];
        ArrayList<Integer> forbid = new ArrayList<>();
        if (forbidden != null){
            for (int i = 0 ; i< forbidden.length;i++){
                forbid.add(forbidden[i]);
            }
        }
        for (int i = 0; i<sentences.length;i++){
            list[i] = new StringBuilder();
            for (int j = 0 ; j<sentences[i].length;j++){
                int codePoint = Integer.parseInt(sentences[i][j]);
                if (forbidden == null || forbid.contains(codePoint) == false) {
                    list[i] = list[i].appendCodePoint(codePoint);
                }
            }
        }
        String[] strings = new String[list.length];

        for (int i = 0; i < list.length; i++) {
            strings[i] = list[i].toString();
        }
        return strings;

    }

    public static void main(String[] args) {
        // Test data
        String[][] sentences = {
                {"72", "101", "108", "108", "111"}, // "Hello"
                {"87", "111", "114", "108", "100"}  // "World"
        };
        int[] forbidden = {}; // No forbidden characters

        // Call the decode function
        String[] result = decode(forbidden, sentences);

        // Print the result
        for (String s : result) {
            System.out.println(s);
        }
    }

}
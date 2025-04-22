package basics;

import java.util.Locale;

public class StringUtils {

    /**
     * Split a string according to a delimiter
     *
     * @param str The string to split
     * @param delimiter The delimiter
     * @return An array containing the substring which fall
     *          between two consecutive occurrences of the delimiter.
     *          If there is no occurrence of the delimiter, it should
     *          return an array of size 1 with the string at element 0
     */
    public static String[] split(String str, char delimiter) {
        int cnt = 1;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == delimiter) {
                cnt++;
            }
        }
        String[] array = new String[cnt];
        int index = 0;
        String current = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == delimiter) {
                array[index] = current;
                current = "";
                index++;
            } else {
                current = current.concat(String.valueOf(str.charAt(i)));
            }
        }
        array[index] = current;  // Move this outside the loop
        return array;
    }

    /**
     * Find the first occurrence of a substring in a string
     *
     * @param str The string to look in
     * @param sub The string to look for
     * @return The index of the start of the first appearance of
     *          the substring in str or -1 if sub does not appear
     *          in str
     */
    public static int indexOf(String str, String sub) {
        for (int i = 0; i < str.length(); i++) {
            String look = "";
            for (int j = 0; j < sub.length(); j++) {
                if (str.length() - i < sub.length()) {
                    return -1;
                } else if (str.charAt(i + j) == sub.charAt(j)) {
                    look = look.concat(String.valueOf(sub.charAt(j)));
                    continue;
                } else {
                    look = "";
                    break;
                }
            }
            if (look.equals(sub)) {
                return i;
            }
        }
        return -1;  // Add return statement for when the substring is not found
    }

    /**
     * Convert a string to lowercase
     *
     * @param str The string to convert
     * @return A new string, same as str but with every
     *          character put to lower case.
     */
    public static String toLowerCase(String str) {
        return str.toLowerCase();
    }

    /**
     * Check if a string is a palindrome
     *
     * A palindrome is a sequence of characters that is the
     * same when read from left to right and from right to
     * left.
     *
     * @param str The string to check
     * @return true if str is a palindrome, false otherwise
     */
    public static boolean palindrome(String str) {
        for (int i = 0; i < (str.length()) / 2; i++) {
            if (str.charAt(i) == str.charAt(str.length() - 1 - i)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}


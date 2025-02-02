/*
 * Objective: Implement a string processor that performs various manipulations and searches on strings.
    • Details: Create a class StringProcessor that provides methods to format, split, and search within strings.
    • Functions to Implement:
        ◦ reverseString(String str): Returns the reverse of the given string.
        ◦ countOccurrences(String text, String sub): Counts how many times sub appears in text.
        ◦ splitAndCapitalize(String str): Splits the string by spaces and capitalizes each word.
 */

class StringProcessor {

    public String reverseString(String str) {
        StringBuilder reversed = new StringBuilder(str);
        return reversed.reverse().toString();
    }

    public int countOccurrences(String text, String sub) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(sub, index)) != -1) {
            count++;
            index += sub.length();
        }
        return count;
    }

    public String splitAndCapitalize(String str) {
        String[] words = str.split(" ");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            capitalized.append(word.substring(0, 1).toUpperCase())
                       .append(word.substring(1).toLowerCase())
                       .append(" ");
        }
        return capitalized.toString().trim();
    }
}

public class task5 {
    public static void main(String[] args) {


        StringProcessor processor = new StringProcessor();

        System.out.println("Reversed String: " + processor.reverseString("hello world"));
        System.out.println("Occurrences of 'is': " + processor.countOccurrences("this is a test :D is it working", "is"));
        System.out.println("Capitalized Words: " + processor.splitAndCapitalize("hello world from java"));
    }
}

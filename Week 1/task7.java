/*
 * Objective: Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
    • Details: The goal is to develop a function that, given a string s and a non-empty string p, returns all the starting indices of the substrings in s that are anagrams of p. An anagram of a string is another string that contains the same characters, only the order of characters can be different. For example, "abc" is an anagram of "bca".
    • Functions to Implement:
        ◦ List<Integer> findAnagrams(String s, String p): Returns a list of starting indices of the anagrams of p in s.
 */


import java.util.*;

class AnagramFinder {

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s.length() < p.length()) {
            return result;
        }

        int[] pCount = new int[26];
        int[] sCount = new int[26];

        for (char c : p.toCharArray()) {
            pCount[c - 'a']++;
        }

        for (int i = 0; i < p.length(); i++) {
            sCount[s.charAt(i) - 'a']++;
        }

        if (Arrays.equals(pCount, sCount)) {
            result.add(0);
        }

        for (int i = p.length(); i < s.length(); i++) {
            sCount[s.charAt(i) - 'a']++;
            sCount[s.charAt(i - p.length()) - 'a']--;

            if (Arrays.equals(pCount, sCount)) {
                result.add(i - p.length() + 1);
            }
        }

        return result;
    }
}

public class task7 {
    public static void main(String[] args) {
        AnagramFinder finder = new AnagramFinder();

        System.out.println(finder.findAnagrams("cbaebabacd", "abc"));
        System.out.println(finder.findAnagrams("abab", "ab"));
    }
}

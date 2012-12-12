
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author joshi18
 */
public class SubstringCount {

    int indexToToalCount[];

    public SubstringCount(String str) {
        int totalCount = 0;
        indexToToalCount = new int[str.length()];
        // List<String> allPalindromicSubStrings = new ArrayList<String>();
        String largestPalindrome = str.substring(0, 1);
        char arr[] = str.toCharArray();
        List<ArrayList<Integer>> posToPalndromeLength = new ArrayList<ArrayList<Integer>>();
        totalCount = arr.length;
        for (int i = 0; i < arr.length; ++i) {
            // allPalindromicSubStrings.add(new String(arr,i,1));
            ArrayList<Integer> currentList = new ArrayList<Integer>();
            currentList.add(0);
            currentList.add(1);
            posToPalndromeLength.add(currentList);
            
            for (int j = 0; i > 0 && j < posToPalndromeLength.get(i - 1).size(); ++j) {
                int prevIndex = i - (posToPalndromeLength.get(i - 1).get(j) + 1);
                if (prevIndex >= 0 && arr[i] == arr[prevIndex]) {
                    ++totalCount;
                    indexToToalCount[i] = totalCount;
                    int palLength = i - prevIndex + 1;
                    posToPalndromeLength.get(i).add(palLength);
                    String currentString = new String(arr, prevIndex, palLength);
                    //    allPalindromicSubStrings.add(currentString);
                    if (currentString.length() > largestPalindrome.length()) {
                        largestPalindrome = currentString;
                    }
                }
            }
        }
        System.out.println(totalCount);
        // System.out.println(allPalindromicSubStrings);
        System.out.println(largestPalindrome);
        for (int i = low; i <= high; ++i) {
        }
    }

    public static void main(String args[]) {
        new SubstringCount("bb");
    }
}

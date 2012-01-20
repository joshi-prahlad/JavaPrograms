package source;

import java.util.Arrays;

public class Permutation {
	/**
	 * Assumes that string s2 is sorted and s1 is empty when called for the
	 * first time.It takes a character from s2 add appends it to s1 and calls
	 * permute again on s2 minus the character appended to s1.If we encounter
	 * same character consecutively in s2 then calls permute only one for all
	 * characters which are same.As characters are only deleted and not added to
	 * s2 the string s2 remains sorted all the time.
	 * 
	 * @param s1
	 * @param s2
	 * @param lastChar
	 */
	private static void permute(String s1, String s2) {
		if (s2.length() == 0) {
			System.out.println(s1);
		} else {
			for (int i = 0; i < s2.length(); ++i) {
				if (i == 0 || (s2.charAt(i) != s2.charAt(i - 1))) {
					permute(s1 + (s2.charAt(i)), s2.substring(0, i)
							+ s2.substring(i + 1));
				}
			}
		}

	}

	public static void listUniquePermutations(String s) {
		char charArray[] = s.toCharArray();
		Arrays.sort(charArray);
		permute("", new String(charArray));
	}

	private static void permute2(String s1, int i, Integer swapCount) {
		if (i >= s1.length() - 1) {
			if (swapCount > 0) {
				System.out.println(s1);
				swapCount = 0;
			}

			return;
		}
		for (int j = i; j < s1.length(); ++j) {
			if (i != j && s1.charAt(i) == s1.charAt(j)) {
				continue;
			}
			char[] ca = s1.toCharArray();
			if (s1.charAt(i) != s1.charAt(j)) {
				char c = ca[i];
				ca[i] = ca[j];
				ca[j] = c;
				++swapCount;
			}

			permute2(new String(ca), i + 1, swapCount);

		}
	}

	private static void rec(Integer a, int b) {
		if (b++ == 11)
			return;
		rec(a, b);
		System.out.println(a++);

	}

	public static void main(String args[]) {
		// permute2("abab", 0, 0);
		// rec(1, 1);
		listUniquePermutations("ABC");

	}
}

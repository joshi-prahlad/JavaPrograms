package source;

import java.util.Random;

/*Let A[1.. m] and B[1.. ] be two arbitrary arrays. A common super-sequence of A and B is
 another sequence that contains both A and B as subsequences. Describe an efficient algorithm
 to compute the length of the shortest common super-sequence of A and B
 */
public class ShortestCommonSupersequence {

	static final int MAX_RAND_STRING_LENGTH = 8;
	static final int MIN_RAND_STRING_LENGTH = 3;

	/**
	 * getRandomString generates a random string whose characters are drawn from
	 * the characters present in the array charSet which is passed as the first
	 * argument.The length of the string generated can be specified as the
	 * second argument of the function(optional).Otherwise the length would be a
	 * random number between MIN_RAND_STRING_LENGTH and MAX_RAND_STRING_LENGTH.
	 * 
	 * @param charSet
	 * @param lengths
	 * @return
	 */
	public String getRandomString(final char[] charSet, int... lengths) {
		char randomChars[] = new char[(lengths.length > 0 ? lengths[0]
				: MIN_RAND_STRING_LENGTH
						+ new Random().nextInt(MAX_RAND_STRING_LENGTH + 1))];
		for (int i = 0; i < randomChars.length; ++i) {
			randomChars[i] = charSet[new Random().nextInt(charSet.length)];
		}
		return new String(randomChars);
	}

	public String getShortestCommonSupersequence(String one, String two) {
		int lengthMatrix[][] = new int[one.length() + 1][two.length() + 1];
		for (int row = 0; row <= one.length(); ++row) {
			for (int column = 0; column <= two.length(); ++column) {
				if (row == 0 || column == 0) {
					lengthMatrix[row][column] = row | column;
				} else {
					if (one.charAt(row - 1) == two.charAt(column - 1)) {
						lengthMatrix[row][column] = lengthMatrix[row - 1][column - 1] + 1;
					} else {
						lengthMatrix[row][column] = Math.min(
								lengthMatrix[row - 1][column],
								lengthMatrix[row][column - 1]) + 1;
					}
				}
			}
		}
		for (int[] row : lengthMatrix) {
			for (int val : row) {
				System.out.print(val + " ");
			}
			System.out.println();
		}
		return "";
	}

	public static void main(String[] args) {
		ShortestCommonSupersequence obj = new ShortestCommonSupersequence();
		String str1 = obj.getRandomString(new char[] { 'A', 'B', 'C', 'D' }, 5);
		String str2 = obj.getRandomString(new char[] { 'A', 'B', 'C', 'D' }, 6);
		System.out.println(str1);
		System.out.println(str2);
		obj.getShortestCommonSupersequence(str1, str2);
	}
}

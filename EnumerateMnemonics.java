package source;

public class EnumerateMnemonics {
	private String[] keyPad = { "", "", "ABC", "DEF", "GHI", "JKL", "MNO",
			"PQRS", "TUV", "WXYZ" };

	public void ListMnemonics(String number) {
		recursiveMnemonics("", number);
	}

	/**
	 * Accepts a empty string and a string representing phone number and list
	 * all the possible combinations of the phone numbers by replacing digits in
	 * the number with the valid characters which are specified in the keyPad
	 * array.
	 * 
	 * @param currentCombination
	 * @param number
	 */
	public void recursiveMnemonics(String currentCombination, String number) {
		if (number.length() == 0) {
			System.out.println(currentCombination);
			return;
		}
		String alphabets = keyPad[Character.digit(number.charAt(0), 10)];
		/**
		 * To stop the premature end of the method call when some digits are
		 * mapped to empty strings in keyPad like 0 and 1.Meaning 213 and 23
		 * will give the same output.
		 */
		if (alphabets.isEmpty()) {
			recursiveMnemonics(currentCombination, number.substring(1));
		} else {
			for (int j = 0; j < alphabets.length(); ++j) {
				recursiveMnemonics(currentCombination + alphabets.charAt(j),
						number.substring(1));
			}
		}

	}

	public static void main(String args[]) {
		new EnumerateMnemonics().ListMnemonics("6378687");
	}
}

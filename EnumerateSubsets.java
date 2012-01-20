package source;

public class EnumerateSubsets {

	public void listSubsets(String str) {
		recursiveSubsets("", str);
	}

	private void recursiveSubsets(String subset, String str) {
		if (str.length() == 0) {
			printString(subset);
			return;
		}
		recursiveSubsets(subset + str.charAt(0), str.substring(1));
		recursiveSubsets(subset, str.substring(1));
	}

	private void printString(String s) {
		System.out.print("{");
		for (int i = 0; i < s.length(); ++i) {
			System.out.print(s.charAt(i));
			if (i != s.length() - 1) {
				System.out.print(",");
			}
		}
		System.out.println("}");
	}

	public static void main(String args[]) {
		new EnumerateSubsets().listSubsets("abcd");
	}
}

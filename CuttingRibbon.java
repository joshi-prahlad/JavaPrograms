/*
 * 
 * Prob Statement:
Polycarpus has a ribbon, its length is n. He wants to cut the ribbon in a way that fulfils the following two conditions:

After the cutting each ribbon piece should have length a, b or c.
After the cutting the number of ribbon pieces should be maximum.
Help Polycarpus and find the number of ribbon pieces after the required cutting.

Input
The first line contains four space-separated integers n, a, b and c (1 ≤ n, a, b, c ≤ 4000) — the length of the original ribbon and the acceptable lengths of the ribbon pieces after the cutting, correspondingly. The numbers a, b and c can coincide.

Output
Print a single number — the maximum possible number of ribbon pieces. It is guaranteed that at least one correct ribbon cutting exists.

Sample test(s)
input
5 5 3 2
output
2
input
7 5 5 2
output
2

Sol:
using DP
f(n) = max(1+ f(n-a),1+f(n-b),1+f(n-c))
f(0) = 0 

 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CuttingRibbon {

	int maxPieces = 0;

	public CuttingRibbon(int n, int a, int b, int c) {
		Set<Integer> numbers = new HashSet<Integer>();
		if (a <= n) {
			numbers.add(a);
		}
		if (b <= n) {
			numbers.add(b);
		}
		if (c <= n) {
			numbers.add(c);
		}
		int pieces[] = new int[n + 1];
		Arrays.fill(pieces, 0);
		for (int i = 1; i <= n; ++i) {
			for (int el : numbers) {
				if (i == el) {
					pieces[i] = Math.max(1, pieces[i]);
				} else if (i > el) {
					if (pieces[i - el] > 0) {
						pieces[i] = Math.max(pieces[i - el] + 1, pieces[i]);
					}
				}
			}
		}
		maxPieces = pieces[n];
	}

	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int a = scan.nextInt();
		int b = scan.nextInt();
		int c = scan.nextInt();
		CuttingRibbon cs = new CuttingRibbon(n, a, b, c);
		System.out.println(cs.maxPieces);
	}
}

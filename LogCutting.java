
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 *
 * Cutting Sticks 
 * You have to cut a wood stick into pieces. The most affordable
 * company, The Analog Cutting Machinery, Inc. (ACM), charges money according to
 * the length of the stick being cut. Their procedure of work requires that they
 * only make one cut at a time. It is easy to notice that different selections
 * in the order of cutting can led to different prices. For example, consider a
 * stick of length 10 meters that has to be cut at 2, 4 and 7 meters from one
 * end. There are several choices. One can be cutting first at 2, then at 4,
 * then at 7. This leads to a price of 10 + 8 + 6 = 24 because the first stick
 * was of 10 meters, the resulting of 8 and the last one of 6. Another choice
 * could be cutting at 4, then at 2, then at 7. This would lead to a price of 10
 * + 4 + 6 = 20, which is a better price.
 *
 * Your boss trusts your computer abilities to find out the minimum cost for
 * cutting a given stick.
 *
 * Input  *
 * The input will consist of several input cases. The first line of each test
 * case will contain a positive number l that represents the length of the stick
 * to be cut. You can assume l < 1000. The next line will contain the number n
 * (n < 50) of cuts to be made. The next line consists of n positive numbers ci
 * ( 0 < ci < l) representing the places where the cuts have to be done, given
 * in strictly increasing order.
 *
 * An input case with l = 0 will represent the end of the input.
 *
 * Output  *
 * You have to print the cost of the optimal solution of the cutting problem,
 * that is the minimum cost of cutting the given stick. Format the output as
 * shown below. Sample Input  *
 * 100 3 25 50 75 10 4 4 5 7 8 0 Sample Output  *
 * The minimum cutting is 200. The minimum cutting is 22.
 *
 *

 *
 * @author joshi18
 */
class Main {

    int minCost = Integer.MAX_VALUE;

    Main(int logLength, int[] marks) {
        int comp[][] = new int[marks.length][marks.length];
        for (int k = 0, l = 0; l < marks.length; ++l) {
            for (int i = k, j = l; i < marks.length && j < marks.length; ++i, ++j) {
                if (i == j) {
                    comp[i][j] = 0;
                    continue;
                }
                if (i == j - 1) {
                    comp[i][j] = 0;
                    continue;
                }
                int min = Integer.MAX_VALUE;
                for (int m = i + 1; m < j; ++m) {
                    int current = marks[j] - marks[i] + comp[i][m] + comp[m][j];
                    if (min > current) {
                        min = current;
                    }
                }
                comp[i][j] = min;
            }
        }
        this.minCost = comp[0][marks.length - 1];
    }

    public int getMinCost() {
        return this.minCost;
    }

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            int l = scan.nextInt();
            if (l == 0) {
                break;
            } else {
                int marks = scan.nextInt();
                int marksArr[] = new int[marks + 2];
                marksArr[0] = 0;
                marksArr[marksArr.length - 1] = l;
                for (int i = 1; i < marksArr.length - 1; ++i) {
                    marksArr[i] = scan.nextInt();
                }
                Main lc = new Main(l, marksArr);
                System.out.println("The minimum cutting is " + lc.getMinCost() + ".");
            }

        }

    }
}

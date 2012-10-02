/*You have an N x N chessboard and you wish to place N kings on it. Each row and column should contain exactly one king, and no two kings should attack each other (two kings attack each other if they are present in squares which share a corner).

The kings in the first K rows of the board have already been placed. You are given the positions of these kings as an array pos[ ]. pos[i] is the column in which the king in the ith row has already been placed. All indices are 0-indexed. In how many ways can the remaining kings be placed?

Input:
The first line contains the number of test cases T. T test cases follow. Each test case contains N and K on the first line, followed by a line having K integers, denoting the array pos[ ] as described above.

Output:
Output the number of ways to place kings in the remaining rows satisfying the above conditions. Output all numbers modulo 1000000007.

Constraints:
1 <= T <= 20
1 <= N <= 16
0 <= K <= N
0 <= pos_i < N
The kings specified in the input will be in different columns and not attack each other.
* 
* 
5
4 1
2
3 0

5 2
1 
3
4 4
1 
3 
0 
2
6 1
2
*/

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author joshi18
 */
public class Solution {

    boolean board[][];
    Set<Integer> occupiedColumns = new HashSet<Integer>();

    Solution(int n) {
        board = new boolean[n][n];
    }

    Solution() {
    }

    void setBoard(boolean[][] board) {
        this.board = board;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                if (board[i][j]) {
                    occupiedColumns.add(j);
                }
            }
        }
    }

    int waysToPlace(int k) {
        if (k == board.length - 1) {
            return 1;
        }
        int totalWays = 0;
        for (int pos = 0; pos < board.length; ++pos) {
            int ways = 1;
            if (!isAttack(k + 1, pos)) {
                board[k + 1][pos] = true;
                occupiedColumns.add(pos);
                ways *= waysToPlace(k + 1);
                board[k + 1][pos] = false;
                occupiedColumns.remove(pos);
            } else {
                ways = 0;
            }
            totalWays += ways;
        }
        return totalWays;
    }

    boolean isAttack(int row, int col) {
        if (occupiedColumns.contains(col)) {
            return true;
        }
        if ((col > 0 && row > 0 && board[row - 1][col - 1]) || (col < board.length - 1 && row > 0 && board[row - 1][col + 1])) {
            return true;
        }
        return false;
    }

    void printArray() {
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String args[]) {
        //Solution sol = new Solution(5);        
        // System.out.println(sol.waysToPlace(-1));
        //sol.printArray();
        readInput();
    }

    static void readInput() {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        for (int i = 0; i < t; ++i) {
            int n = scan.nextInt();
            //System.out.println(" n "+n );            
            int k = scan.nextInt();
            // System.out.println(" k "+k );
            boolean board[][] = new boolean[n][n];
            for (int row = 0; row < k; ++row) {
                int col = scan.nextInt();
                board[row][col] = true;
            }
            Solution s = new Solution();
            s.setBoard(board);
            int ways = s.waysToPlace(k - 1);
            System.out.println(ways);
        }
    }
}


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/*
 *Computes Maximum Weight Matching using Min-sum algorithm.
 *Input format: For a bipartite graph Kn,n(V1,V2,E),V1=(a1,
 *a2...an) and V2=(b1,b2...bn), the first line
 *should contain the value for 'n' then 'n' lines follow
 *Each line 'i' contain 'n' weights for the edges (ai,b1) 
 *(ai,b2).....(ai,bn)
 *
 * Sample InputFor a graph with n=3 and V1=(a1,a2,a3) and V2=(b1,b2,b3)
 * and a1->b1=10, a1->b2=0, a1->b3=25, a2->b1=25,a2->b2=15,a2->b3=0,
 * a3->b1=12,a3->b2=3 and a3->b3=7
 * 
 * 3
 * 10 0 25
 * 25 15 0
 * 12 3 7
 * 
 * 
 * To run
 * 
 * java MWM < input.txt
 * 
 */
/**
 *
 * @author joshi18
 */
public class SimplifiedMinSum {

    private static int CONVERGANCE_FACTOR = 7;
    private static final int MAX_WEIGHT = 600;

    public void solve(int n, int[][] weights, int edges, BufferedWriter br)
            throws Exception {
        int max_iterations = n * MAX_WEIGHT;
        long messageAlphaK[][] = new long[n][n];
        long messageAlphaK_1[][] = new long[n][n];
        long messageBetaK[][] = new long[n][n];
        long messageBetaK_1[][] = new long[n][n];

        int piK[] = new int[n];
        Queue<int[]> piOld = new LinkedList<int[]>();
        int k = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                messageAlphaK[i][j] = weights[i][j];
                messageBetaK[j][i] = weights[i][j];
            }
        }
        while (true) {
            ++k;
            if (k >= max_iterations) {
                System.out
                        .println("No solution found with convergance factor of "
                        + CONVERGANCE_FACTOR
                        + " in "
                        + k
                        + " iterations");
                break;
            }
            copy(messageAlphaK_1, messageAlphaK);
            copy(messageBetaK_1, messageBetaK);

            long maxBeta1[] = new long[n], maxAlpha1[] = new long[n];
            int maxAlpaIndex1[] = new int[n], maxAlphaIndex2[] = new int[n], maxBetaIndex1[] = new int[n], maxBetaIndex2[] = new int[n];
            long maxBeta2[] = new long[n], maxAlpha2[] = new long[n];
            getMax1_2(messageBetaK_1, maxBeta1, maxBeta2, maxBetaIndex1,
                    maxBetaIndex2);
            getMax1_2(messageAlphaK_1, maxAlpha1, maxAlpha2, maxAlpaIndex1,
                    maxAlphaIndex2);
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    /*
                     * int maxAlpha = Integer.MIN_VALUE, maxBeta =
                     * Integer.MIN_VALUE; for (int l = 0; l < n; ++l) { if (l !=
                     * j) { if (messageBetaK_1[l][i] > maxBeta) { maxBeta =
                     * messageBetaK_1[l][i]; } } if (l != i) { if
                     * (messageAlphaK_1[l][j] > maxAlpha) { maxAlpha =
                     * messageAlphaK_1[l][j]; } } }
                     */
                    if (maxBetaIndex1[i] != j) {
                        messageAlphaK[i][j] = weights[i][j] - maxBeta1[i];
                    } else {
                        messageAlphaK[i][j] = weights[i][j] - maxBeta2[i];
                    }
                    if (maxAlpaIndex1[j] != i) {
                        messageBetaK[j][i] = weights[i][j] - maxAlpha1[j];
                    } else {
                        messageBetaK[j][i] = weights[i][j] - maxAlpha2[j];
                    }
                }
            }

            for (int i = 0; i < n; ++i) {
                long maxValue = messageBetaK[0][i];
                piK[i] = 0;
                for (int j = 1; j < n; ++j) {
                    if (messageBetaK[j][i] > maxValue) {
                        maxValue = messageBetaK[j][i];
                        piK[i] = j;
                    }
                }
            }
            piOld.add(Arrays.copyOf(piK, piK.length));
            if (piOld.size() > CONVERGANCE_FACTOR) {
                piOld.poll();
            }
            // System.out.print("Iteration " + k + " ");
            // printArray(piK);
            if (piOld.size() >= CONVERGANCE_FACTOR) {
                if (hasCoverged(piOld)) {
                    System.out.println("Converged after " + k + " iterations "
                            + " convergance factor " + CONVERGANCE_FACTOR);

                    br.write("N " + n + " e " + edges + " k " + k);
                    br.newLine();
                    br.flush();
                    break;
                }
            }
        }
    }

    private void getMax1_2(long[][] arr, long max1[], long max2[],
            int maxIndex1[], int maxIndex2[]) {
        for (int i = 0; i < arr.length;) {
            for (int j = 0; j < arr.length; ++j) {
                if (i == 0) {
                    if (arr[i][j] < arr[i + 1][j]) {
                        max1[j] = arr[i + 1][j];
                        maxIndex1[j] = i + 1;
                        max2[j] = arr[i][j];
                        maxIndex2[j] = i;
                    } else {
                        max1[j] = arr[i][j];
                        maxIndex1[j] = i;
                        max2[j] = arr[i + 1][j];
                        maxIndex2[j] = i + 1;
                    }

                } else {
                    if (max1[j] < arr[i][j]) {
                        max2[j] = max1[j];
                        maxIndex2[j] = maxIndex1[j];
                        max1[j] = arr[i][j];
                        maxIndex1[j] = i;
                    } else if (max2[j] < arr[i][j]) {
                        max2[j] = arr[i][j];
                        maxIndex2[j] = i;
                    }
                }
            }
            if (i == 0) {
                i += 2;
            } else {
                ++i;
            }
        }
    }

    private boolean hasCoverged(Queue<int[]> q) {
        Iterator<int[]> it = q.iterator();
        int[] a = it.next();
        while (it.hasNext()) {
            int[] b = it.next();
            boolean isSame = Arrays.equals(a, b);
            if (!isSame) {
                return false;
            }
        }
        return true;
    }

    private void printArray(int[] arr) {
        for (int el : arr) {
            System.out.print(el + " ");
        }
        System.out.println();
    }

    private int getMaxIndex(int arr[]) {
        int max = arr[0], maxIndex = 0;
        for (int i = 1; i < arr.length; ++i) {
            if (max < arr[i]) {
                max = arr[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private void copy(long[][] dest, long[][] src) {
        for (int i = 0; i < src.length; ++i) {
            for (int j = 0; j < src[0].length; ++j) {
                dest[i][j] = src[i][j];
            }
        }
    }

    public int getRandomWeights(int n, int[][] weights) {

        Random rand = new Random();
        int edgeCount = 0;
        for (int row = 0; row < n; ++row) {
            for (int col = 0; col < n; ++col) {
                // int val = rand.nextInt(3);
                // if (val < 2) {
                weights[row][col] = rand.nextInt(MAX_WEIGHT);
                if (weights[row][col] != 0) {
                    ++edgeCount;
                }
                // } else {
                // weights[row][col] = 0;
                // }
            }
        }
        return edgeCount;
    }

    public void getBipartiteGraph(int[][] graph, int[][] bGraph) {

        TreeSet<Integer> redNodes = new TreeSet<Integer>();
        TreeSet<Integer> blueNodes = new TreeSet<Integer>();
        redNodes.add(0);
        assignColor(graph, redNodes, blueNodes, 0);
        System.out.println("Red " + redNodes);
        System.out.println("Blue " + blueNodes);
        int valid = 0, inValid = 0;
        for (Integer el1 : redNodes) {
            for (Integer el2 : blueNodes) {
                if (graph[el1][el2] == 0) {
                    ++inValid;
                } else {
                    ++valid;
                }
            }
        }
        for(int i=0;i<graph.length;++i){
            for(int j=0;j<graph.length;++j){
                if(graph[i][j]!=0){
                    if(redNodes.contains(i)&&redNodes.contains(j)){
                        System.out.println("Error "+i+" "+j);
                    }
                }
            }
        }
        System.out.println("Edge " + valid + " " + inValid);
    }

    public void assignColor(int[][] graph, Set<Integer> redNodes,
            Set<Integer> blueNodes, int node) {
        for (int i = 0; i < graph.length; ++i) {
            if (graph[node][i] != 0) {
                if (redNodes.contains(node)) {
                    if (!redNodes.contains(i) && !blueNodes.contains(i)) {
                        blueNodes.add(i);
                        assignColor(graph, redNodes, blueNodes, i);
                    }
                } else {
                    if (!redNodes.contains(i) && !blueNodes.contains(i)) {
                        redNodes.add(i);
                        assignColor(graph, redNodes, blueNodes, i);
                    }
                }
            }
        }
    }

    public static void main(String args[]) throws Exception {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int graph[][] = new int[n][n];
        int e = scan.nextInt();
        for (int i = 0; i < e; ++i) {
            int row = scan.nextInt();
            int col = scan.nextInt();
            int edgeWeight = scan.nextInt();
            graph[row - 1][col - 1] = edgeWeight;
           // System.out.println("row "+row+" col "+col+" e "+edgeWeight );
        }
        int weights[][] = new int[n / 2][n / 2];
        SimplifiedMinSum sms = new SimplifiedMinSum();
        sms.getBipartiteGraph(graph, weights);
        //System.out.println("Calling solve");
        //	BufferedWriter br = new BufferedWriter(new FileWriter("E:\\Weights"));

//		sms.solve(n, weights, 0, br);

        /*
         * BufferedWriter br = new BufferedWriter(new FileWriter(
         * "/homes/joshi18/Weights5")); for (int i = 0; i < 1000; ++i) {
         * SimplifiedMinSum sms = new SimplifiedMinSum();
         * 
         * int n = 1000 + new Random().nextInt(100);
         * System.out.println("Creating random matrix n " + n); int weights[][]
         * = new int[n][n]; int edges = sms.getRandomWeights(n, weights);
         * System.out.println("Calling solve"); sms.solve(n, weights, edges,
         * br); }
         */
        //br.flush();
        //br.close();
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/*ProbStatement:
 * A permutation is a list of  K numbers, each between 1 and K (both inclusive), that has no duplicate elements.

 Permutation X is lexicographically smaller than Permutation Y iff for some i <= K:

 All of the first i-1 elements of X are equal to first i-1 elements of Y.
 ith element of X is smaller than ith element of Y.
 You are given a permutation P, you can exchange some of its elements as many times as you want in any order. You have to find the lexicographically smallest Permutation that you can obtain from P.

 K is less than 101.

 Input Format:

 First line of input contains K being the size of permutation.
 Next line contains K single spaced numbers indicating the permutation.
 Each of next K lines contains K characters, character j of line i is equal to 'Y' if you can exchange ith and jth element of a permutation, and 'N' otherwise.

 Output Format:

 Print K numbers with a space separating each of them indicating the permutation.

 Sample Input

 3
 3 1 2
 NNY
 NNN
 YNN

 Sample Output

 2 1 3

 Sample Input 

 3
 3 2 1
 NYN
 YNY
 NYN

 Sample Output

 1 2 3 

 In the first example you can exchange first element with last element to obtain 2 1 3 from 3 1 2.


 In the second example, first can be exchanged with second. So, from 3 2 1 , we can get 2 3 1. 2 3 1 is
 lexicographically smaller than 3 2 1. Matrix also says the second the third element can be swapped.
 From this we get, 2 1 3. 2 1 3 is lexicographically smaller than 2 3 1. As the matrix says the first and the
 second element can be swapped, we get 1 2 3 from 2 1 3 and hence the answer 1 2 3.*/

/*
 * Sol:
 * 
 * Treat an element in the input array as a node and if two elements can be swapped then draw an
 * edge between those two nodes. This would result in an undirected graph.
 * In a connected component of this graph, there exists a set of swap moves which would make that
 * component sorted. So if there is only one connected component then output the elements in sorted
 * order otherwise sort the elements in the connected component and then output.
 */
public class Permutation {

	public int[] get(List<Pair> el, final boolean mat[][]) {
		Graph<Pair> exchangeGraph = new Graph<Pair>();
		for (int i = 0; i < mat.length; ++i) {
			exchangeGraph.addEdge(el.get(i), null);
			for (int j = 0; j < mat.length; ++j) {
				if (mat[i][j]) {
					exchangeGraph.addEdge(el.get(i), el.get(j));
				}
			}
		}
		// System.out.println("Graph " + exchangeGraph);
		List<ArrayList<Pair>> connectedComps = exchangeGraph
				.getConnectedComponents();
		// System.out.println("CC " + connectedComps);
		if (connectedComps.size() == 1) {
			Collections.sort(el, new Comparator<Pair>() {
				public int compare(Pair a, Pair b) {
					return a.el - b.el;
				}
			});
			int ans[] = new int[el.size()];
			for (int i = 0; i < ans.length; ++i) {
				ans[i] = el.get(i).el;
			}
			return ans;
		} else {
			int ans[] = new int[el.size()];
			for (List<Pair> cc : connectedComps) {
				List<Pair> sortedByEl = Arrays.asList(new Pair[cc.size()]);
				List<Pair> sortedByIndex = Arrays.asList(new Pair[cc.size()]);
				Collections.copy(sortedByEl, cc);
				Collections.copy(sortedByIndex, cc);
				Collections.sort(sortedByEl, new Comparator<Pair>() {
					public int compare(Pair a, Pair b) {
						return a.el - b.el;
					}
				});
				Collections.sort(sortedByIndex, new Comparator<Pair>() {
					public int compare(Pair a, Pair b) {
						return a.position - b.position;
					}
				});
				for (int i = 0; i < cc.size(); ++i) {
					Pair byEl = sortedByEl.get(i);
					Pair byIndex = sortedByIndex.get(i);
					ans[byIndex.position] = byEl.el;
				}
			}
			return ans;
		}
	}

	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		int count = scan.nextInt();
		List<Pair> el = new ArrayList<Pair>();
		while (count-- > 0) {
			el.add(new Pair(scan.nextInt(), el.size()));
		}
		boolean mat[][] = new boolean[el.size()][el.size()];
		for (int i = 0; i < el.size(); ++i) {
			String line = scan.next();
			for (int j = 0; j < mat[i].length; ++j) {
				mat[i][j] = line.charAt(j) == 'Y' ? true : false;
			}
		}

		int ans[] = new Permutation().get(el, mat);
		for (int value : ans) {
			System.out.print(value + " ");
		}

	}

}

class Pair {
	int el;
	int position;

	Pair(int el, int position) {
		this.el = el;
		this.position = position;
	}

	public String toString() {
		return "El: " + this.el + " Pos: " + this.position;

	}
}

class Graph<T> {

	HashMap<T, LinkedList<T>> adjList;

	Graph() {
		adjList = new HashMap<T, LinkedList<T>>();
	}

	public void addEdge(T a, T b) {
		if (b == null) {
			adjList.put(a, null);
			return;
		}
		LinkedList<T> adjNodes = adjList.get(a);
		if (adjNodes == null) {
			adjNodes = new LinkedList<T>();
			this.adjList.put(a, adjNodes);
		}
		if (adjNodes.contains(b)) {
			return;
		} else {
			adjNodes.add(b);
		}
	}

	public List<ArrayList<T>> getConnectedComponents() {
		HashSet<T> visitedNodes = new HashSet<T>();
		List<ArrayList<T>> comps = new ArrayList<ArrayList<T>>();

		for (T node : adjList.keySet()) {
			if (!visitedNodes.contains(node)) {
				ArrayList<T> currentComp = new ArrayList<T>();
				comps.add(currentComp);
				dfsCC(node, currentComp, visitedNodes);
			}
		}
		return comps;
	}

	private void dfsCC(T node, List<T> currentComp, HashSet<T> visitedNodes) {
		currentComp.add(node);
		visitedNodes.add(node);
		List<T> adjNodes = adjList.get(node);
		if (adjNodes == null) {
			return;
		}
		for (T adjNode : adjNodes) {
			if (!visitedNodes.contains(adjNode)) {
				dfsCC(adjNode, currentComp, visitedNodes);
			}
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (T node : adjList.keySet()) {
			String srcNode = node.toString();
			List<T> adjNodes = adjList.get(node);
			if (adjNodes == null) {
				sb.append(srcNode + " ===> ");
				sb.append("\n");
			} else {
				for (T dest : adjNodes) {
					sb.append(srcNode + " ===> " + dest.toString());
					sb.append("\n");
				}
			}
		}
		return sb.toString();
	}
}

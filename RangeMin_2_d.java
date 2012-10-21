import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/*
 * Ideas used from http://www.cs.ucsb.edu/~suri/cs235/RangeSearching.pdf
 * Suppose you are a fan of auto-racing and want to figure out which drivers are likely to perform well in an upcoming race. Luckily you have access to a log of the times that each racer started and finished their test race the day before.

 The particular rating algorithm you have chosen is to assign each racer R a score that equals the number of other racers who both started after R started and also finished before R finished.

 Note that a lower score generally suggests that the racer is faster, and this rating algorithm keeps from penalizing fast racers who have slow times simply because they are stuck behind a crash or slow racer. Additionally, this rating algorithm does not reward fast racers who pass tons of slow racers in comparison to fast racers who race when there are not many slow racers on the track to pass (compare this with rating a racer based on the net number of passes).



 More formally, you want to write a program that will read the test race log from standard input. The first line of the log contains a single integer n from 0 to 70,000 that represents the number of racers in the log. The next n lines of the test race log have the following format:



 racerId startTime endTime



 where racerId is an integer in the range [0,10^9] and startTime and endTime are both integers such that 0 <= startTime < endTime <= 10^18. Each racerId will be distinct. Also, the collection of all start and end times will not contain any duplicate elements.



 Given such an input, you should print output in the following format:



 racerId score



 where score is the score as defined above for racer racerId. The output lines should be sorted in ascending order ofscore with ties broken by sorting by racerId, also in ascending order. This can be accomplished with a simple sort at the end.
 */
public class RangeMin_2_d {

	List<Info> sortedTimes;
	Node treeRoot;

	public long calculateScore(long low, long high) {
		return csHelper(this.treeRoot, low, high, Long.MIN_VALUE,
				Long.MAX_VALUE);
	}

	public long csHelper(Node root, long low, long high, long currentLow,
			long currentHigh) {
		if (root == null || currentLow > currentHigh) {
			return 0;
		}
		if (isLeaf(root)) {
			if (root.el.startTime > low && root.el.startTime < high) {
				if (root.el.finishTime > low && root.el.finishTime < high) {
					return 1;
				}
			}
			return 0;
		}
		if (currentLow >= low && currentHigh <= high) {
			int index1 = Arrays.binarySearch(root.sortedFinishTime, new Info(
					-1, -1, low), new InfoFTComp());
			int index2 = Arrays.binarySearch(root.sortedFinishTime, new Info(
					-1, -1, high), new InfoFTComp());
			if (index1 >= 0 && index2 >= 0) {
				return getZeroOrPostv(index2 - index1 - 1);
			}
			if (index1 < 0 && index2 >= 0) {
				index1 = index1 * -1 - 1;
				return getZeroOrPostv(index2 - index1);
			}
			if (index1 >= 0 && index2 < 0) {
				index2 = index2 * -1 - 1;
				return getZeroOrPostv(index2 - index1 - 1);
			}
			if (index1 < 0 && index2 < 0) {
				index2 = index2 * -1 - 1;
				index1 = index1 * -1 - 1;
				return getZeroOrPostv(index2 - index1);
			}
		}
		if (root.el.startTime >= low && root.el.startTime < high) {
			return csHelper(root.leftChild, low, high, currentLow,
					root.el.startTime)
					+ csHelper(root.rightChild, low, high, root.el.startTime,
							currentHigh);
		}
		if (root.el.startTime < high) {
			return csHelper(root.rightChild, low, high, root.el.startTime,
					currentHigh);
		}
		if (root.el.startTime >= low) {
			return csHelper(root.leftChild, low, high, currentLow,
					root.el.startTime);
		}
		return 0;
	}

	private int getZeroOrPostv(int val) {
		return val >= 0 ? val : -val;
	}

	@SuppressWarnings("unchecked")
	public void createTree(List<Info> times) {
		sortedTimes = new ArrayList<Info>(times.size());
		for (Info el : times) {
			sortedTimes.add(el);
		}
		Collections.sort(sortedTimes, new InfoSTComp());
		// System.out.println("Sorted Times List " + sortedTimes);
		List<Node> leafNodes = new ArrayList<Node>();
		for (Info el : sortedTimes) {
			Node newNode = new Node(null, null, el);
			newNode.maxEl = el;
			newNode.sortedFinishTime = new Info[1];
			newNode.sortedFinishTime[0] = el;
			leafNodes.add(newNode);
		}
		Node root = createTreeHelper(leafNodes);
		// addLeafNodes(root);
		this.treeRoot = root;
	}

	private Node createTreeHelper(List<Node> nodes) {
		if (nodes.size() == 1) {
			return nodes.get(0);
		}
		List<Node> newList = new ArrayList<Node>();
		int i = 0;
		for (i = 0; i < nodes.size() - 1; i += 2) {
			Node newNode = new Node(nodes.get(i), nodes.get(i + 1),
					nodes.get(i).maxEl);
			newNode.maxEl = nodes.get(i + 1).maxEl;
			newNode.sortedFinishTime = new Info[nodes.get(i).sortedFinishTime.length
					+ nodes.get(i + 1).sortedFinishTime.length];
			merge(nodes.get(i).sortedFinishTime,
					nodes.get(i + 1).sortedFinishTime, newNode.sortedFinishTime);
			newList.add(newNode);
		}
		if (i != nodes.size()) {
			newList.add(nodes.get(i));
		}
		return createTreeHelper(newList);
	}

	private void merge(Info[] array1, Info[] array2, Info[] result) {
		int j = 0, k = 0, l = 0;
		while (j < array1.length && k < array2.length) {
			if (array1[j].finishTime <= array2[k].finishTime) {
				result[l++] = array1[j];
				++j;
			} else {
				result[l++] = array2[k];
				++k;
			}
		}
		while (j < array1.length) {
			result[l++] = array1[j++];
		}
		while (k < array2.length) {
			result[l++] = array2[k++];
		}
	}

	public void printLeafNodes(Node root) {
		if (root == null) {
			return;
		}
		printLeafNodes(root.leftChild);
		if (isLeaf(root)) {
			System.out.println(root.el);
		}
		printLeafNodes(root.rightChild);
	}

	public void printPreorder(Node root) {
		if (root == null) {
			return;
		}
		System.out.println(root.el);
		System.out.println("FinishTimes "
				+ Arrays.toString(root.sortedFinishTime));
		printPreorder(root.leftChild);
		printPreorder(root.rightChild);
	}

	private boolean isLeaf(Node node) {
		return (node.leftChild == null && node.rightChild == null);
	}

	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		int count = scan.nextInt();
		List<Info> times = new ArrayList<Info>();
		while (count > 0) {
			times.add(new Info(scan.nextLong(), scan.nextLong(), scan
					.nextLong()));
			--count;
		}
		RangeMin_2_d obj = new RangeMin_2_d();
		obj.createTree(times);
		List<Result> resultList = new ArrayList<Result>();
		for (Info el : times) {
			resultList.add(new Result(el, obj.calculateScore(el.startTime,
					el.finishTime)));
		}
		Collections.sort(resultList, new Comparator<Result>() {
			public int compare(Result a, Result b) {
				if (a.score == b.score) {
					return (int) (a.info.id - b.info.id);
				} else {
					return (int) (a.score - b.score);
				}
			}
		});

		for (Result r : resultList) {
			System.out.println(r.info.id + " " + r.score);
		}

	}

}

class Result {
	Info info;
	long score;

	Result(Info inf, long s) {
		this.info = inf;
		this.score = s;
	}
}

class Node {
	Node leftChild;
	Node rightChild;
	Info el;
	Info sortedFinishTime[];
	Info maxEl;

	Node(Node lChild, Node rChild, Info e) {
		this.leftChild = lChild;
		this.rightChild = rChild;
		this.el = e;
	}
}

class InfoSTComp implements Comparator<Info> {
	public int compare(Info a, Info b) {
		if (a.startTime == b.startTime) {
			return 0;
		}
		if (a.startTime > b.startTime) {
			return 1;
		}
		return -1;
	}
}

class InfoFTComp implements Comparator<Info> {
	public int compare(Info a, Info b) {
		if (a.finishTime == b.finishTime) {
			return 0;
		}
		if (a.finishTime > b.finishTime) {
			return 1;
		}
		return -1;
	}
}

class Info {
	long startTime;
	long finishTime;
	long id;

	Info(Info info) {
		this.startTime = info.startTime;
		this.finishTime = info.finishTime;
		this.id = info.id;
	}

	Info(long id, long startTime, long finishTime) {
		this.id = id;
		this.startTime = startTime;
		this.finishTime = finishTime;
	}

	public String toString() {
		return "id " + this.id + " startTime " + this.startTime
				+ " finishTime " + this.finishTime;
	}

}

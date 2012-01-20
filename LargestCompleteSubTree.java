package source;

/*For this problem, a subtree of a binary tree means any connected subgraph. A binary tree is
 complete if every internal node has two children, and every leaf has exactly the same depth.
 Describe and analyze a recursive algorithm to compute the largest complete subtree of a given
 binary tree. Your algorithm should return the root and the depth of this subtree.*/

public class LargestCompleteSubTree<T> {

	public static void main(String args[]) {
		BinaryTree.Node<Integer> root = BinaryTree
				.createTree("1>1,2>2,3>3,4>4,5>5,6>6,8>8,9>9,10>10,11>11,12>12,13>13,18>18,37>37,19>19,20>20,21>21,26>26,27>27");
		BinaryTree<Integer> bt = new BinaryTree<Integer>();
		bt.setRoot(root);
		bt.printInOrder();
		new LargestCompleteSubTree<Integer>().getRoot(bt);
	}

	public void getRoot(BinaryTree<T> tree) {
		ResultNode<T> rsltNode = new ResultNode<T>();
		rsltNode.node = null;
		int size = findRootRecursive(tree.getRoot(), rsltNode);
		System.out.println("Size is " + size + " and root is "
				+ rsltNode.node.getData());
	}

	class ResultNode<T> {
		public BinaryTree.Node<T> node;
	}

	private int findRootRecursive(BinaryTree.Node<T> currentNode,
			ResultNode<T> rsltRoot) {
		if (null == currentNode) {
			return 0;
		}

		int leftSubTreeSize = findRootRecursive(currentNode.getLeftChild(),
				rsltRoot);
		BinaryTree.Node<T> leftTreeRoot = rsltRoot.node;
		int rightSubTreeSize = findRootRecursive(currentNode.getRightChild(),
				rsltRoot);
		BinaryTree.Node<T> rightTreeRoot = rsltRoot.node;
		int maxTreeSize = 0;
		if (leftSubTreeSize >= rightSubTreeSize) {
			rsltRoot.node = leftTreeRoot;
			maxTreeSize = leftSubTreeSize;
		} else {
			rsltRoot.node = rightTreeRoot;
			maxTreeSize = rightSubTreeSize;
		}
		int currentTreeSize = 0;
		if (leftSubTreeSize == rightSubTreeSize) {
			currentTreeSize = leftSubTreeSize + rightSubTreeSize + 1;
		} else {
			currentTreeSize = leftSubTreeSize < rightSubTreeSize ? leftSubTreeSize + 1
					: rightSubTreeSize + 1;
		}

		if (currentTreeSize > maxTreeSize) {
			rsltRoot.node = currentNode;
			maxTreeSize = currentTreeSize;
		}
		return maxTreeSize;
	}
}

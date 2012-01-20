package source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie<T> {
	private TrieNode<T> root;
	public static final char EndOfStringMarker = '$';

	Trie() {
		root = new TrieNode<T>();
		root.addSentinel(null);
	}

	Trie(String word, T sateliteData) {
		this();
		addWordHelper(word, sateliteData, true);
	}

	public boolean isPresent(String word) {
		return addWordHelper(word, null, false);
	}

	public void addWord(String word, T sateliteData) {
		addWordHelper(word, sateliteData, true);
	}

	public List<String> getWords() {
		List<String> words = new ArrayList<String>();
		root.getWords(words);
		return words;
	}

	public void deleteWord(String word) {
		if (isPresent(word)) {
			char wordArray[] = (word + EndOfStringMarker).toCharArray();
			deleteWordRecursive(root, wordArray, 0);
		}

	}

	private boolean deleteWordRecursive(TrieNode<T> currentNode,
			final char[] wordArray, int index) {
		if ((null != currentNode) && (index < wordArray.length)) {
			TrieNodeKey nodeKey = null;
			if (index == wordArray.length - 1) {
				nodeKey = new TrieNodeKey(wordArray[index], true);
			} else {
				nodeKey = new TrieNodeKey(wordArray[index], false);
			}
			if (deleteWordRecursive(currentNode.getChild(nodeKey), wordArray,
					index + 1)) {
				currentNode.removeKeyValue(nodeKey);
				if (0 == currentNode.size()) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	private boolean addWordHelper(String word, T sateliteData, boolean isAdd) {
		TrieNode<T> currentNode = root, prevNode = null;
		char[] wordArray = (word + EndOfStringMarker).toCharArray();
		TrieNodeKey prevTrieNodeKey = null;
		for (int index = 0; index < wordArray.length; ++index) {
			if (null == currentNode) {
				currentNode = new TrieNode<T>();
				prevNode.setChild(prevTrieNodeKey, currentNode);
			}
			TrieNodeKey currentTrieNodeKey = null;
			if (index == wordArray.length - 1) {
				currentTrieNodeKey = new TrieNodeKey(wordArray[index], true);
			} else {
				currentTrieNodeKey = new TrieNodeKey(wordArray[index], false);
			}
			if (!currentNode.isTrieNodeKeyPresent(currentTrieNodeKey)) {
				if (!isAdd) {
					return false;
				}
				TrieNodeValue<T> currentTrieNodeValue = new TrieNodeValue<T>(
						null, sateliteData);
				currentNode.addKeyValue(currentTrieNodeKey,
						currentTrieNodeValue);

			}
			prevNode = currentNode;
			currentNode = currentNode.getChild(currentTrieNodeKey);
			prevTrieNodeKey = currentTrieNodeKey;
		}
		return true;
	}

	public static void main(String[] args) {
		Trie<Object> trie = new Trie<Object>();
		trie.addWord("a", null);
		trie.addWord("aaa", null);
		trie.addWord("abc$$", null);
		trie.addWord("abc", null);
		trie.addWord("abca", null);
		trie.addWord("dbc", null);
		trie.addWord("dabc", null);

		System.out.println(trie.getWords());
		System.out.println(trie.isPresent("dbc"));
		trie.deleteWord("dbc");
		System.out.println(trie.isPresent("dbc"));
		System.out.println(trie.getWords());
	}
}

/**
 * This class is a set of TrieNodeCharacters
 * 
 * @author prahladj
 * 
 * @param <T>
 */
class TrieNode<T> {

	private HashMap<TrieNodeKey, TrieNodeValue<T>> nodeKeyValue;

	TrieNode() {
		nodeKeyValue = new HashMap<TrieNodeKey, TrieNodeValue<T>>();
	}

	TrieNode(TrieNodeKey key, TrieNodeValue<T> value) {
		super();
		nodeKeyValue.put(key, value);
	}

	TrieNode(Character c, T sateliteData) {
		this();
		addCharHelper(c, sateliteData, false);

	}

	public void addKeyValue(TrieNodeKey key, TrieNodeValue<T> value) {
		nodeKeyValue.put(key, value);
	}

	public void removeKeyValue(TrieNodeKey key) {
		nodeKeyValue.remove(key);
	}

	public int size() {
		return nodeKeyValue.size();
	}

	public void addChar(Character c, T sateliteData) {
		addCharHelper(c, sateliteData, false);
	}

	public void addSentinel(T sateliteData) {
		addCharHelper(Trie.EndOfStringMarker, sateliteData, true);
	}

	private void addCharHelper(Character c, T sateliteData, boolean isSentinel) {
		TrieNodeKey nodeKey = new TrieNodeKey(c, isSentinel);
		TrieNodeValue<T> nodeValue = new TrieNodeValue<T>(null, sateliteData);
		nodeKeyValue.put(nodeKey, nodeValue);
	}

	public T getSateliteData(TrieNodeKey key) {

		return nodeKeyValue.get(key).getSateliteData();
	}

	public boolean isTrieNodeKeyPresent(TrieNodeKey key) {
		return nodeKeyValue.containsKey(key);
	}

	public TrieNode<T> getChild(TrieNodeKey key) {
		TrieNodeValue<T> val = nodeKeyValue.get(key);
		if (null != val) {
			return val.getChild();
		}
		return null;
	}

	public void setChild(TrieNodeKey key, TrieNode<T> node) {
		TrieNodeValue<T> val = nodeKeyValue.get(key);
		if (null != val)
			val.setChild(node);
	}

	/**
	 * Returns list of all the strings which start at this node.
	 */
	public void getWords(List<String> words) {
		getWordsRecursive(this, words, "");
	}

	private void getWordsRecursive(TrieNode<T> currentNode, List<String> words,
			String currentWord) {
		if (null != currentNode) {
			for (TrieNodeKey tc : currentNode.nodeKeyValue.keySet()) {
				if (tc.isSpecial()) {
					words.add(currentWord);
				} else {
					getWordsRecursive(currentNode.getChild(tc), words,
							currentWord + tc.getCharacter());
				}
			}
		}
	}
}

class TrieNodeKey {
	private Character c;
	/**
	 * This boolean is set to true if the character marks end of the string.
	 */
	private boolean isSpecial;

	TrieNodeKey(Character c, boolean isSpecial) {
		this.c = c;
		this.isSpecial = isSpecial;
	}

	public boolean equals(Object obj) {
		if (obj instanceof TrieNodeKey) {
			TrieNodeKey other = (TrieNodeKey) obj;
			return (other.c == this.c && other.isSpecial == this.isSpecial);
		}
		return false;
	}

	public int hashCode() {
		return c.hashCode();
	}

	public Character getCharacter() {
		return c;
	}

	public boolean isSpecial() {
		return isSpecial;
	}
}

class TrieNodeValue<T> {
	private TrieNode<T> child;
	private T sateliteData;

	TrieNodeValue(TrieNode<T> child, T sateliteData) {
		this.child = child;
		this.sateliteData = sateliteData;
	}

	public void setChild(TrieNode<T> child) {
		this.child = child;
	}

	public TrieNode<T> getChild() {
		return this.child;
	}

	public T getSateliteData() {
		return sateliteData;
	}
}

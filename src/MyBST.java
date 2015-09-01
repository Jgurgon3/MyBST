
/***********************************************************************
	 * Jim Gurgone
	 * CSC403-510
	 * 3.2.14
	 ***********************************************************************/


import java.io.*;
import java.util.*;

import javax.sound.sampled.Line;


public class MyBST<K extends Comparable<K>, V> {
	private Node<K,V> root;             // root of BST

	private static class Node<K extends Comparable<K>, V> {
		private final K key;           // sorted by key
		private V val;           // associated data
		private Node<K,V> left, right;  // left and right subtrees
		private int N;           // number of nodes in subtree

		public Node(final K key, final V val, final int N) {
			this.key = key;
			this.val = val;
			this.N = N;
		}
	}

	// is the symbol table empty?
	public boolean isEmpty() {
		return size() == 0;
	}

	// return number of key-value pairs in BST
	public int size() {
		return size(root);
	}

	// return number of key-value pairs in BST rooted at x
	private int size(final Node<K,V> x) {
		if (x == null) return 0;
		else return x.N;
	}

	/***********************************************************************
	 *  Search BST for given key, and return associated value if found,
	 *  return null if not found
	 ***********************************************************************/
	// does there exist a key-value pair with given key?
	public boolean contains(final K key) {
		return get(key) != null;
	}

	// return value associated with the given key, or null if no such key exists
	public V get(final K key) {
		return get(root, key);
	}

	private V get(final Node<K,V> x, final K key) {
		if (x == null) return null;
		final int cmp = key.compareTo(x.key);
		if      (cmp < 0) return get(x.left, key);
		else if (cmp > 0) return get(x.right, key);
		else              return x.val;
	}

	/***********************************************************************
	 *  Insert key-value pair into BST
	 *  If key already exists, update with new value
	 ***********************************************************************/
	public void put(final K key, final V val) {
		if (val == null) { delete(key); return; }
		root = put(root, key, val);
		assert check();
	}

	private Node<K,V> put(final Node<K,V> x, final K key, final V val) {
		if (x == null) return new Node<K,V>(key, val, 1);
		final int cmp = key.compareTo(x.key);
		if      (cmp < 0) x.left  = put(x.left,  key, val);
		else if (cmp > 0) x.right = put(x.right, key, val);
		else              x.val   = val;
		x.N = 1 + size(x.left) + size(x.right);
		return x;
	}

	/***********************************************************************
	 *  Delete
	 ***********************************************************************/

	public void deleteMin() {
		if (isEmpty()) throw new RuntimeException("Symbol table underflow");
		root = deleteMin(root);
		assert check();
	}

	private Node<K,V> deleteMin(final Node<K,V> x) {
		if (x.left == null) return x.right;
		x.left = deleteMin(x.left);
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}

	public void deleteMax() {
		if (isEmpty()) throw new RuntimeException("Symbol table underflow");
		root = deleteMax(root);
		assert check();
	}

	private Node<K,V> deleteMax(final Node<K,V> x) {
		if (x.right == null) return x.left;
		x.right = deleteMax(x.right);
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}

	public void delete(final K key) {
		root = delete(root, key);
		assert check();
	}

	private Node<K,V> delete(Node<K,V> x, final K key) {
		if (x == null) return null;
		final int cmp = key.compareTo(x.key);
		if      (cmp < 0) x.left  = delete(x.left,  key);
		else if (cmp > 0) x.right = delete(x.right, key);
		else {
			if (x.right == null) return x.left;
			if (x.left  == null) return x.right;
			final Node<K,V> t = x;
			x = min(t.right);
			x.right = deleteMin(t.right);
			x.left = t.left;
		}
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}


	/***********************************************************************
	 *  Min, max, floor, and ceiling
	 ***********************************************************************/
	public K min() {
//		if (isEmpty()) return null;
//		return min(root).key;
		if (!isEmpty()) 
		{
			while (root.left != null)
			{
				return root.key;
			}
		}
		
			return null;
		
	}

	private Node<K,V> min(final Node<K,V> x) {
		if (x.left == null) return x;
		else                return min(x.left);
	}

	public K max() {
		if (!isEmpty()) 
		{
			while (root.right != null)
			{
				return root.key;
			}
		}
		
			return null;
		
	}
	

	private Node<K,V> max(final Node<K,V> x) {
		if (x.right == null) return x;
		else                 return max(x.right);
	}

	@SuppressWarnings("unchecked")
	public K floor(final K key) {
		Node<K,V> x = root;
		if (x == null) return null;
		final int cmp = key.compareTo(x.key);
		if (cmp == 0) return key;
		if (cmp <  0) 
		{
			while (cmp !=0)
			{
				x = root.left;
			}
			return (K) x;
		}
		else  
		{
				while (cmp !=0)
				{
					x = root.right;
				}	
				return (K) x;
		}
		
		
		
	}

//	private Node<K,V> floor(final Node<K,V> x, final K key) {
//		if (x == null) return null;
//		final int cmp = key.compareTo(x.key);
//		if (cmp == 0) return x;
//		if (cmp <  0) return floor(x.left, key);
//		final Node<K,V> t = floor(x.right, key);
//		if (t != null) return t;
//		else return x;
//	}

	@SuppressWarnings("unchecked")
	public K ceiling(final K key) {
		Node<K,V> x = root;
		if (x == null) return null;
		final int cmp = key.compareTo(x.key);
		if (cmp == 0) return key;
		if (cmp <  0) 
		{
			while (cmp !=0)
			{
				x = root.right;
			}
			return (K) x;
		}
		else  
		{
				while (cmp !=0)
				{
					x = root.left;
				}	
				return (K) x;
		}
			
			
			
	}
	

//	private Node<K,V> ceiling(final Node<K,V> x, final K key) {
//		if (x == null) return null;
//		final int cmp = key.compareTo(x.key);
//		if (cmp == 0) return x;
//		if (cmp < 0) {
//			final Node<K,V> t = ceiling(x.left, key);
//			if (t != null) return t;
//			else return x;
//		}
//		return ceiling(x.right, key);
//	}

	/***********************************************************************
	 *  Rank and selection
	 ***********************************************************************/
	public K select(final int k) {
		if (k < 0 || k >= size())  return null;
		K x = root.key;
		if (k == rank(x))
			return (K) root;
		else if (k > rank(x))
		{
			while (k != rank(x))
			{
				x = (K) root.left;
			}
			return x;
		}
		else 
		{
			while (k != rank(x))
			{
				x = (K) root.right;
			}
			return x;
		}
	}

	// Return key of rank k.
	public int rank(final K key) {
		Node<K,V> x = null;
		int cmp = key.compareTo(x.key);
		if (x == null) return 0;
		else if (cmp < 0)
			{
			while (cmp !=0)
			{
				x = root.left;
			}
			return 0;
			}
		else 
			{
			while (cmp !=0)
			{
				x = root.right;
			}
			return 0;
			}
	}

	

	/***********************************************************************
	 *  Range count and range search.
	 ***********************************************************************/
	public Iterable<K> keys() {
		return keys(min(), max());
	}

	public Iterable<K> keys(final K lo, final K hi) {
		final Queue<K> queue = new Queue<K>();
		keys(root, queue, lo, hi);
		return queue;
	}

	private void keys(final Node<K,V> x, final Queue<K> queue, final K lo, final K hi) {
		if (x == null) return;
		final int cmplo = lo.compareTo(x.key);
		final int cmphi = hi.compareTo(x.key);
		if (cmplo < 0) keys(x.left, queue, lo, hi);
		if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
		if (cmphi > 0) keys(x.right, queue, lo, hi);
	}

	public int size(final K lo, final K hi) {
		if (lo.compareTo(hi) > 0) return 0;
		if (contains(hi)) return rank(hi) - rank(lo) + 1;
		else              return rank(hi) - rank(lo);
	}


	// height of this BST (one-node tree has height 0)
	public int height() { return height(root); }
	private int height(final Node<K,V> x) {
		if (x == null) return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}

	/*************************************************************************
	 *  Check integrity of BST data structure
	 *************************************************************************/
	private boolean check() {
		if (!isBST())            System.out.println("Not in symmetric order");
		if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
		if (!isRankConsistent()) System.out.println("Ranks not consistent");
		return isBST() && isSizeConsistent() && isRankConsistent();
	}

	// does this binary tree satisfy symmetric order?
	// Note: this test also ensures that data structure is a binary tree since order is strict
	private boolean isBST() {
		return isBST(root, null, null);
	}

	// is the tree rooted at x a BST with all keys strictly between min and max
	// (if min or max is null, treat as empty constraint)
	// Credit: Bob Dondero's elegant solution
	private boolean isBST(final Node<K,V> x, final K min, final K max) {
		if (x == null) return true;
		if (min != null && x.key.compareTo(min) <= 0) return false;
		if (max != null && x.key.compareTo(max) >= 0) return false;
		return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
	}

	// are the size fields correct?
	private boolean isSizeConsistent() { return isSizeConsistent(root); }
	private boolean isSizeConsistent(final Node<K,V> x) {
		if (x == null) return true;
		if (x.N != size(x.left) + size(x.right) + 1) return false;
		return isSizeConsistent(x.left) && isSizeConsistent(x.right);
	}

	// check that ranks are consistent
	private boolean isRankConsistent() {
		for (int i = 0; i < size(); i++)
			if (i != rank(select(i))) return false;
		for (final K key : keys())
			if (key.compareTo(select(rank(key))) != 0) return false;
		return true;
	}


	/*****************************************************************************
	 *  Test client
	 *****************************************************************************/
	public static void main(final String[] args) {
		final BST<String, Integer> st = new BST<String, Integer>();
		Scanner stdin = new Scanner(System.in);

        // insert key-value pairs
        st.put("S", 0);
        st.put("E", 1);
        st.put("A", 2);
        st.put("R", 3);
        st.put("C", 4);
        st.put("H", 5);
        st.put("X", 7);
        st.put("M", 9);
        st.put("P", 10);
        st.put("L", 11);
        
        System.out.println("S E A R C H X M P L");
        System.out.println();
        //Floor Operation
		System.out.printf("Input Floor Letter: ");
		String Floor = stdin.next().toUpperCase();
		String f = st.floor(Floor);
        System.out.println("The floor of the letter " + Floor + " is " + f);

        //Floor Operation
        System.out.printf("Input Ceiling Letter: ");
     	String Ceiling = stdin.next().toUpperCase();
      	String c = st.ceiling(Ceiling);
        System.out.println("The ceiling of the letter " + Ceiling + " is " + c);
              
        //Min Operation
      	System.out.println("The Min is " + st.min());
      	
      	//Max Operation
      	System.out.println("The Max is " + st.max());      	
        
       //Rank Operation 
       System.out.printf("Input Rank Letter: ");
       String Rank = stdin.next().toUpperCase();
       System.out.println("The rank of the letter " + Rank +  " is " + st.rank(Rank));
       
       System.out.println();
    	
       //Select Operation
       System.out.printf("Input Position to select: ");
       int Select = stdin.nextInt();
       System.out.println("The letter at position " + Select + " is " + st.select(Select));
      
       

       
	
}
}

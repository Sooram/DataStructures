import java.util.ArrayList;


public class AVLtree<K extends Comparable<K>, V> 
{	
	private TreeNode<K, V> root;
	
	
	public AVLtree(K key, V value)
	{
		root = new TreeNode<K, V>(key, value);
	} // end constructor
	
	public TreeNode<K, V> getRoot()
	{
		return root;
	} // end getRoot
	
	public void insert(K newKey, V value)
	{
		this.root = insertKey(this.root, newKey, value);
		
	} // end insert
	
	public TreeNode<K, V> insertKey(TreeNode<K, V> tNode, K newKey, V value) 
	{
		TreeNode<K, V> newItem;

		if(tNode == null) { // insert point
			tNode = new TreeNode<K, V>(newKey, value);
			return tNode;
		}
		
		if(newKey.compareTo(tNode.getKey()) < 0) {
			newItem = insertKey(tNode.getLeft(), newKey, value);
			tNode.setLeft(newItem);
		}
		else if (newKey.compareTo(tNode.getKey()) > 0){
			newItem = insertKey(tNode.getRight(), newKey, value);
			tNode.setRight(newItem);
		}
		else { // key already exists
			tNode.getValue().add(value);
		}

		if(tNode.heightDiff() > 1) { // tree is not balanced
			tNode = fixTree(tNode);
		}
		return tNode;

	} // end insertKey

	// retrieve all of the values in the LinkedList
	public ArrayList<V> retrieve(TreeNode<K, V> tNode, K newKey) 
	{ 
		ArrayList<V> matched = new ArrayList<>();
		
		if(tNode == null) { // key doesn't exist
			return matched;
		}
		else if(newKey.compareTo(tNode.getKey()) == 0) { // key exists
			matched = tNode.getValue().retrieveAll();
			return matched;
		}
		else if(newKey.compareTo(tNode.getKey()) < 0) {
			matched = retrieve(tNode.getLeft(), newKey);
		}
		else{
			matched = retrieve(tNode.getRight(), newKey);
		}
		
		return matched;
	} // end retrieve
	
	 // pre-order traversal
	public String retrieveAll(TreeNode<K, V> tNode) 
	{
		String listAllElts = new String();
		if(tNode != null) {
//			System.out.print(tNode.getKey() + " ");
			listAllElts += tNode.getKey() + " ";
			retrieveAll(tNode.getLeft());
			retrieveAll(tNode.getRight());
		}
		
		return listAllElts.trim();
	} // end printAll
	
	public void remove(K searchKey) 
	{
		
	}
	
	public void removeAll()
	{
		root = null;
	}
	
	// make the tree maintain AVL tree property
	public TreeNode<K, V> fixTree(TreeNode<K, V> tNode)
	{
		if(tNode.getLeftHeight() > tNode.getRightHeight()) {
			if(tNode.getLeft().getLeftHeight() < tNode.getLeft().getRightHeight()) {
				// double rotation
				tNode.setLeft(leftRotate(tNode.getLeft()));
				tNode = rightRotate(tNode);
			}
			else {
				// single rotation
				tNode = rightRotate(tNode);
			}
		}
		else { // right.height > left.height
			if(tNode.getRight().getRightHeight() < tNode.getRight().getLeftHeight()){
				// double rotation
				tNode.setRight(rightRotate(tNode.getRight()));
				tNode = leftRotate(tNode);
			}
			else {
				// single rotation
				tNode = leftRotate(tNode);
			}
		}
		return tNode;
	} // end fixTree
	
	public TreeNode<K, V> leftRotate(TreeNode<K, V> tNode)
	{
		TreeNode<K, V> right = tNode.getRight();
		tNode.setRight(right.getLeft());
		right.setLeft(tNode);
		tNode = right;
		
		return tNode;
	} // end leftRotate
	
	public TreeNode<K, V> rightRotate(TreeNode<K, V> tNode)
	{
		TreeNode<K, V> left = tNode.getLeft();
		tNode.setLeft(left.getRight());
		left.setRight(tNode);
		tNode = left;
		
		return tNode;
	} // end rightRotate
	

/*
class Test
{
	public static void main(String args[])
	{
		AVLtree<Integer, Integer> t = new AVLtree<>(0, 0);
	    int i;
//	    int insertionCount = 0;
//	    int singleRotationCount = 0;
//	    int doubleRotationCount = 0;
	    
//	      Test.performInsertions(t);
//	      insertionCount      += t.countInsertions;
//	      singleRotationCount += t.countSingleRotations;
//	      doubleRotationCount += t.countDoubleRotations;
	    
//	    System.out.println ("Total Insertions:       " + insertionCount);
//	    System.out.println ("Total Single Rotations: " + singleRotationCount);
//	    System.out.println ("Total Double Rotations: " + doubleRotationCount);
//	    System.out.println ("Ordering: " + t.checkOrderingOfTree(t.root));
//	    System.out.println ("Balance: " + t.checkBalanceOfTree(t.root));
	    Random r = new Random();
        int range = 9999999;
        
        Integer x; 
        x = new Integer (r.nextInt(range) + 1);
        t.insert(x, x);
              
	    for (i = 1; i < 1000; i++){
	        t.insert(i, i);
	    }
	    TreeNode<Integer, Integer> tNode = t.getRoot();
	    System.out.println ("Ordering: " + checkOrderingOfTree(tNode));
	    System.out.println ("Balance: " + checkBalanceOfTree(tNode));

	}
	
//	 private AVLtree<Integer, Integer> tree = new AVLtree<>();
	 
	    private void insert(Integer...integers) {
	        for(Integer i:integers)
	            tree.insert(i, i);
	    }

    private static boolean checkBalanceOfTree(TreeNode<Integer, Integer> current) {
        
        boolean balancedRight = true, balancedLeft = true;
        int leftHeight = 0, rightHeight = 0;
         
        if (current.getRight() != null) {
            balancedRight = checkBalanceOfTree(current.getRight());
            rightHeight = getDepth(current.getRight());
        }
         
        if (current.getLeft() != null) {
            balancedLeft = checkBalanceOfTree(current.getLeft());
            leftHeight = getDepth(current.getLeft());
        }
         
        return balancedLeft && balancedRight && Math.abs(leftHeight - rightHeight) < 2;
    }
     
    private static int getDepth(TreeNode<Integer, Integer> n) {
        int leftHeight = 0, rightHeight = 0;
         
        if (n.getRight() != null)
            rightHeight = n.getRightHeight();
        if (n.getLeft() != null)
            leftHeight = n.getLeftHeight();
         
        return Math.max(rightHeight, leftHeight)+1;
    }
     
    private static boolean checkOrderingOfTree(TreeNode<Integer, Integer> current) {
        if(current.getLeft() != null) {
            if(current.getLeft().getKey().compareTo(current.getKey()) > 0)
                return false;
            else
                return checkOrderingOfTree(current.getLeft());
        } else  if(current.getRight() != null) {
            if(current.getRight().getKey().compareTo(current.getKey()) < 0)
                return false;
            else
                return checkOrderingOfTree(current.getRight());
        } else if(current.getLeft() == null && current.getRight() == null)
            return true;
         
        return true;
    }
   
    public static void performInsertions(AVLtree<Integer, Integer> t){
        
        
        // Delete any old nodes from the tree
        t.removeAll();
        
        // Clear the counts
        t.countInsertions = 0;
        t.countSingleRotations = 0;
        t.countDoubleRotations = 0;
        
        // Generate and insert 100 random numbers
        for (int i = 0; i < count; i++){
          // Prevent insertion of duplicates
          //
          //  Note: must take care in selecting parameters,
          //        to avoid an infinite loop. If count > max - min,
          //        then we have a problem.
          //do {
            x = new Integer (r.nextInt(range) + 1);
                t.insert(x);
          //} while (!t.insert (x));
        }
      }
*/
 
}
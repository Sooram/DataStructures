
public class TreeNode<K extends Comparable<K>, V>
{
	private K key;
	private LinkedList<V> value = new LinkedList<>();
	private TreeNode<K, V> leftChild;
	private TreeNode<K, V> rightChild;
	private int leftHeight = 0;
	private int rightHeight = 0;
	
	// constructor
	public TreeNode(K newKey, V newValue)
	{
		this.key = newKey;
		this.value.add(newValue);
		this.leftChild = null;
		this.rightChild = null;
		this.leftHeight = 0;
		this.rightHeight = 0;
	} // end constructor

	public final void setKey(K newKey) {
	   	key = newKey;
	} 
	    
    public final K getKey() {
     	return key;
    } 
    
    public final LinkedList<V> getValue() {
    	return value;
    }
    
    public final void setLeft(TreeNode<K, V> left) {
    	this.leftChild = left;
    	if(left != null) {
    		this.leftHeight = left.getHeight();
    	}
    	else {
    		this.leftHeight = 0;
    	}
    }
    
    public final void setRight(TreeNode<K, V> right) {
    	this.rightChild = right;
    	if (right != null) {
    		this.rightHeight = right.getHeight();
    	}
    	else {
    		this.rightHeight = 0;
    	}
    }
    
    public final TreeNode<K, V> getLeft() {
    	if(this.leftChild != null) {
    		return this.leftChild;
    	}
    	else {
    		return null;
    	}
    }
    
    public final TreeNode<K, V> getRight() {
    	if(this.rightChild != null) {
    		return this.rightChild;
    	}
    	else {
    		return null;
    	}
    }  
    
    public final void setLeftHeight(int height) {
    	this.leftHeight = height;
    }
    
    public final void setRightHeight(int height) {
    	this.rightHeight = height;
    }
    
    public final int getLeftHeight() {
    	return this.leftHeight;
    }
    
    public final int getRightHeight() {
    	return this.rightHeight;
    }
    
    public int getHeight() {
    		return (Math.max(this.leftHeight, this.rightHeight) + 1);	
    }
    
    public int heightDiff() {
    	return (Math.abs(this.leftHeight - this.rightHeight));
    }
 
} // end TreeNode<T>
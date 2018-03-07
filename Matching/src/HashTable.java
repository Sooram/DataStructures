import java.util.ArrayList;


public class HashTable<K extends Comparable<K>, V>
{
	public final int HASH_TABLE_SIZE = 100;
	private AVLtree<K, V>[] table;	// hash table
//	ArrayList<AVLtree<K, V>> table;
	private int size = 0;	// size of table
	@SuppressWarnings("unchecked")
	public HashTable()
	{ // constructor
		table = new AVLtree[HASH_TABLE_SIZE];
	//	table = new ArrayList<AVLtree<K, V>>(HASH_TABLE_SIZE);
	}
	
	public boolean isEmpty()
	{
		return (size == 0);
	}
	
	public int length()
	{
		return size;
	}
	
	public void insert(K key, V value) throws Error
	{
		int i = hashIndex(key);
		
		if (table[i] == null) { // no collision
			table[i] = new AVLtree<K, V>(key, value);
			size++;
		}
		else { // collision
			AVLtree<K, V> tree = table[i];
			tree.insert(key, value);
		}
		
/*		if (table.get(i) == null) { // no collision
			table.set(i, new AVLtree<K, V>(key, value));
			size++;
		}
		else { // collision
			AVLtree<K, V> tree = table.get(i);
			tree.insert(key, value);
		}
*/	}
	

	public void delete(K searchKey)
	{
		
	}

	public ArrayList<V> retrieveItem(int i, K searchKey)
	{ // return a string of all the values which have 'searchKey' as a key
		ArrayList<V> matched = new ArrayList<>();
		if(table[i] != null) {
			AVLtree<K, V> tree = table[i];
			matched = tree.retrieve(tree.getRoot(), searchKey);
		}
		return matched;
	}
	
	public void retieveSlot(int i)
	{ // print all the values in the tree in pre-order

		if (table[i] != null) {
			AVLtree<K, V> tree = table[i];
			System.out.println(tree.retrieveAll(tree.getRoot()));
		}
		else {
			System.out.println("Empty");
		}
	}
	
	public int hashIndex(K key)
	{
		int index = 0;
		String str = key.toString();
		
		for(int i = 0; i < str.length(); i++) {
			index += (int) str.charAt(i);
		}
		
		index = index % 100;
		
		return index;
	}
	
	public void clear()
	{
		size = 0;
		
		table = null;
		
	}

}

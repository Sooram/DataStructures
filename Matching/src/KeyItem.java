
public class KeyItem
{
	private int lineNo;
	private int index;
	
	public KeyItem(int line, int i) {
		lineNo = line;
		index = i;
	}
	
	public int getLineNo() {
		return lineNo; 
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setLineNo(int value) {
		lineNo = value;
	}
	
	public void setIndex(int value) {
		index = value;
	}
}

/*
public class KeyItem extends Node<String> implements Comparable<KeyItem>
{
    private final LinkedList<String> valueList = new LinkedList<>();

	// constructor
	public KeyItem(String key) {
		super(key);
		this.setNext(valueList.head);
	} // end constructor

	@Override
	public int compareTo(KeyItem o) {
	 	int comp;
	 	String currKeyItem = this.getItem();
	 	String newKeyItem = o.getItem();
    	
  
	 	if(currKeyItem.compareTo(newKeyItem) > 0) {
	 		// original value > new value
    		comp = 1;
    	}
    	else if(currKeyItem.compareTo(newKeyItem) == 0) { 
    		// original value = new value
    		comp = 0;
    	}
    	else { 
    		// original value < new value
    		comp = -1;
    	}
    	
    	return comp;
	} 

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;

	    result = prime * result + ((this.getItem() == null) ? 0 : this.getItem().hashCode());

	    return result;
	} 

	@Override
	public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeyItem other = (KeyItem) obj;
        if (this.getItem() == null) {
            if (other.getItem() != null)
                return false;
        } else if (!this.getItem().equals(other.getItem()))
            return false;     
        return true;
	} 

}
*/
import java.util.ArrayList;


public class Node implements Comparable<Node>
{
	private String key;
	private String name;
	private String line;
	private ArrayList<Edge> edgeList;
	
	public Node(String searchKey, String station, String num)
	{
		key = searchKey;
		name = station;
		line = num;
		edgeList = new ArrayList<Edge>();
	} // end constructor
	
	public String getKey()
	{
		return key;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getLine()
	{
		return line;
	}
/*	
	public void setDistance(int d)
	{
		distance = d;
	}
	
	public int getDistance()
	{
		return distance;
	}
*/	
	public ArrayList<Edge> getEdgeList()
	{
		return edgeList;
	}
	
	public void addEdge(Edge e)
	{
		edgeList.add(e);
	}
	
    @Override
    public int compareTo(Node other) {
	 	int comp;
	 	String thisName = this.name;
	 	String otherName = other.name;
    	
    	if (thisName.compareTo(otherName) > 0) { 
    		// original genre > new genre
    		comp = 1;
    	}
    	else if(thisName.compareTo(otherName) == 0) { 
    		// if original genre = new genre, then compare the title likewise
    		comp = 0;
    	}
    	else { 
    		// original value < new value
    		comp = -1;
    	}
    	
    	return comp;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
       
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

}



public class Edge 
{
	private Node target;	// The vertices of the edge.
	private int weight;	// The weight of the edge.
	
	public Edge(Node node, int edgeWeight)
	{
		target = node;
		weight = edgeWeight;
	} // end constructor
	
	public int getWeight()
	{
		return weight;
	}
	
	public Node getTarget()
	{
		return target;
	}
	

} // end Edge

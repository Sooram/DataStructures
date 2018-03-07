
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Graph 
{	
	private ArrayList<Node> graph;
	private Map<String, LinkedList<Node>> name;
	
	public Graph()
	{
		graph = new ArrayList<Node>();
		name = new HashMap<>();
	} // end constructor
	
	public Map<String, LinkedList<Node>> getNameMap()
	{
		return name;
	}

	public void addNode(Node v)
	{ // add node to the graph
		int numVertices = graph.size();
		
		if(numVertices == 0){
			graph.add(v);
		}
		else if(graph.get(numVertices-1).getKey().compareTo(v.getKey()) < 0) { // most cases, node will be added in sorted order
			graph.add(v);
		}
		else {	// if a node is not added in sorted order, make the graph maintain sorted
			addNodeSorted(v);
		}
		
		
		LinkedList<Node> list = new LinkedList<>();
		if (name.containsKey(v.getName())) {
			list = name.get(v.getName());
			addTransferEdge(list, v);
			list.add(v);
		}
		else {
			list.add(v);
			name.put(v.getName(), list);
		}
		
	}	// end addNode
	
	public void addNodeSorted(Node v)
	{	// add Node 'v' into the proper position
		String key = v.getKey();
		int low = 0;
        int high = graph.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            String midVal = graph.get(mid).getKey();
            int cmp = midVal.compareTo(key);

            if (cmp < 0) {
                low = mid + 1;
            }
            else if (cmp > 0) {
                high = mid - 1;
            }
            else {	// key already exists
                return;
            }
        }
       
        graph.add(low, v);
		
	}	// end addNodeSorted
	
	public int findNodeByKey(String key)
	{	// return index of a node with the given key
		int low = 0;
        int high = graph.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            String midVal = graph.get(mid).getKey();
            int cmp = midVal.compareTo(key);

            if (cmp < 0) {
                low = mid + 1;
            }
            else if (cmp > 0) {
                high = mid - 1;
            }
            else {	
                return mid;
            }
        }
       
        return low;
		
	}	// end findNodeByKey
	
	public int findNodeByName(String name)
	{	// return index of a node with the given name
		int low = 0;
        int high = graph.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            String midVal = graph.get(mid).getName();
            int cmp = midVal.compareTo(name);

            if (cmp < 0) {
                low = mid + 1;
            }
            else if (cmp > 0) {
                high = mid - 1;
            }
            else {	
                return mid;
            }
        }
       
        return low;
		
	}	// ebd findNodeByName
	
	public Node getNode(int index)
	{
		return graph.get(index);
	}
	
	public void addEdge(String key1, String key2, int wgt)
	{
		int sourceIndex = findNodeByKey(key1);
		Edge newEdge;
		
		if(sourceIndex > 0 && graph.get(sourceIndex - 1).getKey().equals(key2)) { // edge with former station
			newEdge = new Edge(graph.get(sourceIndex - 1), wgt);
		}
		else if((sourceIndex < graph.size()-1) && graph.get(sourceIndex + 1).getKey().equals(key2)) { // edge with next station
			newEdge = new Edge(graph.get(sourceIndex + 1), wgt);
		}
		else { // edge with far node
			Node newNode = graph.get(findNodeByKey(key2));
			newEdge = new Edge(newNode, wgt);
		}
	
		graph.get(sourceIndex).addEdge(newEdge);
		
	}
	
	public void addTransferEdge(LinkedList<Node> sourceList, Node target)
	{
		for(Node source : sourceList){
			Edge newEdge1 = new Edge(target, 5);
			source.addEdge(newEdge1);
		
			Edge newEdge2 = new Edge(source, 5);
			target.addEdge(newEdge2);
		}
	}
	
	public void sort()
	{
		Collections.sort(graph);
	}
	
	public int size()
	{
		return graph.size();
	}
	

/*	
	public void removeEdge(Edge e)
	{
		T v = e.getV();
		T w = e.getW();
		
		adjList.get(v).remove(w);
		adjList.get(w).remove(v);
		numEdges--;
	}
	
	public Edge<T> findEdge(T v, T w)
	{
		int wgt = adjList.get(v).get(w);
		return new Edge<>(v, w, wgt);
	}
	
	TreeMap<T, Integer> getAdjList(T v)
	{
		return adjList.get(v);
	}
*/
}



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


public class Dijkstra 
{	// modification from : http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
	
	private Graph graph;
	private Set<Node> settledNodes;
	private PriorityQueue<Node> unsettledNodes;
	private Map<Node, Integer> distance;
	private Map<Node, Node> predecessors;
	  
	public Dijkstra(Graph g)
	{
		graph = g;
		settledNodes = new HashSet<>();
	    unsettledNodes = new PriorityQueue<>();
	    distance = new HashMap<>();
	    predecessors = new HashMap<>();  
	}
	  
	public void findShortestPath(String source, String dest)
	{
		int minDistance = Integer.MAX_VALUE;
		ArrayList<Node> minPath = new ArrayList<>();
		for (Node start : graph.getNameMap().get(source)) {
			distance.clear();
			predecessors.clear();
			distance.put(start, 0);
			unsettledNodes.add(start);

			while (unsettledNodes.size() > 0) {
				Node minNode = unsettledNodes.poll();	// get the minimum node
				settledNodes.add(minNode);
				relax(minNode, unsettledNodes);
			}
	    
			for (Node destination : graph.getNameMap().get(dest)) {
				ArrayList<Node> path = getPath(destination);
			
				if(distance.get(destination) < minDistance) {
					minDistance = distance.get(destination);
					minPath = path;
				}
			}
		}
	    printPath(minPath);
	    System.out.println(minDistance);
	    
	}	// end findShortestPath
   
	public void relax(Node source, PriorityQueue<Node> nodeQueue)
	{
        // Visit and relax each edge 
        for (Edge edge : source.getEdgeList())
        {
            Node target = edge.getTarget();
            int weight = edge.getWeight();
            int distanceThroughSource = getDistance(source) + weight;
            
            if (getDistance(target) > distanceThroughSource) {
            	distance.put(target, distanceThroughSource);
            	predecessors.put(target, source);
            	nodeQueue.add(target);
            }	
        }
    }


	public int getDistance(Node destination) 
	{
		Integer d = distance.get(destination);
	   	if (d == null) {
	   		return Integer.MAX_VALUE;
	    } 
	   	else {
	      return d;
	    }
	}

	  /*
	   * This method returns the path from the source to the selected target and
	   * NULL if no path exists
	   */
	public ArrayList<Node> getPath(Node target) 
	{
		ArrayList<Node> path = new ArrayList<Node>();
	    
	    // check if a path exists
	    if (predecessors.get(target) == null) {
	    	return null;
	    }
	    
	    path.add(target);
	    while (predecessors.get(target) != null) {
	      target = predecessors.get(target);
	      path.add(target);
	    }
	
	    // Put it into the correct order
	    Collections.reverse(path);
	    return path;
	}
	
	public void printPath(ArrayList<Node> path)
	{
		int i;
		for(i = 0; i < path.size()-1; i++) {
			if(path.get(i).getName().equals(path.get(i+1).getName())) {
				System.out.print("[" + path.get(i).getName() + "] ");
				i++;
			}
			else System.out.print(path.get(i).getName() + " ");
		}
		System.out.println(path.get(i).getName());
	}
}

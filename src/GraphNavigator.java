import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


public class GraphNavigator<T> {
	Graph<T> graph;
	LinkedList<T> path;
	
	public GraphNavigator(Graph<T> graph) {
		this.graph = graph;
	}
	
	protected LinkedList<T> constructPath(T node, HashMap<T,T> cameFrom) {
		LinkedList<T> constructedPath = new LinkedList<T>();
		T currNode = node;
		while (cameFrom.get(currNode) != null) {
			constructedPath.add(currNode);
			currNode = cameFrom.get(currNode);
			
		}

		return constructedPath;
	}
	
	public void breadthFirstSearch(T startNode, T goalNode) throws Exception {
		HashMap<T,T> cameFrom = new HashMap<T,T>();
		LinkedList<T> frontier = new LinkedList<T>();
		LinkedList<T> visited = new LinkedList<T>();
		cameFrom.put(startNode, null);
		frontier.add(startNode);
		
		if (!graph.contains(goalNode) && !graph.contains(startNode)) {
			throw new Exception("Node not in graph");
		}
		
		while (!frontier.isEmpty()) {
			T currentNode = frontier.removeFirst();
			
			if (currentNode == goalNode) {break;}
			else {
				visited.add(currentNode);
				Iterator<T> i = graph.getNeighbours(currentNode).iterator();
				while (i.hasNext()){
					T nextNode = (T) i.next();
					if (!visited.contains(nextNode) && !frontier.contains(nextNode)) {
						frontier.add(nextNode);
						cameFrom.put(nextNode, currentNode);
					}
				}
			}
		}
		
		path = this.constructPath(goalNode, cameFrom);
	}
	
	public void depthFirstSearch(T node, T goalNode) {
		
		
	}

	public Graph<T> getGraph() {
		return graph;
	}

	public void setGraph(Graph<T> graph) {
		this.graph = graph;
	}

	public LinkedList<T> getPath() {
		return path;
	}

	
	
}

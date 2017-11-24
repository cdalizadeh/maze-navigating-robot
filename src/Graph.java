import java.util.HashMap;
import java.util.Vector;

public class Graph<T> {
	Vector<T> nodes;
	HashMap<T, Vector<T>> neighbours;

	public Graph(Vector<T> nodes) {
		this.nodes = nodes;
	}
	
	public Graph(Vector<T> nodes, HashMap<T, Vector<T>> neighbours) {
		this.nodes = nodes;
		this.neighbours = neighbours;
	}
	
	public Vector<T> getNodes() {
		return nodes;
	}
	
	public void setNodes(Vector<T> nodes) {
		this.nodes = nodes;
	}
	
	public boolean contains(T node) {
		return nodes.contains(node);
	}
	
	public int size(){
		return nodes.size();
	}

	public Vector<T> getNeighbours(T node) {
		return neighbours.get(node);
	}

	public void setNeighbours(HashMap<T, Vector<T>> neighbours) {
		
		this.neighbours = neighbours;
	}
	
	
};

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Integer[][] neighbourArray = {{5}, {6, 2}, {1, 3}, {2, 4}, {3, 9},
							 {0, 10}, {1, 11}, {12, 8}, {7, 9}, {4, 8, 14},
							 {5, 11}, {6, 10, 16}, {7, 17}, {14, 18},{9, 13},
							 {16, 20}, {11, 15}, {12, 22}, {13, 23}, {24},
							 {15, 21}, {20}, {17}, {18, 24}, {19, 23}};
		
		HashMap<Integer, Vector<Integer>> neighbours = new HashMap<Integer, Vector<Integer>>();
		
		
		Vector<Integer> nodes = new Vector<Integer>();
		for (int i = 0; i < 25; i++) {
			Integer nodeID = Integer.valueOf(i);
			neighbours.put(nodeID, new Vector<Integer>(Arrays.asList(neighbourArray[i])));
			nodes.add(nodeID);
		}
		
		
		double[] pose = {0, 90};
		Graph<Integer> maze = new Graph<Integer>(nodes, neighbours);
		Robot<Integer> bernie = new Robot<Integer>(maze, pose);
		bernie.breadthFirstSearch(0, 19);
		System.out.println(bernie.getPath());
		/*
		while (bernie.getPath() != null) {
			Integer nextNode = bernie.getPath().removeLast();
			bernie.moveNextNode(nextNode);
		}
		*/
		
		
		
		
	}

};
package pa8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class GraphListWeighted implements Graph {
	Map<Integer, ArrayList<int[]>> adjList;
	
	public GraphListWeighted() {
		adjList = new HashMap<>();
	}

	@Override
	public void addEdge(int v, int w) {
		if (!adjList.containsKey(v))
			adjList.put(v, new ArrayList<>());
		adjList.get(v).add(new int[]{w, 1});
	}

	@Override
	public void addWeightedEdge(int v, int w, int weight) {
		if (!adjList.containsKey(v))
			adjList.put(v, new ArrayList<>());
		adjList.get(v).add(new int[]{w, weight});
		
	}

	@Override
	public String bfs(int start) {
		ArrayList<String> res = new ArrayList<String>();
		Queue<Integer> queue = new LinkedList<>();
		Set<Integer> visited = new HashSet<>();
		queue.add(start);
		visited.add(start);
		int curr;
		while (!queue.isEmpty()) {
			curr = queue.poll();
			res.add(Integer.toString(curr));
			if (adjList.get(curr) != null) {
				for (int[] x: adjList.get(curr)) {
					if (!visited.contains(x[0])) {
						queue.add(x[0]);
						visited.add(x[0]);
					}
				}
			}
		}
		return String.join(" ", res);
	}

	@Override
	public String dfs(int start) {
		ArrayList<String> res = new ArrayList<String>();
		Stack<Integer> stack = new Stack<>();
		Set<Integer> visited = new HashSet<>();
		stack.add(start);
		visited.add(start);
		int curr;
		while (!stack.isEmpty()) {
			curr = stack.pop();
			res.add(Integer.toString(curr));
			if (adjList.get(curr) != null) {
				for (int[] x: adjList.get(curr)) {
					if (!visited.contains(x[0])) {
						stack.add(x[0]);
						visited.add(x[0]);
					}
				}
			}
		}
		return String.join(" ", res);
	}
	
	@Override
	public boolean hasCycle() {
		Map<Integer, Integer> visited = new HashMap<Integer, Integer>();
		for (int start: adjList.keySet()) {
			if (!visited.containsValue(start)) {
				Stack<Integer> stack = new Stack<>();
				stack.add(start);
				int curr;
				while (!stack.isEmpty()) {
					curr = stack.pop();
					if (visited.containsKey(curr) && visited.get(curr) == 1) {
						visited.put(curr, 2);
					}
					else {
						stack.add(curr);
						visited.put(curr, 1);
						if (adjList.get(curr) != null) {
							for (int[] x: adjList.get(curr)) {
								if (visited.containsKey(x[0]) && visited.get(x[0]) == 1) {
									return true;
								}
								if (!visited.containsKey(x[0]))
									stack.add(x[0]);
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public String shortestPath(int v, int w) {
		
		MinHeap priorityQueue = new MinHeap();
		Map<Integer, Integer> parent = new HashMap<Integer, Integer>();
		
		for (int node: adjList.keySet()) {
			priorityQueue.insert(Double.POSITIVE_INFINITY, node);
		}
		
		priorityQueue.update(v, 0);
		int curr;
		while (!priorityQueue.isEmpty()) {
			double currCost = priorityQueue.getCost(priorityQueue.peek());
			curr = priorityQueue.delete();
			for (int[] x: adjList.get(curr)) {
				if (priorityQueue.inHeap(x[0]) && priorityQueue.update(x[0], currCost+x[1])) {
					parent.put(x[0], curr);
				}
			}
		}
		int ow = w;
		ArrayList<String> res = new ArrayList<String>();
		while (parent.containsKey(w)) {
			res.add(Integer.toString(w));
			w = parent.get(w);
		}
		res.add(Integer.toString(v));
		List<String> res1 = res.reversed();
		if (v != w)
			return "no path from " + v + " to " + ow;
		return String.join(" ", res1);
	}

}

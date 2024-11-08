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

public class GraphList implements Graph{
	Map<Integer, ArrayList<Integer>> adjList;
	
	public GraphList() {
		adjList = new HashMap<>();
	}

	@Override
	public void addEdge(int v, int w) {
		if (!adjList.containsKey(v))
			adjList.put(v, new ArrayList<Integer>());
		adjList.get(v).add(w);
	}

	@Override
	public void addWeightedEdge(int v, int w, int weight) {
		addEdge(v, w);
		
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
				for (int x: adjList.get(curr)) {
					if (!visited.contains(x)) {
						queue.add(x);
						visited.add(x);
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
				for (int x: adjList.get(curr)) {
					if (!visited.contains(x)) {
						stack.add(x);
						visited.add(x);
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
			if (!visited.containsKey(start)) {
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
							for (int x: adjList.get(curr)) {
								if (visited.containsKey(x) && visited.get(x) == 1) {
									return true;
								}
								if (!visited.containsKey(x))
									stack.add(x);
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
		
		Queue<Integer> queue = new LinkedList<>();
		Set<Integer> visited = new HashSet<Integer>();
		Map<Integer, Integer> parent = new HashMap<Integer, Integer>();
		queue.add(v);
		visited.add(v);
		int curr;
		while (!queue.isEmpty()) {
			curr = queue.poll();
			for (int x: adjList.get(curr)) {
				if (!visited.contains(x)) {
					queue.add(x);
					visited.add(x);
					parent.put(x, curr);
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

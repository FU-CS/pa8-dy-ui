package pa8;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class GraphMatrixWeighted implements Graph{
	int[][] matrix;
	int n;
	
	public GraphMatrixWeighted(int n) {
		matrix = new int[n][n];
		this.n = n;
		for (int i=0; i<n; i++)
			for( int j=0; j<n; j++)
				matrix[i][j] = -1;
	}

	@Override
	public void addEdge(int v, int w) {
		if (v >= n ||w >= n) {
			return;
		}
		matrix[v][w] = 1;
		
	}

	@Override
	public void addWeightedEdge(int v, int w, int weight) {
		if (v >= n ||w >= n) {
			return;
		}
		matrix[v][w] = weight;
		
	}

	@Override
	public String bfs(int start) {
		ArrayList<String> res = new ArrayList<String>();
		Queue<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[n];
		queue.add(start);
		visited[start] = true;
		int curr;
		while (!queue.isEmpty()) {
			curr = queue.poll();
			res.add(Integer.toString(curr));
			for (int x=0; x<n; x++){
				if (matrix[curr][x] !=-1 && !visited[x]) {
					queue.add(x);
					visited[x] = true;
				}
			}
		}
		return String.join(" ", res);
	}

	@Override
	public String dfs(int start) {
		ArrayList<String> res = new ArrayList<String>();
		Stack<Integer> stack = new Stack<>();
		boolean[] visited = new boolean[n];
		stack.add(start);
		visited[start] = true;
		int curr;
		while (!stack.isEmpty()) {
			curr = stack.pop();
			res.add(Integer.toString(curr));
			for (int x=0; x<n; x++){
				if (matrix[curr][x] !=-1 && !visited[x]) {
					stack.add(x);
					visited[x] = true;
				}
			}
		}
		return String.join(" ", res);
	}
	
	@Override
	public boolean hasCycle() {
		int[] visited = new int[n];
		for (int start=0; start<n;start++) {
			if (visited[start] == 0) {
				Stack<Integer> stack = new Stack<>();
				stack.add(start);
				int curr;
				while (!stack.isEmpty()) {
					curr = stack.pop();
					if (visited[curr] == 1) {
						visited[curr] = 2;
					}
					else {
						stack.add(curr);
						visited[curr] = 1;
						for (int x=0; x<n; x++) {
							if (matrix[curr][x] != -1) {
								if (visited[x] == 1)
									return true;
								if (visited[x] == 0)
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
		
		MinHeap priorityQueue = new MinHeap();
		int[] parent = new int[n];
		for (int i=0; i<n; i++) {
			parent[i] = -1;
			priorityQueue.insert(Double.POSITIVE_INFINITY, i);
		}
		
		priorityQueue.update(v, 0);
		int curr;
		while (!priorityQueue.isEmpty()) {
			double currCost = priorityQueue.getCost(priorityQueue.peek());
			curr = priorityQueue.delete();
			for (int x=0; x<n; x++) {
				if (matrix[curr][x] != -1 && priorityQueue.inHeap(x)) {
					if (priorityQueue.update(x, currCost+matrix[curr][x])){ 
						parent[x] = curr;
					}
				}
			}
		}
		
		
		ArrayList<String> res = new ArrayList<String>();
		int ow = w;
		while (w != -1) {
			res.add(Integer.toString(w));
			w = parent[w];
		}
		List<String> res1 = res.reversed();
		if (!res1.get(0).equals(Integer.toString(v)))
			return "no path from " + v + " to " + ow;
		return String.join(" ", res1);
	}
}

package pa8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MinHeap {
	private ArrayList<Double> arr;
	int size;
	private Map<Integer, Integer> nodeToIdx;
	private Map<Integer, Integer> idxToNode;

	public MinHeap() {
		 this.arr = new ArrayList<Double>();
		 this.size = 0;
		 this.idxToNode = new HashMap<>();
		 this.nodeToIdx = new HashMap<>();
	 }

	public void insert(double cost, int node) {
		arr.add(cost);
		idxToNode.put(size, node);
		nodeToIdx.put(node, size);
		size++;
		bubbleUp(size-1);
		
	}
	
	private void bubbleUp(int i) {
		while (i>0 && arr.get((i-1)/2) > arr.get(i)) {
			swap(i, (i-1) /2);
			i = (i-1)/2;
		}
	}
	
	public void bubbleUpNode(int node) {
		bubbleUp(nodeToIdx.get(node));
	}
	
	public boolean update(int node, double newCost) {
		int idx = nodeToIdx.get(node);
//		System.out.println(newCost + " old " + arr.get(idx));
		if (newCost >= arr.get(idx))
			return false;
		arr.set(idx, newCost);
		bubbleUp(idx);
//		System.out.println("trued");
		return true;
	}
	
	public double getCost(int node) {
//		System.out.println("in get cost " + node + " "+ nodeToIdx.get(node) + " "+arr.get(0)); 
		return arr.get(nodeToIdx.get(node));
	}
	
	private void swap(int i, int j) {
		double tmp = arr.get(i);
		arr.set(i, arr.get(j));
		arr.set(j, tmp);
		
//		int tmpNode = this.idxToNode.
		int nodeI = idxToNode.get(i);
		int nodeJ = idxToNode.get(j);
		idxToNode.put(i, nodeJ);
		idxToNode.put(j, nodeI);
		nodeToIdx.put(nodeI, j);
		nodeToIdx.put(nodeJ, i);
	}

	public int delete() {
		if (size == 0)
			return -1;
		
		int tmp = idxToNode.get(0);
		swap(0, size-1);
		
		size --;
		nodeToIdx.remove(tmp);
		idxToNode.remove(size);
		
		bubbleDown(0);
		return tmp;
	}
	
	public void bubbleDown(int idx) {
		if (2*idx+1 >= size)
			return;
		
		int left = 2*idx+1;
		if (size-1 == left) {
			if (arr.get(left) < arr.get(idx)) { //change
				swap(left, idx);
			}
			return;
		}
		
		int right = 2*idx + 2;
		if ((arr.get(left) <= arr.get(right)) && (arr.get(left) < arr.get(idx))) { //change
			swap(idx, left);
			bubbleDown(left);
			return;
		}
		else if (arr.get(right) < arr.get(idx)) { //change
			swap(idx, right);
			bubbleDown(right);
		}
	}

	
	public int peek() {
		if (size == 0)
			return -1;
		return idxToNode.get(0);
	}

	
	public int size() {
		return size;
	}

	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public boolean inHeap(int node) {
		return nodeToIdx.containsKey(node);
	}

	
	public void print() {
		System.out.print("weights: ");
		for (double x: arr)
			System.out.print(x + " ");
		System.out.println();
		
		System.out.print("index to node: ");
		for (int x: idxToNode.keySet())
			System.out.print(x + ": " + idxToNode.get(x) + " , ");
		System.out.println();
		
		System.out.print("node to index: ");
		for (int x: nodeToIdx.keySet())
			System.out.print(x + ": " + nodeToIdx.get(x) + " , ");
		System.out.println();
		
	}

}

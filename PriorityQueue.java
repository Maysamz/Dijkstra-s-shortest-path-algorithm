public class PriorityQueue {


	private Edge[] graph;
	private int graphSize;

	public PriorityQueue() {
		graphSize = 0;
		graph = new Edge[50];
	}
   

	public void insert(Edge element) {
		if (graphSize == graph.length)
			resize();

		graphSize++;
		int firstIndex = graphSize - 1;
		int parent = parent(firstIndex);
		graph[firstIndex] = element;

		while (parent >= 0 && graph[parent].weight > element.weight) {
			elementSwap(parent, firstIndex);
			firstIndex = parent;
			parent = parent(firstIndex);
		}
      
	}
   

	public Edge extract() {
		Edge max= graph[0];
		elementSwap(0, graphSize - 1);
		graphSize--;
		heapify(0);
		return max;
	}
   

	private void heapify(int parent) {
		int left = left(parent);
		int right = right(parent);
		int largest;

		largest = (left < graphSize && graph[left].weight < graph[parent].weight) ? left : parent;
		largest = (right < graphSize && graph[right].weight <graph[largest].weight) ? right : largest;

		if (largest != parent) {
			elementSwap(largest, parent);
			heapify(largest);
		}
	}
   

	public boolean isEmpty() {
		return graphSize <= 0 ? true : false;
	}

	private void elementSwap(int firstIndex, int secondIndex) {
		Edge tempNode = graph[firstIndex];
		graph[firstIndex] = graph[secondIndex];
		graph[secondIndex] = tempNode;
	}

	private void resize() {
		Edge[] allnode = new Edge[graph.length * 2];
		System.arraycopy(graph, 0,allnode , 0, graphSize);
		graph = allnode;
	}

	private int left(int parent) {
		return parent * 2 + 1;
	}

	private int right(int parent) {
		return parent * 2 + 2;
	}

	private int parent(int child) {
		return (child - 1) / 2;
	}
}
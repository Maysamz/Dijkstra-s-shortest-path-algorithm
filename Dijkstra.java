import java.util.Scanner;
import java.util.Stack;


public class Dijkstra {

	private static Scanner read = new Scanner(System.in);
	private static final int INFINITY = Integer.MAX_VALUE;

	private static PriorityQueue smallsetDistanceQueue;
	private static Edge[] graph;
	private static int vertices;
	private static int sourceVertex;

	private static int[] distance;
	private static int[] previous;
	private static boolean[] visited;

	public static void main(String[] args) {
		readVertices();
		readGraph();
		readSourceVertex();
		exerciseDijkstra();
		read.close();
	}

	private static void exerciseDijkstra() {
		int currentVertex;

		initializeDijkstra();
		while (!smallsetDistanceQueue.isEmpty()) {
			currentVertex = smallsetDistanceQueue.extract().index;
			visited[currentVertex] = true;

			for (Edge vertex = graph[currentVertex]; vertex != null; vertex = vertex.edge) {
				if (!visited[vertex.index] && distance[currentVertex] + vertex.weight < distance[vertex.index]) {
					distance[vertex.index] = distance[currentVertex] + vertex.weight;
					previous[vertex.index] = currentVertex;
					smallsetDistanceQueue.insert(new Edge(vertex.index, distance[vertex.index]));
				}
			}
		}

		printPath();
	}

	private static void printPath() {
		Stack<Integer> path;
		StringBuilder fullPath;
		int currentVertex;

		System.out.println("------- Shortest Full Path ------- ");
		for (int vertex = 0; vertex < vertices; vertex++) {
			if (sourceVertex == vertex)
				continue;

			path = new Stack<Integer>();
			fullPath = new StringBuilder();
			currentVertex = vertex;

			while (previous[currentVertex] != INFINITY) {
				path.push(previous[currentVertex]);
				currentVertex = previous[currentVertex];
			}

			while (!path.isEmpty())
				fullPath.append(path.pop() + "->");
			System.out.printf("%-17s *Cost: %d\n", fullPath.toString() + vertex, distance[vertex]);
		}
		System.out.println("----------------------------------------------");
	}

	private static void initializeDijkstra() {
		distance = new int[vertices];
		previous = new int[vertices];
		visited = new boolean[vertices];
		smallsetDistanceQueue = new PriorityQueue();

		for (int vertex = 0; vertex < vertices; vertex++)
			distance[vertex] = (sourceVertex == vertex) ? 0 : INFINITY;
		for (int vertex = 0; vertex < vertices; vertex++)
			previous[vertex] = INFINITY;

		smallsetDistanceQueue.insert(new Edge(sourceVertex, 0));
	}

	private static void readVertices() {
		System.out.println("Enter the number of vertices: ");
		vertices = read.nextInt();
	}

	private static void readSourceVertex() {
		System.out.println("\nEnter the source matrix: ");
		sourceVertex = read.nextInt() ;
	}

	private static void readGraph() {
		int weight;
		graph = new Edge[vertices];

		System.out.println("Enter the Adjacency Matrix( 999 if no edge ): ");
		for (int firstVertex = 0; firstVertex < vertices; firstVertex++) {
			for (int secondVertex = 0; secondVertex < vertices; secondVertex++) {
				weight = read.nextInt();

				if (weight == 0)
					continue;
				graph[firstVertex] = new Edge(secondVertex, graph[firstVertex], weight);
			}
		}
	}
}
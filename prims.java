import java.util.*;

class Main {
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt(); // Number of vertices
        int[][] graph = new int[V][V]; // Adjacency matrix

        // Read graph weights
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                graph[i][j] = sc.nextInt();
            }
        }

        int parent[] = new int[V]; // Stores parent of each vertex
        int key[] = new int[V]; // Minimum edge weight to connect a vertex
        boolean mstSet[] = new boolean[V]; // Tracks vertices in MST

        // Initialize all keys to infinity and vertices as unvisited
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0; // Start MST from vertex 0
        parent[0] = -1; // Root has no parent

        int edgesAdded = 0;

        // Repeat until all vertices are included
        for (int count = 0; count < V; count++) {

            int minKey = Integer.MAX_VALUE;
            int minIndex = -1;

            // Find unvisited vertex with minimum key
            for (int v = 0; v < V; v++) {
                if (!mstSet[v] && key[v] < minKey) {
                    minKey = key[v];
                    minIndex = v;
                }
            }

            // Stop if no reachable vertex exists
            if (minIndex == -1) {
                break;
            }

            mstSet[minIndex] = true; // Include vertex in MST
            edgesAdded++;

            // Update keys of adjacent unvisited vertices
            for (int v = 0; v < V; v++) {

                // Check if edge exists and has smaller weight
                if (graph[minIndex][v] != 0 &&
                    !mstSet[v] &&
                    graph[minIndex][v] < key[v]) {

                    parent[v] = minIndex; // Store parent
                    key[v] = graph[minIndex][v]; // Update minimum weight
                }
            }
        }

        // Check whether graph is connected
        if (edgesAdded < V) {
            System.out.print("Graph is disconnected.");
        } else {

            // Print MST edges and their weights
            for (int i = 1; i < V; i++) {
                System.out.println(
                    parent[i] + " " + i + " " + graph[parent[i]][i]
                );
            }
        }

        sc.close();
    }
}
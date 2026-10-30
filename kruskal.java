import java.util.*;

// Represents an edge with source, destination and weight
class Edge implements Comparable<Edge> {
    int u, v, w;

    public Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    // Sort edges in increasing order of weight
    public int compareTo(Edge other) {
        return Integer.compare(this.w, other.w);
    }
}

class Main {

    static int[] parent;
    static int[] rank;

    // Finds the representative of a set
    static int find(int i) {

        // If vertex is its own parent, it is the root
        if (parent[i] == i) {
            return i;
        }

        // Path compression: directly connect to root
        return parent[i] = find(parent[i]);
    }

    // Combines two different sets
    static void union(int x, int y) {

        int rootX = find(x);
        int rootY = find(y);

        // Attach smaller rank tree below larger rank tree
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        }
        else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        }
        else {
            // If ranks are equal, choose one root
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt(); // Number of vertices
        int E = sc.nextInt(); // Number of edges

        Edge[] edges = new Edge[E];

        // Read all edges
        for (int i = 0; i < E; i++) {
            edges[i] = new Edge(
                sc.nextInt(),
                sc.nextInt(),
                sc.nextInt()
            );
        }

        // Sort edges by increasing weight
        Arrays.sort(edges);

        parent = new int[V];
        rank = new int[V];

        // Initially, every vertex belongs to its own set
        for (int i = 0; i < V; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        System.out.println("Edges in the Minimum Spanning Tree: ");

        int totalWeight = 0;
        int edgesIncluded = 0;

        // Process edges from smallest weight to largest
        for (int i = 0; i < E; i++) {

            int u = edges[i].u;
            int v = edges[i].v;
            int w = edges[i].w;

            // Find the sets of both vertices
            int setU = find(u);
            int setV = find(v);

            // Include edge only if it does not form a cycle
            if (setU != setV) {

                System.out.println(u + " - " + v + " : " + w);

                totalWeight += w; // Add edge weight
                edgesIncluded++;

                // Merge the two sets
                union(setU, setV);
            }

            // MST is complete when V-1 edges are selected
            if (edgesIncluded == V - 1) {
                break;
            }
        }

        // Print total MST weight
        System.out.println("Total Weight of MST: " + totalWeight);
    }
}
import java.util.*;

class Main {
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);

        // Read number of vertices
        int n = sc.nextInt();

        // Read adjacency matrix
        int[][] graph = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = sc.nextInt();
            }
        }

        // Read source vertex
        int src = sc.nextInt();

        // Store shortest distances and previous vertices
        int[] dist = new int[n];
        int[] parent = new int[n];

        // Track visited vertices
        boolean[] visited = new boolean[n];

        // Initially, all distances are infinity
        Arrays.fill(dist, Integer.MAX_VALUE);

        // Initially, no parent exists
        Arrays.fill(parent, -1);

        // Distance from source to itself is 0
        dist[src] = 0;

        // Repeat until all reachable vertices are processed
        for (int count = 0; count < n - 1; count++) {

            int u = -1;
            int minVal = Integer.MAX_VALUE;

            // Find the unvisited vertex with minimum distance
            for (int v = 0; v < n; v++) {
                if (!visited[v] && dist[v] < minVal) {
                    minVal = dist[v];
                    u = v;
                }
            }

            // Stop if no reachable vertex is left
            if (u == -1) break;

            // Mark selected vertex as visited
            visited[u] = true;

            // Update distances of its neighbours
            for (int v = 0; v < n; v++) {

                if (!visited[v] && graph[u][v] != 0
                    && dist[u] != Integer.MAX_VALUE) {

                    // Check if a shorter path is available
                    if (dist[u] + graph[u][v] < dist[v]) {

                        dist[v] = dist[u] + graph[u][v];

                        // Store previous vertex for path printing
                        parent[v] = u;
                    }
                }
            }
        }

        // Print shortest distance and path
        System.out.println("Vertex\tDistance\tPath");

        for (int i = 0; i < n; i++) {

            // If vertex is unreachable
            if (dist[i] == Integer.MAX_VALUE) {
                System.out.println(i + "\tINF\tNo Path");
            }

            else {

                // Reconstruct path using parent array
                List<Integer> path = new ArrayList<>();

                for (int curr = i; curr != -1; curr = parent[curr]) {
                    path.add(curr);
                }

                // Reverse path to get source-to-destination order
                Collections.reverse(path);

                // Convert path into a string
                StringBuilder pathStr = new StringBuilder();

                for (int j = 0; j < path.size(); j++) {
                    pathStr.append(path.get(j));

                    if (j < path.size() - 1) {
                        pathStr.append(" ");
                    }
                }

                // Print vertex, distance and path
                System.out.println(i + "\t" + dist[i] + "\t\t" + pathStr);
            }
        }

        sc.close();
    }
}
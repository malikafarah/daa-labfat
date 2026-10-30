import java.util.Scanner;

class Main {
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);

        // Read number of vertices
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        // Store distances between vertices
        int[][] dist = new int[n][n];

        // Represents infinity (no direct path)
        final int INF = 1_000_000_000;

        // Read adjacency matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                int val = sc.nextInt();

                // Replace 999 with infinity
                if (i != j && val == 999) {
                    dist[i][j] = INF;
                } else {
                    dist[i][j] = val;
                }
            }
        }

        // Try every vertex as an intermediate vertex
        for (int k = 0; k < n; k++) {

            for (int i = 0; i < n; i++) {

                for (int j = 0; j < n; j++) {

                    // Check if both paths exist
                    if (dist[i][k] != INF && dist[k][j] != INF) {

                        // Update if a shorter path is found
                        if (dist[i][k] + dist[k][j] < dist[i][j]) {

                            dist[i][j] = dist[i][k] + dist[k][j];
                        }
                    }
                }
            }
        }

        // Print the final shortest distance matrix
        for (int i = 0; i < n; i++) {

            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < n; j++) {

                // Print INF if no path exists
                if (dist[i][j] >= INF / 2) {
                    sb.append("INF");
                } else {
                    sb.append(dist[i][j]);
                }

                if (j < n - 1) {
                    sb.append(" ");
                }
            }

            System.out.println(sb.toString());
        }

        sc.close();
    }
}
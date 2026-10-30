import java.util.*;

public class Main {

    static int n;
    static int[][] cost;
    static int[][] dp;
    static int[][] parent;

    // Finds the minimum cost to visit all remaining cities
    static int tsp(int mask, int u) {

        // If all cities are visited, return to the starting city (city 1)
        if (mask == (1 << n) - 1) {
            return cost[u][0];
        }

        // If this state was already calculated, return its stored answer
        if (dp[mask][u] != -1) {
            return dp[mask][u];
        }

        // Initially, assume the minimum cost is infinity
        int min = Integer.MAX_VALUE;

        // Stores the next city that gives the minimum cost
        int nextCity = -1;

        // Try visiting every city
        for (int v = 0; v < n; v++) {

            // Check whether city v has NOT been visited
            if ((mask & (1 << v)) == 0) {

                // Mark city v as visited
                int newMask = mask | (1 << v);

                // Cost to travel from current city u to v
                // + minimum cost of visiting the remaining cities
                int total = cost[u][v] + tsp(newMask, v);

                // Update minimum cost if a better route is found
                if (total < min) {
                    min = total;
                    nextCity = v;
                }
            }
        }

        // Store the next city to help reconstruct the route
        parent[mask][u] = nextCity;

        // Store the minimum cost for this state
        dp[mask][u] = min;

        return min;
    }

    // Prints the route using the parent array
    static void printPath() {

        // Start from city 1 (index 0)
        int mask = 1;
        int curr = 0;

        System.out.print("Route with least expense: 1 ");

        // Visit the remaining n-1 cities
        for (int i = 1; i < n; i++) {

            // Get the next city from the parent array
            int next = parent[mask][curr];

            // Print city number (add 1 because indexing starts from 0)
            System.out.print((next + 1) + " ");

            // Mark the next city as visited
            mask = mask | (1 << next);

            // Move to the next city
            curr = next;
        }

        // Return to the starting city
        System.out.println("1");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Read the number of cities
        n = sc.nextInt();

        // Read the travel cost matrix
        cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = sc.nextInt();
            }
        }

        // There are 2^n possible visited-city combinations
        dp = new int[1 << n][n];

        // Stores the next city for route reconstruction
        parent = new int[1 << n][n];

        // Initialize DP and parent arrays
        for (int i = 0; i < (1 << n); i++) {
            Arrays.fill(dp[i], -1);
            Arrays.fill(parent[i], -1);
        }

        // Start from city 1 (index 0), with only city 1 visited
        int minCost = tsp(1, 0);

        // Display the optimal route
        printPath();

        // Display the minimum travel cost
        System.out.println("Total expense: " + minCost);

        sc.close();
    }
}
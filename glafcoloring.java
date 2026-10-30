class FullPaletteStalls {

    int[][] adj; // Adjacency matrix representing connections between stalls
    int k, v;    // k = number of colors, v = number of stalls
    int[] color; // Stores the color assigned to each stall

    // Constructor
    FullPaletteStalls(int[][] adj, int k) {
        this.adj = adj;
        this.k = k;
        this.v = adj.length;
        this.color = new int[v];
    }

    // Starts the coloring process
    void run() {

        if (solve(0)) {

            // Print assigned colors
            for (int i = 0; i < v; i++) {
                System.out.println("Stall " + (i + 1) + "-> Color " + color[i]);
            }

        } else {
            System.out.println("No painting uses all colors");
        }
    }

    // Recursive backtracking function
    boolean solve(int s) {

        // Base case: All stalls have been colored
        if (s == v) {

            int count = 0;
            boolean[] used = new boolean[k + 1];

            // Count how many different colors were used
            for (int i = 0; i < v; i++) {
                if (!used[color[i]]) {
                    used[color[i]] = true;
                    count++;
                }
            }

            // Valid only if all k colors are used
            return count == k;
        }

        // Try every available color
        for (int c = 1; c <= k; c++) {

            boolean safe = true;

            // Check whether an adjacent stall has the same color
            for (int i = 0; i < s; i++) {
                if (adj[s][i] == 1 && color[i] == c) {
                    safe = false;
                    break;
                }
            }

            // If safe, assign the color and move to next stall
            if (safe) {
                color[s] = c;

                // Continue recursively
                if (solve(s + 1)) {
                    return true;
                }
            }
        }

        // No valid color found, backtrack
        return false;
    }
}
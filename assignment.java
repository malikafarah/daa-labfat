class SettledPair {

    int n, fixedNurse, fixedWard;
    int[][] allowance; // Cost of assigning each nurse to each ward

    int minTotal = Integer.MAX_VALUE; // Minimum total cost found
    int[] best, current; // Best and current assignments
    boolean[] used; // Tracks already assigned wards

    // Constructor
    public SettledPair(int n, int[][] allowance, int fixedNurse, int fixedWard) {
        this.n = n;
        this.allowance = allowance;
        this.fixedNurse = fixedNurse;
        this.fixedWard = fixedWard;

        best = new int[n];
        current = new int[n];
        used = new boolean[n];
    }

    // Recursive Branch and Bound function
    public void search(int idx, int cost) {

        // Base case: All nurses have been assigned
        if (idx == n) {

            // Update minimum cost if a better assignment is found
            if (cost < minTotal) {
                minTotal = cost;

                // Copy current assignment into best
                System.arraycopy(current, 0, best, 0, n);
            }
            return;
        }

        // Calculate lower bound for remaining assignments
        int lb = cost;

        for (int i = idx; i < n; i++) {

            // Fixed nurse must take the fixed ward
            if (i == fixedNurse) {
                lb += allowance[i][fixedWard];

            } else {

                int min = Integer.MAX_VALUE;

                // Find minimum available assignment cost
                for (int j = 0; j < n; j++) {
                    if (!used[j] && j != fixedWard) {
                        min = Math.min(min, allowance[i][j]);
                    }
                }

                if (min != Integer.MAX_VALUE) {
                    lb += min;
                }
            }
        }

        // Prune branch if it cannot improve the current minimum
        if (lb >= minTotal) return;

        // Handle the nurse with a fixed ward
        if (idx == fixedNurse) {

            current[idx] = fixedWard;
            search(idx + 1, cost + allowance[idx][fixedWard]);

        } else {

            // Try assigning every available ward
            for (int j = 0; j < n; j++) {

                // Ward must be unused and not reserved
                if (!used[j] && j != fixedWard) {

                    used[j] = true; // Mark ward as assigned
                    current[idx] = j;

                    // Recur with updated cost
                    search(idx + 1, cost + allowance[idx][j]);

                    used[j] = false; // Backtrack
                }
            }
        }
    }

    // Display the best assignment
    public void show() {

        for (int i = 0; i < n; i++) {
            System.out.println(
                "Nurse " + (i + 1) + " takes Ward " + (best[i] + 1)
            );
        }

        System.out.println("Minimum Allowance: " + minTotal);
    }
}
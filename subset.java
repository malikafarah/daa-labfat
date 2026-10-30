class DonationSubsetFinder {

    int[] donations; // Stores donation amounts
    int n;           // Number of donations
    int target;      // Required target sum
    int[] subset;    // Stores selected donations
    int count;       // Number of selected elements
    boolean found = false; // Checks whether any valid subset exists

    // Constructor
    DonationSubsetFinder(int[] donations, int n, int target) {
        this.donations = donations;
        this.n = n;
        this.target = target;
        subset = new int[n];
        count = 0;
    }

    // Recursive backtracking function
    void findSubset(int index, int sum) {

        // If target sum is reached, print the subset
        if (sum == target) {
            found = true;

            for (int i = 0; i < count; i++) {
                System.out.print(subset[i] + " ");
            }

            System.out.println();
            return;
        }

        // Stop if all elements are checked or sum exceeds target
        if (index == n || sum > target) {
            return;
        }

        // OPTION 1: Include current donation
        subset[count] = donations[index];
        count++;

        findSubset(index + 1, sum + donations[index]);

        // Backtrack: Remove the previously selected donation
        count--;

        // OPTION 2: Exclude current donation
        findSubset(index + 1, sum);
    }

    // Starts the subset search
    void run() {
        findSubset(0, 0);

        // Print message if no valid subset exists
        if (!found) {
            System.out.println("No subset found");
        }
    }
}
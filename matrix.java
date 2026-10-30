import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Read number of dimensions
        int n = sc.nextInt();

        // Store dimensions
        int[] p = new int[n];

        for (int i = 0; i < n; i++) {
            p[i] = sc.nextInt();
        }

        // Number of matrices
        int numMatrices = n - 1;

        // DP table stores minimum multiplication cost
        int[][] dp = new int[numMatrices + 1][numMatrices + 1];

        // len = number of matrices in the chain
        for (int len = 2; len <= numMatrices; len++) {

            // i = starting matrix
            for (int i = 1; i <= numMatrices - len + 1; i++) {

                // j = ending matrix
                int j = i + len - 1;

                // Initially, minimum cost is infinity
                dp[i][j] = Integer.MAX_VALUE;

                // Try every possible splitting position
                for (int k = i; k < j; k++) {

                    // Calculate total multiplication cost
                    int cost = dp[i][k] + dp[k + 1][j]
                             + p[i - 1] * p[k] * p[j];

                    // Store minimum cost
                    if (cost < dp[i][j]) {
                        dp[i][j] = cost;
                    }
                }
            }
        }

        // Print minimum cost
        System.out.println(dp[1][numMatrices]);

        sc.close();
    }
}
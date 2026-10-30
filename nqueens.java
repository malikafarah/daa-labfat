class NQueensStartChar {

    int n, r, c;
    int[] board; // board[i] stores the column of the queen in row i

    // Constructor
    NQueensStartChar(int n, int startRow, int startCol) {
        this.n = n;
        this.r = startRow; // Fixed queen's row
        this.c = startCol; // Fixed queen's column
        this.board = new int[n];
    }

    // Start solving with the fixed queen
    void solveAll() {
        board[r] = c;
        solve(0);
    }

    // Recursive backtracking function
    void solve(int i) {

        // Base case: All rows have been filled
        if (i == n) {

            // Print the chessboard
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < n; y++) {
                    System.out.print(board[x] == y ? "Q" : ". ");
                }
                System.out.println();
            }

            System.out.println();
            return;
        }

        // Skip the row containing the fixed queen
        if (i == r) {
            solve(i + 1);
            return;
        }

        // Try placing a queen in every column
        for (int j = 0; j < n; j++) {

            boolean isSafe = true;

            // Check previously placed queens
            for (int k = 0; k < n; k++) {

                // Check only earlier rows and the fixed queen's row
                if (k < i || k == r) {

                    // Check same column or diagonal attack
                    if (board[k] == j ||
                        Math.abs(k - i) == Math.abs(board[k] - j)) {

                        isSafe = false;
                        break;
                    }
                }
            }

            // If position is safe, place queen and move to next row
            if (isSafe) {
                board[i] = j;
                solve(i + 1);
            }
        }
    }
}
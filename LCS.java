import java.util.Scanner;

public class Main {

    // Checks whether a character is a consonant
    private static boolean isConsonant(char c) {

        // Convert character to uppercase
        c = Character.toUpperCase(c);

        // Check if it is an alphabet and not a vowel
        return (c >= 'A' && c <= 'Z') &&
               (c != 'A' && c != 'E' && c != 'I' &&
                c != 'O' && c != 'U');
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Read two input strings
        if (!sc.hasNextLine()) return;
        String s1 = sc.nextLine().trim();

        if (!sc.hasNextLine()) return;
        String s2 = sc.nextLine().trim();

        // Find lengths of both strings
        int n = s1.length();
        int m = s2.length();

        // DP array to store LCS length
        int[][] dpLen = new int[n + 1][m + 1];

        // DP array to store maximum consonants
        int[][] dpCons = new int[n + 1][m + 1];

        // Traverse both strings
        for (int i = 1; i <= n; i++) {

            char c1 = s1.charAt(i - 1);

            for (int j = 1; j <= m; j++) {

                char c2 = s2.charAt(j - 1);

                // CASE 1: Characters are equal
                if (c1 == c2) {

                    // Take diagonal value and add 1
                    dpLen[i][j] = dpLen[i - 1][j - 1] + 1;

                    // Add 1 if the matching character is a consonant
                    dpCons[i][j] = dpCons[i - 1][j - 1]
                                   + (isConsonant(c1) ? 1 : 0);
                }

                // CASE 2: Characters are not equal
                else {

                    // If top value has a greater LCS length
                    if (dpLen[i - 1][j] > dpLen[i][j - 1]) {

                        // Copy top value
                        dpLen[i][j] = dpLen[i - 1][j];
                        dpCons[i][j] = dpCons[i - 1][j];
                    }

                    // If left value has a greater LCS length
                    else if (dpLen[i][j - 1] > dpLen[i - 1][j]) {

                        // Copy left value
                        dpLen[i][j] = dpLen[i][j - 1];
                        dpCons[i][j] = dpCons[i][j - 1];
                    }

                    // If both lengths are equal
                    else {

                        // Keep the common LCS length
                        dpLen[i][j] = dpLen[i - 1][j];

                        // Choose the maximum consonant count
                        dpCons[i][j] = Math.max(
                            dpCons[i - 1][j],
                            dpCons[i][j - 1]
                        );
                    }
                }
            }
        }

        // Get final LCS length
        int lcsLength = dpLen[n][m];

        // Get maximum consonants
        int maxConsonants = dpCons[n][m];

        // Print their product
        System.out.println(lcsLength * maxConsonants);

        sc.close();
    }
}
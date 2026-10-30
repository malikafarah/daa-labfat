import java.util.*;

public class OBST{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); //number of nodes

        int k[] = new int[n]; //keys
        int f[] = new int[n]; // freq

        for(int i = 0 ; i<n; i++){
            k[i] = sc.nextInt();
        }

        for(int i = 0 ; i<n; i++){
            f[i] = sc.nextInt();
        }

        // create a cost matrix & root matrix
        int cost[][] = new int[n][n];
        int root[][] = new int[n][n];

        
        //Handle all single keys
        for(int i = 0 ; i<n; i++){
            cost[i][i] = f[i];
            root[i][i] = i;
        }

        //Check different lengths

        for(int len = 2; len <= n; len++){

            for(int i = 0; i <= n - len; i++){

                int j = i + len - 1;

                int sum = 0;

                // Calculate sum of frequencies
                for(int k = i; k <= j; k++){
                    sum += freq[k];
                }

                // Check all nodes as root
                for(int r = i; r <= j; r++){

                    int left = 0;
                    int right = 0;

                    if(r > i)
                        left = cost[i][r-1];

                    if(r < j)
                        right = cost[r+1][j];

                    int total = left + right + sum;

                    if(total < cost[i][j]){
                        cost[i][j] = total;
                        root[i][j] = r;
                    }
                }
            }
        }

        System.out.println("Minimum cost: " + cost[0][n - 1]);
        printTree(root, k, 0 , n-1, -1);

    }

    static void printTree(int[][] root, int[] keys,
                          int i, int j, int parent) {

        if (i > j) {
            return;
        }

        int r = root[i][j];

        if (parent == -1) {
            System.out.println(keys[r] + " is Root");
        } else {
            System.out.println(keys[r] + " is child of " + keys[parent]);
        }

        printTree(root, keys, i, r - 1, r);
        printTree(root, keys, r + 1, j, r);
    }
}
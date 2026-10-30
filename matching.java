static int L, R;
static List<int[]> graph;
static int[] matchR;
static int[] matchL;
static boolean[] vis;

// Finds an augmenting path using DFS
static boolean kuhn(int u) {

    // Avoid visiting the same left vertex again
    if (vis[u]) return false;

    vis[u] = true;

    // Check all neighbours of left vertex u
    for (int v : graph.get(u)) {

        // If right vertex is unmatched OR its current match
        // can be assigned to another right vertex
        if (matchR[v] == -1 || kuhn(matchR[v])) {

            matchR[v] = u; // Match right vertex with u
            matchL[u] = v; // Store match for left vertex
            return true;
        }
    }

    return false;
}


static void analyzeMinVertexCover(int nL, int nR, List<int[]> adjacency) {

    L = nL; // Number of left vertices
    R = nR; // Number of right vertices
    graph = adjacency; // Store adjacency list

    matchR = new int[R]; // Stores matched left vertex for each right vertex
    matchL = new int[L]; // Stores matched right vertex for each left vertex

    Arrays.fill(matchR, -1); // Initially, all vertices are unmatched
    Arrays.fill(matchL, -1);

    int matching = 0;

    // Find maximum matching using Kuhn's algorithm
    for (int i = 0; i < L; i++) {

        vis = new boolean[L]; // Reset visited array for each DFS

        if (kuhn(i)) {
            matching++; // Increase matching count
        }
    }

    // Track visited vertices during minimum vertex cover calculation
    boolean[] visL = new boolean[L];
    boolean[] visR = new boolean[R];

    Queue<Integer> q = new LinkedList<>();

    // Start BFS from unmatched left vertices
    for (int i = 0; i < L; i++) {
        if (matchL[i] == -1) {
            visL[i] = true;
            q.offer(i);
        }
    }

    // BFS to find alternating paths
    while (!q.isEmpty()) {

        int u = q.poll();

        for (int v : graph.get(u)) {

            // Follow only unmatched edges from left to right
            if (matchL[u] != v && !visR[v]) {

                visR[v] = true;

                // Follow matched edge from right to left
                if (matchR[v] != -1 && !visL[matchR[v]]) {

                    visL[matchR[v]] = true;
                    q.offer(matchR[v]);
                }
            }
        }
    }

    ArrayList<Integer> left = new ArrayList<>();
    ArrayList<Integer> right = new ArrayList<>();

    // Minimum vertex cover includes unvisited left vertices
    for (int i = 0; i < L; i++) {
        if (!visL[i]) {
            left.add(i + 1); // Convert to 1-based numbering
        }
    }

    // Minimum vertex cover includes visited right vertices
    for (int i = 0; i < R; i++) {
        if (visR[i]) {
            right.add(i + 1);
        }
    }

    // Print maximum matching and minimum vertex cover size
    System.out.println("Maximum Matching: " + matching);
    System.out.println("Minimum Vertex cover Size: " + matching);

    // Print selected left vertices
    System.out.print("Cover (Left): ");

    if (left.isEmpty()) {
        System.out.println("None");
    } else {
        for (int i = 0; i < left.size(); i++) {
            if (i > 0) System.out.print(",");
            System.out.print(left.get(i));
        }
        System.out.println();
    }

    // Print selected right vertices
    System.out.print("Cover (Right): ");

    if (right.isEmpty()) {
        System.out.println("None");
    } else {
        for (int i = 0; i < right.size(); i++) {
            if (i > 0) System.out.print(",");
            System.out.print(right.get(i));
        }
        System.out.println();
    }
}
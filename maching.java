// Gale-Shapley algorithm to find a stable matching
static int[] galeShapley(int n, int[][] menPref, int[][] womenPref) {

    int[] womanPartner = new int[n]; // Stores partner of each woman
    int[] manPartner = new int[n];   // Stores partner of each man

    Arrays.fill(womanPartner, -1); // Initially everyone is free
    Arrays.fill(manPartner, -1);

    int rank[][] = new int[n][n];

    // Store each woman's preference ranking
    // Smaller rank means higher preference
    for (int w = 0; w < n; w++) {
        for (int i = 0; i < n; i++) {
            rank[w][womenPref[w][i]] = i;
        }
    }

    int next[] = new int[n]; // Next woman each man should propose to

    Queue<Integer> free = new LinkedList<>();

    // Initially, all men are free
    for (int i = 0; i < n; i++) {
        free.add(i);
    }

    // Continue until no free men remain
    while (!free.isEmpty()) {

        int m = free.poll(); // Take a free man

        // Propose to his next preferred woman
        int w = menPref[m][next[m]++];

        // If the woman is free, accept the proposal
        if (womanPartner[w] == -1) {

            womanPartner[w] = m;
            manPartner[m] = w;

        } else {

            // Get her current partner
            int cur = womanPartner[w];

            // Check whether she prefers the new man
            if (rank[w][m] < rank[w][cur]) {

                womanPartner[w] = m; // Accept new man
                manPartner[m] = w;

                manPartner[cur] = -1; // Previous partner becomes free
                free.add(cur);

            } else {

                // Reject proposal; man proposes again later
                free.add(m);
            }
        }
    }

    return manPartner; // Return final matching
}


// Compare employer-optimal and freelancer-optimal matchings
static void compareOptimalMatchings(int n, int[][] menPref, int[][] womenPref) {

    // Find employer-optimal matching
    int[] emp = galeShapley(n, menPref, womenPref);

    // Reverse preferences to make freelancers propose
    int[][] revMen = womenPref;
    int[][] revWomen = menPref;

    // Find freelancer-optimal matching
    int[] temp = galeShapley(n, revMen, revWomen);

    int[] freeOptimal = new int[n];

    // Convert the reversed matching into original employer-freelancer order
    for (int f = 0; f < n; f++) {
        freeOptimal[temp[f]] = f;
    }

    int diff = 0;

    // Print employer-optimal matching
    for (int i = 0; i < n; i++) {
        System.out.println(
            "Employer-Optimal : Employer " + (i + 1) +
            " - Freelancer " + (emp[i] + 1)
        );
    }

    // Print freelancer-optimal matching
    for (int i = 0; i < n; i++) {
        System.out.println(
            "Freelancer-Optimal : Employer " + (i + 1) +
            " - Freelancer " + (freeOptimal[i] + 1)
        );
    }

    // Count assignments that differ between both matchings
    for (int i = 0; i < n; i++) {
        if (emp[i] != freeOptimal[i]) {
            diff++;
        }
    }

    System.out.println("Differing Assignments : " + diff);
}
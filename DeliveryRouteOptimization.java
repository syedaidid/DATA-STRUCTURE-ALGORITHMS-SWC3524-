import java.util.*;
import java.util.Collections;
import java.util.PriorityQueue;

public class DeliveryRouteOptimization {

    // Distance matrix between locations (Warehouses and Centers)
    static int[][] distanceMatrix = {
            { 0, 12, 18, 22 },
            { 12, 0, 40, 30 },
            { 18, 40, 0, 35 },
            { 22, 30, 35, 0 }
        };

    // Names of locations corresponding to distance matrix indices
    static String[] locations = { "Warehouse A", "Warehouse B", "Center C", "Center D" };

    /**
     * @author Idham
     * Solves TSP using greedy nearest-neighbor approach
     * @param distanceMatrix Matrix of distances between locations
     * @param locations Array of location names
     * @return Formatted string with route and total distance
     */
    public static String greedyTSP(int[][] distanceMatrix, String[] locations) {
        int n = distanceMatrix.length; // Number of locations
        boolean[] visited = new boolean[n]; // Track visited locations
        int current = 0; // Start from Warehouse A (index 0)
        visited[current] = true;
        int totalDistance = 0;
        StringBuilder route = new StringBuilder(locations[current]);

        // Visit all locations
        for (int i = 1; i < n; i++) {
            int nearest = -1;
            int minDist = Integer.MAX_VALUE;

            // Find the nearest unvisited location
            for (int j = 0; j < n; j++) {
                if (!visited[j] && distanceMatrix[current][j] < minDist) {
                    minDist = distanceMatrix[current][j];
                    nearest = j;
                }
            }

            // Move to the nearest location
            totalDistance += minDist;
            current = nearest;
            visited[current] = true;
            route.append(" -> ").append(locations[current]);
        }

        // Return to starting point
        totalDistance += distanceMatrix[current][0];
        route.append(" -> ").append(locations[0]);

        return "Route: " + route.toString() + " |  Total Distance: " + totalDistance + " km";
    }

    // Constant representing infinity for DP initialization
    private static final int INF = Integer.MAX_VALUE;

    /**
     * @author Nik
     * Solves TSP using Dynamic Programming with memoization
     * @param dist Distance matrix between locations
     * @return String with optimal cost and path
     */
    public static String dynamicProgrammingTSP(int[][] dist) {
        int n = dist.length;
        int VisitedAll = (1 << n) - 1; // Bitmask representing all locations visited
        int[][] memo = new int[n][1 << n]; // Memoization table
        String[][] path = new String[n][1 << n]; // Path reconstruction table

        // Start from location 0 with only itself visited (mask = 1)
        int cost = dynamicProgrammingTSPH(0, 1, dist, memo, VisitedAll, path);

        return "Cost: " + cost + ", Path: " + path[0][1];
    }

    /**
     * DP helper function using bitmask technique
     * @param pos Current location index
     * @param mask Bitmask representing visited locations
     * @param dist Distance matrix
     * @param memo Memoization table
     * @param VisitedAll Complete visitation bitmask
     * @param path Path reconstruction table
     * @return Minimum cost to complete tour from current state
     */
    private static int dynamicProgrammingTSPH(int pos, int mask, int[][] dist, int[][] memo, int VisitedAll, String[][] path) {
        // Base case: all locations visited
        if (mask == VisitedAll) {
            path[pos][mask] = locations[pos] + " -> " + locations[0];
            return dist[pos][0]; // Return to start
        }

        // Return memoized result if available
        if (memo[pos][mask] != 0) {
            return memo[pos][mask];   
        }

        int ans = INF;

        // Try all unvisited locations
        for (int location = 0; location < dist.length; location++) {
            if ((mask & (1 << location)) == 0) { // If location not visited
                // Recursively compute cost
                int newAns = dist[pos][location] + dynamicProgrammingTSPH(location, mask | (1 << location), dist, memo, VisitedAll, path);

                // Update best solution
                if (newAns < ans) {
                    ans = newAns;
                    path[pos][mask] = locations[pos] + "->" + path[location][mask | (1 << location)];
                }
            }
        }

        // Memoize and return result
        return memo[pos][mask] = ans;
    }

    /**
     * @author Syed
     * Solves TSP using backtracking
     * @param dist Distance matrix between cities
     * @return Formatted path string with total distance or "No path found"
     */
    public static String backtrackingTSP(int[][] dist) {
        int n = dist.length; // Number of cities
        boolean[] visited = new boolean[n]; // Track visited cities
        visited[0] = true; // Start from first city (index 0)
        StringBuilder path = new StringBuilder(locations[0] + " -> "); // Initialize path

        // Begin recursive backtracking
        int minCost = tspBacktracking(0, dist, visited, n, 1, 0, path);

        if (minCost == Integer.MAX_VALUE) {
            return "No path found"; // No valid Hamiltonian cycle
        }
        return path.toString() + " = " + minCost; // Return formatted solution
    }

    /**
     * Recursive backtracking helper for TSP
     * @param pos Current city position
     * @param dist Distance matrix
     * @param visited Visited cities array
     * @param n Total number of cities
     * @param count Number of visited cities
     * @param cost Accumulated path cost so far
     * @param path Current path being constructed
     * @return Minimum cost of complete tour from current state
     */
    private static int tspBacktracking(int pos, int[][] dist, boolean[] visited, int n, int count, int cost,
    StringBuilder path) {
        // Base case: all cities visited
        if (allVisited(visited)) {
            path.append(" -> ").append(locations[0]); // Complete cycle
            return cost + dist[pos][0]; // Add return trip cost
        }

        int minAns = Integer.MAX_VALUE; // Track minimum path cost
        StringBuilder bestPath = new StringBuilder(path); // Track best path

        // Explore all unvisited cities
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                visited[i] = true; // Mark as visited
                int prevLength = path.length(); // Remember path length

                // Append current location to path
                path.append((count == 1) ? locations[i] : " -> " + locations[i]);

                // Recursively explore from this city
                int currentCost = tspBacktracking(i, dist, visited, n, count + 1, 
                        cost + dist[pos][i], path);

                // Update best solution
                if (currentCost < minAns) {
                    minAns = currentCost;
                    bestPath.setLength(0); // Reset best path
                    bestPath.append(path); // Store new best path
                }

                // Backtrack
                visited[i] = false;
                path.setLength(prevLength); // Revert path
            }
        }

        // Restore best found path
        path.setLength(0);
        path.append(bestPath);
        return minAns;
    }

    /**
     * Checks if all cities have been visited
     * @param visited Boolean array of visited status
     * @return true if all cities visited, false otherwise
     */
    private static boolean allVisited(boolean[] visited) {
        for (boolean cityVisited : visited) {
            if (!cityVisited) return false;
        }
        return true;
    }

    /**
     * @author Aisy
     * Sorts array using insertion sort algorithm
     * @param arr Array to be sorted (modified in-place)
     */
    public static void insertionSort(int[] arr) { 
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i]; // Current element to insert
            int j = i - 1;

            // Shift elements greater than key to the right
            while (j >= 0 && arr[j] > key) { 
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key; // Insert key in correct position
        }   
    }

    /**
     * Performs binary search on sorted array
     * @param arr Sorted array to search
     * @param target Value to search for
     * @return Index of target if found, -1 otherwise
     */
    public static int binarySearch(int[] arr, int target) { 
        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2; // Prevent overflow

            if (arr[mid] == target)
                return mid; // Target found
            else if (arr[mid] < target)
                left = mid + 1; // Search right half
            else 
                right = mid - 1; // Search left half
        }
        return -1; // Element not found
    }
    // Driver method
    public static void main(String[] args)
    {
        System.out.println(greedyTSP(distanceMatrix, locations));
        System.out.println(dynamicProgrammingTSP(distanceMatrix));
        System.out.println(backtrackingTSP(distanceMatrix));
        // Sorting and Searching
        int[] arr = {8, 3, 5, 1, 9, 2};
        insertionSort(arr);
        System.out.println("Sorted Array: " + Arrays.toString(arr));
        System.out.println("Binary Search (5 found at index): " + binarySearch(arr,
                5));
        // // Min-Heap Test
        // MinHeap heap = new MinHeap();
        // heap.insert(10);
        // heap.insert(3);
        // heap.insert(15);
        // System.out.println("Min-Heap Extract Min: " + heap.extractMin());
       
        // // Splay Tree Test
        // SplayTree tree = new SplayTree();
        // tree.insert(20);
        // tree.insert(10);
        // tree.insert(30);
        // System.out.println("Splay Tree Search (10 found): " + tree.search(10));
    }
}
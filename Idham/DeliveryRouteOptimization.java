import java.util.Arrays;

public class DeliveryRouteOptimization 
{
    // Distance Matrix (Adjacency Matrix)
    static int[][] distanceMatrix = 
    {
        {0, 12, 18, 22},
        {12, 0, 40, 30},
        {18, 40, 0, 35},
        {22, 30, 35, 0}
    };

    // Location names
    static String[] locations = {"Warehouse A", "Warehouse B", "Center C", "Center D"};

    // Greedy TSP
    public static String greedyTSP(int[][] dist) {
        int n = dist.length; // Number of locations
        boolean[] visited = new boolean[n]; // Track visited locations
        int current = 0; // Start from Warehouse A (index 0)
        visited[current] = true;
        int totalDistance = 0;
        StringBuilder route = new StringBuilder(locations[current]);

        for (int i = 1; i < n; i++) 
        {
            int nearest = -1;
            int minDist = Integer.MAX_VALUE;

            // Find the nearest unvisited location
            for (int j = 0; j < n; j++) 
            {
                if (!visited[j] && dist[current][j] < minDist) 
                {
                    minDist = dist[current][j];
                    nearest = j;
                }
            }

            // Move to the nearest location
            totalDistance += minDist;
            current = nearest;
            visited[current] = true;
            route.append(" -> ").append(locations[current]);
        }

        // Return to Warehouse A
        totalDistance += dist[current][0];
        route.append(" -> ").append(locations[0]);

        return "Route: " + route.toString() + "\nTotal Distance: " + totalDistance + " km";
    }

    // Main method to run
    public static void main(String[] args) 
    {
        System.out.println(greedyTSP(distanceMatrix));
    }
}


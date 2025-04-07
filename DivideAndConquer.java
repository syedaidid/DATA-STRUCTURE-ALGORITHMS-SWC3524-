import java.util.*;
/**
   * @author Alif
*/
public class DivideAndConquer {
    
    static int[][] distances = {
        {0, 12, 18, 22},
        {12, 0, 40, 30},
        {18, 40, 0, 35},
        {22, 30, 35, 0}
    };

    static String[] places = {"Warehouse A", "Warehouse B", "Center C", "Center D"};
    static List<Integer> optimalRoute = new ArrayList<Integer>();
    static int lowestCost = Integer.MAX_VALUE;
    
    // Recursive method to divide the places into two parts and solve TSP on each part
    public static void solveTSPDivideConquer(List<Integer> route, int cost, Set<Integer> visited) {
        if (route.size() == places.length) {
            // Base case: All places have been visited, calculate cost to return to the start
            cost += distances[route.get(route.size() - 1)][route.get(0)];
            if (cost < lowestCost) {
                lowestCost = cost;
                optimalRoute = new ArrayList<>(route);
            }
            return;
        }

        // Divide: Split into two subproblems (halves of the places)
        for (int i = 0; i < places.length; i++) {
            if (!visited.contains(i)) {
                route.add(i);
                visited.add(i);

                // Conquer: Recur for the next place
                solveTSPDivideConquer(route, cost + (route.size() > 1 ? distances[route.get(route.size() - 2)][i] : 0), visited);

                // Backtrack: Unmark the place and remove it from the route
                visited.remove(i);
                route.remove(route.size() - 1);
            }
        }
    }
    
    public static void main(String[] args) {
        List<Integer> route = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        
        // Start the recursion with the first place (Warehouse A)
        route.add(0);
        visited.add(0);
        solveTSPDivideConquer(route, 0, visited);

        // Print the optimal route and cost
        System.out.println("==============================");
        System.out.println("      OPTIMAL DELIVERY ROUTE");
        System.out.println("==============================");
        
        for (int i = 0; i < optimalRoute.size(); i++) {
            System.out.print(places[optimalRoute.get(i)]);
            if (i < optimalRoute.size() - 1) {
                System.out.print(" → ");
            }
        }
        System.out.println(" → " + places[0]); // Return to the starting point
        
        System.out.println("------------------------------");
        System.out.println("Total Delivery Cost: " + lowestCost + " units");
        System.out.println("==============================");
    }
}
import java.util.*;
public class DeliveryRouteOptimization 
{
 // Distance Matrix (Adjacency Matrix)
 private static int[][] dist = 
 {
 {0, 12, 18, 22}, 
 {12, 0, 40, 30}, 
 {18, 40, 0, 35}, 
 {22, 30, 35, 0} 
 };
 // Location names
 private static String[] locations = {"Warehouse A", "Warehouse B", "Center C", "Center D"};
 private static final int INF = Integer.MAX_VALUE;
public static String dynamicProgrammingTSP(int[][] dist)
{
 int n = dist.length;
 int VisitedAll = (1 << n) - 1;
 int [][] memo = new int[n][1 << n];
 String[][] path = new String[n][1 << n];
 
 int cost = dynamicProgrammingTSPH(0,1,dist,memo,VisitedAll,path);
 
 return "Cost: " + cost + ", Path: " + path[0][1];
}

private static int dynamicProgrammingTSPH(int pos, int mask, int[][] dist, int[][] memo, int VisitedAll,String[][] path)
{
 if (mask == VisitedAll)
 {
  path[pos][mask] = locations[pos] + " -> " + locations[0];
  return dist[pos][0];
 }
 if (memo[pos][mask] !=0)
 {
  return memo[pos][mask];   
 }
 int ans = INF;
 
 for (int location = 0; location < dist.length; location++)
 {
  if ((mask &(1 << location)) == 0)
  {
   int newAns = dist[pos][location] + dynamicProgrammingTSPH(location,mask | (1 << location), dist, memo, VisitedAll, path);
   if (newAns < ans)
   {
    ans = newAns;
    path[pos][mask] = locations[pos] + "->" + path[location][mask | (1 << location)];
   }
  }
 }
 return memo[pos][mask] = ans;
}

public static void main(String[] args)
{
 System.out.println(dynamicProgrammingTSP(dist));
}
}
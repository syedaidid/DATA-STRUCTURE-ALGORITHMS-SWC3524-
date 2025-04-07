import java.util.*;

public class MinHeap 
{
    // Min-Heap
    private PriorityQueue<Integer> heap = new PriorityQueue<>();

    // Method to insert element into the heap
    public void insert(int value) 
    {
        heap.add(value);
    }

    // Method to extract the minimum (smallest) element from the heap
    public int extractMin() 
    {
        return heap.poll(); // poll() removes and returns the smallest element
    }

    // Driver method
    public static void main(String[] args) 
    {
        // Min-Heap Test
        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(3);
        heap.insert(15);
        System.out.println("Min-Heap Extract Min: " + heap.extractMin()); // Output should be 3
    }
}

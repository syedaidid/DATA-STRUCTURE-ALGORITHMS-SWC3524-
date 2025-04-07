import java.util.*;

public class MinHeap 
{
    private PriorityQueue<Integer> heap = new PriorityQueue<>();

    public void insert(int num) 
    {
        heap.add(num);
    }

    public int extractMin() 
    {
        if (heap.isEmpty()) 
        {
            throw new IllegalStateException("Heap is empty!");
        }
        return heap.remove();
    }
    public static void main(String[] args) 
    {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(3);
        heap.insert(15);
        System.out.println("Min-Heap Extract Min: " + heap.extractMin());
    }
}
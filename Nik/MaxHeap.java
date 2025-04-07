import java.util.*;

public class MaxHeap 
{
    private PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());

    public void insert(int num) 
    {
        heap.add(num);
    }

    public int extractMax() 
    {
        if (heap.isEmpty()) 
        {
            throw new IllegalStateException("Heap is empty!");
        }
        return heap.remove();
    }

    public static void main(String[] args) 
    {
        MaxHeap heap = new MaxHeap();
        heap.insert(10);
        heap.insert(3);
        heap.insert(15);
        System.out.println("Max-Heap Extract Max: " + heap.extractMax());
    }
}
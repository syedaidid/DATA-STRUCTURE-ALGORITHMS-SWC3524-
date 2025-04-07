import java.util.*;

public class MinHeap {
    // Array to store heap elements
    private int[] heap;
    private int size; // Current size of the heap
    private int capacity; // Maximum capacity of the heap

    // Constructor to initialize the heap
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        heap = new int[capacity];
    }

    // Parent index for a given index
    private int parent(int i) {
        return (i - 1) / 2;
    }

    // Left child index for a given index
    private int leftChild(int i) {
        return 2 * i + 1;
    }

    // Right child index for a given index
    private int rightChild(int i) {
        return 2 * i + 2;
    }

    // Method to swap two elements in the heap
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // Method to heapify (maintain the heap property) after extracting the minimum
    private void heapify(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        // Check if left child exists and is smaller than the current smallest
        if (left < size && heap[left] < heap[smallest]) {
            smallest = left;
        }

        // Check if right child exists and is smaller than the current smallest
        if (right < size && heap[right] < heap[smallest]) {
            smallest = right;
        }

        // If the smallest element is not the current element, swap and heapify further
        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    // Method to insert a new element in the heap
    public void insert(int value) {
        if (size == capacity) {
            System.out.println("Heap is full");
            return;
        }

        // Insert the new value at the end of the heap
        heap[size] = value;
        size++;

        // Move the new value to its correct position
        int i = size - 1;
        while (i != 0 && heap[parent(i)] > heap[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    // Method to extract the minimum (root) element from the heap
    public int extractMin() {
        if (size == 0) {
            System.out.println("Heap is empty");
            return Integer.MAX_VALUE;
        }

        // The root contains the minimum element
        int root = heap[0];

        // Replace root with the last element in the heap
        heap[0] = heap[size - 1];
        size--;

        // Heapify the root to maintain the heap property
        heapify(0);

        return root;
    }

    // Method to get the minimum element (root) without extracting it
    public int getMin() {
        if (size == 0) {
            System.out.println("Heap is empty");
            return Integer.MAX_VALUE;
        }
        return heap[0];
    }

    // Main method to test the Min-Heap
    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap(10);

        // Insert some elements
        minHeap.insert(10);
        minHeap.insert(3);
        minHeap.insert(15);
        
    

        // Extract the minimum element
        System.out.println("Extracted Min: " + minHeap.extractMin());


    }
}

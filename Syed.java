import java.util.*;

public class Syed {
    
    /**
     * MinHeap implementation using Java's PriorityQueue
     * Provides O(log n) insert and extract operations
     */
    public static class MinHeap {
        private PriorityQueue<Integer> heap = new PriorityQueue<>();
        
        // Insert maintains heap property automatically
        public void insert(int value) {
            heap.add(value);  // O(log n) insertion
        }
        
        // Most efficient extraction method
        public int extractMin() {
            if (heap.isEmpty()) throw new IllegalStateException("Heap is empty");
            return heap.poll();  // O(log n) extraction
        }
        
        // Alternative manual extraction (less efficient)
        public int extractMinManual() {
            if (heap.isEmpty()) throw new IllegalStateException("Heap is empty");
            int min = heap.peek();
            heap.remove(min);  // O(n) linear search removal
            return min;
        }
        
        public boolean isEmpty() {
            return heap.isEmpty(); 
        }
    }

    /**
     * MaxHeap implementation using reverse-ordered PriorityQueue
     * Mirror of MinHeap with descending order
     */
    public static class MaxHeap {
        private PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        
        public void insert(int value) {
            heap.add(value);  // O(log n) insertion
        }
        
        // Standard efficient extraction
        public int extractMax() {
            if (heap.isEmpty()) throw new IllegalStateException("Heap is empty");
            return heap.poll();  // O(log n) extraction
        }
        
        // Manual extraction alternative
        public int extractMaxManual() {
            if (heap.isEmpty()) throw new IllegalStateException("Heap is empty");
            int max = heap.peek();
            heap.remove(max);  // O(n) linear search removal
            return max;
        }
        
        public boolean isEmpty() {
            return heap.isEmpty();
        }
    }

    /**
     * Splay Tree implementation with self-adjusting property
     * Frequently accessed nodes move closer to root (O(log n) amortized)
     */
    public static class SplayTree {
        private static class Node {
            int value;
            Node left, right, parent;
            
            public Node(int value) {
                this.value = value;
                this.left = this.right = this.parent = null;
            }
        }
        
        private Node root;
        
        public SplayTree() {
            this.root = null;
        }
        
        // Insert with splaying to bring new node to root
        public void insert(int value) {
            Node newNode = new Node(value);
            if (root == null) {
                root = newNode;
                return;
            }
            
            // Standard BST insertion
            Node current = root;
            Node parent = null;
            while (current != null) {
                parent = current;
                if (value < current.value) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            
            newNode.parent = parent;
            if (value < parent.value) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
            
            splay(newNode);  // Self-adjusting step
        }
        
        // Search with splaying to bring found node to root
        public boolean search(int value) {
            Node node = findNode(value);
            if (node != null) {
                splay(node);  // Self-adjusting
                return true;
            }
            return false;
        }
        
        // Internal BST search helper
        private Node findNode(int value) {
            Node current = root;
            while (current != null) {
                if (value == current.value) {
                    return current;
                } else if (value < current.value) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            return null;
        }
        
        // Core splaying operation with zig, zig-zig, and zig-zag cases
        private void splay(Node node) {
            while (node.parent != null) {
                Node parent = node.parent;
                Node grandparent = parent.parent;
                
                if (grandparent == null) {
                    // Zig case (single rotation)
                    if (node == parent.left) {
                        rotateRight(parent);
                    } else {
                        rotateLeft(parent);
                    }
                } else {
                    if (node == parent.left && parent == grandparent.left) {
                        // Zig-zig case (left-left)
                        rotateRight(grandparent);
                        rotateRight(parent);
                    } else if (node == parent.right && parent == grandparent.right) {
                        // Zig-zig case (right-right)
                        rotateLeft(grandparent);
                        rotateLeft(parent);
                    } else if (node == parent.right && parent == grandparent.left) {
                        // Zig-zag case (left-right)
                        rotateLeft(parent);
                        rotateRight(grandparent);
                    } else {
                        // Zig-zag case (right-left)
                        rotateRight(parent);
                        rotateLeft(grandparent);
                    }
                }
            }
            root = node;  // Node is now root
        }
        
        // Standard left rotation
        private void rotateLeft(Node node) {
            Node rightChild = node.right;
            node.right = rightChild.left;
            if (rightChild.left != null) {
                rightChild.left.parent = node;
            }
            rightChild.parent = node.parent;
            if (node.parent == null) {
                root = rightChild;
            } else if (node == node.parent.left) {
                node.parent.left = rightChild;
            } else {
                node.parent.right = rightChild;
            }
            rightChild.left = node;
            node.parent = rightChild;
        }
        
        // Standard right rotation
        private void rotateRight(Node node) {
            Node leftChild = node.left;
            node.left = leftChild.right;
            if (leftChild.right != null) {
                leftChild.right.parent = node;
            }
            leftChild.parent = node.parent;
            if (node.parent == null) {
                root = leftChild;
            } else if (node == node.parent.right) {
                node.parent.right = leftChild;
            } else {
                node.parent.left = leftChild;
            }
            leftChild.right = node;
            node.parent = leftChild;
        }
    }

    // Test driver
    public static void main(String[] args) {
        // Min-Heap Test
        MinHeap minHeap = new MinHeap();
        minHeap.insert(10);
        minHeap.insert(3);
        minHeap.insert(15);
        System.out.println("Min-Heap Extract Min: " + minHeap.extractMin());  // Should return 3

        // Max-Heap Test
        MaxHeap maxHeap = new MaxHeap();
        maxHeap.insert(10);
        maxHeap.insert(3);
        maxHeap.insert(15);
        System.out.println("Max-Heap Extract Max: " + maxHeap.extractMax());  // Should return 15

        // Splay Tree Test
        SplayTree tree = new SplayTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        System.out.println("Splay Tree Search (10 found): " + tree.search(10));  // Returns true and splays 10 to root
    }
}
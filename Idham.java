import java.util.*;

public class Idham {
    // ===================== MinHeap Implementation =====================
    public static class MinHeap {
        private PriorityQueue<Integer> heap = new PriorityQueue<>();

        public void insert(int value) {
            heap.add(value);
        }

        public int extractMin() {
            return heap.poll(); // Removes and returns the smallest element
        }

        public void displayHeap() {
            System.out.println("Min-Heap Elements: " + heap);
        }

        public void demo() {
            MinHeap heap = new MinHeap();
            heap.insert(10);
            heap.insert(3);
            heap.insert(15);
            System.out.println("Min-Heap Extract Min: " + heap.extractMin()); // Output: 3
            heap.displayHeap();
        }
    }

    // ===================== MaxHeap Implementation =====================
    public static class MaxHeap {
        private PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());

        public void insert(int value) {
            heap.add(value);
        }

        public int extractMax() {
            return heap.poll(); // Removes and returns the largest element
        }

        public void displayHeap() {
            System.out.println("Max-Heap Elements: " + heap);
        }

        public void demo() {
            MaxHeap heap = new MaxHeap();
            heap.insert(10);
            heap.insert(3);
            heap.insert(15);
            System.out.println("Max-Heap Extract Max: " + heap.extractMax()); // Output: 15
            heap.displayHeap();
        }
    }

    // ===================== SplayTree Implementation =====================
    public static class SplayTree {
        static class SplayTreeNode {
            int key;
            SplayTreeNode left, right;

            public SplayTreeNode(int item) {
                key = item;
                left = right = null;
            }
        }

        private SplayTreeNode root;

        public SplayTree() {
            root = null;
        }

        // Insert method with splaying
        public void insert(int key) {
            root = insertRec(root, key);
            root = splay(root, key); // Splay the newly inserted node to the root
        }

        private SplayTreeNode insertRec(SplayTreeNode root, int key) {
            if (root == null) return new SplayTreeNode(key);
            if (key < root.key) root.left = insertRec(root.left, key);
            else if (key > root.key) root.right = insertRec(root.right, key);
            return root;
        }

        // Search method with splaying
        public boolean search(int key) {
            root = splay(root, key); // Splay the tree with the searched key
            return root != null && root.key == key;
        }

        // Splay operation
        private SplayTreeNode splay(SplayTreeNode root, int key) {
            if (root == null || root.key == key) return root;

            // Key is in the left subtree
            if (key < root.key) {
                if (root.left == null) return root; // Key not found
                if (key < root.left.key) {
                    // Zig-Zig (Left Left)
                    root.left.left = splay(root.left.left, key);
                    root = rotateRight(root);
                } else if (key > root.left.key) {
                    // Zig-Zag (Left Right)
                    root.left.right = splay(root.left.right, key);
                    if (root.left.right != null) root.left = rotateLeft(root.left);
                }
                return (root.left == null) ? root : rotateRight(root);
            } else {
                // Key is in the right subtree
                if (root.right == null) return root; // Key not found
                if (key > root.right.key) {
                    // Zag-Zag (Right Right)
                    root.right.right = splay(root.right.right, key);
                    root = rotateLeft(root);
                } else if (key < root.right.key) {
                    // Zag-Zig (Right Left)
                    root.right.left = splay(root.right.left, key);
                    if (root.right.left != null) root.right = rotateRight(root.right);
                }
                return (root.right == null) ? root : rotateLeft(root);
            }
        }

        // Right Rotation
        private SplayTreeNode rotateRight(SplayTreeNode y) {
            SplayTreeNode x = y.left;
            y.left = x.right;
            x.right = y;
            return x;
        }

        // Left Rotation
        private SplayTreeNode rotateLeft(SplayTreeNode x) {
            SplayTreeNode y = x.right;
            x.right = y.left;
            y.left = x;
            return y;
        }

        // Preorder Traversal
        public void preOrder() {
            System.out.print("Tree (PreOrder): ");
            preOrderRec(root);
            System.out.println();
        }

        private void preOrderRec(SplayTreeNode node) {
            if (node != null) {
                System.out.print(node.key + " ");
                preOrderRec(node.left);
                preOrderRec(node.right);
            }
        }

        public void demo() {
            SplayTree tree = new SplayTree();
            tree.insert(20);
            tree.insert(10);
            tree.insert(30);

            boolean found = tree.search(10);
            System.out.println("Splay Tree Search (10 found): " + found); // Output: true

            tree.preOrder(); // Display structure of the tree
        }
    }

    // ===================== Main Method =====================
    public static void main(String[] args) {
        System.out.println("=== Testing MinHeap ===");
        new MinHeap().demo();
        
        System.out.println("\n=== Testing MaxHeap ===");
        new MaxHeap().demo();
        
        System.out.println("\n=== Testing SplayTree ===");
        new SplayTree().demo();
    }
}
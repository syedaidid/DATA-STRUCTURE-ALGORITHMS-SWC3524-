import java.util.*;

public class AlifHilmy {
    // ========== MinHeap ==========
    static class MinHeap {
        private int[] heap;
        private int size;
        private int capacity;

        public MinHeap(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            heap = new int[capacity];
        }

        private int parent(int i) { return (i - 1) / 2; }
        private int leftChild(int i) { return 2 * i + 1; }
        private int rightChild(int i) { return 2 * i + 2; }

        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

        private void heapify(int i) {
            int smallest = i;
            int left = leftChild(i);
            int right = rightChild(i);

            if (left < size && heap[left] < heap[smallest]) smallest = left;
            if (right < size && heap[right] < heap[smallest]) smallest = right;

            if (smallest != i) {
                swap(i, smallest);
                heapify(smallest);
            }
        }

        public void insert(int value) {
            if (size == capacity) {
                System.out.println("Heap is full");
                return;
            }
            heap[size] = value;
            size++;

            int i = size - 1;
            while (i != 0 && heap[parent(i)] > heap[i]) {
                swap(i, parent(i));
                i = parent(i);
            }
        }

        public int extractMin() {
            if (size == 0) {
                System.out.println("Heap is empty");
                return Integer.MAX_VALUE;
            }

            int root = heap[0];
            heap[0] = heap[size - 1];
            size--;
            heapify(0);
            return root;
        }
    }

    // ========== Splay Tree ==========
    static class SplayTree {
        private class Node {
            int key;
            Node left, right;
            public Node(int key) { this.key = key; }
        }

        private Node root;

        private Node rightRotate(Node x) {
            if (x.left == null) return x;
            Node y = x.left;
            x.left = y.right;
            y.right = x;
            return y;
        }

        private Node leftRotate(Node x) {
            if (x.right == null) return x;
            Node y = x.right;
            x.right = y.left;
            y.left = x;
            return y;
        }

        private Node splay(Node root, int key) {
            if (root == null || root.key == key) return root;

            if (key < root.key) {
                if (root.left == null) return root;
                if (key < root.left.key) {
                    root.left.left = splay(root.left.left, key);
                    root = rightRotate(root);
                } else if (key > root.left.key) {
                    root.left.right = splay(root.left.right, key);
                    if (root.left.right != null) root.left = leftRotate(root.left);
                }
                return (root.left == null) ? root : rightRotate(root);
            } else {
                if (root.right == null) return root;
                if (key > root.right.key) {
                    root.right.right = splay(root.right.right, key);
                    root = leftRotate(root);
                } else if (key < root.right.key) {
                    root.right.left = splay(root.right.left, key);
                    if (root.right.left != null) root.right = rightRotate(root.right);
                }
                return (root.right == null) ? root : leftRotate(root);
            }
        }

        public void insert(int key) {
            if (root == null) {
                root = new Node(key);
                return;
            }
            root = splay(root, key);
            if (root.key == key) return;

            Node newNode = new Node(key);
            if (key < root.key) {
                newNode.right = root;
                newNode.left = root.left;
                root.left = null;
            } else {
                newNode.left = root;
                newNode.right = root.right;
                root.right = null;
            }
            root = newNode;
        }

        public boolean search(int key) {
            root = splay(root, key);
            return root != null && root.key == key;
        }

        private void inorderTraversal(Node node) {
            if (node == null) return;
            inorderTraversal(node.left);
            System.out.print(node.key + " ");
            inorderTraversal(node.right);
        }

        public void printInOrder() {
            inorderTraversal(root);
        }
    }

    // ========== Main ==========
    public static void main(String[] args) {
        // Splay Tree Test
        SplayTree tree = new SplayTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);

        System.out.println("Splay Tree Search (10 found): " + tree.search(10));
        tree.printInOrder();
        System.out.println();

        // MinHeap Test
        MinHeap minHeap = new MinHeap(10);
        minHeap.insert(10);
        minHeap.insert(3);
        minHeap.insert(15);

        System.out.println("Extracted Min: " + minHeap.extractMin());
    }
}
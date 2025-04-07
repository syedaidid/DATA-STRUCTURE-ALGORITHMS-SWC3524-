public class Splay {
    static int[][] distanceMatrix = {
        {0, 12, 18, 22},
        {12, 0, 40, 30},
        {18, 40, 0, 35},
        {22, 30, 35, 0}
    };
    static String[] locations = {"Warehouse A", "Warehouse B", "Center C", "Center D"};

    // Splay Tree class for storing locations and their distances
    static class SplayTree {
        private class Node {
            int key;
            Node left, right;

            public Node(int key) {
                this.key = key;
                left = right = null;
            }
        }

        private Node root;

        public SplayTree() {
            root = null;
        }

        private Node rightRotate(Node x) {
            // Check if x.left is null before accessing it
            if (x.left == null) return x;

            Node y = x.left;
            x.left = y.right;
            y.right = x;
            return y;
        }

        private Node leftRotate(Node x) {
            // Check if x.right is null before accessing it
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
                    root = rightRotate(root);  // Zig-Zig
                    root = rightRotate(root);
                } else if (key > root.left.key) {
                    root.left = leftRotate(root.left);  // Zig-Zag
                    root = rightRotate(root);
                }
            } else {
                if (root.right == null) return root;

                if (key > root.right.key) {
                    root = leftRotate(root);  // Zag-Zag
                    root = leftRotate(root);
                } else if (key < root.right.key) {
                    root.right = rightRotate(root.right);  // Zag-Zig
                    root = leftRotate(root);
                }
            }
            return root;
        }

        public void insert(int key) {
            if (root == null) {
                root = new Node(key);
                return;
            }

            root = splay(root, key);

            if (root.key == key) {
                return; // Key already exists
            }

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

        public void inorderTraversal(Node node) {
            if (node == null) return;
            inorderTraversal(node.left);
            System.out.print(node.key + " ");
            inorderTraversal(node.right);
        }

        public void printInOrder() {
            inorderTraversal(root);
        }
    }

    public static void main(String[] args) {
        // Splay Tree Test
        SplayTree tree = new SplayTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        
        System.out.println("Splay Tree Search (10 found): " + tree.search(10));  // Searching for 10
        tree.printInOrder();  // Print the tree after insertions
    }
}

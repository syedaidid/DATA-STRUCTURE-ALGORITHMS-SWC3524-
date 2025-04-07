public class SplayTree 
{
    static class Node 
    {
        int key;
        Node left, right, parent;

        Node(int key) 
        {
            this.key = key;
            this.left = this.right = this.parent = null;
        }
    }
    private Node root;

    public SplayTree() 
    {
        root = null;
    }
    // Rotate node x to the left
    private void leftRotate(Node x) 
    {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) 
        {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) 
        {
            root = y;
        } 
        else if (x == x.parent.left) 
        {
            x.parent.left = y;
        } 
        else 
        {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }
    // Rotate node x to the right
    private void rightRotate(Node x) 
    {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) 
        {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) 
        {
            root = y;
        } 
        else if (x == x.parent.right) 
        {
            x.parent.right = y;
        } 
        else 
        {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }
    // Splay operation to bring node to root
    private void splay(Node x) 
    {
        while (x.parent != null) 
        {
            if (x.parent.parent == null) 
            {
                if (x == x.parent.left) 
                {
                    rightRotate(x.parent);
                } 
                else 
                {
                    leftRotate(x.parent);
                }
            } 
            else if (x == x.parent.left && x.parent == x.parent.parent.left) 
            {
                rightRotate(x.parent.parent);
                rightRotate(x.parent);
            } 
            else if (x == x.parent.right && x.parent == x.parent.parent.right) 
            {
                leftRotate(x.parent.parent);
                leftRotate(x.parent);
            } 
            else if (x == x.parent.right && x.parent == x.parent.parent.left) 
            {
                leftRotate(x.parent);
                rightRotate(x.parent);
            } 
            else 
            {
                rightRotate(x.parent);
                leftRotate(x.parent);
            }
        }
    }
    // Insert a key into the tree
    public void insert(int key) 
    {
        Node node = new Node(key);
        Node y = null;
        Node x = root;
        
        while (x != null) 
        {
            y = x;
            if (node.key < x.key) 
            {
                x = x.left;
            }
            else 
            {
                x = x.right;
            }
        }
        node.parent = y;
        if (y == null) 
        {
            root = node;
        } 
        else if (node.key < y.key) 
        {
            y.left = node;
        } 
        else 
        {
            y.right = node;
        }
        splay(node);
    }
    // Search for a key in the tree
    public boolean search(int key) 
    {
        Node node = searchNode(key);
        if (node != null) 
        {
            splay(node);
            return true;
        }
        return false;
    }
    // Helper method to search for a node
    private Node searchNode(int key) 
    {
        Node x = root;
        while (x != null) 
        {
            if (key == x.key) 
            {
                return x;
            } 
            else if (key < x.key) 
            {
                x = x.left;
            } 
            else 
            {
                x = x.right;
            }
        }
        return null;
    }
    public static void main(String[] args) 
    {
        SplayTree tree = new SplayTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        System.out.println("Splay Tree Search (10 found): " + tree.search(10));
    }
}
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.List;

public class RedBlackTree {

    public static class RedBlackNode {

        Comparable element; // The data in the node
        RedBlackNode left; // Left child
        RedBlackNode right; // Right child
        int color; // Color

        // Constructors
        RedBlackNode(Comparable theElement) {

            this(theElement, null, null);
        }

        RedBlackNode(Comparable theElement, RedBlackNode lt, RedBlackNode rt) {

            element = theElement;
            left = lt;
            right = rt;
            color = RedBlackTree.BLACK;
        }
    }

    public static final int BLACK = 1; // Black must be 1
    public static final int RED = 0;

    public RedBlackNode header;
    public RedBlackNode nullNode;

    // Used in insert routine and its helpers
    public RedBlackNode current;
    public RedBlackNode parent;
    public RedBlackNode grand;
    public RedBlackNode great;

    // Static initializer for nullNode
    {
        nullNode = new RedBlackNode(null);
        nullNode.left = nullNode.right = nullNode;
    }

    /**
     * Construct the tree.
     */
    public RedBlackTree() {

        header = new RedBlackNode(null);
        header.left = header.right = nullNode;
    }

    public void MergeSort(Integer[] inarray, int l, int h) {

        int low = l;
        int high = h;
        int mid = (low + high) / 2;

        if (low >= high) {
            return;
        }

        MergeSort(inarray, low, mid);
        MergeSort(inarray, mid + 1, high);
        int end_low = mid;
        int start_high = mid + 1;
        while ((l <= end_low) && (start_high <= high)) {
            if (inarray[low] < inarray[start_high]) {
                low++;
            } else {
                int Temp = inarray[start_high];
                for (int k = start_high - 1; k >= low; k--) {
                    inarray[k + 1] = inarray[k];
                }
                inarray[low] = Temp;
                low++;
                end_low++;
                start_high++;
            }
        }
    }

    /**
     * Insert into the tree.
     * 
     * @param value the value to insert.
     * @throws DuplicatevalueException if value is already present.
     */
    public void insert(Comparable value) {

        current = parent = grand = header;
        nullNode.element = value;

        while (compare(value, current) != 0) {
            great = grand;
            grand = parent;
            parent = current;
            if (compare(value, current) < 0)
                current = current.left;
            else
                current = current.right;
            // Check if two red children; fix if so
            if (current.left.color == RED && current.right.color == RED) {
                fixTree(value);
            }
        }

        // Insertion fails if already present
        current = new RedBlackNode(value, nullNode, nullNode);

        // Attach to parent
        if (compare(value, parent) < 0)
            parent.left = current;
        else
            parent.right = current;
        fixTree(value);
    }

    /**
     * Compare value and x.element, using compareTo, with
     * caveat that if x is header, then value is always larger.
     * This routine is called if is possible that x is header.
     * If it is not possible for x to be header, use compareTo directly.
     */
    public final int compare(Comparable value, RedBlackNode x) {

        if (x == header)
            return 1;
        else
            return value.compareTo(x.element);
    }

    /**
     * Internal routine that is called during an insertion
     * if a node has two red children. Performs flip and rotations.
     * 
     * @param value the value being inserted.
     */
    public void fixTree(Comparable value) {

        // Do the color flip
        current.color = RED;
        current.left.color = BLACK;
        current.right.color = BLACK;

        if (parent.color == RED) { // Have to rotate
            grand.color = RED;
            if ((compare(value, grand) < 0) != (compare(value, parent) < 0)) {
                parent = rotate(value, grand);
            } // Start dbl rotate
            current = rotate(value, great);
            current.color = BLACK;
        }
        header.right.color = BLACK; // Make root black
    }

    /**
     * Internal routine that performs a single or double rotation.
     * Because the result is attached to the parent, there are four cases.
     * Called by handleReorient.
     * 
     * @param value  the value in handleReorient.
     * @param parent the parent of the root of the rotated subtree.
     * @return the root of the rotated subtree.
     */
    public RedBlackNode rotate(Comparable value, RedBlackNode parent) {

        if (compare(value, parent) < 0)
            return parent.left = compare(value, parent.left) < 0 ? rotateWithLeftChild(parent.left) : // LL
                    rotateWithRightChild(parent.left); // LR
        else
            return parent.right = compare(value, parent.right) < 0 ? rotateWithLeftChild(parent.right) : // RL
                    rotateWithRightChild(parent.right); // RR
    }

    /**
     * Rotate binary tree node with left child.
     */
    public RedBlackNode rotateWithLeftChild(RedBlackNode lchild) {

        RedBlackNode rchild = lchild.left;
        lchild.left = rchild.right;
        rchild.right = lchild;
        return rchild;
    }

    /**
     * Rotate binary tree node with right child.
     */
    public RedBlackNode rotateWithRightChild(RedBlackNode rchild) {

        RedBlackNode lchild = rchild.right;
        rchild.right = lchild.left;
        lchild.left = rchild;
        return lchild;
    }

    /**
     * Find an value in the tree.
     * 
     * @param value the value to search for.
     * @return the matching value or null if not found.
     */
    public Comparable search(Comparable value) {

        nullNode.element = value;
        current = header.right;

        for (;;) {
            if (value.compareTo(current.element) < 0)
                current = current.left;
            else if (value.compareTo(current.element) > 0)
                current = current.right;
            else if (current != nullNode)
                return current.element;
            else
                return null;
        }
    }

    public static void main(String[] args) throws Exception {

        RedBlackTree rbt = new RedBlackTree();
        try {
            String filepath = "C:\\Users\\chris\\Documents\\Exercises\\CEID\\4ο ΕΞ\\Δομές Δεδομένων\\Project\\Project\\src\\integers.txt";
            File file = new File(filepath);
            Scanner sc = new Scanner(file);
            List<Integer> lines = new ArrayList<Integer>();

            while (sc.hasNextLine()) {
                int i = sc.nextInt();
                lines.add(i);
            }
            sc.close();
            Integer[] InputArray = lines.toArray(new Integer[lines.size()]);
            rbt.MergeSort(InputArray, 0, InputArray.length - 1);

            System.out.println("Press ENTER to start the process of building the Red Black Tree...");
            try {
                System.in.read();
            } catch (Exception e) {
            }

            for (Integer i : InputArray) {
                rbt.insert(i);
            }

            System.out.println("\nThe Red-Black Tree was successfully built!");
            System.out.println("------------------------------------------");

            String ans = "";

            do {
                System.out.println("\nMain Menu:");
                System.out.println("----------");
                System.out.println("1. Insert a number in the Red-Black Tree");
                System.out.println("2. Search for a number in the Red-Black Tree");
                System.out.println("3. End of program");

                Scanner sci = new Scanner(System.in);
                Scanner scs = new Scanner(System.in);
                int decision = sci.nextInt();

                switch (decision) {
                    case 1:
                        System.out.println("\nWhich number would you like to insert?");
                        int numa = sci.nextInt();
                        rbt.insert(numa);
                        System.out
                                .println("\nNumber " + numa + " was successfully inserted in the Red-Black Tree!");
                        break;
                    case 2:
                        System.out.println("\nWhich number are you looking for?");
                        int val = sci.nextInt();
                        Comparable result = rbt.search(val);
                        if (result != null) {
                            System.out.println("\nNumber " + val + " was found in the Red-Black Tree!");
                        } else {
                            System.out.println("\nNumber " + val + " not found in the Red-Black Tree!");
                        }
                        break;
                    case 3:
                        System.out.println("\nEND OF PROGRAM");
                        System.out.println("--------------");
                        ans = "NO";
                        break;
                    default:
                        System.out.println("\nWrong choice!");
                        break;
                }

                if (decision != 3) {
                    System.out.println("\nWould you like to return to the Main Menu?(YES/NO)");
                    ans = scs.nextLine();
                    if (ans.equals("NO") || ans.equals("no")) {
                        System.out.println("\nEND OF PROGRAM");
                        System.out.println("--------------");
                        ans = "NO";
                    }
                }
            } while (ans.equals("YES") || ans.equals("yes"));

        } catch (IOException ie) {
            System.out.print("There was a problem!: " + ie);
        }
    }
}
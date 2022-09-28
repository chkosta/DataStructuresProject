import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Trie {

    public class TrieNode {

        Map<Character, TrieNode> children;
        boolean isEndOfWord;

        public TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }

    public final TrieNode root;

    public Trie() {

        root = new TrieNode();
    }

    public void insert(String word) {

        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode();
                current.children.put(ch, node);
            }
            current = node;
        }
        // mark the current nodes endOfWord as true
        current.isEndOfWord = true;
    }

    public boolean search(String word) {

        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            // if node does not exist for given char then return false
            if (node == null) {
                return false;
            }
            current = node;
        }
        // return true of current's endOfWord is true else return false.
        return current.isEndOfWord;
    }

    public void remove(String word) {

        remove(root, word, 0);
    }

    public void remove(TrieNode current, String word, int index) {

        int a = 2;
        if (index == word.length()) {
            // when end of word is reached only delete if currrent.endOfWord is true.
            if (!current.isEndOfWord) {
                System.out.println("\nWord " + word + " doesn't exist in the Trie!");
                a = 0;
                return;
            }
            current.isEndOfWord = false;
            // if current has no other mapping then return true
            if (current.children.size() == 0) {
                System.out.println("\nWord " + word + " was successfully deleted from the Trie!");
                a = 1;
                return;
            } else {
                System.out.println("\nWord " + word + " doesn't exist in the Trie!");
                a = 0;
                return;
            }
        }

        char ch = word.charAt(index);
        TrieNode node = current.children.get(ch);
        if (node == null) {
            System.out.println("\nWord " + word + " doesn't exist in the Trie!");
            a = 0;
            return;
        }

        remove(node, word, index + 1);

        // if true is returned then delete the mapping of character and trienode
        // reference from map.
        if (a == 1) {
            current.children.remove(ch);
            // return true if no mappings are left in the map.
            if (current.children.size() == 0) {
                System.out.println("\nWord " + word + " was successfully deleted from the Trie!");
                return;
            } else {
                System.out.println("\nWord " + word + " doesn't exist in the Trie!");
                return;
            }
        }
        System.out.println("\nWord " + word + " doesn't exist in the Trie!");
        return;
    }

    public static void main(String[] args) throws IOException {

        // Read words from words.txt, build a Trie with the ability of search,
        // insert and delete a word
        Trie tr = new Trie();
        try {
            String filepath = "C:\\Users\\chris\\Documents\\Exercises\\CEID\\4ο ΕΞ\\Δομές Δεδομένων\\Project\\Project\\src\\words.txt";
            File file = new File(filepath);
            Scanner sc = new Scanner(file);
            List<String> lines = new ArrayList<String>();
            String line = "";

            while (sc.hasNext()) {
                line = sc.next();
                lines.add(line.toLowerCase());
            }

            sc.close();
            String[] InputArray = lines.toArray(new String[lines.size()]);

            System.out.println("Press ENTER to start the process of building the Trie...");
            try {
                System.in.read();
            } catch (Exception e) {
            }

            for (String s : InputArray) {
                tr.insert(s);
            }

            System.out.println("The Trie was successfully built!");
            System.out.println("--------------------------------");

            String ans = "";

            do {
                System.out.println("\nMain Menu:");
                System.out.println("----------");
                System.out.println("1. Search for a word in the Trie");
                System.out.println("2. Insert a word in the Trie");
                System.out.println("3. Delete a word in the Trie");
                System.out.println("4. End of program");

                Scanner sci = new Scanner(System.in);
                Scanner scs = new Scanner(System.in);
                int decision = sci.nextInt();

                switch (decision) {
                    case 1:
                        System.out.println("\nWhich word are you looking for?");
                        String words = scs.nextLine();
                        if (tr.search(words) == true) {
                            System.out.println("\nWord " + words + " was found in the Trie!");
                        } else {
                            System.out.println("\nWord " + words + " not found in the Trie!");
                        }
                        break;
                    case 2:
                        System.out.println("\nWhich word would you like to insert?");
                        String wordi = scs.nextLine();
                        tr.insert(wordi);
                        System.out.println("\nWord " + wordi + " was successfully inserted!");
                        break;
                    case 3:
                        System.out.println("\nWhich word would you like to delete?");
                        String wordr = scs.nextLine();
                        tr.remove(wordr);
                        break;
                    case 4:
                        ans = "ΝΟ";
                        System.out.println("\nEND OF PROGRAM");
                        System.out.println("--------------");
                        break;
                    default:
                        System.out.println("\nWrong choice!");
                        ans = "ΝΟ";
                        break;
                }

                if (decision != 4) {
                    System.out.println("\nWould you like to return to the Main Menu?(YES/NO)");
                    ans = scs.nextLine();
                    if (ans.equals("ΝΟ") || ans.equals("no")) {
                        System.out.println("\nEND OF PROGRAM");
                        System.out.println("--------------");
                    }
                }
            } while (ans.equals("YES") || ans.equals("yes"));

        } catch (IOException ie) {
            System.out.print("There was a problem!: " + ie);
        }
    }
}
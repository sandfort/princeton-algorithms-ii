public class Dictionary {
    private static final char OFFSET = 'A';
    private Node root = new Node();

    private static class Node {
        private boolean isLast = false;
        private Node[] next = new Node[26];
    }

    public void add(String s) {
        root = add(root, s, 0);
    }

    public boolean containsWord(String s) {
        Node x = get(root, s, 0);

        if (x == null) {
            return false;
        }

        return x.isLast;
    }

    public boolean containsPrefix(String s) {
        Node x = get(root, s, 0);

        return x != null;
    }

    private Node get(Node x, String s, int d) {
        if (x == null) {
            return null;
        }

        if (d == s.length()) {
            return x;
        }

        int c = s.charAt(d) - OFFSET;
        return get(x.next[c], s, d + 1);
    }

    private Node add(Node x, String s, int d) {
        if (x == null) {
            x = new Node();
        }

        if (d == s.length()) {
            x.isLast = true;
            return x;
        }

        int c = s.charAt(d) - OFFSET;
        x.next[c] = add(x.next[c], s, d + 1);

        return x;
    }
}

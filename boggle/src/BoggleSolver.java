public class BoggleSolver {
    // TODO replace this SET with a data structure that supports prefix search
    private SET<String> dict;

    /**
     * Initialize the data structure using the given array of strings as the
     * dictionary.
     *
     * @param dictionary An array of strings consisting only of uppercase
     * letters A through Z.
     */
    public BoggleSolver(String[] dictionary) {
        this.dict = new SET<String>();
        for (String word : dictionary) {
            this.dict.add(word);
        }
    }

    /**
     * Return the set of all valid words in the given Boggle board as an
     * <tt>Iterable</tt>.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        SET<String> words = new SET<String>();

    }

    /**
     * Return the score of the given word if it is in the dictionary, zero
     * otherwise.
     *
     * @param word A word containing only uppercase letters A through Z.
     */
    public int scoreOf(String word) {
        if (!dict.contains(word)) {
            return 0;
        }

        int len = word.length();

        if (len < 3) {
            return 0;
        } else if (len < 5) {
            return 0;
        } else if (len == 5) {
            return 3;
        } else if (len == 6) {
            return 3;
        } else if (len == 7) {
            return 5;
        }

        return 11;
    }

    public static void main(String args[]) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

    // I hate this method.
    private Iterable<Tile> adj(BoggleBoard board, int i, int j) {
        Stack<Tile> adj = new Stack<Tile>();
        if (i > 0) {
            adj.push(new Tile(i - 1, j));
            if (j > 0) adj.push(new Tile(i - 1, j - 1));
            if (j < board.cols() - 1) adj.push(new Tile(i - 1, j + 1));
        }
        if (i < board.rows() - 1) {
            adj.push(new Tile(i + 1, j));
            if (j > 0) adj.push(new Tile(i + 1, j - 1));
            if (j < board.cols() - 1) adj.push(new Tile(i + 1, j + 1));
        }
        if (j > 0) adj.push(new Tile(i, j - 1));
        if (j < board.cols() - 1) adj.push(new Tile(i, j + 1));
        return adj;
    }

    private class Tile {
        public int i, j;
        public Tile(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}


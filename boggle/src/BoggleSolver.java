public class BoggleSolver {
    private Dictionary dict;

    /**
     * Initialize the data structure using the given array of strings as the
     * dictionary.
     *
     * @param dictionary An array of strings consisting only of uppercase
     * letters A through Z.
     */
    public BoggleSolver(String[] dictionary) {
        this.dict = new Dictionary();
        for (String word : dictionary) {
            this.dict.add(word);
        }
    }

    private SET<String> dfs(BoggleBoard board, int i, int j) {
        Tile s = new Tile(i, j);
        boolean[][] marked = new boolean[board.rows()][board.cols()];
        return dfs(board, s, "" + board.getLetter(i, j), marked);
    }

    private SET<String> dfs(BoggleBoard g, Tile v, String prefix,
            boolean[][] marked) {
        marked[v.i][v.j] = true;
        SET<String> words = new SET<String>();

        if (prefix.charAt(prefix.length()-1) == 'Q') {
            prefix += 'U';
        }

        if (dict.containsWord(prefix) && prefix.length() > 2) {
            words.add(prefix);
        }

        for (Tile w : adj(g, v)) {
            if (!marked[w.i][w.j]) {
                char letter = g.getLetter(w.i, w.j);
                String word = prefix + letter;
                if (dict.containsPrefix(word)) {
                    words = words.union(dfs(g, w, word, marked));
                }
            }
        }

        marked[v.i][v.j] = false;

        return words;
    }

    /**
     * Return the set of all valid words in the given Boggle board as an
     * <tt>Iterable</tt>.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        SET<String> words = new SET<String>();

        for (int i = 0; i < board.rows(); ++i) {
            for (int j = 0; j < board.cols(); ++j) {
                words = words.union(dfs(board, i, j));
            }
        }

        return words;
    }

    /**
     * Return the score of the given word if it is in the dictionary, zero
     * otherwise.
     *
     * @param word A word containing only uppercase letters A through Z.
     */
    public int scoreOf(String word) {
        if (!dict.containsWord(word)) return 0;
        int len = word.length();
        if      (len <  3) return 0;
        else if (len <  5) return 1;
        else if (len == 5) return 2;
        else if (len == 6) return 3;
        else if (len == 7) return 5;
        return 11;
    }

    /**
     * args[0] - dictionary
     * args[1] - board
     */
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

    private Iterable<Tile> adj(BoggleBoard board, Tile tile) {
        return adj(board, tile.i, tile.j);
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


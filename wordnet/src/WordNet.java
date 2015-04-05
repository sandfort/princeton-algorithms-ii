public class WordNet {
    /**
     * Construct a WordNet given a list of synsets and their hypernym
     * relations.
     *
     * @param synsets   The name of the synset input file.
     * @param hypernyms The name of the hypernyms input file.
     */
    public WordNet(String synsets, String hypernyms) {
        // TODO implement
    }

    /**
     * Return all nouns in the WordNet.
     */
    public Iterable<String> nouns() {
        // TODO implement
        return null;
    }

    /**
     * Is the word a noun in this WordNet?
     */
    public boolean isNoun(String word) {
        // TODO implement
        return false;
    }

    /**
     * Return the distance between nouns A and B.
     */
    public int distance(String nounA, String nounB) {
        // TODO implement
        return 0;
    }

    /**
     * Return a synset that is the common ancestor of nouns A and B in a
     * shortest ancestral path.
     */
    public String sap(String nounA, String nounB) {
        // TODO implement
        return "";
    }

    /**
     * Unit tests.
     */
    public static void main(String[] args) {
        StdOut.println("No unit tests for WordNet.");
    }
}


public class WordNet {
    Synset[] sets; // array of synsets by synset id
    int v;  // number of synsets in this wordnet
    Digraph net; // hypernym directed graph

    /**
     * Construct a WordNet given a list of synsets and their hypernym
     * relations.
     *
     * @param synsets   The name of the synset input file.
     * @param hypernyms The name of the hypernyms input file.
     */
    public WordNet(String synsets, String hypernyms) {
        // TODO implement
        // - read in synsets
        // - store synsets in a resizing array by id
        // - initialize digraph with length of synset array
        // - read in hypernyms
        // - make edges from synsets to their hypernyms

        this.sets = new Synset[2];

        In in = new In(synsets);
        while (in.hasNextLine()) {
            add(new Synset(in.readLine()));
        }

        this.net = new Digraph(v);

        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] ids = in.readLine().split(",");
            int id = Integer.parseInt(ids[0]);
            for (int i = 1; i < ids.length; ++i) {
                int hypernym = Integer.parseInt(ids[i]);
                net.addEdge(id, hypernym);
            }
        }
    }

    private void resize(int capacity) {
        Synset[] temp = new Synset[capacity];
        for (int i = 0; i < v; ++i) {
            temp[i] = sets[i];
        }
        sets = temp;
    }

    private void add(Synset synset) {
        if (v == sets.length) {
            resize(2 * sets.length);
        }

        sets[synset.id] = synset;
        ++v;
    }

    /**
     * Return all nouns in the WordNet.
     */
    public Iterable<String> nouns() {
        SET<String> nouns = new SET<String>();
        for (Synset synset : sets) {
            nouns = nouns.union(synset.synset);
        }

        return nouns;
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
        WordNet wn = new WordNet(args[0], args[1]);
    }

    static class Synset {
        public int id;
        public SET<String> synset;
        public String definition;

        public Synset(String line) {
            String[] fields = line.split(",");

            this.id = Integer.parseInt(fields[0]);
            String[] nouns = fields[1].split(" ");
            this.synset = new SET<String>();
            for (String noun : nouns) {
                synset.add(noun);
            }
            this.definition = fields[2];
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("id: %d\n", id));
            sb.append("synonyms: { ");
            for (String syn : synset) {
                sb.append(String.format("%s ", syn));
            }
            sb.append("}\n");
            sb.append("def: ");
            sb.append(definition);
            sb.append("\n");

            return sb.toString();
        }
    }
}


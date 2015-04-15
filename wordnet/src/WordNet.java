public class WordNet {
    // array of synsets by synset id
    private Synset[] sets;

    // number of synsets in this wordnet
    private int v;

    // hypernym directed graph
    private Digraph net;

    // ST keyed by noun, values are a bag of synsets that the noun belongs to.
    private LinearProbingHashST<String, Bag<Integer>> nouns;

    private SAP sap;

    /**
     * Construct a WordNet given a list of synsets and their hypernym
     * relations.
     *
     * @param synsets   The name of the synset input file.
     * @param hypernyms The name of the hypernyms input file.
     */
    public WordNet(String synsets, String hypernyms) {
        checkForNull(synsets);
        checkForNull(hypernyms);

        this.sets = new Synset[2];
        this.nouns = new LinearProbingHashST<String, Bag<Integer>>();

        In in = new In(synsets);
        while (in.hasNextLine()) {
            Synset synset = new Synset(in.readLine());
            add(synset);
            for (String noun : synset.nouns.split(" ")) {
                if (nouns.contains(noun)) {
                    nouns.get(noun).add(synset.id);
                } else {
                    Bag<Integer> ids = new Bag<Integer>();
                    ids.add(synset.id);
                    nouns.put(noun, ids);
                }
            }
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

        // Check for cycles
        if ((new DirectedCycle(net).hasCycle())) {
            throw new IllegalArgumentException();
        }

        int roots = 0;
        for (int site = 0; site < v; ++site) {
            if (net.outdegree(site) == 0) {
                ++roots;
            }
        }

        if (roots != 1) {
            throw new IllegalArgumentException();
        }

        this.sap = new SAP(net);
    }

    /**
     * Return all nouns in the WordNet.
     */
    public Iterable<String> nouns() {
        return nouns.keys();
    }

    /**
     * Is the word a noun in this WordNet?
     */
    public boolean isNoun(String word) {
        checkForNull(word);
        return nouns.contains(word);
    }

    /**
     * Return the distance between nouns A and B.
     */
    public int distance(String nounA, String nounB) {
        checkForNull(nounA);
        checkForNull(nounB);

        Iterable<Integer> synsetsA = nouns.get(nounA);
        Iterable<Integer> synsetsB = nouns.get(nounB);

        if (synsetsA == null || synsetsB == null) {
            throw new IllegalArgumentException();
        }

        return sap.length(synsetsA, synsetsB);
    }

    /**
     * Return a synset that is the common ancestor of nouns A and B in a
     * shortest ancestral path.
     */
    public String sap(String nounA, String nounB) {
        checkForNull(nounA);
        checkForNull(nounB);

        Iterable<Integer> synsetsA = nouns.get(nounA);
        Iterable<Integer> synsetsB = nouns.get(nounB);

        if (synsetsA == null || synsetsB == null) {
            throw new IllegalArgumentException();
        }

        return sets[sap.ancestor(synsetsA, synsetsB)].nouns;
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

    private void checkForNull(Object argument) {
        if (argument == null) {
            throw new NullPointerException();
        }
    }

    private static class Synset {
        private int id;
        private String nouns;
        private String definition;

        public Synset(String line) {
            String[] fields = line.split(",");

            this.id = Integer.parseInt(fields[0]);
            this.nouns = fields[1];
            this.definition = fields[2];
        }

        public int id() {
            return id;
        }

        public String[] nouns() {
            return nouns.split(" ");
        }

        public String toString() {
            return new StringBuffer()
                .append(String.format("id: %d\n", id))
                .append(String.format("synonyms: { %s }\n", nouns))
                .append(String.format("def: %s\n", definition))
                .toString();
        }
    }
}


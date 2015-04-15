/**
 * Outcast.
 *
 * Given a list of wordnet nouns A_1 to A_n, which noun is least related to the
 * others? To identify an outcast, compute the sum of the distances between
 * each noun and every other one:
 *
 *      d_i = dist(A_i, A_1) + dist(A_i, A_2) + , ... + dist(A_i, A_n)
 *
 *  and return a noun A_t for which d_t is maximum.
 */
public class Outcast {
    private WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[]  nouns) {
        int greatest = 0;
        String outcast = null;

        for (String noun : nouns) {
            int dist = 0;

            for (String other : nouns) {
                if (!noun.equals(other)) {
                    dist += wordnet.distance(noun, other);
                }
            }

            if (dist > greatest) {
                greatest = dist;
                outcast = noun;
            }
        }

        return outcast;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; ++t) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}


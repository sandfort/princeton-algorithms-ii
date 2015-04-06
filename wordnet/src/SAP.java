/**
 * A class representing the shortest ancestral path of two nodes in a digraph.
 */
public class SAP {
    /**
     * @param g A digraph, not necessarily a DAG.
     */
    public SAP(Digraph g) {
        // TODO implement
    }

    /**
     * Calculate the length of the shortest ancestral path between <tt>v</tt>
     * and <tt>w</tt>.
     * @return the length, or -1 if there is no ancestral path.
     */
    public int length(int v, int w) {
        // TODO implement
        return 0;
    }

    /**
     * Find the common ancestor of <tt>v</tt> and <tt>w</tt> that participates
     * in a shortest ancestral path.
     * @return the ID of the vertex, or -1 if there is no ancestral path.
     */
    public int ancestor(int v, int w) {
        // TODO implement
        return 0;
    }

    /**
     * Calculate the length of the shortest ancestral path between any vertex
     * in <tt>v</tt> and any vertex in <tt>w</tt>.
     * @return the length, or -1 if there is no ancestral path.
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        // TODO implement
        return 0;
    }

    /**
     * Find a common ancestor that participates in the shortest ancestral path.
     * @return the vertex ID, or -1 if there is no ancestral path.
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        // TODO implement
        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph g = new Digraph(in);
        SAP sap = new SAP(g);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
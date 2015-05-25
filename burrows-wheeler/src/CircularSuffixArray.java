import java.util.Arrays;
import java.util.Comparator;

/**
 * This class describes the abstraction of a sorted array of the N circular
 * suffixes of a string of length N.
 */
public class CircularSuffixArray {
    private String s;
    private int n;
    private Integer[] index;

    /**
     * Construct a circular suffix array of the string <tt>s</tt>.
     */
    public CircularSuffixArray(String s) {
        this.s = s;
        this.n = s.length();
        this.index = new Integer[n];
        for (int i = 0; i < n; ++i) {
            index[i] = i;
        }

        Arrays.sort(index, new SuffixComparator(s));
    }

    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        StdOut.println("Original Suffixes:");
        for (int i = 0; i < csa.length(); ++i) {
            StdOut.println(csa.suffix(i));
        }
        StdOut.println();
        StdOut.println("Sorted Suffixes:");
        for (Integer i : csa.index) {
            StdOut.println(csa.suffix(i));
        }
    }

    /**
     * Return the length of the string <tt>s</tt>.
     */
    public int length() {
        return n;
    }

    /**
     * Return the index of the <tt>i</tt>th sorted suffix.
     *
     * We define <tt>index(i)</tt> to be the index of the original suffix that
     * appears <em>ith</em> in the sorted array. For example,
     * <tt>index(11) = 2</tt> means tat the <em>2nd</em> original suffix appears
     * <em>11th</em> in the sorted order.
     */
    public int index(int i) {
        return index[i];
    }

    /**
     * For testing purposes only.
     */
    private String suffix(int i) {
        return s.substring(i, n) + s.substring(0, i);
    }

    private class SuffixComparator implements Comparator<Integer> {
        private String s;
        private int n;

        public SuffixComparator(String s) {
            this.s = s;
            this.n = s.length();
        }

        public int compare(Integer a, Integer b) {
            for (int i = 0; i < n; ++i) {
                int ca = (a + i) % n;
                int cb = (b + i) % n;
                if (s.charAt(ca) != s.charAt(cb)) {
                    return s.charAt(ca) - s.charAt(cb);
                }
            }

            return 0;
        }
    }
}


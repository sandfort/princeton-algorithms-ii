import java.util.Arrays;

public class BurrowsWheeler {
    /**
     * Apply Burrows-Wheeler encoding, reading from standard input and writing
     * to standard output.
     */
    public static void encode() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int i = 0;
        while (csa.index(i) != 0) ++i;
        BinaryStdOut.write(i);
        for (i = 0; i < csa.length(); ++i) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(s.charAt(csa.length()-1));
            } else {
                BinaryStdOut.write(s.charAt(csa.index(i)-1));
            }
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    /**
     * Apply Burrows-Wheeler decoding, reading from standard input and writing
     * to standard output.
     */
    public static void decode() {
        int first = BinaryStdIn.readInt();
        char[] t = BinaryStdIn.readString().toCharArray();
        BinaryStdIn.close();
        int n = t.length;
        char[] s = Arrays.copyOf(t, n);
        Arrays.sort(s);

        int[] next = new int[n];

        int i = 0;
        int j = 0;
        while (i < n) {
            while (j < n && t[j] != s[i]) ++j;
            next[i++] = j;
            if (i >= n) break;
            if (s[i] != s[i-1]) j = 0;
            else ++j;
        }

        i = first;
        j = 0;
        while (j < n) {
            BinaryStdOut.write(s[i]);
            i = next[i];
            ++j;
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        } else if (args[0].equals("+")) {
            decode();
        }
    }
}


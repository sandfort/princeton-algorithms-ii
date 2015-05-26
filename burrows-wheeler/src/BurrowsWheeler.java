public class BurrowsWheeler {
    /**
     * Apply Burrows-Wheeler encoding, reading from standard input and writing
     * to standard output.
     */
    public static void encode() {
        StringBuilder sb = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) sb.append(BinaryStdIn.readChar());
        String s = sb.toString();
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

        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    /**
     * Apply Burrows-Wheeler decoding, reading from standard input and writing
     * to standard output.
     */
    public static void decode() {
        // TODO implement
        StdOut.println("Decode not yet implemented.");
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        } else if (args[0].equals("+")) {
            decode();
        } else {
            StdOut.println("Usage: - to encode, + to decode.");
        }
    }
}


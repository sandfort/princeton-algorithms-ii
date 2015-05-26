public class MoveToFront {
    /**
     * Apply move-to-front encoding, reading from standard input and writing to
     * standard output.
     */
    public static void encode() {
        int r = 256;
        char[] seq = new char[r];
        for (int i = 0; i < r; ++i) {
            seq[i] = (char) i;
        }

        char c;
        while (!BinaryStdIn.isEmpty()) {
            c = BinaryStdIn.readChar();
            int i = 0;
            while (seq[i] != c) ++i;
            BinaryStdOut.write((char) i);
            while (i > 0) seq[i] = seq[--i];
            seq[0] = c;
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    /**
     * Apply move-to-front decoding, reading from standard input and writing to
     * standard output.
     */
    public static void decode() {
        int r = 256;
        char[] seq = new char[r];
        for (int i = 0; i < r; ++i) {
            seq[i] = (char) i;
        }

        int i;
        while (!BinaryStdIn.isEmpty()) {
            i = (int) BinaryStdIn.readChar();
            char c = seq[i];
            BinaryStdOut.write(c);
            while (i > 0) seq[i] = seq[--i];
            seq[0] = c;
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


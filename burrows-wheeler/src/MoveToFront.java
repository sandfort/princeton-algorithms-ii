public class MoveToFront {
    /**
     * Apply move-to-front encoding, reading from standard input and writing to
     * standard output.
     */
    public static void encode() {
        // TODO implement
        StdOut.println("Encode not yet implemented.");
    }

    /**
     * Apply move-to-front decoding, reading from standard input and writing to
     * standard output.
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


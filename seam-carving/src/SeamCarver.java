public class SeamCarver {
    private Picture picture;
    /**
     * Create a seam carver object based on the given picture.
     */
    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    /**
     * The current picture.
     */
    public Picture picture() {
        return picture;
    }

    /**
     * The width of the current picture.
     */
    public int width() {
        return picture.width();
    }

    /**
     * The height of the current picture.
     */
    public int height() {
        return picture.height();
    }

    /**
     * The energy of the pixel at column <tt>x</tt> and row <tt>y</tt>.
     */
    public double energy(int x, int y) {
        // TODO implement
        return 0.0;
    }

    /**
     * The sequence of indices for the horizontal seam.
     */
    public int[] findHorizontalSeam() {
        // TODO implement
        return new int[]{};
    }

    /**
     * The sequence of indices for the vertical seam.
     */
    public int[] findVerticalSeam() {
        // TODO implement
        return new int[]{};
    }

    /**
     * Remove the given horizontal seam from the current picture.
     */
    public void removeHorizontalSeam(int[] seam) {
        // TODO implement
        return;
    }

    /**
     * Remove the given vertical seam from the current picture.
     */
    public void removeVerticalSeam(int[] seam) {
        // TODO implement
        return;
    }
}


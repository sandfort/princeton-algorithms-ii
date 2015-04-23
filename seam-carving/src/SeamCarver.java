import java.awt.Color;

import static java.lang.Math.*;

public class SeamCarver {
    private Picture picture;
    private final static int BORDER_ENERGY = 195075;

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
     *
     *  The energy of pixel (x, y) is the square of the x-gradient plus the
     *  square of the y-gradient.
     */
    public double energy(int x, int y) {
        return squareOfXGradient(x, y) + squareOfYGradient(x, y);
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

    private double squareOfXGradient(int x, int y) {
        if (x == 0 || x == picture.width() - 1) {
            return BORDER_ENERGY;
        }

        Color left  = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);

        double red   = pow(abs(left.getRed()   - right.getRed()),   2);
        double green = pow(abs(left.getGreen() - right.getGreen()), 2);
        double blue  = pow(abs(left.getBlue()  - right.getBlue()),  2);

        return red + green + blue;
    }

    private double squareOfYGradient(int x, int y) {
        if (y == 0 || y == picture.height() - 1) {
            return BORDER_ENERGY;
        }

        Color top    = picture.get(x, y - 1);
        Color bottom = picture.get(x, y + 1);

        double red   = pow(abs(top.getRed()   - bottom.getRed()),   2);
        double green = pow(abs(top.getGreen() - bottom.getGreen()), 2);
        double blue  = pow(abs(top.getBlue()  - bottom.getBlue()),  2);

        return red + green + blue;
    }

}


import java.awt.Color;

import static java.lang.Math.*;

/**
 * i = x = horizontal = width
 * j = y = vertical   = height
 */
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
        if (x == 0 || y == 0 || 
                x == picture.width()-1 ||
                y == picture.height()-1) {
            return BORDER_ENERGY;
        }
        return squareOfXGradient(x, y) + squareOfYGradient(x, y);
    }

    /**
     * The sequence of indices for the horizontal seam.
     *
     * Use findVerticalSeam() to implement this.
     */
    public int[] findHorizontalSeam() {
        // TODO implement
        return new int[]{};
    }

    /**
     * The sequence of indices for the vertical seam.
     *
     * Make sure you understand the topological sort algorithm for computing a
     * DAG. Do not create an EdgeWeightedDigraph. Instead, construct a 2D energy
     * array using the energy() method. You can traverse this matrix treating
     * some entries as reachable from (x, y) to calculate where the seam is
     * located.
     *
     * Test with the PrintSeams client.
     */
    public int[] findVerticalSeam() {
        double[][] energy = new double[picture.width()][picture.height()];
        for (int j = 0; j < picture.height(); ++j) {
            for (int i = 0; i < picture.width(); ++i) {
                energy[j][i] = energy(i, j);
            }
        }

        Iterable<Pixel> s = topological();

        // TODO actually find the shortest path!
        return new int[]{};
    }

    private Iterable<Pixel> topological() {
        boolean[][] marked = new boolean[picture.height()][picture.width()];
        Stack<Pixel> s = new Stack<Pixel>();

        for (int j = 0; j < picture.height(); ++j) {
            for (int i = 0; i < picture.width(); ++i) {
                if (!marked[j][i]) {
                    dfs(s, marked, new Pixel(i, j));
                }
            }
        }

        return s;
    }

    private void dfs(Stack<Pixel> s, boolean[][] marked, Pixel v) {
        marked[v.y()][v.x()] = true;
        for (Pixel w : adj(v)) {
            if (!marked[w.y()][w.x()]) {
                dfs(s, marked, w);
            }
        }

        s.push(v);
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
        Color left  = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);

        double red   = pow(abs(left.getRed()   - right.getRed()),   2);
        double green = pow(abs(left.getGreen() - right.getGreen()), 2);
        double blue  = pow(abs(left.getBlue()  - right.getBlue()),  2);

        return red + green + blue;
    }

    private double squareOfYGradient(int x, int y) {
        Color top    = picture.get(x, y - 1);
        Color bottom = picture.get(x, y + 1);

        double red   = pow(abs(top.getRed()   - bottom.getRed()),   2);
        double green = pow(abs(top.getGreen() - bottom.getGreen()), 2);
        double blue  = pow(abs(top.getBlue()  - bottom.getBlue()),  2);

        return red + green + blue;
    }

    private Iterable<Pixel> adj(Pixel p) {
        Stack<Pixel> adj = new Stack<Pixel>();
        int x = p.x();
        int y = p.y();
        if (p.y() < picture.height()) {
            adj.push(new Pixel(x, y + 1));

            if (p.x() > 0) {
                adj.push(new Pixel(x - 1, y + 1));
            }

            if (p.x() < picture.width()) {
                adj.push(new Pixel(x + 1, y + 1));
            }
        }

        return adj;
    }

    public static class Pixel {
        private int x;
        private int y;

        public Pixel(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }
    }
}


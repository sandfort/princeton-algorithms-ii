import java.awt.Color;

import static java.lang.Math.*;

/**
 * i = x = horizontal = width
 * j = y = vertical   = height
 */
public class SeamCarver {
    private final static int BORDER_ENERGY = 195075;

    private Picture picture;
    private double[][] energy;
    private int[][] colors;
    private boolean transposed;

    /**
     * Create a seam carver object based on the given picture.
     */
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        this.colors = getColors(picture);
        this.energy = energyMatrix();
        this.transposed = false;
    }

    /**
     * The current picture.
     */
    public Picture picture() {
        if (transposed) {
            transpose();
        }

        return picture;
    }

    /**
     * The width of the current picture.
     */
    public int width() {
        if (transposed) {
            return picture.height();
        }

        return picture.width();
    }

    /**
     * The height of the current picture.
     */
    public int height() {
        if (transposed) {
            return picture.width();
        }

        return picture.height();
    }

    /**
     * The energy of the pixel at column <tt>x</tt> and row <tt>y</tt>.
     *
     *  The energy of pixel (x, y) is the square of the x-gradient plus the
     *  square of the y-gradient.
     */
    public double energy(int x, int y) {
        if (transposed) {
            int t = x;
            x = y;
            y = t;
        }

        return energy[y][x];
    }

    /**
     * The sequence of indices for the horizontal seam.
     */
    public int[] findHorizontalSeam() {
        if (!transposed) {
            transpose();
        }

        return findSeam();
    }

    /**
     * The sequence of indices for the vertical seam.
     */
    public int[] findVerticalSeam() {
        if (transposed) {
            transpose();
        }
        
        return findSeam();
    }

    /**
     * Remove the given horizontal seam from the current picture.
     */
    public void removeHorizontalSeam(int[] seam) {
        if (!transposed) {
            transpose();
        }

        removeSeam(seam);
    }

    /**
     * Remove the given vertical seam from the current picture.
     */
    public void removeVerticalSeam(int[] seam) {
        if (transposed) {
            transpose();
        }

        removeSeam(seam);
    }

    /**
     * Assumes a vertical seam.
     */
    private void removeSeam(int[] seam) {
        int[][] colors = getColors(picture);
        for (int j = 0; j < picture.height(); ++j) {
            colors[j] = removeElement(colors[j], seam[j]);
        }
        picture = buildPicture(colors);
        energy = energyMatrix();
    }

    private int[] removeElement(int[] a, int e) {
        int[] b = new int[a.length - 1];
        System.arraycopy(a, 0, b, 0, e);
        System.arraycopy(a, e + 1, b, e, b.length - e);

        return b;
    }

    private int[][] getColors(Picture p) {
        int[][] colors = new int[p.height()][p.width()];
        for (int j = 0; j < p.height(); ++j) {
            for (int i = 0; i < p.width(); ++i) {
                colors[j][i] = p.get(i, j).getRGB();
            }
        }

        return colors;
    }

    /**
     * Assumes rows are all the same length.
     */
    private Picture buildPicture(int[][] colors) {
        int height = colors.length;
        int width = colors[0].length;
        Picture p = new Picture(width, height);

        for (int j = 0; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                p.set(i, j, new Color(colors[j][i]));
            }
        }

        return p;
    }

    private void relax(Pixel[][] edgeTo, double[][] distTo, Pixel v) {
        for (Pixel w : adj(v)) {
            if (distTo[w.y()][w.x()] > distTo[v.y()][v.x()] + v.energy()) {
                distTo[w.y()][w.x()] = distTo[v.y()][v.x()] + v.energy();
                edgeTo[w.y()][w.x()] = v;
            }
        }
    }

    private double squareOfXGradient(int x, int y) {
        Color left = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);

        double red = pow(abs(left.getRed() - right.getRed()), 2);
        double green = pow(abs(left.getGreen() - right.getGreen()), 2);
        double blue = pow(abs(left.getBlue() - right.getBlue()), 2);

        return red + green + blue;
    }

    private double squareOfYGradient(int x, int y) {
        Color top = picture.get(x, y - 1);
        Color bottom = picture.get(x, y + 1);

        double red = pow(abs(top.getRed() - bottom.getRed()), 2);
        double green = pow(abs(top.getGreen() - bottom.getGreen()), 2);
        double blue = pow(abs(top.getBlue() - bottom.getBlue()), 2);

        return red + green + blue;
    }

    private Iterable<Pixel> adj(Pixel p) {
        Stack<Pixel> adj = new Stack<Pixel>();
        int x = p.x();
        int y = p.y();
        if (p.y() < picture.height() - 1) {
            adj.push(new Pixel(x, y + 1, energy[y+1][x]));

            if (p.x() > 0) {
                adj.push(new Pixel(x - 1, y + 1, energy[y+1][x-1]));
            }

            if (p.x() < picture.width() - 1) {
                adj.push(new Pixel(x + 1, y + 1, energy[y+1][x+1]));
            }
        }

        return adj;
    }

    private Iterable<Pixel> topological() {
        boolean[][] marked = new boolean[picture.height()][picture.width()];
        Stack<Pixel> s = new Stack<Pixel>();

        for (int i = 0; i < picture.width(); ++i) {
            if (!marked[0][i]) {
                dfs(s, marked, new Pixel(i, 0, energy[0][i]));
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

    private void transpose() {
        Picture t = new Picture(picture.height(), picture.width());
        for (int j = 0; j < picture.width(); ++j) {
            for (int i = 0; i < picture.height(); ++i) {
                t.set(i, j, picture.get(j, i));
            }
        }

        int[][] tc = new int[colors[0].length][colors.length];
        for (int j = 0; j < colors.length; ++j) {
            for (int i = 0; i < colors[0].length; ++i) {
                tc[i][j] = colors[j][i];
            }
        }

        picture = t;
        colors = tc;
        energy = energyMatrix();
        transposed = !transposed;
        
    }

    private double calculateEnergy(int x, int y) {
        if (x == 0 || y == 0 || x == picture.width()-1 ||
                y == picture.height()-1) {
            return BORDER_ENERGY;
        }
        return squareOfXGradient(x, y) + squareOfYGradient(x, y);
    }

    private double[][] energyMatrix() {
        double[][] energy = new double[picture.height()][picture.width()];

        for (int j = 0; j < picture.height(); ++j) {
            for (int i = 0; i < picture.width(); ++i) {
                energy[j][i] = calculateEnergy(i, j);
            }
        }

        return energy;
    }

    private int[] findSeam() {
        Iterable<Pixel> s = topological();

        Pixel[][] edgeTo = new Pixel[picture.height()][picture.width()];
        double[][] distTo = new double[picture.height()][picture.width()];
        for (int i = 0; i < picture.width(); ++i) {
            distTo[0][i] = 0.0;
        }
        for (int j = 1; j < picture.height(); ++j) {
            for (int i = 0; i < picture.width(); ++i) {
                distTo[j][i] = Double.POSITIVE_INFINITY;
            }
        }

        // relax vertices in topological order
        for (Pixel v : s) {
            relax(edgeTo, distTo, v);
        }

        double shortest = Double.POSITIVE_INFINITY;
        Pixel seamEnd = null;
        int j = picture.height() - 1;
        for (int i = 0; i < picture.width(); ++i) {
            if (distTo[j][i] < shortest) {
                seamEnd = new Pixel(i, j, energy[j][i]);
                shortest = distTo[j][i];
            }
        }

        int[] seam = new int[picture.height()];
        int cut = seamEnd.x();
        while (j > 0) {
            seam[j] = cut;
            cut = edgeTo[j--][cut].x();
        }
        seam[0] = cut;

        return seam;
    }

    private class Pixel {
        private int x;
        private int y;
        private double energy;

        public Pixel(int x, int y, double energy) {
            this.x = x;
            this.y = y;
            this.energy = energy;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        public double energy() {
            return energy;
        }

        public String toString() {
            return String.format("(%d, %d)", x, y);
        }
    }
}


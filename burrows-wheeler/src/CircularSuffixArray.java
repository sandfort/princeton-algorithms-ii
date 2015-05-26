/**
 * This class describes the abstraction of a sorted array of the N circular
 * suffixes of a string of length N.
 */
public class CircularSuffixArray {
    private int n;
    private int[] index;
    private char[] s;
    private static final int CUTOFF = 8;

    /**
     * Construct a circular suffix array of the string <tt>s</tt>.
     */
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new NullPointerException();
        }

        this.n = s.length();
        this.s = s.toCharArray();
        this.index = new int[n];
        for (int i = 0; i < n; ++i) {
            index[i] = i;
        }

        sort(index);
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
        if (i >= n) {
            throw new IndexOutOfBoundsException();
        }

        return index[i];
    }

    private void sort(int[] a) {
        sort(a, 0, n-1);
    }

    private void sort(int[] a, int lo, int hi) {
        int m = hi - lo + 1;

        if (m <= CUTOFF) {
            insertionSort(a, lo, hi);
            return;
        } else if (m <= 40) {
            int median = median3(a, lo, lo + m/2, hi);
            exch(a, median, lo);
        } else {
            int eps = m/8;
            int mid = lo + m/2;
            int m1 = median3(a, lo, lo + eps, lo + eps + eps);
            int m2 = median3(a, mid - eps, mid, mid + eps);
            int m3 = median3(a, hi - eps - eps, hi - eps, hi);
            int ninther = median3(a, m1, m2, m3);
            exch(a, ninther, lo);
        }

        int i = lo, j = hi+1;
        int p = lo, q = hi+1;
        int v = a[lo];
        while (true) {
            while (less(a[++i], v))
                if (i == hi) break;
            while (less(v, a[--j]))
                if (j == lo) break;

            if (i == j && eq(a[i], v))
                exch(a, ++p, i);
            if (i >= j) break;

            exch(a, i, j);
            if (eq(a[i], v)) exch(a, ++p, i);
            if (eq(a[j], v)) exch(a, --q, j);
        }

        i = j + 1;
        for (int k = lo; k <= p; ++k) exch(a, k, j--);
        for (int k = hi; k >= q; --k) exch(a, k, i++);

        sort(a, lo, j);
        sort(a, i, hi);
    }

    private void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo; i <= hi; ++i)
            for (int j = i; j > lo && less(a[j], a[j-1]); --j)
                exch(a, j, j-1);
    }

    private int median3(int[] a, int i, int j, int k) {
        return (less(a[i], a[j]) ?
               (less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
               (less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i));
    }

    private int mod(int i) {
        if (i < n) return i;
        return i - n;
    }

    private boolean less(int a, int b) {
        for (int i = 0; i < n; ++i) {
            if (s[mod(a+i)] < s[mod(b+i)]) return true;
            if (s[mod(a+i)] > s[mod(b+i)]) return false;
        }

        return false;
    }

    private boolean eq(int a, int b) {
        for (int i = 0; i < n; ++i)
            if (s[mod(a+i)] != s[mod(b+i)])
                return false;

        return true;
    }

    private void exch(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}


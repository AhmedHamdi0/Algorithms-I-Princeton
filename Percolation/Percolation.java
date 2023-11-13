import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] statusGrid;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF full;
    private int gridSize;
    private int virtualTop;
    private int virtualBottom;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("N must be > 0");
        gridSize = n;
        int gridSquared = n * n;
        statusGrid = new boolean[gridSize][gridSize];
        grid = new WeightedQuickUnionUF(gridSquared + 2); // includes virtual top and bottom
        full = new WeightedQuickUnionUF(gridSquared + 1); // includes virtual top
        virtualTop = gridSquared;             // index n^2 if n is the grid size
        virtualBottom = gridSquared + 1;      // index n^2 + 1
        openSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateSite(row, col);
        int shiftRow = row - 1;
        int shiftCol = col - 1;

        // If already open, return
        if (isOpen(row, col)) {
            return;
        }

        // Open site
        statusGrid[shiftRow][shiftCol] = true;
        openSites++;

        int flatIndex = flattenGrid(row, col);

        // Connect to virtual top if in the first row
        if (row == 1) {
            grid.union(flatIndex, virtualTop);
            full.union(flatIndex, virtualTop);
        }

        // Connect to virtual bottom if in the last row
        if (row == gridSize) {
            grid.union(flatIndex, virtualBottom);
        }

        // Connect to adjacent open sites
        connectAdjacentSites(row, col, flatIndex);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        return statusGrid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        int flatIndex = flattenGrid(row, col);
        return full.connected(virtualTop, flatIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.connected(virtualTop, virtualBottom);
    }

    // Private methods

    private int flattenGrid(int row, int col) {
        return gridSize * (row - 1) + col - 1;
    }

    private void validateSite(int row, int col) {
        if (!isOnGrid(row, col)) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private boolean isOnGrid(int row, int col) {
        int shiftRow = row - 1;
        int shiftCol = col - 1;
        return shiftRow >= 0 && shiftCol >= 0 && shiftRow < gridSize && shiftCol < gridSize;
    }

    private void connectAdjacentSites(int row, int col, int flatIndex) {
        // Check and connect to the left site
        if (isOnGrid(row, col - 1) && isOpen(row, col - 1)) {
            int leftFlatIndex = flattenGrid(row, col - 1);
            grid.union(flatIndex, leftFlatIndex);
            full.union(flatIndex, leftFlatIndex);
        }

        // Check and connect to the right site
        if (isOnGrid(row, col + 1) && isOpen(row, col + 1)) {
            int rightFlatIndex = flattenGrid(row, col + 1);
            grid.union(flatIndex, rightFlatIndex);
            full.union(flatIndex, rightFlatIndex);
        }

        // Check and connect to the upper site
        if (isOnGrid(row - 1, col) && isOpen(row - 1, col)) {
            int upperFlatIndex = flattenGrid(row - 1, col);
            grid.union(flatIndex, upperFlatIndex);
            full.union(flatIndex, upperFlatIndex);
        }

        // Check and connect to the lower site
        if (isOnGrid(row + 1, col) && isOpen(row + 1, col)) {
            int lowerFlatIndex = flattenGrid(row + 1, col);
            grid.union(flatIndex, lowerFlatIndex);
            full.union(flatIndex, lowerFlatIndex);
        }
    }

    public static void main(String[] args) {
        int n = 5; // Grid size

        Percolation percolation = new Percolation(n);

        // Open specific sites
        percolation.open(1, 1);
        percolation.open(2, 2);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(4, 3);
        percolation.open(4, 4);
        percolation.open(5, 5);

        System.out.println(
                "Is site (1, 1) open? " + percolation.isOpen(1, 1)); // Expected output: true
        System.out.println(
                "Is site (3, 2) open? " + percolation.isOpen(3, 2)); // Expected output: false

        System.out.println(
                "Is site (4, 3) full? " + percolation.isFull(4, 3)); // Expected output: true
        System.out.println(
                "Is site (5, 5) full? " + percolation.isFull(5, 5)); // Expected output: false

        System.out.println(
                "Number of open sites: " + percolation.numberOfOpenSites()); // Expected output: 6

        System.out.println(
                "Does the system percolate? " + percolation.percolates()); // Expected output: false
    }
}

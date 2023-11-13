/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;
import java.util.LinkedList;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private final int[][] tiless;
    private final int size;

    public Board(int[][] tiles) {
        this.tiless = copyBoard(tiles);
        this.size = tiles.length;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(String.format("%2d ", tiless[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        LinkedList<Integer> list = convertBoardToList();
        int num = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != i + 1 && list.get(i) != 0) {
                num++;
            }
        }
        return num;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int totalDistance = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiless[i][j] == 0) {
                    continue;
                }
                totalDistance += Math.abs(((tiless[i][j] - 1) / size) - i) + Math.abs(
                        ((tiless[i][j] - 1) % size) - (j % size));
            }
        }
        return totalDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        LinkedList<Integer> convertedList = this.convertBoardToList();
        for (int i = 1; i < convertedList.size(); i++) {
            if (convertedList.get(i - 1) != i) {
                return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        boolean flag = true;
        if (y == null)
            return false;
        if (y.getClass().equals(this.getClass())) {
            Board that = (Board) y;
            if (this.dimension() == that.dimension()) {
                for (int i = 0; i < dimension(); i++) {
                    for (int j = 0; j < dimension(); j++) {
                        if (this.tiless[i][j] != that.tiless[i][j]) {
                            flag = false;
                            break;
                        }
                    }
                    if (!flag)
                        break;
                }
            }
        }
        return flag;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<Board>();

        int[] space = findBlankSquare();
        int i = space[0];
        int j = space[1];

        if (i + 1 < size) {
            neighbors.add(new Board(swap(i, j, i + 1, j)));
        }
        if (i - 1 >= 0) {
            neighbors.add(new Board(swap(i, j, i - 1, j)));
        }
        if (j + 1 < size) {
            neighbors.add(new Board(swap(i, j, i, j + 1)));
        }
        if (j - 1 >= 0) {
            neighbors.add(new Board(swap(i, j, i, j - 1)));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {                           // here
        int[] space = findBlankSquare();
        int[] twin = new int[4];

        for (int i = 0; i < size; i++) {
            int[] temp = new int[4];
            temp[0] = i;
            temp[2] = i;
            for (int j = 0; j < size; j++) {
                if (space[0] == i && space[1] == j) {
                    break;
                }
                if (temp[1] != 0) {
                    temp[1] = j;
                }
                else {
                    temp[3] = j;
                }
            }
            if (temp[3] != 0) {
                twin = temp;
                break;
            }
        }
        return new Board(swap(twin[0], twin[1], twin[2], twin[3]));
    }


    /* private methods */
    // Swap entries in tiles board and return a swapped clone of tiles
    private int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] tilesCopy = copyBoard(tiless);
        int temp = tilesCopy[row2][col2];
        tilesCopy[row2][col2] = tilesCopy[row1][col1];
        tilesCopy[row1][col1] = temp;
        return tilesCopy;
    }

    // Copy Board
    private int[][] copyBoard(int[][] tiles) {
        return Arrays.stream(tiles).map(r -> r.clone()).toArray(int[][]::new);    // here
    }

    // Find index of Zero
    private int[] findBlankSquare() {
        int[] temp = new int[2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiless[i][j] == 0) {
                    temp[0] = i;
                    temp[1] = j;
                    break;
                }
            }
        }
        return temp;
    }

    // Convert Board to LinkedList
    private LinkedList<Integer> convertBoardToList() {
        LinkedList<Integer> temp = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                temp.addLast(tiless[i][j]);
            }
        }
        return temp;
    }
}

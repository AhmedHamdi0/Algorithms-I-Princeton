/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode prev;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        @Override
        public int compareTo(SearchNode that) {
            return Integer.compare(this.board.manhattan() + this.moves,
                                   that.board.manhattan() + that.moves);
        }
    }

    private SearchNode goalNode = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<SearchNode> pq = new MinPQ<>();
        SearchNode dequeuedNode = new SearchNode(initial, 0, null);
        pq.insert(dequeuedNode);

        while (!pq.isEmpty()) {
            Board dequeuedBoard = dequeuedNode.board;
            
            if (dequeuedBoard.isGoal()) {     // checks if dequeued board is the goal board
                goalNode = dequeuedNode;
                return;
            }
            for (Board board : dequeuedBoard.neighbors()) {
                if (dequeuedNode.prev == null || !board.equals(dequeuedNode.prev.board)) {
                    pq.insert(new SearchNode(board, dequeuedNode.moves + 1, dequeuedNode));
                }
            }
            dequeuedNode = pq.delMin();
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return goalNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return goalNode.moves;
        }
        else {
            return -1;
        }
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            SearchNode goalNodeCopy = goalNode;
            LinkedList<Board> boards = new LinkedList<>();
            boards.addLast(goalNodeCopy.board);
            while (goalNodeCopy.prev != null) {
                boards.addFirst(goalNodeCopy.prev.board);
                goalNodeCopy = goalNodeCopy.prev;
            }
            return boards;
        }
        return null;
    }

}

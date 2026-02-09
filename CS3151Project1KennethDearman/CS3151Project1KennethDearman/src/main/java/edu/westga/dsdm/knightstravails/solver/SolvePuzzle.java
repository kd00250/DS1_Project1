package edu.westga.dsdm.knightstravails.solver;

import edu.westga.dsdm.knightstravails.model.Position;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The Solve Puzzle Class
 *
 * @author Kenneth Dearman
 */
public class SolvePuzzle {
    private LinkedList<Position> solutionPath;
    private static final int[][] POSIBLE_MOVES = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};

    /**
     * instatiates a new instance of SolvePuzzle.
     * @pre none
     * @post none
     */
    public SolvePuzzle() {
        this.solutionPath = new LinkedList<>();
    }

    public LinkedList<Position> getSolution(Position currentPos, Position endPosition) {
        if (currentPos == null || endPosition == null) {
            throw new IllegalArgumentException("Either start or end position cannot be null.");
        }
        Queue<Position> nextNodes = new LinkedList<>();
        HashMap<Position, Position> predecesor = new HashMap<>();
        HashSet<Position> selected = new HashSet<>();

        nextNodes.offer(currentPos);
        selected.add(currentPos);
        predecesor.put(currentPos, null);

        while (!nextNodes.isEmpty()) {
            Position spaceOnBoard = nextNodes.poll();

            if (spaceOnBoard.equals(endPosition)) {
                this.solutionPath = reconstructPath(predecesor, endPosition);
                return this.solutionPath;
            }

            for (int[] move : KNIGHT_MOVES) {
                int newRow = spaceOnBoard.row() + move[0];
                int newCol = spaceOnBoard.col() + move[1];

                if (this.isValid(newRow, newCol) {

                }
            }
        }
        return null;
    }

    /**
     * Checks if the neighbor move is valid or not
     * @param row the row of the neighbor
     * @param col the col of the neighbor
     * @return true or false if the move is valid
     */
    private boolean isValid(int row, int col) {
        return row >= 0 && row < Position.MAX_ROWS && col >= 0 && col < Position.MAX_COLS;
    }
}

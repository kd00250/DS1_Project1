package edu.westga.dsdm.knightstravails.solver;

import edu.westga.dsdm.knightstravails.model.Position;

import java.util.LinkedList;

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
        
        return null;
    }
}

package edu.westga.dsdm.knightstravails.solver;

import edu.westga.dsdm.knightstravails.model.Position;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The Solve Puzzle Class.
 *
 * @author Kenneth Dearman
 */
public class SolvePuzzle {
    private LinkedList<Position> solutionPath;
    private static final int[][] POSSIBLE_MOVES = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};

    /**
     * instantiates a new instance of SolvePuzzle.
     * @pre none
     * @post none
     */
    public SolvePuzzle() {
        this.solutionPath = new LinkedList<>();
    }

    /**
     * Solves the Knight transversal puzzle.
     * @param startPos the current/starting position
     * @param endPos the end/target position
     * @pre start != null && end != null
     * @post none
     * @return the linked list of the solution path from the start to the target position
     */
    public LinkedList<Position> getSolution(Position startPos, Position endPos) {
        if (startPos == null || endPos == null) {
            throw new IllegalArgumentException("Either start or end position cannot be null.");
        }
        Queue<Position> nextNodes = new LinkedList<>();
        HashMap<Position, Position> previous = new HashMap<>();
        HashSet<Position> selected = new HashSet<>();

        nextNodes.add(startPos);
        selected.add(startPos);
        previous.put(startPos, null);

        while (!nextNodes.isEmpty()) {
            Position original = nextNodes.poll();

            if (original.equals(endPos)) {
                this.solutionPath = this.getPath(previous, endPos);
                return this.solutionPath;
            }

            for (int[] move : POSSIBLE_MOVES) {
                int newRow = original.row() + move[0];
                int newCol = original.col() + move[1];

                if (this.isValidMove(newRow, newCol)) {
                    Position neighbor = new Position(newRow, newCol);

                    if (!selected.contains(neighbor)) {
                        nextNodes.add(neighbor);
                        previous.put(neighbor, original);
                        selected.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets the solution path from the passed in predecessor hashmap.
     * @param predecessor the hashmap of previous nodes
     * @param endPosition the target node to reach
     * @return a linked list that is the solution path from start to finish
     */
    private LinkedList<Position> getPath(HashMap<Position, Position> predecessor, Position endPosition) {
        LinkedList<Position> path = new LinkedList<>();
        Position current = endPosition;

        while (current != null) {
            path.addFirst(current);
            current = predecessor.get(current);
        }
        return path;
    }

    /**
     * Checks if the neighbor move is valid or not.
     * @param row the row of the neighbor
     * @param col the col of the neighbor
     * @pre none
     * @post none
     * @return true or false if the move is valid
     */
    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < Position.MAX_ROWS && col >= 0 && col < Position.MAX_COLS;
    }
}

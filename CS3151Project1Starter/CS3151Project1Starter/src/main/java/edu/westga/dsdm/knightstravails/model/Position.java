package edu.westga.dsdm.knightstravails.model;

/**
 * Positions stores a position on a chess board and can provide the neighboring
 * positions a knight can move to from that position.
 *
 * @param row the row index
 * @param col the column index
 * @author DSDM
 */
public record Position(int row, int col) {
    /**
     * The number rows of the chess board.
     */
    public static final int MAX_ROWS = 8;
    /**
     * The number columns of the chess board.
     */
    public static final int MAX_COLS = 8;

    /**
     * Instantiates a new position on a board with MAX_ROWS x MAX_COLS cells.
     *
     * @param row the row index
     * @param col the column index
     * @pre row >= 0 and row < MAX_ROWS and col >= 0 and col < MAX_COLS
     * @post getRow() == row and getCol() == col
     */
    public Position {
        if (row < 0 || row >= MAX_ROWS || col < 0 || col >= MAX_COLS) {
            throw new IllegalArgumentException("invalid chess board position");
        }
    }

    /**
     * Gets the row index.
     *
     * @return the row
     * @pre none
     * @post none
     */
    @Override
    public int row() {
        return this.row;
    }

    /**
     * Gets the column index.
     *
     * @return the column
     * @pre none
     * @post none
     */
    @Override
    public int col() {
        return this.col;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        return this.row() == ((Position) obj).row() && this.col() == ((Position) obj).col();
    }

    @Override
    public int hashCode() {
        return 8 * this.row() + this.col();
    }
}

package edu.westga.dsdm.knightstravails.viewmodel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import edu.westga.dsdm.knightstravails.model.Position;
import edu.westga.dsdm.knightstravails.solver.SolvePuzzle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

/**
 * The KnightsTravailsViewModel implements the logic for the
 * knight's travails puzzle GUI.
 *
 * @author DSDM
 */
public class KnightsTravailsViewModel {
    private static final double MOVE_DELAY = 0.7;

    private final SimpleObjectProperty<Position> knightPositionProperty;
    private final SimpleObjectProperty<Position> targetPositionProperty;
    private Position startPosition;
    private final BooleanProperty wonProperty;
    private final BooleanProperty lostProperty;
    private final SimpleIntegerProperty numberMovesProperty;
    private Stack<Position> priorMoves;
    private SolvePuzzle solver;

    /**
     * Instantiates a new puzzle viewmodel for the logic of the knight's travails
     * puzzle GUI.
     *
     * @pre none
     * @post a new view model representing a knight's travails puzzle instance
     */
    public KnightsTravailsViewModel() {
        this.knightPositionProperty = new SimpleObjectProperty<Position>();
        this.targetPositionProperty = new SimpleObjectProperty<Position>();
        this.wonProperty = new SimpleBooleanProperty(false);
        this.lostProperty = new SimpleBooleanProperty(false);
        this.startPosition = null;
        this.numberMovesProperty = new SimpleIntegerProperty();
        this.priorMoves = new Stack<Position>();
        this.solver = new SolvePuzzle();
    }

    /**
     * Gets the knight position property.
     *
     * @return the knight position property
     * @pre none
     * @post none
     */
    public SimpleObjectProperty<Position> getKnightPositionProperty() {
        return this.knightPositionProperty;
    }

    /**
     * Gets the target position property.
     *
     * @return the knight position property
     * @pre none
     * @post none
     */
    public SimpleObjectProperty<Position> getTargetPositionProperty() {
        return this.targetPositionProperty;
    }

    /**
     * Gets the won property.
     *
     * @return the solved property
     * @pre none
     * @post none
     */
    public BooleanProperty wonProperty() {
        return this.wonProperty;
    }

    /**
     * Gets the lost property.
     *
     * @return the solved property
     * @pre none
     * @post none
     */
    public BooleanProperty lostProperty() {
        return this.lostProperty;
    }

    /**
     * Gets the number moves property.
     *
     * @return the solved property
     * @pre none
     * @post none
     */
    public SimpleIntegerProperty numberMovesProperty() {
        return this.numberMovesProperty;
    }

    /**
     * Moves knight to the position selected by the user if the move is valid.
     *
     * @param position the new position of the knight
     * @pre position != null
     * @post the knight is moved to the specified position if valid AND
     * (knight has reached the goal in the minimum number moves ->
     * this.wonProperty.getValue() == true) AND
     * (knight has reached the goal in more than the minimum number of
     * moves -> this.lostProperty.getValue() == true)
     */
    public void moveKnight(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Move cannot be null.");
        }
        Position currentPosition = this.knightPositionProperty.getValue();
        if (this.isValidMove(currentPosition, position)) {
            this.priorMoves.push(currentPosition);
            this.knightPositionProperty.setValue(position);
            this.numberMovesProperty.setValue(this.numberMovesProperty.getValue() + 1);
        }
        if (this.knightPositionProperty.getValue().equals(this.targetPositionProperty.getValue())) {
            if (this.didUserWin()) {
                this.wonProperty.setValue(true);
            } else {
                this.lostProperty.setValue(true);
            }
        }
    }

    /**
     * Checks if the user has completed the puzzle in the correct amount of moves.
     * @pre none
     * @post return true or false
     * @return true or false if the user has won or not
     */
    private boolean didUserWin() {
        int winningMoves = this.solver.getSolution(this.startPosition, this.targetPositionProperty.getValue()).size();
        return winningMoves > this.numberMovesProperty.getValue();
    }

    /**
     * Checks if a move is valid.
     *
     * @param currentPosition the position that the knight is currently in
     * @param newPosition the position that the user wants to move the knight
     * @pre none
     * @post returns true or false
     * @return true or false based on if the move is valid
     */
    private boolean isValidMove(Position currentPosition, Position newPosition) {
        int rowDifference = Math.abs(newPosition.row() - currentPosition.row());
        int colDifference = Math.abs(newPosition.col() - currentPosition.col());
        return ((rowDifference == 2 && colDifference == 1) || (rowDifference == 1 && colDifference == 2));
    }

    /**
     * Undoes the most recent move.
     *
     * @pre none
     * @post the most recent move is undone
     */
    public void undo() {
        if (this.numberMovesProperty().getValue() != 0) {
            this.knightPositionProperty.setValue(this.priorMoves.pop());
            this.numberMovesProperty.setValue(this.numberMovesProperty.getValue() - 1);
        }
    }

    /**
     * Shows the shortest path from the start to the goal position.
     *
     * @pre none
     * @post wonProperty.getValue == true && lostProperty.getValue() == true
     */
    public void showSolution() {
        this.wonProperty.setValue(true);
        this.lostProperty.setValue(true);
        LinkedList<Position> solutionPath = this.solver.getSolution(this.startPosition, this.targetPositionProperty.getValue());
        this.tracePath(solutionPath.iterator(), solutionPath.size() - 1);
    }

    /**
     * Instantiates a new knight's travails puzzle instance.
     *
     * @pre none
     * @post wonProperty.getValue == false && lostProperty.getValue() == false
     */
    public void initializeNewPuzzle() {
        Random rand = new Random();
        this.startPosition = new Position(rand.nextInt(Position.MAX_ROWS), rand.nextInt(Position.MAX_COLS));
        this.knightPositionProperty.setValue(this.startPosition);
        Position targetPosition = this.startPosition;
        while (targetPosition.equals(this.startPosition)) {
            targetPosition = new Position(rand.nextInt(Position.MAX_ROWS), rand.nextInt(Position.MAX_COLS));
        }
        this.targetPositionProperty.setValue(targetPosition);
        this.wonProperty.setValue(false);
        this.lostProperty.setValue(false);
        this.numberMovesProperty.setValue(0);
    }

    /**
     * Traces the specified path by setting the knight position property one-by-one
     * to the positions on the path.
     *
     * @param path       the path to be traced
     * @param pathLength the number moves on the path
     * @pre the number positions the iterator path returns is equal to pathLength+1
     * @post none
     */
    private void tracePath(Iterator<Position> path, int pathLength) {
        this.numberMovesProperty.setValue(0);
        if (path.hasNext()) {
            this.knightPositionProperty.setValue(path.next());
        }
        if (path.hasNext()) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(MOVE_DELAY), _ -> {
                Position nextPosition = path.next();
                this.knightPositionProperty.setValue(nextPosition);
                this.numberMovesProperty.setValue(this.numberMovesProperty.getValue() + 1);
            }));
            timeline.setCycleCount(pathLength);
            timeline.play();
        }
    }
}

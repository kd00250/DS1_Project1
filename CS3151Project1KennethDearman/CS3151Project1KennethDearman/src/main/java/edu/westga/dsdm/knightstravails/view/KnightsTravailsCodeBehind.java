package edu.westga.dsdm.knightstravails.view;

import edu.westga.dsdm.knightstravails.model.Position;
import edu.westga.dsdm.knightstravails.viewmodel.KnightsTravailsViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;

import java.io.InputStream;

/**
 * Class KnightsTravailsCodeBehind handles the GUI-specific behavior for the
 * knight's travails puzzle application.
 *
 * @author DSDM
 */
public class KnightsTravailsCodeBehind {
    /**
     * The height of a cell on the chess board in the GUI.
     */
    private static final int CELL_HEIGHT = 60;

    /**
     * The height of an image place on a cell on the chess board in the GUI.
     */
    private static final int CELL_IMAGE_HEIGHT = 52;

    @FXML
    private AnchorPane chessBoardPane;

    @FXML
    private Label headerLabel;

    @FXML
    private Label numberMovesLabel;

    @FXML
    private Label youWonLabel;

    @FXML
    private Label youLostLabel;

    private Button[][] squareButtons;

    @FXML
    private Button undoButton;

    @FXML
    private Button showSolutionButton;

    @FXML
    private Pane numberMovesPane;

    private KnightsTravailsViewModel viewModel;
    private final SimpleObjectProperty<Position> knightPositionProperty;
    private final SimpleObjectProperty<Position> targetPositionProperty;
    private ImageView knightIcon;
    private ImageView targetIcon;
    private ImageView knightOnTargetIcon;

    /**
     * Instantiates a new student info code behind.
     *
     * @pre none
     * @post none
     */
    public KnightsTravailsCodeBehind() {
        this.viewModel = new KnightsTravailsViewModel();
        this.knightPositionProperty = new SimpleObjectProperty<Position>();
        this.targetPositionProperty = new SimpleObjectProperty<Position>();
        this.knightIcon = this.createChessSquareIcon("images/Knight.png");
        this.knightOnTargetIcon = this.createChessSquareIcon("images/KnightOnTarget.png");
        this.targetIcon = this.createChessSquareIcon("images/Target.png");
    }

    private ImageView createChessSquareIcon(String imageName) {
        InputStream imageStream = this.getClass().getResourceAsStream(imageName);
        if (imageStream == null) {
            System.err.println("Unable to load image: " + imageName);
            Platform.exit();
            System.exit(1);
        }
        ImageView icon = new ImageView(new Image(imageStream));
        icon.setFitHeight(CELL_IMAGE_HEIGHT);
        icon.setPreserveRatio(true);
        return icon;
    }

    @FXML
    private void initialize() {
        this.setupChessBoard();
        this.setupBindings();
        this.setupListeners();
        this.viewModel.initializeNewPuzzle();
        this.numberMovesPane.setBorder(new Border(
                new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    private void setupBindings() {
        this.knightPositionProperty.bind(this.viewModel.getKnightPositionProperty());
        this.targetPositionProperty.bind(this.viewModel.getTargetPositionProperty());
        this.numberMovesLabel.textProperty().bindBidirectional(this.viewModel.numberMovesProperty(),
                new NumberStringConverter());
    }

    private void setupChessBoard() {
        this.squareButtons = new Button[8][8];
        for (int row = 0; row < Position.MAX_ROWS; row++) {
            for (int col = 0; col < Position.MAX_COLS; col++) {
                this.squareButtons[row][col] = new Button("");
                this.squareButtons[row][col].setLayoutX(col * CELL_HEIGHT + 2);
                this.squareButtons[row][col].setLayoutY(row * CELL_HEIGHT + 2);
                this.squareButtons[row][col].setPrefSize(CELL_HEIGHT, CELL_HEIGHT);
                this.squareButtons[row][col].setPadding(new Insets(2));
                if ((row + col) % 2 == 1) {
                    this.squareButtons[row][col].setStyle("-fx-background-color: DarkSlateGray");
                }
                this.chessBoardPane.getChildren().add(this.squareButtons[row][col]);
                this.addBoardSquareListener(row, col);
            }
        }
    }

    private void addBoardSquareListener(int row, int col) {
        this.squareButtons[row][col].setOnAction(_ -> this.viewModel.moveKnight(new Position(row, col)));
    }

    private void setupListeners() {
        this.knightPositionProperty.addListener((_, oldValue, newValue) -> {
            if (oldValue != null) {
                if (oldValue.equals(this.targetPositionProperty.getValue())) {
                    this.squareButtons[oldValue.row()][oldValue.col()].setGraphic(this.targetIcon);
                } else {
                    this.squareButtons[oldValue.row()][oldValue.col()].setGraphic(null);
                }
            }
            if (newValue != null) {
                if (newValue.equals(this.targetPositionProperty.getValue())) {
                    this.squareButtons[newValue.row()][newValue.col()].setGraphic(this.knightOnTargetIcon);
                } else {
                    this.squareButtons[newValue.row()][newValue.col()].setGraphic(this.knightIcon);
                }
            }
        });
        this.targetPositionProperty.addListener((_, oldValue, newValue) -> {
            if (oldValue != null) {
                this.squareButtons[oldValue.row()][oldValue.col()].setGraphic(null);
            }
            if (newValue != null) {
                this.squareButtons[newValue.row()][newValue.col()].setGraphic(this.targetIcon);
            }
        });
    }

    @FXML
    void handleUndo() {
        this.viewModel.undo();
    }

    @FXML
    void handleNewPuzzle() {
        this.viewModel.initializeNewPuzzle();
    }

    @FXML
    void handleShowSolution() {
        this.viewModel.showSolution();
    }

    @FXML
    void handleExit() {
        Platform.exit();
        System.exit(0);
    }
}

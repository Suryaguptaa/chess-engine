package com.surya.chess.ui;

import com.surya.chess.domain.board.Board;
import com.surya.chess.domain.board.Cell;
import com.surya.chess.domain.common.Color;
import com.surya.chess.domain.common.Position;
import com.surya.chess.domain.game.Game;
import com.surya.chess.domain.move.Move;
import com.surya.chess.domain.piece.*;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ChessController {

    private static final int SIZE = 8;

    private final GridPane boardView;
    private final Board board;
    private final Game game;

    private Position selectedPosition = null;

    public ChessController() {
        this.boardView = new GridPane();
        this.board = new Board();
        this.game = new Game(board);

        setupInitialPieces();
        buildBoardUI();
        refreshBoard();
    }

    public Parent getView() {
        return boardView;
    }

    // --------------------------------------------------
    // INITIAL SETUP
    // --------------------------------------------------

    private void setupInitialPieces() {

        // ---------- WHITE PIECES ----------
        // Pawns
        for (int col = 0; col < 8; col++) {
            Position p = new Position(6, col);
            board.getCell(p).placePiece(new Pawn(Color.WHITE, p));
        }

        // Rooks
        place(new Rook(Color.WHITE, new Position(7, 0)));
        place(new Rook(Color.WHITE, new Position(7, 7)));

        // Knights
        place(new Knight(Color.WHITE, new Position(7, 1)));
        place(new Knight(Color.WHITE, new Position(7, 6)));

        // Bishops
        place(new Bishop(Color.WHITE, new Position(7, 2)));
        place(new Bishop(Color.WHITE, new Position(7, 5)));

        // Queen & King
        place(new Queen(Color.WHITE, new Position(7, 3)));
        place(new King(Color.WHITE, new Position(7, 4)));


        // ---------- BLACK PIECES ----------
        // Pawns
        for (int col = 0; col < 8; col++) {
            Position p = new Position(1, col);
            board.getCell(p).placePiece(new Pawn(Color.BLACK, p));
        }

        // Rooks
        place(new Rook(Color.BLACK, new Position(0, 0)));
        place(new Rook(Color.BLACK, new Position(0, 7)));

        // Knights
        place(new Knight(Color.BLACK, new Position(0, 1)));
        place(new Knight(Color.BLACK, new Position(0, 6)));

        // Bishops
        place(new Bishop(Color.BLACK, new Position(0, 2)));
        place(new Bishop(Color.BLACK, new Position(0, 5)));

        // Queen & King
        place(new Queen(Color.BLACK, new Position(0, 3)));
        place(new King(Color.BLACK, new Position(0, 4)));
    }

    private void place(Piece piece) {
        board.getCell(piece.getPosition()).placePiece(piece);
    }


    // --------------------------------------------------
    // UI BUILDING
    // --------------------------------------------------

    private void buildBoardUI() {
        boardView.setAlignment(Pos.CENTER);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                Button cellButton = new Button();
                cellButton.setMinSize(80, 80);
                cellButton.setFocusTraversable(false);

                int r = row;
                int c = col;

                cellButton.setOnAction(e -> handleClick(r, c));

                // Chessboard coloring
                if ((row + col) % 2 == 0) {
                    cellButton.setStyle("-fx-background-color: #EEE;");
                } else {
                    cellButton.setStyle("-fx-background-color: #777;");
                }

                boardView.add(cellButton, col, row);
            }
        }
    }

    // --------------------------------------------------
    // CLICK HANDLING
    // --------------------------------------------------
    private java.util.List<Position> highlightedMoves = new java.util.ArrayList<>();

    private void handleClick(int row, int col) {
        Position clicked = new Position(row, col);
        Cell cell = board.getCell(clicked);

        // First click: select piece
        if (selectedPosition == null) {
            if (!cell.isEmpty() &&
                    cell.getPiece().getColor() == game.getCurrentTurn()) {

                selectedPosition = clicked;
                highlightedMoves =
                        cell.getPiece().getValidMoves(board);
            }
            refreshBoard();
            return;
        }

        // Second click: try move
        try {
            game.makeMove(new Move(selectedPosition, clicked));
        } catch (Exception e) {
            System.out.println("Invalid move: " + e.getMessage());
        }

        selectedPosition = null;
        highlightedMoves.clear();
        refreshBoard();
    }


    // --------------------------------------------------
    // UI REFRESH
    // --------------------------------------------------

    private void refreshBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                Button button = (Button) getNodeFromGrid(row, col);
                Position pos = new Position(row, col);
                Cell cell = board.getCell(pos);

                // ---------- BACKGROUND ----------
                if (highlightedMoves.contains(pos)) {
                    button.setStyle("-fx-background-color: #6fa8dc;");
                } else if (selectedPosition != null &&
                        selectedPosition.equals(pos)) {
                    button.setStyle("-fx-background-color: #f6b26b;");
                } else {
                    if ((row + col) % 2 == 0) {
                        button.setStyle("-fx-background-color: #EEE;");
                    } else {
                        button.setStyle("-fx-background-color: #777;");
                    }
                }

                // ---------- PIECE SYMBOL ----------
                if (cell.isEmpty()) {
                    button.setText("");
                } else {
                    button.setText(getSymbol(cell.getPiece()));
                }
            }
        }
    }


    private Parent getNodeFromGrid(int row, int col) {
        for (var node : boardView.getChildren()) {
            if (GridPane.getRowIndex(node) == row &&
                    GridPane.getColumnIndex(node) == col) {
                return (Parent) node;
            }
        }
        return null;
    }

    // --------------------------------------------------
    // PIECE SYMBOLS (TEMP)
    // --------------------------------------------------

    private String getSymbol(Piece piece) {

        if (piece instanceof Pawn) {
            return piece.getColor() == Color.WHITE ? "♙" : "♟";
        }
        if (piece instanceof Rook) {
            return piece.getColor() == Color.WHITE ? "♖" : "♜";
        }
        if (piece instanceof Knight) {
            return piece.getColor() == Color.WHITE ? "♘" : "♞";
        }
        if (piece instanceof Bishop) {
            return piece.getColor() == Color.WHITE ? "♗" : "♝";
        }
        if (piece instanceof Queen) {
            return piece.getColor() == Color.WHITE ? "♕" : "♛";
        }
        if (piece instanceof King) {
            return piece.getColor() == Color.WHITE ? "♔" : "♚";
        }

        return "?";
    }

}

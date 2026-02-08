package com.surya.chess.domain.game;

import com.surya.chess.domain.board.Board;
import com.surya.chess.domain.board.Cell;
import com.surya.chess.domain.common.Color;
import com.surya.chess.domain.common.Position;
import com.surya.chess.domain.move.Move;
import com.surya.chess.domain.piece.Piece;

import java.util.List;

public class Game {

    private final Board board;
    private Color currentTurn;
    private GameState gameState;

    public Game(Board board) {
        this.board = board;
        this.currentTurn = Color.WHITE;
        this.gameState = GameState.ACTIVE;
    }

    public Color getCurrentTurn() {
        return currentTurn;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void makeMove(Move move) {
//        if (gameState != GameState.ACTIVE) {
//            throw new IllegalStateException("Game is not active");
//        }

        Cell fromCell = board.getCell(move.getFrom());
        Piece piece = fromCell.getPiece();

        if (piece == null) {
            throw new IllegalArgumentException("No piece at source position");
        }

        if (piece.getColor() != currentTurn) {
            throw new IllegalArgumentException("Not your turn");
        }

        List<Position> validMoves = piece.getValidMoves(board);

        if (!validMoves.contains(move.getTo())) {
            throw new IllegalArgumentException("Invalid move");
        }

        // Execute move
        fromCell.removePiece();
        board.getCell(move.getTo()).setPiece(piece);
        piece.moveTo(move.getTo());

        // Switch turn
        currentTurn =
                (currentTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
}

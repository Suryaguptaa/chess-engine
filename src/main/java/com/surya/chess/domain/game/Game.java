package com.surya.chess.domain.game;

import com.surya.chess.domain.board.Board;
import com.surya.chess.domain.board.Cell;
import com.surya.chess.domain.common.Color;
import com.surya.chess.domain.common.Position;
import com.surya.chess.domain.move.Move;
import com.surya.chess.domain.piece.King;
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

        if (gameState != GameState.ACTIVE) {
            throw new IllegalStateException("Game is not active");
        }


        Cell fromCell = board.getCell(move.getFrom());
        Piece piece = fromCell.getPiece();

        if (piece == null) {
            throw new IllegalArgumentException("No piece at source position");
        }


        if (piece.getColor() != currentTurn) {
            throw new IllegalArgumentException("Not " + piece.getColor() + "'s turn");
        }


        List<Position> validMoves = piece.getValidMoves(board);

        if (!validMoves.contains(move.getTo())) {
            throw new IllegalArgumentException("Invalid move");
        }


        Cell toCell = board.getCell(move.getTo());
        Piece targetPiece = toCell.getPiece();


        if (targetPiece != null && targetPiece.getColor() == piece.getColor()) {
            throw new IllegalArgumentException("Cannot capture your own piece");
        }


        fromCell.removePiece();
        toCell.setPiece(piece);
        piece.moveTo(move.getTo());

        currentTurn =
                (currentTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Position findKing(Color color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                Cell cell = board.getCell(pos);

                if (!cell.isEmpty()) {
                    Piece p = cell.getPiece();
                    if (p instanceof King && p.getColor() == color) {
                        return pos;
                    }
                }
            }
        }
        throw new IllegalStateException("King not found");
    }

    private boolean isSquareUnderAttack(Position square, Color byColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                Cell cell = board.getCell(pos);

                if (!cell.isEmpty()) {
                    Piece attacker = cell.getPiece();
                    if (attacker.getColor() == byColor) {
                        if (attacker.getValidMoves(board).contains(square)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isKingInCheck(Color color) {
        Position kingPos = findKing(color);
        Color opponent = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
        return isSquareUnderAttack(kingPos, opponent);
    }



}
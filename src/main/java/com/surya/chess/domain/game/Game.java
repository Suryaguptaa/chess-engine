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
        Piece capturedPiece = toCell.getPiece();

        if (capturedPiece != null && capturedPiece.getColor() == piece.getColor()) {
            throw new IllegalArgumentException("Cannot capture your own piece");
        }

        fromCell.removePiece();
        toCell.setPiece(piece);
        piece.moveTo(move.getTo());


        if (isKingInCheck(currentTurn)) {

            // ROLLBACK
            toCell.removePiece();
            fromCell.setPiece(piece);
            piece.moveTo(move.getFrom());

            if (capturedPiece != null) {
                toCell.setPiece(capturedPiece);
            }

            throw new IllegalArgumentException("Move leaves king in check");
        }

        currentTurn =
                (currentTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;


        if (isCheckmate(currentTurn)) {
            gameState = GameState.CHECKMATE;
            System.out.println("CHECKMATE! " +
                    (currentTurn == Color.WHITE ? "Black" : "White") + " wins.");
        }

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

    private boolean hasAnyLegalMove(Color color) {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                Position from = new Position(row, col);
                Cell fromCell = board.getCell(from);

                if (fromCell.isEmpty()) continue;

                Piece piece = fromCell.getPiece();
                if (piece.getColor() != color) continue;

                for (Position to : piece.getValidMoves(board)) {

                    Cell toCell = board.getCell(to);
                    Piece captured = toCell.getPiece();


                    fromCell.removePiece();
                    toCell.setPiece(piece);
                    piece.moveTo(to);

                    boolean kingStillInCheck = isKingInCheck(color);


                    toCell.removePiece();
                    fromCell.setPiece(piece);
                    piece.moveTo(from);
                    if (captured != null) {
                        toCell.setPiece(captured);
                    }

                    if (!kingStillInCheck) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCheckmate(Color color) {
        return isKingInCheck(color) && !hasAnyLegalMove(color);
    }





}
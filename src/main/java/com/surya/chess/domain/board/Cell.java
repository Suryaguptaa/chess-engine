package com.surya.chess.domain.board;

import com.surya.chess.domain.common.Position;
import com.surya.chess.domain.piece.Piece;

public class Cell {

    private final Position position;
    private Piece piece;

    public Cell(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void placePiece(Piece piece) {
        if (this.piece != null) {
            throw new IllegalStateException(
                    "Cell " + position + " is already occupied"
            );
        }
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }
}

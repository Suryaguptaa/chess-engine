package com.surya.chess.domain.board;

import com.surya.chess.domain.common.Position;
import com.surya.chess.domain.piece.Piece;

import java.util.Optional;

public class Cell {

    private final Position position;
    private Piece piece;

    public Cell(Position position){
        this.position = position;
    }

    public Position getPosition(){
        return position;
    }

    public Optional<Piece> getPiece(){
        return Optional.ofNullable(piece);
    }

    public boolean isEmpty(){
        return piece == null;
    }

    void placePiece(Piece piece){
        if(this.piece != null){
            throw new IllegalStateException(
                    "Cell " + position + " is already occupied"
            );
        }

        this.piece = piece;
    }

    void removePiece(){
        if(this.piece == null){
            throw new IllegalStateException(
                    "Cell " + position + " is already empty"
            );
        }
        this.piece = null;
    }
}

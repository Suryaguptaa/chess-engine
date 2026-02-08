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

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Cell getCell(Position position) {
        Cell[][] cells = new Cell[0][];
        return cells[position.getRow()][position.getCol()];
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

    public void removePiece(){
//        if(this.piece == null){
//            throw new IllegalStateException(
//                    "Cell " + position + " is already empty"
//            );
//        }
        this.piece = null;
    }
}

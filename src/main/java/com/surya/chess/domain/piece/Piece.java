package com.surya.chess.domain.piece;

import com.surya.chess.domain.board.Board;
import com.surya.chess.domain.common.Color;
import com.surya.chess.domain.common.Position;

import java.util.List;

public abstract class Piece {

    protected final Color color;
    protected Position position;

    protected Piece(Color color, Position position){
        this.color = color;
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void moveTo (Position newPosition){
        this.position = newPosition;
    }

    public abstract List<Position> getValidMove(Board board);

    public abstract List<Position> getValidMoves(Board board);
}

package com.surya.chess.domain.common;

import java.util.Objects;


public final class Position {

    private final int row;
    private final int col;

    private Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    public static Position of(int row, int col) {
        if(row<0 || row>=8 || col <0 || col >=8 ){
            throw new IllegalArgumentException(
                    "Position must be within 0-7 for both row and column"
                    );
        }
        return new Position(row, col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o){
        if(this ==o) return true;
        if(!(o instanceof Position)) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode(){
        return Objects.hash(row,col);
    }

    @Override
    public String toString(){
        return "(" + row + "," + col + ")";
    }
}

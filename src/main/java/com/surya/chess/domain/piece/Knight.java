package com.surya.chess.domain.piece;

import com.surya.chess.domain.board.Board;
import com.surya.chess.domain.common.Color;
import com.surya.chess.domain.common.Position;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    public Knight(Color color, Position position){
        super(color, position);
    }

    @Override
    public List<Position> getValidMove(Board board) {
        return List.of();
    }

    @Override
    public List<Position> getValidMoves(Board board){
        List<Position> validMoves = new ArrayList<>();

        int [][] moves = {
                {-2,-1}, {-2,1},
                {-1,-2},{-1,2},
                {1,-2},{1,2},
                {2,-1},{2,1}
        };

        int row = position.getRow();
        int col = position.getCol();

        for(int [] move : moves){
            Position next = new Position(row+move[0], col + move[1]);

            if(!board.isWithinBounds(next)){
                continue;
            }

            if (board.getCell(next).isEmpty()){
                validMoves.add(next);
            }

        }

        return validMoves;
    }
}

package com.surya.chess.domain.piece;

import com.surya.chess.domain.board.Board;
import com.surya.chess.domain.common.Color;
import com.surya.chess.domain.common.Position;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{

    public Rook(Color color, Position position){
        super(color,position);
    }

    @Override
    public List<Position> getValidMove(Board board) {
        return List.of();
    }

    @Override
    public List<Position> getValidMoves(Board board){
        List<Position> validMoves = new ArrayList<>();

        int [][] directions = {
                {-1,0},
                {1,0},
                {0,-1},
                {0,1}
        };

        for(int[] dir : directions){
            int row = position.getRow();
            int col = position.getCol();

            while (true){
                row+=dir[0];
                col+=dir[0];

                Position next = new Position(row, col);

                if(!board.isWithinBounds(next)){
                    break;
                }

                if(board.getCell(next).isEmpty()){
                    validMoves.add(next);
                }
                else{
                    break;
                }
            }
        }
        return validMoves;
    }
}

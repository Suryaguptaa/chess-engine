package com.surya.chess.domain.piece;

import com.surya.chess.domain.board.Board;
import com.surya.chess.domain.common.Color;
import com.surya.chess.domain.common.Position;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Color color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> getValidMove(Board board) {
        return List.of();
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        int row = position.getRow();
        int col = position.getCol();

        for (int[] dir : directions) {
            Position next =
                    new Position(row + dir[0], col + dir[1]);

            if (!board.isWithinBounds(next)) {
                continue;
            }

            if (board.getCell(next).isEmpty()) {
                validMoves.add(next);
            }
            // later: capture + check-safety here
        }

        return validMoves;
    }
}

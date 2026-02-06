package com.surya.chess.domain.piece;

import com.surya.chess.domain.board.Board;
import com.surya.chess.domain.board.Cell;
import com.surya.chess.domain.common.Color;
import com.surya.chess.domain.common.Position;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Color color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> getValidMove(Board board) {
        return List.of();
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();

        int direction = (color == Color.WHITE) ? -1 : 1;
        int currentRow = position.getRow();
        int currentCol = position.getCol();

        // 1-step forward
        Position oneStepForward =
                new Position(currentRow + direction, currentCol);

        if (board.isWithinBounds(oneStepForward)
                && board.getCell(oneStepForward).isEmpty()) {
            validMoves.add(oneStepForward);

            // 2-step forward (only if first move)
            boolean isFirstMove =
                    (color == Color.WHITE && currentRow == 6)
                            || (color == Color.BLACK && currentRow == 1);

            if (isFirstMove) {
                Position twoStepsForward =
                        new Position(currentRow + 2 * direction, currentCol);

                if (board.isWithinBounds(twoStepsForward)
                        && board.getCell(twoStepsForward).isEmpty()) {
                    validMoves.add(twoStepsForward);
                }
            }
        }

        return validMoves;
    }
}

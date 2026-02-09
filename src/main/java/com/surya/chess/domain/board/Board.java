package com.surya.chess.domain.board;

import com.surya.chess.domain.common.Position;

public class Board {

    private static final int SIZE = 8;
    private final Cell[][] cells;

    public Board() {
        cells = new Cell[SIZE][SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new Cell(new Position(row, col));
            }
        }
    }

    public boolean isWithinBounds(Position position) {
        int r = position.getRow();
        int c = position.getCol();
        return r >= 0 && r < SIZE && c >= 0 && c < SIZE;
    }

    public Cell getCell(Position position) {
        return cells[position.getRow()][position.getCol()];
    }
}

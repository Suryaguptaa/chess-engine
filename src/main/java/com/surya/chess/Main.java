package com.surya.chess;

import com.surya.chess.domain.board.Board;
import com.surya.chess.domain.common.Color;
import com.surya.chess.domain.common.Position;
import com.surya.chess.domain.game.Game;
import com.surya.chess.domain.move.Move;
import com.surya.chess.domain.piece.Pawn;
import com.surya.chess.domain.piece.Rook;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Board board = new Board();
        Game game = new Game(board);

        // -------------------------
        // INITIAL PIECE SETUP
        // -------------------------

        // WHITE PAWN
        Position wp = new Position(6, 0);
        Pawn whitePawn = new Pawn(Color.WHITE, wp);
        board.getCell(wp).placePiece(whitePawn);

        // BLACK PAWN
        Position bp = new Position(1, 0);
        Pawn blackPawn = new Pawn(Color.BLACK, bp);
        board.getCell(bp).placePiece(blackPawn);

        // WHITE ROOK (optional test)
        Position wr = new Position(7, 0);
        Rook whiteRook = new Rook(Color.WHITE, wr);
        board.getCell(wr).placePiece(whiteRook);

        // -------------------------
        // CLI GAME LOOP
        // -------------------------

        Scanner scanner = new Scanner(System.in);

        System.out.println("♟ Chess Engine Started");
        System.out.println("Enter moves as: row col");
        System.out.println("----------------------");

        while (true) {
            try {
                System.out.println("\nTurn: " + game.getCurrentTurn());

                System.out.print("From (row col): ");
                int fromRow = scanner.nextInt();
                int fromCol = scanner.nextInt();

                System.out.print("To (row col): ");
                int toRow = scanner.nextInt();
                int toCol = scanner.nextInt();

                Move move = new Move(
                        new Position(fromRow, fromCol),
                        new Position(toRow, toCol)
                );

                game.makeMove(move);
                System.out.println("✅ Move successful");

            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }
}

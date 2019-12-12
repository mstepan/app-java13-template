package com.max.app.chess;

public final class ChessMain {


    public static void main(String[] args) throws Exception {

        ChessBoard board = new ChessBoard();

        System.out.println(board.toString());

        board.move(BoardPosition.of(1, 1), BoardPosition.of(3, 1));

        board.move(BoardPosition.of(6, 4), BoardPosition.of(4, 4));

        System.out.println("-------------------------------------");
        System.out.println(board.toString());

        System.out.printf("Main done. java version %s%n", System.getProperty("java.version"));
    }


}

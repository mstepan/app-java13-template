package com.max.app.chess;

public final class BoardPosition {

    final int row;
    final int col;

    private BoardPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static BoardPosition of(int row, int col) {
        return new BoardPosition(row, col);
    }
}

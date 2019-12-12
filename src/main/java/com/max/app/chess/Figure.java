package com.max.app.chess;

public enum Figure {

    EMPTY('.', 0),

    W_KING('♔', 1),
    W_QUEEN('♕', 2),
    W_ROOK('♖', 3),
    W_BISHOP('♗', 4),
    W_KNIGHT('♘', 5),
    W_PAWN('♙', 6),

    B_KING('♚', 7),
    B_QUEEN('♛', 8),
    B_ROOK('♜', 9),
    B_BISHOP('♝', 10),
    B_KNIGHT('♞', 11),
    B_PAWN('♟', 12);

    private final char rep;
    private final int code;

    Figure(char rep, int code) {
        this.rep = rep;
        this.code = code;
    }

    public static Figure findByCode(int code) {

        for (Figure singleFigure : values()) {
            if (singleFigure.code == code) {
                return singleFigure;
            }
        }
        throw new IllegalArgumentException("Can't find figure for code: " + code);
    }

    public char getRep() {
        return rep;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(rep);
    }
}

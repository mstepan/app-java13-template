package com.max.app.chess;

import java.util.BitSet;

import static com.max.app.chess.Figure.B_BISHOP;
import static com.max.app.chess.Figure.B_KING;
import static com.max.app.chess.Figure.B_KNIGHT;
import static com.max.app.chess.Figure.B_PAWN;
import static com.max.app.chess.Figure.B_QUEEN;
import static com.max.app.chess.Figure.B_ROOK;
import static com.max.app.chess.Figure.EMPTY;
import static com.max.app.chess.Figure.W_BISHOP;
import static com.max.app.chess.Figure.W_KING;
import static com.max.app.chess.Figure.W_KNIGHT;
import static com.max.app.chess.Figure.W_PAWN;
import static com.max.app.chess.Figure.W_QUEEN;
import static com.max.app.chess.Figure.W_ROOK;

public class ChessBoard {

    private static final int SIDE_SIZE = 8;

    private static final int BOARD_SIZE = SIDE_SIZE * SIDE_SIZE;

    private static final int BITS_TO_REPRESENT_BOARD = 256;

    private static final int FIGURE_CODE_SIZE = 4;

    private static final Figure[] INITIAL_STATE = {
            W_ROOK, W_KNIGHT, W_BISHOP, W_QUEEN, W_KING, W_BISHOP, W_KNIGHT, W_ROOK,
            W_PAWN, W_PAWN, W_PAWN, W_PAWN, W_PAWN, W_PAWN, W_PAWN, W_PAWN,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            B_PAWN, B_PAWN, B_PAWN, B_PAWN, B_PAWN, B_PAWN, B_PAWN, B_PAWN,
            B_ROOK, B_KNIGHT, B_BISHOP, B_QUEEN, B_KING, B_BISHOP, B_KNIGHT, B_ROOK,
    };

    private final BitSet encodedState;

    public ChessBoard() {
        this(INITIAL_STATE);
    }

    public ChessBoard(Figure[] completedBoard) {
        this.encodedState = encodeState(completedBoard);
    }

    /**
     * time: O(1)
     * space: O(1)
     */
    public void move(BoardPosition fromLocation, BoardPosition toLocation) {

        int fromOffset = calculateOffset(fromLocation);

        Figure fromFigure = decodeFigureAtOffset(encodedState, fromOffset);

        clearFigureBits(encodedState, fromOffset);

        int toOffset = calculateOffset(toLocation);

        clearFigureBits(encodedState, toOffset);

        encodeFigureAtOffset(fromFigure, encodedState, toOffset);
    }

    private static int calculateOffset(BoardPosition position) {
        int offset = (position.row * SIDE_SIZE + position.col) * FIGURE_CODE_SIZE;
        assert offset < BITS_TO_REPRESENT_BOARD : "offset >= 256, offset: " + offset;
        return offset;
    }

    private static void clearFigureBits(BitSet state, int offset) {
        state.clear(offset, offset + FIGURE_CODE_SIZE);
    }

    private BitSet encodeState(Figure[] board) {

        BitSet curState = new BitSet(BITS_TO_REPRESENT_BOARD);

        for (int i = 0, offset = 0; i < board.length; ++i, offset += FIGURE_CODE_SIZE) {
            Figure curFigure = board[i];
            encodeFigureAtOffset(curFigure, curState, offset);
        }

        return curState;
    }

    private void encodeFigureAtOffset(Figure curFigure, BitSet encoded, int offset) {

        int code = curFigure.getCode();

        for (int stateOffset = 0; stateOffset < FIGURE_CODE_SIZE; ++stateOffset) {
            if ((code & 1) != 0) {
                encoded.set(offset + stateOffset);
            }

            code >>= 1;
        }
    }

    private Figure[] decode() {

        Figure[] board = new Figure[BOARD_SIZE];

        for (int i = 0, offset = 0; i < board.length; ++i, offset += FIGURE_CODE_SIZE) {
            board[i] = decodeFigureAtOffset(encodedState, offset);
        }

        return board;
    }

    private Figure decodeFigureAtOffset(BitSet encoded, int offset) {
        int figureCode = 0;

        for (int index = 0; index < FIGURE_CODE_SIZE; ++index) {
            boolean bitValue = encoded.get(offset + index);

            if (bitValue) {
                figureCode |= (1 << index);
            }
        }

        return Figure.findByCode(figureCode);
    }

    @Override
    public String toString() {
        Figure[] decodedState = decode();

        StringBuilder buf = new StringBuilder(BOARD_SIZE);

        for (int i = 0; i < decodedState.length; ++i) {

            if (i != 0 && (i % SIDE_SIZE) == 0) {
                buf.append(System.getProperty("line.separator"));
            }

            buf.append(decodedState[i].getRep()).append(" ");
        }

        return buf.toString();
    }


}

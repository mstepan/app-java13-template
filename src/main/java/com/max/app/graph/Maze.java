package com.max.app.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

final class Maze {

    private final int rows;
    private final int cols;
    private final int[][] arr;

    enum MoveDirection {
        DOWN(1, 0),
        UP(-1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);

        final int rowDelta;
        final int colDelta;

        MoveDirection(int rowDelta, int colDelta) {
            this.rowDelta = rowDelta;
            this.colDelta = colDelta;
        }

        int moveRow(int row) {
            return row + rowDelta;
        }

        int moveCol(int col) {
            return col + colDelta;
        }

//        @Override
//        public String toString(){
//            return name();
//        }
    }

    Maze(int[][] originalArr) {
        Objects.requireNonNull(originalArr);
        checkSameNumOfColumns(originalArr);

        rows = originalArr.length;
        cols = originalArr[0].length;
        arr = new int[rows][];

        for (int i = 0; i < rows; ++i) {
            arr[i] = Arrays.copyOf(originalArr[i], cols);
        }
    }

    List<Location> neighbour(Location cur) {
        List<Location> adj = new ArrayList<>();

        int row = cur.row;
        int col = cur.col;

        for (MoveDirection singleDirection : MoveDirection.values()) {
            int adjRow = singleDirection.moveRow(row);
            int adjCol = singleDirection.moveCol(col);

            if (isAvailable(adjRow, adjCol)) {
                adj.add(new Location(adjRow, adjCol));
            }
        }

        return adj;
    }


    boolean isAvailable(Location location) {
        return isAvailable(location.row, location.col);
    }

    boolean notInBoundary(Location location) {
        return notInBoundary(location.row, location.col);
    }

    private boolean isAvailable(int row, int col) {
        if (notInBoundary(row, col)) {
            return false;
        }

        return arr[row][col] == 1;
    }

    private void checkSameNumOfColumns(int[][] arr) {

        int cols = arr[0].length;

        for (int i = 1; i < arr.length; ++i) {
            if (arr[i].length != cols) {
                throw new IllegalArgumentException("Not all rows have same columns");
            }
        }
    }

    private boolean notInBoundary(int row, int col) {
        return row < 0 || row >= rows || col < 0 || col >= cols;
    }
}

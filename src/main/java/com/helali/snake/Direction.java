package com.helali.snake;

import javafx.geometry.Point2D;

public enum Direction {
    RIGHT(new Point2D(1, 0)),

    LEFT(new Point2D(-1, 0)),

    UP(new Point2D(0, -1)),

    DOWN(new Point2D(0, 1));

    final Point2D vector;

    Direction(Point2D vector) {
        this.vector = vector;
    }
}



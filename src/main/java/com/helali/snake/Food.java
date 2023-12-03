package com.helali.snake;

import javafx.geometry.Point2D;

public class Food {
    private Point2D position;

    public Food(Point2D position) {
        this.position = position;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }
}

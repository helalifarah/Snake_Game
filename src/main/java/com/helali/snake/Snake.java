package com.helali.snake;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Snake {

    private Direction direction = Direction.RIGHT;
    private Point2D head;
    private Point2D previousTail;

    private List<Point2D> body = new ArrayList<>();

    private int score = 0;

    public Snake(Point2D head) {
        this.head = head;
        this.previousTail = head;
        body.add(head);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void update() {
         if (direction == null) {
        // Handle the case where direction is null, e.g., by returning or setting a default direction.
           return;
         }
        head = head.add(direction.vector);
        previousTail = body.remove(body.size() - 1);
        body.add(0,head);
    }


    public Point2D getPosition() {
        return head;
    }

    public boolean isCollidingWith(Food food) {

        if (head.equals(food.getPosition())) {
            return true;
        }
        return false;
    }

    public void grow() {
        body.add(previousTail);
        score++;
    }

    public int getScore() {
        return score;
    }

    public int getLength() {
        return body.size();
    }

    public List<Point2D> getBody() {
        return body;
    }

    public boolean isOutOfBounds(int size) {
        return head.getX() < 0 || head.getY() < 0
                || head.getX() > size || head.getY() > size;
    }

    public boolean isDead() {
        return body.lastIndexOf(head) > 0;
    }
}

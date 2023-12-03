package com.helali.snake;

import javafx.geometry.Point2D;
import java.util.Random;

public class Game {
    private final int size;

    private Snake snake;
    private Food food;

    private boolean isOver = false;
    private boolean foodEaten = false; // Track if food has been eaten

    private Random random = new Random(123);

    public Game(int size) {
        this.size = size;
        snake = new Snake(new Point2D(size / 2, size / 2));
        food = new Food(getRandomPosition());
    }

    public void update() {
        snake.update();

        if (snake.isCollidingWith(food)) {
            snake.grow();
            food.setPosition(getRandomPosition());
            foodEaten = true; // Set the flag to true when food is eaten
        } else {
            foodEaten = false; // Reset the flag when no food is eaten
        }

        if (snake.isDead() || snake.isOutOfBounds(size)) {
            isOver = true;
        }
    }

    public boolean isOver() {
        return isOver;
    }

    public boolean isFoodEaten() {
        return foodEaten;
    }

    private Point2D getRandomPosition() {
        return new Point2D(random.nextInt(size), random.nextInt(size));
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public void setDirection(Direction direction) {
        snake.setDirection(direction);
    }
}

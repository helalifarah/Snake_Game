package com.helali.snake;
import javafx.geometry.Point2D;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SnakeTest {
    private Snake snake;
    @Before
    public void init() {
        snake = new Snake(new Point2D(0, 0));
    }
    @Test
    public void testSnakeMoves() {

        for (Direction direction : Direction.values()) {
            Point2D oldPosition = snake.getPosition();
            snake.setDirection(direction);
            snake.update();
            Point2D newPosition = snake.getPosition();
            System.out.println("Direction: " + direction + ", Old Position: " + oldPosition + ", New Position: " + newPosition);
            assertThat(newPosition, is(oldPosition.add(direction.vector)));
        }
    }
    @Test
    public void testSnakeFoodCollision() {
        Snake snake = new Snake(new Point2D(10, 5));
        Food food = new Food(new Point2D(10, 5));
        assertTrue("Collision with food expected, but not detected", snake.isCollidingWith(food));
    }


    @Test
    public void testSnakeGrows() {
        snake.setDirection(Direction.RIGHT);
        snake.update();
        snake.grow();

        assertThat(snake.getLength(), is(2));
        assertThat(snake.getBody(), hasItem(new Point2D(0, 0)));
    }

    @Test
    public void testSnakeOutOfBounds() {
        Snake snake = new Snake(new Point2D(25, 0));

        assertTrue("Snake is expected to be out of bounds when size is 24", snake.isOutOfBounds(24));
        assertFalse("Snake is expected to be within bounds when size is 25", snake.isOutOfBounds(25));
    }


    @Test
    public void testSnakeDies() {
        for (int i = 0; i < 5; i++) {
            snake.setDirection(Direction.RIGHT);
            snake.update();
            snake.grow();
        }

        snake.setDirection(Direction.UP);
        snake.update();

        snake.setDirection(Direction.LEFT);
        snake.update();

        snake.setDirection(Direction.DOWN);
        snake.update();

        assertTrue("Snake should be dead", snake.isDead());
    }

    @Test
    public void testHeadIsInFront() {
        snake.setDirection(Direction.RIGHT);
        snake.update();
        snake.grow();

        snake.update();
        assertThat(snake.getBody().get(0), is(snake.getPosition()));
    }
}

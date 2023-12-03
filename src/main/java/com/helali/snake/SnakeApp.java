package com.helali.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;

public class SnakeApp extends Application {

    private static final int GAME_SIZE = 24;
    private static final int TILE_SIZE = 30;
    private Scene scene;

    private Game game;
    private Direction nextDirection;
    private boolean isGameRunning = true;
    private int score = 0; // Track the score

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake Game");

        Canvas canvas = new Canvas(TILE_SIZE * GAME_SIZE, TILE_SIZE * GAME_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Pane root = new Pane(canvas);
        scene = new Scene(root); // Use the class-level scene variable
        primaryStage.setScene(scene);

        scene.setOnKeyPressed(event -> {
            if (isGameRunning) {
                switch (event.getCode()) {
                    case UP:
                        nextDirection = Direction.UP;
                        break;
                    case DOWN:
                        nextDirection = Direction.DOWN;
                        break;
                    case LEFT:
                        nextDirection = Direction.LEFT;
                        break;
                    case RIGHT:
                        nextDirection = Direction.RIGHT;
                        break;
                }
            }
        });

        game = new Game(GAME_SIZE);

        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (isGameRunning) {
                    if (now - lastUpdate >= 100_000_000) {
                        lastUpdate = now;

                        game.setDirection(nextDirection);
                        game.update();

                        if (game.isFoodEaten()) {
                            score++; // Increment the score when food is eaten
                        }

                        render(gc);

                        if (game.isOver()) {
                            isGameRunning = false;
                            showGameOverScreen(primaryStage);
                        }
                    }
                }
            }
        }.start();

        primaryStage.show();
    }

    private void render(GraphicsContext gc) {
        gc.setFill(Color.web("#202836"));


        gc.fillRect(0, 0, TILE_SIZE * GAME_SIZE, TILE_SIZE * GAME_SIZE);

        gc.setFill(Color.rgb(37, 150, 190));
        game.getSnake().getBody().forEach(p -> {
            gc.fillRect(p.getX() * TILE_SIZE, p.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        });

        gc.setFill(Color.RED); // Set the fill color to red
        Point2D foodPosition = game.getFood().getPosition();
        double foodX = foodPosition.getX() * TILE_SIZE + TILE_SIZE / 2; // Calculate the center X of the circle
        double foodY = foodPosition.getY() * TILE_SIZE + TILE_SIZE / 2; // Calculate the center Y of the circle
        double radius = TILE_SIZE / 2; // Radius of the circle
        gc.fillOval(foodX - radius, foodY - radius, 2 * radius, 2 * radius); // Draw a circle

        // Display the score
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 10, 20);
    }

    private void showGameOverScreen(Stage primaryStage) {
        // Create a game-over screen with a message and score
        Pane gameOverPane = new Pane();
        gameOverPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        // Create a message label
        javafx.scene.control.Label gameOverLabel = new javafx.scene.control.Label("Game Over!");
        gameOverLabel.setStyle("-fx-font-size: 30; -fx-text-fill: white;");
        gameOverLabel.setLayoutX(100);
        gameOverLabel.setLayoutY(100);

        // Display the score
        javafx.scene.control.Label scoreLabel = new javafx.scene.control.Label("Score: " + score);
        scoreLabel.setStyle("-fx-font-size: 20; -fx-text-fill: white;");
        scoreLabel.setLayoutX(100);
        scoreLabel.setLayoutY(150);

        // Create a replay button
        javafx.scene.control.Button replayButton = new javafx.scene.control.Button("Replay");
        replayButton.setStyle("-fx-font-size: 20;");
        replayButton.setLayoutX(100);
        replayButton.setLayoutY(200);
        replayButton.setOnAction(e -> startNewGame(primaryStage));

        gameOverPane.getChildren().addAll(gameOverLabel, scoreLabel, replayButton);

        Scene gameOverScene = new Scene(gameOverPane, TILE_SIZE * GAME_SIZE, TILE_SIZE * GAME_SIZE);
        primaryStage.setScene(gameOverScene);
    }

    private void startNewGame(Stage primaryStage) {
        // Reset the game variables and start a new game
        score = 0;
        isGameRunning = true;
        game = new Game(GAME_SIZE);
        nextDirection = Direction.RIGHT;

        // Return to the main game scene
        primaryStage.setScene(scene);
    }

}

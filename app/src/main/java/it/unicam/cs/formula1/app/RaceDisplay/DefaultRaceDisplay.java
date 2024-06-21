/*
 * Copyright (c) 2024 Mattia Giaccaglia
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package it.unicam.cs.formula1.app.RaceDisplay;

import it.unicam.cs.formula1.Bot.Bot;
import it.unicam.cs.formula1.GameEngine.GameEngine;
import it.unicam.cs.formula1.Position.Position;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of the {@link RaceDisplay} interface.
 * Handles the visual representation of the race, including displaying the track and updating bot positions.
 */
public class DefaultRaceDisplay implements RaceDisplay {
    private final GameEngine gameEngine;
    private final Map<Bot, Circle> botCircles;

    /**
     * Constructor for DefaultRaceDisplay, initializes the map to track each bot associated Circle for visualization.
     *
     * @param gameEngine the game engine managing the race logic
     */
    public DefaultRaceDisplay(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.botCircles = new HashMap<>();
    }

    @Override
    public void displayTrack(Pane root) {
        int[][] grid = gameEngine.getTrack().getTrackLayout();
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++) {
                Rectangle rect = createRectangleForGrid(grid[i][j], j, i);
                root.getChildren().add(rect);
            }
    }

    @Override
    public void updateBotPositions(Pane root) {
        for (Bot bot : gameEngine.getBots()) {
            Circle circle = botCircles.computeIfAbsent(bot, this::createCircleForBot);
            updateCirclePosition(circle, bot.getCurrentPosition());
            ensureCircleInPane(circle, root);
        }
    }

    @Override
    public void updateCirclePosition(Circle circle, Position position) {
        circle.setCenterX(position.getY() * 20 + 10);
        circle.setCenterY(position.getX() * 20 + 10);
    }

    @Override
    public Rectangle createRectangleForGrid(int cellValue, int x, int y) {
        Rectangle rectangle = new Rectangle(x * 20, y * 20, 20, 20);
        rectangle.setFill(cellValue == 0 ? Color.BLACK :
                (cellValue == 1 ? Color.WHITE : (cellValue == 2 ? Color.GREEN : Color.RED)));
        return rectangle;
    }

    @Override
    public Circle createCircleForBot(Bot bot) {
        return new Circle(bot.getCurrentPosition().getY() * 20 + 10, bot.getCurrentPosition().getX() * 20 + 10, 10, Color.BLUE);
    }

    @Override
    public void ensureCircleInPane(Circle circle, Pane root) {
        if (!root.getChildren().contains(circle))
            root.getChildren().add(circle);
    }
}

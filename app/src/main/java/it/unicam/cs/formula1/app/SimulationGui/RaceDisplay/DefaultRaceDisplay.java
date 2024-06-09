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

package it.unicam.cs.formula1.app.SimulationGui.RaceDisplay;

import it.unicam.cs.formula1.Bot.DefaultBot;
import it.unicam.cs.formula1.GameEngine.DefaultGameEngine;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.HashMap;
import java.util.Map;

public class DefaultRaceDisplay implements RaceDisplay {

    private final DefaultGameEngine defaultGameEngine;
    private final Map<DefaultBot, Circle> botCircles = new HashMap<>();

    public DefaultRaceDisplay(DefaultGameEngine defaultGameEngine) {
        this.defaultGameEngine = defaultGameEngine;
    }

    @Override
    public void displayTrack(Pane root) {
        int[][] grid = defaultGameEngine.getDefaultTrack().getTrack();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Rectangle rect = new Rectangle(j * 20, i * 20, 20, 20);
                rect.setFill(grid[i][j] == 0 ? Color.BLACK : (grid[i][j] == 1 ? Color.WHITE : (grid[i][j] == 2 ? Color.GREEN : Color.RED)));
                root.getChildren().add(rect);
            }
        }
    }

    @Override
    public void updateBotPositions(Pane root) {
        for (DefaultBot defaultBot : defaultGameEngine.getDefaultBots()) {
            Circle circle = botCircles.get(defaultBot);
            if (circle == null) {
                circle = new Circle(defaultBot.getActualPosition().y() * 20 + 10, defaultBot.getActualPosition().x() * 20 + 10, 10, Color.BLUE);
                root.getChildren().add(circle);
                botCircles.put(defaultBot, circle);
            }
            circle.setCenterX(defaultBot.getActualPosition().y() * 20 + 10);
            circle.setCenterY(defaultBot.getActualPosition().x() * 20 + 10);
        }
    }
}

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
import it.unicam.cs.formula1.Position.Position;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Interface representing the display for the race.
 * Provides methods to display the track, update bot positions, and create visual elements for the bots and track.
 */
public interface RaceDisplay {

    /**
     * Displays the track on the specified pane.
     *
     * @param root the pane on which to display the track
     */
    void displayTrack(Pane root);

    /**
     * Creates a circle representing the specified bot
     *
     * @param bot the bot for which to create a circle
     * @return a circle representing the bot
     */
    Circle createCircleForBot(Bot bot);

    /**
     * Updates the positions of the bots on the specified pane
     *
     * @param root the pane on which to update bot positions
     */
    void updateBotPositions(Pane root);

    /**
     * Updates the position of the specified circle to the given position.
     *
     * @param circle the circle representing a bot
     * @param position the new position of the bot
     */
    void updateCirclePosition(Circle circle, Position position);

    /**
     * Creates a rectangle representing a grid cell with the specified value at the given coordinates.
     *
     * @param cellValue the value of the grid cell
     * @param x the x-coordinate of the grid cel
     * @param y the y-coordinate of the grid cell
     * @return a rectangle representing the grid cell
     */
    Rectangle createRectangleForGrid(int cellValue, int x, int y);
}

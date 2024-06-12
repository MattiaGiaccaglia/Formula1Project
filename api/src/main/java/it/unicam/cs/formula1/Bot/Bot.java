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

package it.unicam.cs.formula1.Bot;

import it.unicam.cs.formula1.Movement.Movement;
import it.unicam.cs.formula1.Position.Position;

/**
 * Represents a bot in the game.
 * Provides methods to update the bots position, calculate its next moves, and retrieve its current position and name.
 */
public interface Bot {

    /**
     * Updates the bots position based on the specified direction
     *
     * @param direction the new position of the bot
     */
    void updatePosition(Position direction);

    /**
     * Calculates the bots next moves based on its current position and movement strategy.
     */
    void calculateNextMoves();

    /**
     * Returns the current position of the bot.
     *
     * @return the current position of the bot
     */
    Position getCurrentPosition();

    /**
     * Returns the previous move of the bot.
     *
     * @return the previous move of the bot.
     */
    Position getPreviousMove();

    /**
     * Returns the name of the bot.
     *
     * @return the name of the bot
     */
    String getName();

    /**
     * Returns the Movement of the bot.
     *
     * @return the Movement of the bot
     */
    Movement getMovement();
}

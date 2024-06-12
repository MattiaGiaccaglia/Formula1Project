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

package it.unicam.cs.formula1.Movement;

import it.unicam.cs.formula1.Position.Position;

/**
 * Defines the movement strategies for a bot.
 * Provides methods to accelerate, decelerate, and calculate the main point for movement.
 */
public interface Movement {

    /**
     * Accelerates the movement from the main point based on the previous move.
     *
     * @param mainPoint the main point from which to accelerate
     * @param previousMove the previous move made
     * @return the new position after acceleration
     */
    Position accelerate(Position mainPoint, Position previousMove);

    /**
     * Decelerates the movement from the main point based on the previous move.
     *
     * @param mainPoint the main point from which to decelerate
     * @param previousMove the previous move made
     * @return the new position after deceleration
     */
    Position decelerate(Position mainPoint, Position previousMove);

    /**
     * Calculates the main point for movement based on the actual position and the previous move.
     *
     * @param actualPosition the actual position of the bot
     * @param previousMove the previous move made
     * @return the new main point position
     */
    Position calculateMainPoint(Position actualPosition, Position previousMove);
}

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

package it.unicam.cs.formula1.TrackOperation;

import it.unicam.cs.formula1.Bot.Bot;
import it.unicam.cs.formula1.Position.Position;

import java.util.List;

/**
 * Defines operations for manipulating and verifying positions on a track.
 * Provides methods to check the validity and passability of positions and tracks, and to generate nearby positions.
 */
public interface TrackOperation {

    /**
     * Checks if the specified positions are valid and if the tracks between them are passable.
     *
     * @param mainPoint  the main position to check from
     * @param mainPoint1 the first subsequent position to check after the main position
     * @param mainPoint2 the second subsequent position to check
     * @return true if all positions are valid and the tracks between them are passable, false otherwise
     */
    boolean isValidAndPassable(Position mainPoint, Position mainPoint1, Position mainPoint2);

    /**
     * Checks if a track between two positions is passable by stepping through the path and validating each intermediate position.
     *
     * @param start  the starting position
     * @param arrive the ending position
     * @return true if the track between start and arrive is passable, false otherwise
     */
    boolean checkPassableTrack(Position start, Position arrive);

    /**
     * Calculates and returns a list of valid nearby positions to the specified position.
     *
     * @param position the position to calculate nearby moves from
     * @return a list of {@link Position} representing valid nearby positions
     */
    List<Position> calculateNearbyMoves(Position position);

    /**
     * Checks if the specified position is valid within the track.
     *
     * @param position the position to check
     * @return true if the position is within the track limits and not marked as invalid, false otherwise
     */
    boolean isValidPosition(Position position);

    /**
     * Executes a nearby move for the specified bot if the main point is not valid or if the bot is unable to move directly to a valid position.
     * If no valid moves are available, the bot is eliminated.
     *
     * @param bot the bot for which execute the nearby move
     */
    void executeNearbyMove(Bot bot);
}
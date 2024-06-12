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

package it.unicam.cs.formula1.GameEngine;

import it.unicam.cs.formula1.Bot.Bot;
import it.unicam.cs.formula1.Track.Track;

import java.util.List;

/**
 * Represents the game engine responsible for managing the game.
 * Provides methods to load the game, start the race, update the race status, and retrieve track and bot information.
 */
public interface GameEngine {

    /**
     * Starts the race and manages the game loop until the race is over.
     */
    void startRace();

    /**
     * Checks if the race is over.
     *
     * @return true if the race is over, false otherwise
     */
    Boolean isRaceOver();

    /**
     * Updates the race status, typically by calculating the next moves for all bots.
     * ALso eliminates bots that have crashed during the race.
     */
    void updateRace();

    /**
     * Displays the current status of the race, including the positions of all bots.
     */
    void displayStatus();

    /**
     * Returns the track on which the game is being played.
     *
     * @return the track
     */
    Track getTrack();

    /**
     * Returns the list of bots participating in the game.
     *
     * @return the list of bots
     */
    List<Bot> getBots();
}

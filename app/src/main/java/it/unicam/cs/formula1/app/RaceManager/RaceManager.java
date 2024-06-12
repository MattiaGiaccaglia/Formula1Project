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

package it.unicam.cs.formula1.app.RaceManager;

import it.unicam.cs.formula1.Bot.Bot;

import java.util.List;

/**
 * Interface representing the manager for the race.
 * Provides methods to start the race, check for race completion, and determine if the race is finished.
 */
public interface RaceManager {

    /**
     * Starts the race and manages the race updates.
     */
    void startRace();

    /**
     * Checks if the race is completed by evaluating the positions of the bots.
     * Displays a dialog if a bot has reached the finish position or if all bots are eliminated.
     *
     * @param bots the list of bots participating in the race
     */
    void checkRaceCompletion(List<Bot> bots);

    /**
     * Determines if the race is finished.
     *
     * @return true if the race is finished, false otherwise
     */
    boolean isRaceFinished();
}

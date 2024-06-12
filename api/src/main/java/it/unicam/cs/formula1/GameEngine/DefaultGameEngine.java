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
import it.unicam.cs.formula1.Bot.BotException;
import it.unicam.cs.formula1.Bot.BotFactory;
import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.Track.Track;
import it.unicam.cs.formula1.Track.TrackException;
import it.unicam.cs.formula1.Track.TrackFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Default implementation of the {@link GameEngine} interface.
 * Provides methods to load, start, and manage the race.
 */
public class DefaultGameEngine implements GameEngine {
    private final Track track;
    private final List<Bot> bots;
    private Runnable onRaceUpdated;
    private final Set<Position> finishPositions;

    /**
     * Constructs a new DefaultGameEngine with the specified file path.
     * Initializes the track and bots based on the configuration file.
     *
     * @param filePath the path to the track configuration file
     * @throws TrackException if there is an error with the track configuration
     * @throws IOException if an I/O error occurs reading from the file
     * @throws BotException if there are issues with the bot configuration
     */
    public DefaultGameEngine(String filePath) throws TrackException, IOException, BotException {
        this.track = TrackFactory.loadTrackFromConfig(filePath);
        this.bots = BotFactory.createBotsFromConfig(filePath, track);
        this.finishPositions = new HashSet<>(track.getEndPositions());
    }

    @Override
    public void startRace() {
        System.out.println("Race started!");
        while (!isRaceOver()) {
            updateRace();
            if (onRaceUpdated != null)
                onRaceUpdated.run();
            displayStatus();
        }
    }

    @Override
    public Boolean isRaceOver(){
        Optional<Bot> winningBot = bots.stream()
                .filter(bot -> finishPositions.contains(bot.getCurrentPosition()))
                .findFirst();
        if (winningBot.isPresent()) {
            System.out.println("Bot " + winningBot.get().getName() + " won the race!");
            return true;
        }
        return false;
    }

    @Override
    public void updateRace() {
        for (Bot bot : bots)
            bot.calculateNextMoves();
    }

    @Override
    public void displayStatus() {
        for (Bot bot : bots)
            System.out.println("Bot " + bot.getName() + ", is in position: " + bot.getCurrentPosition());
    }

    @Override
    public Track getTrack() {
        return track;
    }
    @Override
    public List<Bot> getBots() {
        return bots;
    }
}

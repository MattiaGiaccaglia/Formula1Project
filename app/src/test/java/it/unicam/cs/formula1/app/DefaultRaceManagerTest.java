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

package it.unicam.cs.formula1.app;

import it.unicam.cs.formula1.Bot.Bot;
import it.unicam.cs.formula1.Bot.BotException;
import it.unicam.cs.formula1.GameEngine.DefaultGameEngine;
import it.unicam.cs.formula1.GameEngine.GameEngine;
import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.Track.TrackException;
import it.unicam.cs.formula1.app.RaceDisplay.DefaultRaceDisplay;
import it.unicam.cs.formula1.app.RaceDisplay.RaceDisplay;
import it.unicam.cs.formula1.app.RaceManager.DefaultRaceManager;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultRaceManagerTest {
    private DefaultRaceManager raceManager;
    private GameEngine gameEngine;

    @BeforeEach
    void setUp() throws IOException, TrackException, BotException {
        String jsonFilePath = "jsonTest.json";
        String JsonContent = """
                {
                   "track":[
                   "212000",
                   "101101",
                   "313000"
                   ],
                  "bots": [
                      {"name": "Bot1"},
                      {"name":  "Bot2"}
                  ]
                }
                """;
        try (FileWriter file = new FileWriter(jsonFilePath)) {
            file.write(JsonContent);
        }
        gameEngine = new DefaultGameEngine(jsonFilePath);
        Pane root = new Pane();
        RaceDisplay raceDisplay = new DefaultRaceDisplay(gameEngine);
        raceManager = new DefaultRaceManager(gameEngine, raceDisplay, root);
        Files.delete(Path.of(jsonFilePath));
    }

    @Test
    void isRaceFinished() {
        Bot bot1 = gameEngine.getBots().get(1);
        bot1.updatePosition(new Position(1, 0));
        assertFalse(raceManager.isRaceFinished());
        bot1.updatePosition(new Position(2, 0));
        assertTrue(raceManager.isRaceFinished());
        bot1.updatePosition(new Position(1,5));
        gameEngine.updateRace();
        assertFalse(raceManager.isRaceFinished());

        Bot bot2 = gameEngine.getBots().get(0);
        bot2.updatePosition(new Position(1,5));
        gameEngine.updateRace();
        assertTrue(raceManager.isRaceFinished());
    }
}
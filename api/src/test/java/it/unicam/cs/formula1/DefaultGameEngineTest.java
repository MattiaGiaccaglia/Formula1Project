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

package it.unicam.cs.formula1;

import it.unicam.cs.formula1.Bot.Bot;
import it.unicam.cs.formula1.Bot.BotException;
import it.unicam.cs.formula1.GameEngine.DefaultGameEngine;
import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.Track.TrackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DefaultGameEngineTest {
    private DefaultGameEngine gameEngine;

    @BeforeEach
    void setUp() throws IOException, TrackException, BotException {
        String jsonFilePath = "jsonTest.json";
        String JsonContent = """
                {
                   "track":[
                   "212",
                   "101",
                   "313"
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
        Files.delete(Path.of(jsonFilePath));
    }

    @Test
    void testConstructor() {
        assertNotNull(gameEngine.getTrack());
        assertEquals(2, gameEngine.getBots().size());
    }

    @Test
    void testStartRace(){
        gameEngine.startRace();
    }

    @Test
    void testIsRaceOver() {
        Bot bot = gameEngine.getBots().get(0);
        bot.updatePosition(new Position(1, 0));
        assertFalse(gameEngine.isRaceOver());
        bot.updatePosition(new Position(2, 0));
        assertTrue(gameEngine.isRaceOver());
    }

    @Test
    void testUpdateRace() {
        Bot bot = gameEngine.getBots().get(0);
        Position position = bot.getCurrentPosition();
        assertEquals(position, bot.getCurrentPosition());
        gameEngine.updateRace();
        assertNotEquals(position, bot.getCurrentPosition());
    }

    @Test
    void testDisplayStatus(){
        Bot bot = gameEngine.getBots().get(0);
        bot.updatePosition(new Position(0,0));
        String test = "Bot Bot1, is in position: (0, 0)";
        gameEngine.displayStatus();
        assertEquals(test, "Bot " + bot.getName() + ", is in position: " + bot.getCurrentPosition());
    }
}
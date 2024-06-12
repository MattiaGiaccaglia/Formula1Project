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
import it.unicam.cs.formula1.Bot.BotFactory;
import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.Track.Track;
import it.unicam.cs.formula1.Track.TrackException;
import it.unicam.cs.formula1.Track.TrackFactory;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class BotFactoryTest {

    @Test
    void testCreateBotsFromConfig() throws IOException, BotException, TrackException {
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
        Track track = TrackFactory.loadTrackFromConfig(jsonFilePath);
        List<Bot> bots = BotFactory.createBotsFromConfig(jsonFilePath, track);
        assertNotNull(bots);
        assertEquals(2, bots.size());

        Bot bot1 = bots.get(0);
        assertEquals("Bot1", bot1.getName());
        assertEquals(new Position(0, 0), bot1.getCurrentPosition());

        Bot bot2 = bots.get(1);
        assertEquals("Bot2", bot2.getName());
        assertEquals(new Position(0, 2), bot2.getCurrentPosition());
        Files.delete(Paths.get(jsonFilePath));
    }

    @Test
    void testCreateBotsFromConfigInvalidNumber() throws IOException, TrackException {
        String jsonFilePath = "jsonTest.json";
        String JsonContent = """
                {
                   "track":[
                   "211",
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
        Track track = TrackFactory.loadTrackFromConfig(jsonFilePath);
        Exception exception = assertThrows(BotException.class, () -> {
            BotFactory.createBotsFromConfig(jsonFilePath, track);
        });
        String expectedMessage = "The number of bots inserted exceeds the starting slots.";
        String message = exception.getMessage();
        assertTrue(message.contains(expectedMessage));
        Files.delete(Paths.get(jsonFilePath));
    }

    @Test
    void testCreateBotsFromConfigMissingBots() throws IOException, TrackException {
        String jsonFilePath = "jsonTest.json";
        String JsonContent = """
                {
                   "track":[
                   "211",
                   "101",
                   "313"
                   ]
                }
                """;
        try (FileWriter file = new FileWriter(jsonFilePath)) {
            file.write(JsonContent);
        }
        Track track = TrackFactory.loadTrackFromConfig(jsonFilePath);
        Exception exception = assertThrows(BotException.class, () -> {
            BotFactory.createBotsFromConfig(jsonFilePath, track);
        });
        String expectedMessage = "The configuration file does not contain the key 'bots'.";
        String message = exception.getMessage();
        assertTrue(message.contains(expectedMessage));
        Files.delete(Paths.get(jsonFilePath));
    }
}
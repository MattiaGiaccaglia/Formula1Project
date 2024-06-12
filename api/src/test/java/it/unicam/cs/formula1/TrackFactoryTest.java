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

import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.Track.Track;
import it.unicam.cs.formula1.Track.TrackException;
import it.unicam.cs.formula1.Track.TrackFactory;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrackFactoryTest {

    @Test
    void testLoadTrackFromConfig() throws IOException, TrackException {
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
        assertNotNull(track);
        int[][] expectedTrackLayout = {
                 {2, 1, 2},
                {1, 0, 1},
                {3, 1, 3}
        };
        assertArrayEquals(expectedTrackLayout, track.getTrackLayout());
        List<Position> expectedStartPositions = List.of(new Position(0, 0), new Position(0, 2));
        assertEquals(expectedStartPositions, track.getStartPositions());

        List<Position> expectedEndPositions = List.of(new Position(2, 0), new Position(2, 2));
        assertEquals(expectedEndPositions, track.getEndPositions());
        Files.delete(Paths.get(jsonFilePath));
    }

    @Test
    void testLoadTrackFromConfigFileNotFound() {
        Exception exception = assertThrows(NoSuchFileException.class, () -> {
            TrackFactory.loadTrackFromConfig("invalidPath.json");
        });
        String expectedMessage = "File not found: invalidPath.json";
        String message = exception.getMessage();
        assertTrue(message.contains(expectedMessage));
    }


    @Test
    void testLoadTrackFromConfigInvalidTrack() throws IOException {
        String jsonFilePath = "jsonTest.json";
        String invalidJsonContent = """
        {
            "track": [
                "210",
                "000",
                "010"
            ]
        }
        """;
        try (FileWriter file = new FileWriter(jsonFilePath)) {
            file.write(invalidJsonContent);
        }
        Exception exception = assertThrows(TrackException.class, () -> {
            TrackFactory.loadTrackFromConfig(jsonFilePath);
        });
        String expectedMessage = "Start or finish positions are missing in the track.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Files.delete(Paths.get(jsonFilePath));
    }

    @Test
    void testLoadTrackFromConfigMissingTrack() throws IOException {
        String jsonFilePath = "jsonTest.json";
        String invalidJsonContent = """
        {
        }
        """;
        try (FileWriter file = new FileWriter(jsonFilePath)) {
            file.write(invalidJsonContent);
        }
        Exception exception = assertThrows(TrackException.class, () -> {
            TrackFactory.loadTrackFromConfig(jsonFilePath);
        });
        String expectedMessage = "The configuration file does not contain the key 'track'.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Files.delete(Paths.get(jsonFilePath));
    }
}
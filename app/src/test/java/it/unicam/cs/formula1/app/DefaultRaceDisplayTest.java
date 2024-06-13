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

import it.unicam.cs.formula1.Bot.BotException;
import it.unicam.cs.formula1.GameEngine.DefaultGameEngine;
import it.unicam.cs.formula1.GameEngine.GameEngine;
import it.unicam.cs.formula1.Track.TrackException;
import it.unicam.cs.formula1.app.RaceDisplay.DefaultRaceDisplay;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultRaceDisplayTest {

    private DefaultRaceDisplay raceDisplay;
    private Pane root;

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
        GameEngine gameEngine = new DefaultGameEngine(jsonFilePath);
        root = new Pane();
        raceDisplay = new DefaultRaceDisplay(gameEngine);
        Files.delete(Path.of(jsonFilePath));
    }

    @Test
    void displayTrack() {
        raceDisplay.displayTrack(root);
        assertEquals(9, root.getChildren().size());
        Rectangle rectangle = (Rectangle) root.getChildren().get(0);
        assertEquals(Color.GREEN, rectangle.getFill());
        rectangle = (Rectangle) root.getChildren().get(6);
        assertEquals(Color.RED, rectangle.getFill());
    }
}
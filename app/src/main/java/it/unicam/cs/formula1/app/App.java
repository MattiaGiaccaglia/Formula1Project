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
import it.unicam.cs.formula1.app.RaceDisplay.RaceDisplay;
import it.unicam.cs.formula1.app.RaceManager.RaceManager;
import it.unicam.cs.formula1.app.RaceDisplay.DefaultRaceDisplay;
import it.unicam.cs.formula1.app.RaceManager.DefaultRaceManager;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class for the race simulation GUI.
 * Sets up the GUI components, initializes the game engine, and starts the race.
 */
public class App extends Application {

    /**
     * Starts the JavaFX application and initializes the race simulation GUI.
     *
     * @param primaryStage the primary stage for this application
     * @throws IOException if an I/O error occurs during game loading
     * @throws TrackException if there is an error with the track configuration
     * @throws BotException if there are issues with the bot configuration
     */
    @Override
    public void start(Stage primaryStage) throws IOException, TrackException, BotException {
        VBox root = new VBox(10);
        Scene scene = new Scene(root);
        root.setAlignment(Pos.TOP_CENTER);
        primaryStage.setTitle("Race Simulation");
        primaryStage.setScene(scene);

        Pane racePane = new Pane();
        root.getChildren().add(racePane);

        GameEngine gameEngine = new DefaultGameEngine("../TrackRace.json");

        RaceDisplay raceDisplay = new DefaultRaceDisplay(gameEngine);
        RaceManager raceManager = new DefaultRaceManager(gameEngine, raceDisplay, racePane);
        raceDisplay.displayTrack(racePane);

        Button startButton = new Button("Start race");
        startButton.setOnAction(event -> {raceManager.startRace(); startButton.setDisable(true); });
        root.getChildren().add(startButton);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


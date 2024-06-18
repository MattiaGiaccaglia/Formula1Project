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
import it.unicam.cs.formula1.GameEngine.GameEngine;
import it.unicam.cs.formula1.app.RaceDisplay.RaceDisplay;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents a record-based implementation of the {@link RaceManager} interface.
 * Manages the race by starting the race, checking for race completion, and determining if the race is finished.
 *
 * @param gameEngine the game engine managing the game logic
 * @param raceDisplay the display for visualizing the race
 * @param root the pane on which to display the race
 */
public record DefaultRaceManager(GameEngine gameEngine, RaceDisplay raceDisplay, Pane root) implements RaceManager {

    @Override
    public void startRace() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable raceTask = () -> {
            gameEngine.updateRace();
            Platform.runLater(() -> raceDisplay.updateBotPositions(root));
            if (isRaceFinished()) {
                scheduler.shutdown();
                Platform.runLater(() -> checkRaceCompletion(gameEngine.getBots()));
            }
        };
        scheduler.scheduleAtFixedRate(raceTask, 0, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void checkRaceCompletion(List<Bot> bots) {
        if (bots.isEmpty()) {
            Platform.runLater(this::showAllBotsEliminatedDialog);
            return;
        }
        for (Bot bot : bots)
            if (gameEngine.getTrack().getEndPositions().contains(bot.getCurrentPosition())) {
                Platform.runLater(() -> showRaceFinishedDialog(bot));
                break;
            }
    }

    @Override
    public boolean isRaceFinished() {
        return gameEngine.getBots().stream()
                .anyMatch(defaultBot -> gameEngine.getTrack().getEndPositions().contains(defaultBot.getCurrentPosition()))
                || gameEngine.getBots().isEmpty();
    }

    /**
     * Displays a dialog indicating which bot has won the race.
     *
     * @param defaultBot the bot that won the race
     */
    private void showRaceFinishedDialog(Bot defaultBot) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Race finished!");
        alert.setHeaderText(null);
        alert.setContentText("Bot " + defaultBot.getName() + " won the race!");
        alert.setOnHidden(evt -> Platform.exit());
        alert.show();
    }

    /**
     * Displays a dialog indicating that all bots have been eliminated and the race is over.
     */
    private void showAllBotsEliminatedDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Race finished!");
        alert.setHeaderText(null);
        alert.setContentText("All bots have been eliminated. The race is over.");
        alert.setOnHidden(evt -> Platform.exit());
        alert.show();
    }
}

package it.unicam.cs.formula1.app.SimulationGui.RaceManager;

import it.unicam.cs.formula1.Bot.DefaultBot;
import it.unicam.cs.formula1.GameEngine.DefaultGameEngine;
import it.unicam.cs.formula1.app.SimulationGui.RaceDisplay.RaceDisplay;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.util.List;

public class DefaultRaceManager implements RaceManager {

    private DefaultGameEngine defaultGameEngine;
    private RaceDisplay raceDisplay;
    private AnimationTimer timer;
    private Pane root;

    public DefaultRaceManager(DefaultGameEngine defaultGameEngine, RaceDisplay raceDisplay, Pane root) {
        this.defaultGameEngine = defaultGameEngine;
        this.raceDisplay = raceDisplay;
        this.root = root;
    }

    @Override
    public void startRace() {
        if (timer != null)
            timer.stop(); // Ferma il timer se è già in esecuzione
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                defaultGameEngine.updateRace();
                raceDisplay.updateBotPositions(root);
                if (isRaceFinished()) {
                    stop();
                    checkRaceCompletion(defaultGameEngine.getDefaultBots());
                }
            }
        };
        timer.start();
    }

    @Override
    public void checkRaceCompletion(List<DefaultBot> defaultBots) {
        for (DefaultBot defaultBot : defaultBots)
            if (defaultGameEngine.getDefaultTrack().getEnd().contains(defaultBot.getActualPosition())) {
                Platform.runLater(() -> showRaceFinishedDialog(defaultBot));
                break;
            }
    }

    @Override
    public boolean isRaceFinished() {
        return defaultGameEngine.getDefaultBots().stream()
                .anyMatch(defaultBot -> defaultGameEngine.getDefaultTrack().getEnd().contains(defaultBot.getActualPosition()));
    }

    private void showRaceFinishedDialog(DefaultBot defaultBot) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Race finished!");
        alert.setHeaderText(null);
        alert.setContentText("Bot " + defaultBot.getName() + " won the race!");
        alert.showAndWait();
    }
}

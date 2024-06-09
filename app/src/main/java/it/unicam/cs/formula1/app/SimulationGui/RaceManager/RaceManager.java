package it.unicam.cs.formula1.app.SimulationGui.RaceManager;


import it.unicam.cs.formula1.Bot.DefaultBot;
import java.util.List;

public interface RaceManager {
    void startRace();
    void checkRaceCompletion(List<DefaultBot> defaultBots);
    boolean isRaceFinished();
}

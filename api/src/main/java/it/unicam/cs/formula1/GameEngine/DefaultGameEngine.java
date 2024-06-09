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

import it.unicam.cs.formula1.Bot.DefaultBot;
import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.Track.DefaultTrack;
import it.unicam.cs.formula1.Track.TrackException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DefaultGameEngine implements GameEngine{
    private DefaultTrack defaultTrack;
    private List<DefaultBot> defaultBots;
    private Runnable onRaceUpdated;

    public DefaultGameEngine() {
        defaultTrack = new DefaultTrack();
        defaultBots = new ArrayList<>();
    }

    @Override
    public void loadGame(String filePath) throws IOException, TrackException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        // Carica il defaultTrack
        defaultTrack.loadTrackFromFile(filePath);
        // Carica i bot
        JSONArray jsonArrayBots = jsonObject.getJSONArray("bots");
        if(jsonArrayBots.length() > defaultTrack.getStart().size())
            throw new IOException("The number of bots inserted exceeds the starting slots.");
        for (int i = 0; i < jsonArrayBots.length(); i++) {
            JSONObject botJson = jsonArrayBots.getJSONObject(i);
            defaultBots.add(new DefaultBot(botJson.getString("name"), defaultTrack.getStart().get(i), defaultTrack));
        }
    }

    @Override
    public void startRace() {
        System.out.println("Race started!");
        int numeroStep = 0;
        Set<Position> posizioniArrivo = new HashSet<>(defaultTrack.getEnd());
        while (true) {
            updateRace();
            if (onRaceUpdated != null)
                onRaceUpdated.run();  // Chiama il callback per aggiornare l'UI
            displayStatus();
            numeroStep++;
            Optional<DefaultBot> botVincitore = defaultBots.stream()
                    .filter(defaultBot -> posizioniArrivo.contains(defaultBot.getActualPosition()))
                    .findFirst();
            if (botVincitore.isPresent()) {
                System.out.println("Bot " + botVincitore.get().getName() + " won the race after " + numeroStep + " steps");
                break;
            }
        }
    }

    @Override
    public void updateRace() {
        for (DefaultBot defaultBot : defaultBots)
            defaultBot.calculateNextMoves();
    }

    @Override
    public void displayStatus() {
        for (DefaultBot defaultBot : defaultBots)
            System.out.println("Bot " + defaultBot.getName() + ", is in the position: " + defaultBot.getActualPosition());
    }


    public DefaultTrack getDefaultTrack() {
        return defaultTrack;
    }

    public List<DefaultBot> getDefaultBots() {
        return defaultBots;
    }

    public Runnable getOnRaceUpdated() {
        return onRaceUpdated;
    }

    public void setOnRaceUpdated(Runnable onRaceUpdated) {
        this.onRaceUpdated = onRaceUpdated;
    }
}

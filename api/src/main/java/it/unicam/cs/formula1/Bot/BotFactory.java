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

package it.unicam.cs.formula1.Bot;


import it.unicam.cs.formula1.Movement.DefaultMovement;
import it.unicam.cs.formula1.Movement.Movement;
import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.Track.*;
import it.unicam.cs.formula1.TrackOperation.DefaultTrackOperation;
import it.unicam.cs.formula1.TrackOperation.TrackOperation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating instances of {@link Bot}.
 * Provides methods to create default bots and bots from a configuration file.
 */
public class BotFactory {

    /**
     * Factory method to create a default bot with the specified name, starting position, and track.
     *
     * @param name          the name of the bot
     * @param startPosition the starting position of the bot
     * @param track         the track on which the bot will operate
     * @return a new instance of DefaultBot
     */
    public static DefaultBot createDefaultBot(String name, Position startPosition, Track track) {
        Movement movement = new DefaultMovement();
        TrackOperation trackOperation = new DefaultTrackOperation(track);
        return new DefaultBot(name, startPosition, movement, trackOperation);
    }

    /**
     * Creates a list of bots from a configuration file.
     * The configuration file should be a JSON file with bot details.
     *
     * @param configPath the path to the configuration file
     * @param track      the track on which the bots will operate
     * @return a list of {@link Bot}
     * @throws IOException if an I/O error occurs reading from the file
     * @throws BotException if there are issues with the bot configuration
     */
    public static List<Bot> createBotsFromConfig(String configPath, Track track) throws IOException, BotException {
        List<Bot> bots = new ArrayList<>();
        String content = new String(Files.readAllBytes(Paths.get(configPath)));
        JSONObject jsonObject = new JSONObject(content);
        if (!jsonObject.has("bots"))
            throw new BotException("The configuration file does not contain the key 'bots'.");
        JSONArray jsonArray = jsonObject.getJSONArray("bots");
        int startPositionsSize = track.getStartPositions().size();
        if(jsonArray.length() > startPositionsSize)
            throw new BotException("The number of bots inserted exceeds the starting slots.");
        for (int i = 0; i < jsonArray.length() && i < startPositionsSize; i++) {
            JSONObject botJson = jsonArray.getJSONObject(i);
            String name = botJson.getString("name");
            Position startPosition = track.getStartPositions().get(i);
            Bot bot = createDefaultBot(name, startPosition, track);
            bots.add(bot);
        }
        return bots;
    }
}

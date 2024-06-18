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
import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.Track.*;
import it.unicam.cs.formula1.TrackOperation.DefaultTrackOperation;
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
     * Creates a list of bots from a configuration file.
     *
     * @param configPath the path to the configuration file
     * @param track      the track on which the bots will operate
     * @return a list of {@link Bot}
     * @throws IOException if an I/O error occurs reading from the file
     * @throws BotException if there are issues with the bot configuration
     */
    public static List<Bot> createBotsFromConfig(String configPath, Track track) throws IOException, BotException {
        String content = Files.readString(Paths.get(configPath));
        JSONArray jsonArray = parseAndValidateBotConfig(content);
        return instantiateBots(jsonArray, track);
    }

    /**
     * Creates a default bot with the specified name, starting position, and track.
     *
     * @param name          the name of the bot
     * @param startPosition the starting position of the bot
     * @param track         the track on which the bot will operate
     * @return a new instance of DefaultBot
     */
    private static Bot createDefaultBot(String name, Position startPosition, Track track) {
        return new DefaultBot(name, startPosition, new DefaultMovement(), new DefaultTrackOperation(track));
    }

    /**
     * Parses the JSON content and validates the presence of 'bots' key.
     *
     * @param content JSON content as String
     * @return JSONArray of bot configurations
     * @throws BotException if the required 'bots' key is missing
     */
    private static JSONArray parseAndValidateBotConfig(String content) throws BotException {
        JSONObject jsonObject = new JSONObject(content);
        if (!jsonObject.has("bots"))
            throw new BotException("The configuration file does not contain the key 'bots'.");
        return jsonObject.getJSONArray("bots");
    }

    /**
     * Instantiates bots based on the JSON array and the given track.
     *
     * @param jsonArray the JSON array containing bot details
     * @param track     the track on which the bots will operate
     * @return a list of instantiated bots
     * @throws BotException if there are more bots than starting positions
     */
    private static List<Bot> instantiateBots(JSONArray jsonArray, Track track) throws BotException {
        List<Bot> bots = new ArrayList<>();
        int startPositionsSize = track.getStartPositions().size();
        if (jsonArray.length() > startPositionsSize)
            throw new BotException("The number of bots exceeds the available starting positions.");
        for (int i = 0; i < jsonArray.length() && i < startPositionsSize; i++) {
            JSONObject botJson = jsonArray.getJSONObject(i);
            bots.add(createDefaultBot(botJson.getString("name"), track.getStartPositions().get(i), track));
        }
        return bots;
    }
}

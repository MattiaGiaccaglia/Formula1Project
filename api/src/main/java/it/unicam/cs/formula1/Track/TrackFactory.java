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

package it.unicam.cs.formula1.Track;


import it.unicam.cs.formula1.Position.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating and loading {@link Track} instances.
 */
public class TrackFactory {

    /**
     * Factory method to create a DefaultTrack instance by loading the track from a file.
     *
     * @param filePath the path to the track configuration file
     * @return a new DefaultTrack instance representing the loaded track
     * @throws TrackException if there is an error with the track configuration
     * @throws IOException if an I/O error occurs reading from the file
     */
    public static DefaultTrack loadTrackFromConfig(String filePath) throws TrackException, IOException {
        Path of = Path.of(filePath);
        if (!Files.exists(of))
            throw new NoSuchFileException("File not found: " + filePath);
        String content = new String(Files.readAllBytes(of));
        JSONObject jsonObject = new JSONObject(content);
        if(!jsonObject.has("track"))
            throw new TrackException("The configuration file does not contain the key 'track'.");
        JSONArray jsonArray = jsonObject.getJSONArray("track");
        int[][] trackLayout = new int[jsonArray.length()][];
        List<Position> startPositions = new ArrayList<>();
        List<Position> endPositions = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String row = jsonArray.getString(i);
            trackLayout[i] = new int[row.length()];
            for (int j = 0; j < row.length(); j++) {
                trackLayout[i][j] = Character.getNumericValue(row.charAt(j));
                if (trackLayout[i][j] == 2)
                    startPositions.add(new Position(i, j));
                else if (trackLayout[i][j] == 3)
                    endPositions.add(new Position(i, j));
            }
        }
        if (startPositions.isEmpty() || endPositions.isEmpty())
            throw new TrackException("Start or finish positions are missing in the track.");
        return new DefaultTrack(trackLayout, startPositions, endPositions);
    }
}


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
     * Loads a track configuration from a specified file and creates a DefaultTrack object.
     *
     * @param filePath the path to the track configuration file
     * @return a new DefaultTrack instance representing the loaded track
     * @throws TrackException if there is an error with the track configuration
     * @throws IOException if an I/O error occurs reading from the file
     */
    public static DefaultTrack loadTrackFromConfig(String filePath) throws TrackException, IOException {
        String content = readFileContent(filePath);
        JSONObject jsonObject = new JSONObject(content);
        validateJSONContent(jsonObject);
        Track track = parseTrackLayout(jsonObject.getJSONArray("track"));
        validatePositions(track.getStartPositions(), track.getEndPositions());
        return new DefaultTrack(track.getTrackLayout(), track.getStartPositions(), track.getEndPositions());
    }

    /**
     * Parses the JSON array representing a track layout into a DefaultTrack object.
     * Extracts the track layout, start positions, and end positions from the JSON array.
     *
     * @param jsonArray the JSON array containing the track layout data
     * @return a DefaultTrack instance with initialized layout and positions
     */
    private static DefaultTrack parseTrackLayout(JSONArray jsonArray) {
        int[][] trackLayout = new int[jsonArray.length()][];
        List<Position> startPositions = new ArrayList<>();
        List<Position> endPositions = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String row = jsonArray.getString(i);
            trackLayout[i] = new int[row.length()];
            for (int j = 0; j < row.length(); j++) {
                trackLayout[i][j] = Character.getNumericValue(row.charAt(j));
                if (trackLayout[i][j] == 2) startPositions.add(new Position(i, j));
                else if (trackLayout[i][j] == 3) endPositions.add(new Position(i, j));
            }
        }
        return new DefaultTrack(trackLayout, startPositions, endPositions);
    }

    /**
     * Reads the entire content of a file specified by the file path.
     *
     * @param filePath the path to the file to be read
     * @return the content of the file as a String
     * @throws IOException if the file does not exist or cannot be read
     */
    private static String readFileContent(String filePath) throws IOException {
        Path path = Path.of(filePath);
        if (!Files.exists(path))
            throw new NoSuchFileException("File not found: " + filePath);
        return new String(Files.readAllBytes(path));
    }

    /**
     * Validates that the provided JSON object contains the necessary 'track' key.
     *
     * @param jsonObject the JSON object to validate
     * @throws TrackException if the required key is missing
     */
    private static void validateJSONContent(JSONObject jsonObject) throws TrackException {
        if (!jsonObject.has("track"))
            throw new TrackException("The configuration file does not contain the key 'track'.");
    }

    /**
     * Validates that the list of start and end positions are not empty.
     *
     * @param startPositions the list of start positions on the track
     * @param endPositions the list of end positions on the track
     * @throws TrackException if start or finish positions are missing
     */
    private static void validatePositions(List<Position> startPositions, List<Position> endPositions) throws TrackException {
        if (startPositions.isEmpty() || endPositions.isEmpty())
            throw new TrackException("Start or finish positions are missing in the track.");
    }
}


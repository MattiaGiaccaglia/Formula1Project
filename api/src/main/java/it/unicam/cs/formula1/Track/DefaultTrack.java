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

import java.util.List;

/**
 * Default implementation of the {@link Track} interface.
 * Represents an immutable track in the game.
 */
public class DefaultTrack implements Track {
    private final int[][] track;
    private final List<Position> start;
    private final List<Position> end;

    /**
     * Constructor of DefaultTrack
     *
     * @param trackLayout the 2D array representing the track layout
     * @param startPositions the list of starting positions
     * @param endPositions the list of ending positions
     */
    public DefaultTrack(int[][] trackLayout, List<Position> startPositions, List<Position> endPositions) {
        this.track = trackLayout;
        this.start = List.copyOf(startPositions);
        this.end = List.copyOf(endPositions);
    }

    @Override
    public int[][] getTrackLayout() {
        return track;
    }

    @Override
    public List<Position> getStartPositions() {
        return this.start;
    }

    @Override
    public List<Position> getEndPositions() {
        return this.end;
    }
}



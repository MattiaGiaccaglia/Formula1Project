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

package it.unicam.cs.formula1;

import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.Track.DefaultTrack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultTrackTest {

    int[][] trackLayout;
    private DefaultTrack track;
    private List<Position> startPositions;
    private List<Position> endPositions;

    @BeforeEach
    public void setUp() {
        trackLayout = new int[][]{
                {2, 1, 2},
                {1, 0, 1},
                {3, 1, 3}
        };
        startPositions = List.of(new Position(0, 0), new Position(0, 2));
        endPositions = List.of(new Position(2, 0), new Position(2, 2));
        track = new DefaultTrack(trackLayout, startPositions, endPositions);
    }

    @Test
    public void testGetTrackLayout() {
        assertArrayEquals(trackLayout, track.getTrackLayout());
    }

    @Test
    public void testGetStartPositions() {
        assertEquals(startPositions, track.getStartPositions());
        assertEquals(2, track.getTrackLayout()[startPositions.get(0).getX()][startPositions.get(0).getY()]);
    }

    @Test
    public void testGetEndPositions() {
        assertEquals(endPositions, track.getEndPositions());
        assertEquals(3, track.getTrackLayout()[endPositions.get(0).getX()][endPositions.get(0).getY()]);
    }
}

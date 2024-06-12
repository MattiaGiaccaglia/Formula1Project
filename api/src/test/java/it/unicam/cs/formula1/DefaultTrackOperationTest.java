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
import it.unicam.cs.formula1.Track.Track;
import it.unicam.cs.formula1.TrackOperation.DefaultTrackOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultTrackOperationTest {

    private DefaultTrackOperation trackOperation;

    @BeforeEach
    void setUp() {
        int[][] trackLayout = {
                {2, 1, 2},
                {1, 0, 1},
                {3, 1, 3}
        };
        List<Position> startPositions = List.of(new Position(0, 0), new Position(0, 2));
        List<Position> endPositions = List.of(new Position(2, 0), new Position(2, 2));
        Track track = new DefaultTrack(trackLayout, startPositions, endPositions);
        trackOperation = new DefaultTrackOperation(track);
    }

    @Test
    void testIsValidAndPassable() {
        Position mainPoint = new Position(0, 0);
        Position mainPoint1 = new Position(1, 0);
        Position mainPoint2 = new Position(2, 0);
        assertTrue(trackOperation.isValidAndPassable(mainPoint, mainPoint1, mainPoint2));

        Position invalidPoint = new Position(1, 1);
        mainPoint2 = new Position(2,2);
        assertFalse(trackOperation.isValidAndPassable(mainPoint, invalidPoint, mainPoint2));
    }

    @Test
    void testCheckPassableTrack() {
        Position start = new Position(0, 0);
        Position end = new Position(2, 0);
        assertTrue(trackOperation.checkPassableTrack(start, end));

        start = new Position(0, 1);
        end = new Position(2, 1);
        assertFalse(trackOperation.checkPassableTrack(start, end));
    }

    @Test
    void testCalculateNearbyMoves() {
        Position position = new Position(0, 0);
        List<Position> nearbyMoves = trackOperation.calculateNearbyMoves(position);

        assertTrue(nearbyMoves.contains(new Position(1, 0)));
        assertTrue(nearbyMoves.contains(new Position(0, 1)));
        assertFalse(nearbyMoves.contains(new Position(0, 0)));
        assertFalse(nearbyMoves.contains(new Position(1, 1)));
    }

    @Test
    void testIsValidPosition() {
        Position validPosition = new Position(0, 0);
        Position invalidPosition = new Position(1, 1);
        assertTrue(trackOperation.isValidPosition(validPosition));
        assertFalse(trackOperation.isValidPosition(invalidPosition));
    }
}

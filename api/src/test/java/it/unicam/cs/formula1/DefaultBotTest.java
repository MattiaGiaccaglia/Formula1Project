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

import it.unicam.cs.formula1.Bot.Bot;
import it.unicam.cs.formula1.Bot.DefaultBot;
import it.unicam.cs.formula1.Movement.DefaultMovement;
import it.unicam.cs.formula1.Movement.Movement;
import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.Track.DefaultTrack;
import it.unicam.cs.formula1.Track.Track;
import it.unicam.cs.formula1.TrackOperation.DefaultTrackOperation;
import it.unicam.cs.formula1.TrackOperation.TrackOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultBotTest {
    private Movement movement;
    private TrackOperation trackOperation;

    @BeforeEach
    void setUp() {
        int[][] trackLayout = {
                {2, 0, 2},
                {1, 0, 1},
                {1, 0, 1},
                {1, 0, 1},
                {3, 0, 3}
        };
        List<Position> startPositions = List.of(new Position(0, 0), new Position(0, 2));
        List<Position> endPositions = List.of(new Position(4, 0), new Position(4, 2));
        Track track = new DefaultTrack(trackLayout, startPositions, endPositions);
        movement = new DefaultMovement();
        trackOperation = new DefaultTrackOperation(track);
    }

    @Test
    void testConstructor() {
        Bot bot = new DefaultBot("TestBot", new Position(0, 0), movement, trackOperation);
        assertEquals("TestBot", bot.getName());
        assertEquals(new Position(0, 0), bot.getCurrentPosition());
        assertEquals(new Position(0, 0), bot.getPreviousMove());
        assertEquals(movement, bot.getMovement());
    }

    @Test
    void testUpdatePosition() {
        Bot bot = new DefaultBot("TestBot", new Position(0, 0), movement, trackOperation);
        Position newPosition = new Position(1, 0);
        bot.updatePosition(newPosition);
        assertEquals(new Position(1, 0), bot.getCurrentPosition());
        assertEquals(new Position(1, 0), bot.getPreviousMove());
    }

    @Test
    void testCalculateNextMoves() {
        Bot bot = new DefaultBot("TestBot", new Position(0, 0), movement, trackOperation);
        bot.calculateNextMoves();
        assertEquals(new Position(1,0), bot.getCurrentPosition());
        bot.calculateNextMoves();
        assertEquals(new Position(3,0), bot.getCurrentPosition());
        bot.calculateNextMoves();
        assertEquals(new Position(4,0), bot.getCurrentPosition());
    }
}

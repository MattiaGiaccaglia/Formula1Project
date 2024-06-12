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


import it.unicam.cs.formula1.Movement.Movement;
import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.TrackOperation.TrackOperation;

/**
 * Default implementation of the {@link Bot} interface.
 * Provides methods to update the bots position and calculate next moves.
 */
public class DefaultBot implements Bot {
    private final String name;
    private final Movement movement;
    private final TrackOperation trackOperation;
    private boolean eliminated;
    private Position actualPosition;
    private Position previousMove;

    /**
     * Constructs a new DefaultBot with the specified name, starting position, movement, track operation, and random number generator.
     *
     * @param name            the name of the bot
     * @param startPosition   the starting position of the bot
     * @param movement        the movement strategy for the bot
     * @param trackOperation  the track operation for the bot
     */
    public DefaultBot(String name, Position startPosition, Movement movement, TrackOperation trackOperation) {
        this.name = name;
        this.actualPosition = startPosition;
        this.previousMove = new Position(0, 0);
        this.movement = movement;
        this.trackOperation = trackOperation;
        this.eliminated = false;
    }

    @Override
    public void updatePosition(Position direction) {
        this.previousMove = new Position(direction.x() - this.actualPosition.x(),
                direction.y() - this.actualPosition.y());
        this.actualPosition = direction;
    }

    @Override
    public void calculateNextMoves() {
        Position mainPoint = movement.calculateMainPoint(actualPosition, previousMove);
        Position nextMainPoint1 = movement.calculateMainPoint(mainPoint, previousMove);
        Position nextMainPoint2 = movement.calculateMainPoint(nextMainPoint1, previousMove);
        if (actualPosition.equals(mainPoint) || !trackOperation.isValidPosition(mainPoint)) {
            trackOperation.executeNearbyMove(this);
            return;
        }
        Position newPosition = trackOperation.isValidAndPassable(mainPoint, nextMainPoint1, nextMainPoint2)
                ? movement.accelerate(mainPoint, previousMove)
                : movement.decelerate(mainPoint, previousMove);
        updatePosition(newPosition);
    }
    @Override
    public Movement getMovement() {
        return movement;
    }

    @Override
    public Position getPreviousMove() {
        return previousMove;
    }

    @Override
    public Position getCurrentPosition() {
        return this.actualPosition;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void isEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    @Override
    public boolean getEliminated() {
        return eliminated;
    }

}

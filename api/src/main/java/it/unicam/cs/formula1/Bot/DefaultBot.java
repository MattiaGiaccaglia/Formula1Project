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
import it.unicam.cs.formula1.Track.DefaultTrack;
import it.unicam.cs.formula1.TrackOperation.DefaultTrackOperation;
import it.unicam.cs.formula1.TrackOperation.TrackOperation;

import java.util.List;
import java.util.Random;

public class DefaultBot implements Bot {
    private String name;
    private Position actualPosition;
    private final DefaultMovement defaultMovement;
    private final TrackOperation trackOperation;
    private Position previousMove;

    public DefaultBot(String name, Position startPosition, DefaultTrack defaultTrack) {
        this.name = name;
        this.actualPosition = startPosition;
        this.defaultMovement = new DefaultMovement();
        this.trackOperation = new DefaultTrackOperation(defaultTrack);
        this.previousMove = new Position(0,0);
    }

    @Override
    public void updatePosition(Position direction) {
        this.previousMove = new Position(direction.x() - this.actualPosition.x(),
                direction.y() - this.actualPosition.y());
        this.actualPosition = direction;
    }

    @Override
    public void calculateNextMoves() {
        Position mainPoint = defaultMovement.calculateMainPoint(actualPosition, previousMove);
        Position nextMainPoint1 = defaultMovement.calculateMainPoint(mainPoint, previousMove);
        Position nextMainPoint2 = defaultMovement.calculateMainPoint(nextMainPoint1, previousMove);
        if (actualPosition.equals(mainPoint) || !trackOperation.isValidPosition(mainPoint)) {
            eseguiMossaVicina();
            return;
        }
        Position newPosition = trackOperation.isValidAndPassable(mainPoint, nextMainPoint1, nextMainPoint2)
                ? defaultMovement.accelerate(mainPoint, previousMove)
                : defaultMovement.decelerate(mainPoint, previousMove);
        updatePosition(newPosition);
    }
    private void eseguiMossaVicina() {
        Position mainPoint = defaultMovement.calculateMainPoint(actualPosition, previousMove);
        List<Position> mosseVicine = trackOperation.calculateNearbyMoves(actualPosition);
        // Filtra le mosse vicine direttamente nella lista originale
        mosseVicine.removeIf(mossa -> !trackOperation.calculateNearbyMoves(mainPoint).contains(mossa));
        Random random = new Random();
        this.updatePosition(mosseVicine.get(random.nextInt(mosseVicine.size())));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getActualPosition() {
        return actualPosition;
    }

    public void setActualPosition(Position actualPosition) {
        this.actualPosition = actualPosition;
    }

    public DefaultMovement getDefaultMovement() {
        return defaultMovement;
    }

    public TrackOperation getTrackOperation() {
        return trackOperation;
    }

    public Position getPreviousMove() {
        return previousMove;
    }

    public void setPreviousMove(Position previousMove) {
        this.previousMove = previousMove;
    }
}

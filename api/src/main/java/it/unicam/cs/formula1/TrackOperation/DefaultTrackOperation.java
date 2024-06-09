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

package it.unicam.cs.formula1.TrackOperation;
import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.Track.DefaultTrack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Implementa {@link TrackOperation} per fornire dettagli concreti su come
 * le operazioni di verifica e manipolazione delle posizioni su un defaultTrack vengono eseguite.
 *
 * @author Mattia Giaccaglia
 */
 public class DefaultTrackOperation implements TrackOperation {
    private DefaultTrack defaultTrack;

    /**
     * Costruisce una nuova istanza di OperazioniTracciato con il defaultTrack specificato.
     *
     * @param defaultTrack il defaultTrack su cui operare.
     */
    public DefaultTrackOperation(DefaultTrack defaultTrack){
        this.defaultTrack = defaultTrack;
    }

    @Override
    public boolean isValidAndPassable(Position mainPoint, Position mainPoint1, Position mainPoint2) {
        return isValidPosition(mainPoint) && isValidPosition(mainPoint1) &&
                checkPassableTrack(mainPoint, mainPoint1) &&
                checkPassableTrack(mainPoint1, mainPoint2);
    }

    @Override
    public boolean checkPassableTrack(Position start, Position arrive) {
        int xStart = start.x();
        int yStart = start.y();
        int xEnd = arrive.x();
        int yEnd = arrive.y();
        int dx = xEnd - xStart;
        int dy = yEnd - yStart;
        return IntStream.rangeClosed(0, Math.max(Math.abs(dx), Math.abs(dy)))
                .allMatch(i -> isValidPosition(new Position(xStart + i * Integer.signum(dx), yStart + i * Integer.signum(dy))));
    }

    @Override
    public List<Position> calculateNearbyMoves(Position position) {
        List<Position> mosseVicine = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++)
            for (int dy = -1; dy <= 1; dy++)
                if (dx != 0 || dy != 0) {
                    Position nuovoPunto = new Position(position.x() + dx, position.y() + dy);
                    if (isValidPosition(nuovoPunto))
                        mosseVicine.add(nuovoPunto);
                }
        return mosseVicine;
    }

    @Override
    public boolean isValidPosition(Position position) {
        int x = position.x();
        int y = position.y();
        int[][] track = defaultTrack.getTrack();
        return x >= 0 && y >= 0 && x < track.length && y < track[x].length && track[x][y] != 0;
    }

    public DefaultTrack getTracciato() {
        return defaultTrack;
    }

    public void setTracciato(DefaultTrack defaultTrack) {
        this.defaultTrack = defaultTrack;
    }
}

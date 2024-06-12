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

import it.unicam.cs.formula1.Bot.Bot;
import it.unicam.cs.formula1.Position.Position;
import it.unicam.cs.formula1.Track.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implements {@link TrackOperation} to provide concrete details on how
 * to check and manipulate positions on a {@link Track}.
 */
 public class DefaultTrackOperation implements TrackOperation {
    private final Track track;

    /**
     * Constructs a new instance of DefaultTrackOperation with the specified track
     *
     * @param track the track to operate on
     */
    public DefaultTrackOperation(Track track) {
        this.track = track;
    }

    @Override
    public boolean isValidAndPassable(Position mainPoint, Position mainPoint1, Position mainPoint2) {
        return isValidPosition(mainPoint) && isValidPosition(mainPoint1) &&
                checkPassableTrack(mainPoint, mainPoint1) &&
                checkPassableTrack(mainPoint1, mainPoint2);
    }

    @Override
    public boolean checkPassableTrack(Position start, Position arrive) {
        int dx = Math.abs(arrive.x() - start.x()), dy = Math.abs(arrive.y() - start.y());
        int steps = Math.max(dx, dy);

        for (int i = 1; i <= steps; i++) {
            int intermediateX = start.x() + i * Integer.signum(arrive.x() - start.x());
            int intermediateY = start.y() + i * Integer.signum(arrive.y() - start.y());
            if (!isValidPosition(new Position(intermediateX, intermediateY)))
                return false;
        }
        return true;
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
        int[][] trackLayout = track.getTrackLayout();
        return x >= 0 && y >= 0 && x < trackLayout.length && y < trackLayout[x].length && trackLayout[x][y] != 0;
    }

    @Override
    public void executeNearbyMove(Bot bot){
        Position mainPoint = bot.getMovement().calculateMainPoint(bot.getCurrentPosition(), bot.getPreviousMove());
        List<Position> mosseVicine = calculateNearbyMoves(bot.getCurrentPosition());
        mosseVicine.removeIf(mossa -> !calculateNearbyMoves(mainPoint).contains(mossa));
        Random random = new Random();
        bot.updatePosition(mosseVicine.get(random.nextInt(mosseVicine.size())));
    }

}

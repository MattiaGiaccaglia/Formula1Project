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

package it.unicam.cs.formula1.Movement;
import it.unicam.cs.formula1.Position.Position;

public class DefaultMovement implements Movement {
    private static final int MAX_SPEED = 4;
    private int speed;
    public DefaultMovement(){
        this.speed = 1;
    }

    @Override
    public Position accelerate(Position mainPoint, Position previousMove) {
        if (speed < MAX_SPEED) {
            speed++;  // Incrementa la velocità prima dell'uso
            return new Position(mainPoint.x() + Integer.signum(previousMove.x()), mainPoint.y() + Integer.signum(previousMove.y()));
        }
        return mainPoint;
    }

    @Override
    public Position decelerate(Position mainPoint, Position previousMove) {
        if (speed > 1) {
            speed--; // Decrementa la velocità
            return new Position(mainPoint.x() - Integer.signum(previousMove.x()), mainPoint.y() - Integer.signum(previousMove.y()));
        }
        return mainPoint;
    }

    @Override
    public Position calculateMainPoint(Position actualPosition, Position previousMove) {
        return new Position(actualPosition.x() + previousMove.x(),
                actualPosition.y() + previousMove.y());
    }
}

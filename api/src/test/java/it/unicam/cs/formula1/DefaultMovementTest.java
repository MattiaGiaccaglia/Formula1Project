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

import it.unicam.cs.formula1.Movement.DefaultMovement;
import it.unicam.cs.formula1.Position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultMovementTest {
    private DefaultMovement movement;

    @BeforeEach
    public void setUp() {
        movement = new DefaultMovement();
    }

    @Test
    public void testAccelerate() {
        Position actualPosition = new Position(4,0);
        Position previousMove = new Position(3, 0);
        Position mainPoint = movement.calculateMainPoint(actualPosition, previousMove);
        Position result = movement.accelerate(mainPoint, previousMove);
        assertEquals(new Position(8,0), result);
    }

    @Test
    public void testDecelerate() {
        Position actualPosition = new Position(4, 0);
        Position previousMove = new Position(3, 0);
        Position mainPoint = movement.calculateMainPoint(actualPosition, previousMove);
        movement.setSpeed(2);
        Position result = movement.decelerate(mainPoint, previousMove);
        assertEquals(new Position(6,0), result);
    }

    @Test
    public void testCalculateMainPoint() {
        Position actualPosition = new Position(2, 2);
        Position previousMove = new Position(1, 2);
        Position result = movement.calculateMainPoint(actualPosition, previousMove);
        assertEquals(new Position(3,4), result);
    }
}
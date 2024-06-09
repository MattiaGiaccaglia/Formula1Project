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

import java.util.List;

/**
 * Definisce le operazioni per manipolare e verificare posizioni su un tracciato.
 * Questa interfaccia fornisce un contratto per verificare la validità e percorribilità
 * di posizioni e tracciati, nonché per generare posizioni vicine a una data posizione.
 */
public interface TrackOperation {

    /**
     * Verifica se le posizioni specificate sono valide e se i tracciati tra esse sono percorribili.
     * Questo metodo controlla la validità delle posizioni e verifica la percorribilità del tracciato
     * tra una posizione e la sua successiva. In particolare, verifica la validità delle prime due posizioni
     * e controlla se il percorso tra la posizione principale e il primo punto successivo, e tra il primo
     * e il secondo punto successivo, è percorribile.
     *
     * @param mainPoint La posizione principale da cui iniziare il controllo.
     * @param mainPoint1 La prima posizione successiva da verificare dopo la posizione principale.
     * @param mainPoint2 La seconda posizione successiva da verificare.
     * @return {@code true} se tutte le posizioni sono valide e i tracciati tra di loro sono percorribili, altrimenti {@code false}.
     */
    boolean isValidAndPassable(Position mainPoint, Position mainPoint1, Position mainPoint2);

    /**
     * Verifica se un tracciato tra due posizioni è percorribile.
     * Il controllo viene effettuato verificando se tutte le posizioni tra la posizione di start e quella di arrive
     * sono valide e percorribili nel tracciato. Il metodo calcola il percorso direttamente connesso tra le due posizioni
     * utilizzando una linea retta e verifica ciascun punto lungo questa linea.
     *
     * @param start La posizione di start dalla quale iniziare il controllo.
     * @param arrive La posizione di arrive alla quale termina il controllo.
     * @return {@code true} se ogni posizione tra start e arrive è valida e percorribile, altrimenti {@code false}.
     */
    boolean checkPassableTrack(Position start, Position arrive);

    /**
     * Calcola e restituisce una lista di posizioni vicine valide alla position specificata.
     * Genera tutte le posizioni adiacenti (inclusi i punti diagonalmente adiacenti) alla position data,
     * escludendo la position stessa. Ogni position generata viene verificata per determinare se è valida
     * all'interno del tracciato. Solo le posizioni valide vengono aggiunte alla lista finale.
     *
     * @param position La position da cui calcolare gli otto vicini.
     * @return Una lista di {@link Position} che rappresentano tutte le posizioni valide intorno alla position data.
     */
    List<Position> calculateNearbyMoves(Position position);

    /**
     * Controlla se la position specificata è valida all'interno del tracciato.
     * Una position è considerata valida se si trova all'interno dei limiti dimensionali del tracciato
     * e il valore nella cella corrispondente del tracciato non è zero (0 indica una position non valida).
     *
     * @param position La position da verificare.
     * @return {@code true} se la position è dentro i limiti del tracciato e il punto non è marcato come non valido,
     *         altrimenti {@code false}.
     */
    boolean isValidPosition(Position position);
}

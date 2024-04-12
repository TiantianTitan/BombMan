/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go;

import fr.ubx.poo.ubomb.game.Direction;

// For GameObjects that can move
public interface Movable {
    boolean canMove(Direction direction);
    void doMove(Direction direction);
}

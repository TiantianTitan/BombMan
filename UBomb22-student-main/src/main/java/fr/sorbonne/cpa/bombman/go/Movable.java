
package fr.sorbonne.cpa.bombman.go;

import fr.sorbonne.cpa.bombman.game.Direction;

// For GameObjects that can move
public interface Movable {
    boolean canMove(Direction direction);
    void doMove(Direction direction);
}

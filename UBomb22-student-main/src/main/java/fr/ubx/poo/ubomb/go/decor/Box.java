package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.Movable;
import fr.ubx.poo.ubomb.go.Walkable;
import fr.ubx.poo.ubomb.launcher.Entity;

public class Box extends Decor {

    private boolean moveRequested = false;
    public Box(Position position) {
        super(position);
    }

    @Override
    public void explode() {
    }

    @Override
    public Entity getEntity() {
        return Entity.Box;
    }
}

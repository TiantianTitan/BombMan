package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.launcher.Entity;

public class Box extends Decor {

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

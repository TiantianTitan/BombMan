package fr.sorbonne.cpa.bombman.go.decor;

import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.launcher.Entity;

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

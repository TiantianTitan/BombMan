package fr.sorbonne.cpa.bombman.go.decor;

import fr.sorbonne.cpa.bombman.game.Game;
import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.go.GameObject;
import fr.sorbonne.cpa.bombman.launcher.Entity;

public abstract class Decor extends GameObject {

    private Entity entity = Entity.Empty;
    public Decor(Game game, Position position) {
        super(game, position);
    }

    public Decor(Position position) {
        super(position);
    }

    public Entity getEntity(){
        return this.entity;
    }

}
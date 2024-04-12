/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.decor;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.launcher.Entity;

public class Tree extends Decor {
    public Tree(Position position) {
        super(position);
    }

    @Override
    public Entity getEntity() {
        return Entity.Tree;
    }
}

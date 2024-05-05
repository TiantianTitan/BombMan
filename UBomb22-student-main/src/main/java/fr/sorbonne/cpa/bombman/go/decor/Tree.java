
package fr.sorbonne.cpa.bombman.go.decor;
import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.launcher.Entity;

public class Tree extends Decor {
    public Tree(Position position) {
        super(position);
    }

    @Override
    public Entity getEntity() {
        return Entity.Tree;
    }
}

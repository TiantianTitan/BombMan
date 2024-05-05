
package fr.sorbonne.cpa.bombman.go.decor;

import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.launcher.Entity;

public class Stone extends Decor {
    public Stone(Position position) {
        super(position);
    }

    @Override
    public Entity getEntity() {
        return Entity.Stone;
    }
}

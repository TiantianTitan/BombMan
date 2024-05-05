package fr.sorbonne.cpa.bombman.go.decor.bonus;

import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.launcher.Entity;
import fr.sorbonne.cpa.bombman.go.character.Player;

public class Princess extends Bonus {
        public Princess(Position position) {
            super(position);
        }

    @Override
    public void explode() {}

    @Override
    public void takenBy(Player player) {
        player.take(this);
    }

    @Override
    public Entity getEntity() {
        return Entity.Princess;
    }

}


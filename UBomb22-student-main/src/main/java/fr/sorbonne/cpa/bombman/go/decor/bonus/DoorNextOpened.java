package fr.sorbonne.cpa.bombman.go.decor.bonus;

import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.go.character.Player;
import fr.sorbonne.cpa.bombman.launcher.Entity;

public class DoorNextOpened extends Bonus {
    public DoorNextOpened(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        player.take(this);
    }

    @Override
    public Entity getEntity() {
        return Entity.DoorNextOpened;
    }

}

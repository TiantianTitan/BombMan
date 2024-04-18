package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.launcher.Entity;

public class DoorPrevOpened extends Bonus {
    public DoorPrevOpened(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        player.take(this);
    }

    @Override
    public Entity getEntity() {
        return Entity.DoorPrevOpened;
    }

}

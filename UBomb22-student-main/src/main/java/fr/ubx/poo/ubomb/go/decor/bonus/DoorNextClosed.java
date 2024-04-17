package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Takeable;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.launcher.Entity;

public class DoorNextClosed extends Bonus implements Takeable {
    private boolean opened = false;
    public DoorNextClosed(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        player.take(this);
    }

    @Override
    public Entity getEntity() {
        return Entity.DoorNextClosed;
    }
    @Override
    public boolean walkableBy(Player player) {
        boolean ret = false;
        if(opened) ret = true;
        return ret;
    }

    public void openDoor(){
        opened = true;
    }

    public boolean isOpened() {
        return opened;
    }
}

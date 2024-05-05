package fr.sorbonne.cpa.bombman.go.decor.bonus;

import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.go.character.Player;
import fr.sorbonne.cpa.bombman.launcher.Entity;
import fr.sorbonne.cpa.bombman.go.Takeable;

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

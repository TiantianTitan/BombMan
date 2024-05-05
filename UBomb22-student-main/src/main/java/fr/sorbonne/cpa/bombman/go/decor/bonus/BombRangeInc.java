package fr.sorbonne.cpa.bombman.go.decor.bonus;

import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.go.character.Player;
import fr.sorbonne.cpa.bombman.launcher.Entity;
import fr.sorbonne.cpa.bombman.view.ImageResource;

public class BombRangeInc extends  Bonus{

    public BombRangeInc(Position position) {
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
        return Entity.BombRangeInc;
    }

    @Override
    public ImageResource getImageResource() {
        return ImageResource.BONUS_BOMB_RANGE_INC;
    }

}

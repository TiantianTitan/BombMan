package fr.sorbonne.cpa.bombman.go.decor.bonus;

import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.go.character.Player;
import fr.sorbonne.cpa.bombman.launcher.Entity;
import fr.sorbonne.cpa.bombman.view.ImageResource;

public class BombRangeDec extends  Bonus{

    public BombRangeDec(Position position) {
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
        return Entity.BombRangeDec;
    }

    @Override
    public ImageResource getImageResource() {
        return ImageResource.BONUS_BOMB_RANGE_DEC;
    }
}

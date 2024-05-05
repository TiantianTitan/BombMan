package fr.sorbonne.cpa.bombman.go.decor.bonus;

import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.launcher.Entity;
import fr.sorbonne.cpa.bombman.view.ImageResource;
import fr.sorbonne.cpa.bombman.go.character.Player;

public class BombNumberDec extends  Bonus{

    public BombNumberDec(Position position) {
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
        return Entity.BombNumberDec;
    }

    @Override
    public ImageResource getImageResource() {
        return ImageResource.BONUS_BOMB_NB_DEC;
    }
}

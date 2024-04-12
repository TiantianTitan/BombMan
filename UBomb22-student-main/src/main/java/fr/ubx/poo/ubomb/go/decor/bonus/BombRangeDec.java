package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.launcher.Entity;
import fr.ubx.poo.ubomb.view.ImageResource;

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

package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.launcher.Entity;
import fr.ubx.poo.ubomb.view.ImageResource;

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

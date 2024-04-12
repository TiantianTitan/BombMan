package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.launcher.Entity;
import fr.ubx.poo.ubomb.view.ImageResource;
import fr.ubx.poo.ubomb.view.ImageResourceFactory;
import javafx.scene.image.ImageView;

public class BombNumberInc extends  Bonus{

    public BombNumberInc(Position position) {
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
        return Entity.BombNumberInc;
    }

    @Override
    public ImageResource getImageResource() {
        return ImageResource.BONUS_BOMB_NB_INC;
    }
}

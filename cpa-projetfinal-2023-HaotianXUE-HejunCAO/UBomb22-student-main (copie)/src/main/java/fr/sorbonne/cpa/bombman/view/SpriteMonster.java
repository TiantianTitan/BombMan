package fr.sorbonne.cpa.bombman.view;

import fr.sorbonne.cpa.bombman.go.decor.bonus.Monster;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpriteMonster extends Sprite {


    public SpriteMonster(Pane layer,Monster monster) {
        super(layer, null, monster);
        updateImage();
    }

    @Override
    public void updateImage() {
        Monster monster = (Monster) getGameObject();
        Image image = ImageResourceFactory.getMonster(monster.getDirection(),monster.isAttack()).getImage();
        setImage(image);
    }
}

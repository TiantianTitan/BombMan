/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.sorbonne.cpa.bombman.go.decor.bonus;

import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.view.ImageResource;
import fr.sorbonne.cpa.bombman.go.Takeable;
import fr.sorbonne.cpa.bombman.go.character.Player;
import fr.sorbonne.cpa.bombman.go.decor.Decor;

public abstract class Bonus extends Decor implements Takeable {
    public Bonus(Position position) {
        super(position);
    }

    @Override
    public boolean walkableBy(Player player) {
        return true;
    }

    @Override
    public void explode() {
        remove();
    }

    public ImageResource getImageResource(){
        return null;
    }

}

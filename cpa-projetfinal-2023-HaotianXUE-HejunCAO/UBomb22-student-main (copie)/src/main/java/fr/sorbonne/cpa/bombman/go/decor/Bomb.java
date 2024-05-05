package fr.sorbonne.cpa.bombman.go.decor;

import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.launcher.Entity;


public class Bomb extends Decor{

    private int second = 3;
    private boolean explode = false;
    public Bomb(Position position) {
        super(position);
    }

    @Override
    public void explode() {

    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public Entity getEntity() {
        return super.getEntity();
    }


    public boolean isExplode() {
        return explode;
    }

    public void setExplode(boolean explode) {
        this.explode = explode;
    }
}
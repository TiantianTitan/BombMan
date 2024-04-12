package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.launcher.Entity;


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
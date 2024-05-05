package fr.sorbonne.cpa.bombman.go.decor.bonus;

import fr.sorbonne.cpa.bombman.engine.Timer;
import fr.sorbonne.cpa.bombman.game.Direction;
import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.go.character.Player;
import fr.sorbonne.cpa.bombman.launcher.Entity;
import fr.sorbonne.cpa.bombman.go.TakeVisitor;

public class Monster extends Bonus implements TakeVisitor {
    private int lives;
    private Direction direction;
    private boolean attack = false;

    private Timer invisibility;

    private Bonus bonus;

    public Monster(Position position) {
        super(position);
        this.lives = 1;
        this.direction = Direction.random();
    }

    public Monster(Position position,int lives){
        super(position);
        this.lives = lives;
        this.direction = Direction.random();
    }


    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    @Override
    public void take(Bonus bonus) {
        if(bonus!=null) {
            if (bonus.getClass() == BombNumberInc.class) {
                bonus = new BombNumberInc(getPosition());
            }
            if (bonus.getClass() == BombNumberDec.class) {
                bonus = new BombNumberDec(getPosition());
            }
            if (bonus.getClass() == BombRangeInc.class) {
                bonus = new BombRangeInc(getPosition());
            }
            if (bonus.getClass() == BombRangeDec.class) {
                bonus = new BombRangeDec(getPosition());
            }
            bonus.remove();
        }
    }


    public Timer getInvisibility() {
        return invisibility;
    }

    public void setInvisibility(Timer invisibility) {
        this.invisibility = invisibility;
    }


    public void timeStart(){
        this.invisibility.start();
    }

    public void timeUpdate(long now){
        this.invisibility.update(now);
    }



    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    @Override
    public Entity getEntity() {
        return Entity.Monster;
    }

    @Override
    public void takenBy(Player player) {
        player.take(this);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives){
        this.lives = lives;
    }

    public Direction getDirection(){
        return direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }
    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
    }

}

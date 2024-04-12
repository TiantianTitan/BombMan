/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.engine.Timer;
import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.*;
import fr.ubx.poo.ubomb.go.TakeVisitor;
import fr.ubx.poo.ubomb.go.decor.Decor;
import fr.ubx.poo.ubomb.go.decor.bonus.*;

public class Player extends GameObject implements Movable, TakeVisitor {

    private Direction direction;
    private int scores;
    private boolean moveRequested = false;
    private int lives;
    // Création de recorder le nombre de key
    private int keys;
    private int bombs;
    private int range;
    private boolean bless = false;
    private boolean go_upstairs = false;
    private boolean go_downstairs = false;
    private final boolean player1;
    private boolean win = false;

    public boolean isPlayer1() {
        return player1;
    }

    private boolean invicility = false;
    private final long timeInvicility =game.configuration().playerInvisibilityTime();

    public Player(Game game, Position position, boolean player1) {
        super(game, position);
        this.player1 = player1;
        this.direction = Direction.DOWN;
        if(player1) {
            this.lives = game.configuration().playerLives();
            this.bombs = game.configuration().bombBagCapacity();
        }
        else {
            this.lives = game.configuration2().playerLives();
            this.bombs = game.configuration2().bombBagCapacity();
        }
        this.range = 1;
        this.scores = 50;
    }

    public boolean isBless() {
        return bless;
    }

    public void setBless(boolean bless) {
        this.bless = bless;
    }


    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public boolean isInvicility() {
        return invicility;
    }

    public void setInvicility(boolean invicility) {
        this.invicility = invicility;
    }

    public long getTimeInvicility() {
        return timeInvicility;
    }



    @Override
    public void take(Bonus bonus){
        if(bonus != null){
           if(bonus.getClass() == Heart.class){
               this.scores += 5;
               this.lives ++;
           }
           if(bonus.getClass() == Key.class){
               this.keys++;
           }
           if(bonus.getClass() == BombNumberInc.class){
               this.scores += 5;
               this.bombs++;
           }
           if(bonus.getClass() == BombNumberDec.class){
               this.scores -= 5;
               this.bombs--;
           }
           if(bonus.getClass() == BombRangeInc.class){
               this.scores += 5;
               this.range++;
           }
            if(bonus.getClass() == BombRangeDec.class){
                this.scores -= 5;
                this.range--;
            }
            if(bonus.getClass() == Monster.class){
                if(!isInvicility()) {
                    setInvicility(true);
                    bless = true;
                }
            }
            if(bonus.getClass() == Princess.class){
                this.win = true;
            }

           if(bonus.getClass() != Monster.class) bonus.remove();
        }
    }

    public void take(DoorNextOpened doorNextOpened) { go_upstairs =true;}
    public void take(DoorPrevOpened doorPrevOpenedOpened) {go_downstairs = true;}
    public void take(DoorNextClosed doorNextClosed) {
        if(doorNextClosed.isOpened()) go_upstairs =true;
    }


    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        Position nextPos = direction.nextPosition(getPosition());

        GameObject next = game.grid().get(nextPos);

        if(next != null) System.out.println("Player: " + (player1 ? "Player1" : "Player2") + " trying to move " + direction + " to " + nextPos + " walkable: " + (nextPos == null ? "null" : next.walkableBy(this)));
        else System.out.println("Player: " + (player1 ? "Player1" : "Player2") + " trying to move " + direction + " to " + nextPos);

        if (next instanceof Takeable takeable) {
                takeable.takenBy(this);
        }
        setPosition(nextPos);
    }


    public int getLives() {
        return lives;
    }

    public Direction getDirection() {
        return direction;
    }

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
            setModified(true);
        }
        moveRequested = true;
    }

    public final boolean canMove(Direction direction) {


        boolean ret = true;
        // Vérification de border
        boolean cantMoveBorder = (direction == Direction.LEFT && this.getPosition().x() == 0)|| (direction == Direction.UP && this.getPosition().y() == 0) || (direction == Direction.RIGHT && this.getPosition().x() == game.grid().width()-1) || (direction == Direction.DOWN && this.getPosition().y() == game.grid().height()-1);
        if(cantMoveBorder) ret = false;

        // Vérification de Decor
        Decor nextPos = game.grid().get(direction.nextPosition(getPosition()));
        boolean cantMoveDecor = nextPos!= null && (!nextPos.walkableBy(this));
        if(cantMoveDecor) ret = false;

        return ret;
    }

    public void update(long now) {
        if (moveRequested) {
            System.out.println("Player: " + (player1 ? "Player1" : "Player2") + " trying to move " + direction);
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }

    @Override
    public void explode() {
        return;
    }


    public int getKeys() {
        return keys;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }

    public int getBombs() {
        return bombs;
    }


    public int getRange() {
        return range;
    }

    public boolean isWin() {
        return win;
    }

    public boolean isGo_upstairs() {
        return go_upstairs;
    }

    public boolean isGo_downstairs() {
        return go_downstairs;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public void setRange(int range) {
        this.range = range;
    }
}

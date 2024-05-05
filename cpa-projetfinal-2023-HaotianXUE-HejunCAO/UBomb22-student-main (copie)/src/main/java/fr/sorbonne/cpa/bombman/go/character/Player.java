
package fr.sorbonne.cpa.bombman.go.character;

import fr.sorbonne.cpa.bombman.game.Direction;
import fr.sorbonne.cpa.bombman.game.Game;
import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.go.Movable;
import fr.sorbonne.cpa.bombman.go.Takeable;
import fr.sorbonne.cpa.bombman.go.decor.bonus.*;
import fr.sorbonne.cpa.bombman.go.GameObject;
import fr.sorbonne.cpa.bombman.go.TakeVisitor;
import fr.sorbonne.cpa.bombman.go.decor.Decor;

/**
 * Represents a player character in the game. This class includes attributes and methods to manage the player's
 * state, handle movement, interaction with various game objects, and track game-specific metrics like scores and lives.
 * It implements both {@link Movable} for movement functionalities and {@link TakeVisitor} for interacting with
 * takeable game objects.
 */
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



    private boolean itemToken ;

    public boolean isPlayer1() {
        return player1;
    }

    private boolean invicility = false;
    private final long timeInvicility =game.configuration().playerInvisibilityTime();

    public boolean isItemToken() {
        return itemToken;
    }
    public void setItemToken(boolean itemToken) {
        this.itemToken = itemToken;
    }

    /**
     * Constructs a Player with specified game context and initial position.
     *
     * @param game      Reference to the {@link Game} object for accessing game-wide properties and methods.
     * @param position  Initial position of the player in the game grid.
     * @param player1   Boolean flag to determine if this is the primary player (true) or a secondary player (false).
     */
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
        this.itemToken = false;
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


    /**
     * Handles the acquisition of a bonus by the player. Depending on the type of bonus,
     * the player's attributes such as lives, scores, bomb capacity, etc., are adjusted.
     *
     * @param bonus The bonus object that the player interacts with.
     */
    @Override
    public void take(Bonus bonus){
        if(bonus != null){

           if(bonus.getClass() == Heart.class){
               this.scores += 5;
               this.lives ++;
               this.itemToken = true;
           }
           if(bonus.getClass() == Key.class){
               this.keys++;
               this.itemToken = true;
           }
           if(bonus.getClass() == BombNumberInc.class){
               this.scores += 5;
               this.bombs++;
               this.itemToken = true;
           }
           if(bonus.getClass() == BombNumberDec.class){
               this.scores -= 5;
               this.bombs--;
               this.itemToken = true;
           }
           if(bonus.getClass() == BombRangeInc.class){
               this.scores += 5;
               this.range++;
               this.itemToken = true;
           }
            if(bonus.getClass() == BombRangeDec.class){
                this.scores -= 5;
                this.range--;
                this.itemToken = true;
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

    /**
     * Attempts to move the player in the specified direction if the movement is possible.
     * The movement is validated by checking against game grid boundaries and other potential obstacles.
     *
     * @param direction The direction in which to move the player.
     */
    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        Position nextPos = direction.nextPosition(getPosition());

        GameObject next = game.grid().get(nextPos);

      
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
    /**
     * Determines whether the player can move in the given direction.
     * This involves checking against grid boundaries and collision with non-walkable objects.
     *
     * @param direction The direction to check for possible movement.
     * @return true if movement is possible, false otherwise.
     */
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
    /**
     * Updates the player's state, handling movements and other periodic checks.
     *
     * @param now The current game time in milliseconds, used for handling timed behaviors.
     */
    public void update(long now) {
        if (moveRequested) {
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

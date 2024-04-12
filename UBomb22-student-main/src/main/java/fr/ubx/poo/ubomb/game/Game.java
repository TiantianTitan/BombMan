package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Player;

import java.util.LinkedList;
import java.util.List;

public class Game {

    private final Configuration configuration;
    private final Configuration configuration2 ;
    private Player player;
    private Player player2;
    private final Grid grid;
    public String[] stringMaps;
    private int currentLevel;

    public Game(Configuration configuration, Grid grid) {
        this.configuration = configuration;
        this.configuration2 = null;
        this.grid = grid;
        player = new Player(this, configuration.playerPosition(),true);
    }

    public Game(Configuration configuration,Configuration configuration2, Grid grid){
        this.configuration = configuration;
        this.configuration2 = configuration2;
        this.grid = grid;
        player = new Player(this,configuration.playerPosition(),true);
        player2 = new Player(this,configuration2.playerPosition(),false);
    }


    public Configuration configuration() {
        return configuration;
    }
    public Configuration configuration2() {
        return configuration2;
    }

    // Returns the player, monsters and bomb at a given position
    public List<GameObject> getGameObjects(Position position) {
        List<GameObject> gos = new LinkedList<>();
        if (player().getPosition().equals(position))
            gos.add(player);
        return gos;
    }

    public Grid grid() {
        return grid;
    }

    public Player player() {
        return this.player;
    }
    public Player player2(){ return this.player2; }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}

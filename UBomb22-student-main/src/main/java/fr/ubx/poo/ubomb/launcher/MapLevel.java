package fr.ubx.poo.ubomb.launcher;

import fr.ubx.poo.ubomb.game.Grid;

public class MapLevel {

    private final int width;
    private final int height;
    private final Entity[][] grid;

    public MapLevel(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Entity[height][width];
    }

    public int width() {
        return width;    }

    public int height() {
        return height;
    }

    public Entity get(int i, int j) {
        return grid[j][i];
    }

    public void set(int i, int j, Entity entity) {
        grid[j][i] = entity;
    }

}

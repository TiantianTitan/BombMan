package fr.ubx.poo.ubomb.launcher;

import static fr.ubx.poo.ubomb.launcher.Entity.*;

public class MapLevelDefault extends MapLevel {
    private final static Entity[][] level1 = {
            {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty},
            {Empty, Empty, Stone, Empty, Stone, Box, Stone, Stone, Stone, Stone, Empty, Empty},
            {Empty, Empty, Empty, BombRangeInc, Stone, Empty, Stone, Empty, Empty, Stone, Empty, Empty},
            {Empty, Empty, BombRangeDec, Empty, Stone, Empty, Stone, Empty, Empty, Stone, Empty, Empty},
            {Empty, BombNumberDec, Empty, Empty, Stone, Stone, Stone, Empty, Empty, Empty, Empty, Empty},
            {Empty, BombNumberInc, Empty, Empty, Empty, Empty, Empty, Key, Empty, Stone, Empty, Empty},
            {Empty, Tree, Empty, Tree, Empty, Empty, Empty, Empty, Empty, Stone, Monster, Empty},
            {Empty, Empty, Princess, Tree, Empty, Empty, Empty, Empty, Empty, Stone, Empty, Empty},
            {Empty, Tree, Tree, Tree, Empty, Empty, Empty, Empty, Empty, Stone, Empty, Empty},
            {Empty, Empty, Empty, Empty, Monster, Empty, Empty, Empty, Empty, Empty, Empty, Empty},
            {Stone, Stone, Stone, Stone, Stone, Empty, Empty, Empty, Stone, Stone, Empty, Stone},
            {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Monster, Empty, Empty, Empty, Empty},
            {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Monster, Empty}
    };
    private final static int width = 12;
    private final static int height = 13;

    public MapLevelDefault() {
        super(width, height);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                set(i, j, level1[j][i]);
    }
}

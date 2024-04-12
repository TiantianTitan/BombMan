package fr.ubx.poo.ubomb.launcher;

import static fr.ubx.poo.ubomb.launcher.Entity.*;
import static fr.ubx.poo.ubomb.launcher.Entity.Empty;

public class Map2Players extends  MapLevel{

    private final static Entity[][] level1 = {
            {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty,Empty,Empty,Empty,Empty,Empty},
            {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty,Empty,Empty,Empty,Empty,Empty},
            {Empty, Stone, Stone, Stone, Empty, Tree , Tree , Tree , Empty, Stone, Stone, Stone,Empty, Tree ,Tree ,Tree ,Empty},
            {Empty, Empty, Empty, Stone, Empty, Tree , Empty, Tree , Empty, Empty, Empty, Stone,Empty,Empty,Empty,Tree ,Empty},
            {Empty, Empty, Empty, Stone, Empty, Tree , Empty, Tree , Empty, Empty, Empty, Stone,Empty,Empty,Empty,Tree ,Empty},
            {Empty, Empty, Empty, Stone, Empty, Tree , Empty, Tree , Empty, Empty, Empty, Stone,Empty,Empty,Empty,Tree ,Empty},
            {Empty, Empty, Empty, Stone, Empty, Tree , Empty, Tree , Empty, Empty, Empty, Stone,Empty,Empty,Empty,Tree ,Empty},
            {Empty, Stone, Stone, Stone, Empty, Heart, Empty, Heart, Empty, Stone, Stone, Stone,Empty, Tree ,Tree ,Tree ,Empty},
            {Empty, Stone, Empty, Empty, Empty, Tree , Empty, Tree , Empty, Stone, Empty, Empty,Empty,Empty,Empty,Tree ,Empty},
            {Empty, Stone, Empty, Empty, Empty, Tree , Empty, Tree , Empty, Stone, Empty, Empty,Empty,Empty,Empty,Tree ,Empty},
            {Empty, Stone, Empty, Empty, Empty, Tree , Empty, Tree , Empty, Stone, Empty, Empty,Empty,Empty,Empty,Tree ,Empty},
            {Empty, Stone, Empty, Empty, Empty, Tree , Empty, Tree , Empty, Stone, Empty, Empty, Empty,Empty,Empty,Tree ,Empty},
            {Empty, Stone, Stone, Stone, Empty, Tree , Tree , Tree , Empty, Stone, Stone, Stone, Empty, Tree ,Tree ,Tree ,Empty},
            {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty,Empty,Empty,Empty,Empty,Empty},
            {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty,Empty,Empty,Empty,Empty,Empty},

    };
    private final static int width = 17;
    private final static int height = 15;

    public Map2Players() {
        super(width, height);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                set(i, j, level1[j][i]);
    }
}

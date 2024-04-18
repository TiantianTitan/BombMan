package fr.ubx.poo.ubomb.launcher;

import static fr.ubx.poo.ubomb.launcher.Entity.*;
import static fr.ubx.poo.ubomb.launcher.Entity.Empty;

public class Map2Players extends  MapLevel{

    private final static Entity[][] level1 = {
            {Empty, Empty, Box, Empty, Box, Box, Box, Box, Box, Box, Box, Box,Box,Box,Box,Box,Box},
            {Empty, Box, Box, Box, Box, Box, Box, Box, Box, Box, Box, Box,Box,Box,Box,Box,Box},
            {Box, Stone, Stone, Stone, Box, Tree , Tree , Tree , Box, Stone, Stone, Stone,Box, Tree ,Box ,Tree ,Box},
            {Box, Box, Box, Stone, Box, Tree , Box, Tree , Box, Box, Box, Stone,Box,Tree,Box,Tree ,Box},
            {Box, Box, Box, Stone, Box, Tree , Box, Tree , Box, Box, Box, Stone,Box,Tree,Box,Tree ,Box},
            {Box, Box, Box, Stone, Box, Tree , Box, Tree , Box, Box, Box, Stone,Box,Tree,Box,Tree ,Box},
            {Box, Box, Box, Stone, Box, Tree , Box, Tree , Box, Box, Box, Stone,Box,Tree,Box,Tree ,Box},
            {Box, Stone, Stone, Stone, Box, Heart, Box, Heart, Box, Stone, Stone, Stone,Box, Tree ,Tree ,Tree ,Box},
            {Box, Stone, Box, Box, Box, Tree , Box, Tree , Box, Stone, Box, Box,Box,Box,Box,Tree ,Box},
            {Box, Stone, Box, Box, Box, Tree , Box, Tree , Box, Stone, Box, Box,Box,Box,Box,Tree ,Box},
            {Box, Stone, Box, Box, Box, Tree , Box, Tree , Box, Stone, Box, Box,Box,Box,Box,Tree ,Box},
            {Box, Stone, Box, Box, Box, Tree , Box, Tree , Box, Stone, Box, Box, Box,Box,Box,Tree ,Box},
            {Box, Stone, Stone, Stone, Box, Tree , Tree , Tree , Box, Stone, Stone, Stone, Box, Box ,Box ,Tree ,Box},
            {Box, Box, Box, Box, Box, Box, Box, Box, Box, Box, Box, Box,Box,Box,Box,Box,Empty},
            {Box, Box, Box, Box, Box, Box, Box, Box, Box, Box, Box, Box,Box,Box,Box,Empty,Empty},

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

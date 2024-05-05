package fr.sorbonne.cpa.bombman.launcher;

public class Map2Players extends  MapLevel{

    private final static Entity[][] level1 = {
            {Entity.Empty, Entity.Empty, Entity.Box, Entity.Empty, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box},
            {Entity.Empty, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box},
            {Entity.Box, Entity.Stone, Entity.Stone, Entity.Stone, Entity.Box, Entity.Tree , Entity.Tree , Entity.Tree , Entity.Box, Entity.Stone, Entity.Stone, Entity.Stone, Entity.Box, Entity.Tree , Entity.Box , Entity.Tree , Entity.Box},
            {Entity.Box, Entity.Box, Entity.Box, Entity.Stone, Entity.Box, Entity.Tree , Entity.Box, Entity.Tree , Entity.Box, Entity.Box, Entity.Box, Entity.Stone, Entity.Box, Entity.Tree, Entity.Box, Entity.Tree , Entity.Box},
            {Entity.Box, Entity.Box, Entity.Box, Entity.Stone, Entity.Box, Entity.Tree , Entity.Box, Entity.Tree , Entity.Box, Entity.Box, Entity.Box, Entity.Stone, Entity.Box, Entity.Tree, Entity.Box, Entity.Tree , Entity.Box},
            {Entity.Box, Entity.Box, Entity.Box, Entity.Stone, Entity.Box, Entity.Tree , Entity.Box, Entity.Tree , Entity.Box, Entity.Box, Entity.Box, Entity.Stone, Entity.Box, Entity.Tree, Entity.Box, Entity.Tree , Entity.Box},
            {Entity.Box, Entity.Box, Entity.Box, Entity.Stone, Entity.Box, Entity.Tree , Entity.Box, Entity.Tree , Entity.Box, Entity.Box, Entity.Box, Entity.Stone, Entity.Box, Entity.Tree, Entity.Box, Entity.Tree , Entity.Box},
            {Entity.Box, Entity.Stone, Entity.Stone, Entity.Stone, Entity.Box, Entity.Heart, Entity.Box, Entity.Heart, Entity.Box, Entity.Stone, Entity.Stone, Entity.Stone, Entity.Box, Entity.Tree , Entity.Tree , Entity.Tree , Entity.Box},
            {Entity.Box, Entity.Stone, Entity.Box, Entity.Box, Entity.Box, Entity.Tree , Entity.Box, Entity.Tree , Entity.Box, Entity.Stone, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Tree , Entity.Box},
            {Entity.Box, Entity.Stone, Entity.Box, Entity.Box, Entity.Box, Entity.Tree , Entity.Box, Entity.Tree , Entity.Box, Entity.Stone, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Tree , Entity.Box},
            {Entity.Box, Entity.Stone, Entity.Box, Entity.Box, Entity.Box, Entity.Tree , Entity.Box, Entity.Tree , Entity.Box, Entity.Stone, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Tree , Entity.Box},
            {Entity.Box, Entity.Stone, Entity.Box, Entity.Box, Entity.Box, Entity.Tree , Entity.Box, Entity.Tree , Entity.Box, Entity.Stone, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Tree , Entity.Box},
            {Entity.Box, Entity.Stone, Entity.Stone, Entity.Stone, Entity.Box, Entity.Tree , Entity.Tree , Entity.Tree , Entity.Box, Entity.Stone, Entity.Stone, Entity.Stone, Entity.Box, Entity.Box , Entity.Box , Entity.Tree , Entity.Box},
            {Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Empty},
            {Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Box, Entity.Empty, Entity.Empty},

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

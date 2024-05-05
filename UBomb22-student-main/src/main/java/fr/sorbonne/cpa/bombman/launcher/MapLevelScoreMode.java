package fr.sorbonne.cpa.bombman.launcher;

public class MapLevelScoreMode extends  MapLevel{

    private final static Entity[][] level1 = {
            {Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty},
            {Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty},
            {Entity.Empty, Entity.Stone, Entity.Stone, Entity.Stone, Entity.Empty, Entity.Tree , Entity.Tree , Entity.Tree , Entity.Empty, Entity.Stone, Entity.Stone, Entity.Stone, Entity.Empty, Entity.Tree , Entity.Empty, Entity.Tree , Entity.Empty},
            {Entity.Empty, Entity.Empty, Entity.Empty, Entity.Stone, Entity.Empty, Entity.Tree , Entity.Empty, Entity.Tree , Entity.Empty, Entity.Empty, Entity.Empty, Entity.Stone, Entity.Empty, Entity.Tree, Entity.Empty, Entity.Tree , Entity.Empty},
            {Entity.Empty, Entity.Empty, Entity.Empty, Entity.Stone, Entity.Empty, Entity.Tree , Entity.Empty, Entity.Tree , Entity.Empty, Entity.Empty, Entity.Empty, Entity.Stone, Entity.Empty, Entity.Tree, Entity.Empty, Entity.Tree , Entity.Empty},
            {Entity.Empty, Entity.Empty, Entity.Empty, Entity.Stone, Entity.Empty, Entity.Tree , Entity.Empty, Entity.Tree , Entity.Empty, Entity.Empty, Entity.Empty, Entity.Stone, Entity.Empty, Entity.Tree, Entity.Empty, Entity.Tree , Entity.Empty},
            {Entity.Empty, Entity.Empty, Entity.Empty, Entity.Stone, Entity.Empty, Entity.Tree , Entity.Empty, Entity.Tree , Entity.Empty, Entity.Empty, Entity.Empty, Entity.Stone, Entity.Empty, Entity.Tree, Entity.Empty, Entity.Tree , Entity.Empty},
            {Entity.Empty, Entity.Stone, Entity.Stone, Entity.Stone, Entity.Empty, Entity.Heart, Entity.Empty, Entity.Heart, Entity.Empty, Entity.Stone, Entity.Stone, Entity.Stone, Entity.Empty, Entity.Tree, Entity.Tree, Entity.Tree , Entity.Empty},
            {Entity.Empty, Entity.Stone, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Tree , Entity.Empty, Entity.Tree , Entity.Empty, Entity.Stone, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Tree , Entity.Empty},
            {Entity.Empty, Entity.Stone, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Tree , Entity.Empty, Entity.Tree , Entity.Empty, Entity.Stone, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Tree , Entity.Empty},
            {Entity.Empty, Entity.Stone, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Tree , Entity.Empty, Entity.Tree , Entity.Empty, Entity.Stone, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Tree , Entity.Empty},
            {Entity.Empty, Entity.Stone, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Tree , Entity.Empty, Entity.Tree , Entity.Empty, Entity.Stone, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Tree , Entity.Empty},
            {Entity.Empty, Entity.Stone, Entity.Stone, Entity.Stone, Entity.Empty, Entity.Tree , Entity.Tree , Entity.Tree , Entity.Empty, Entity.Stone, Entity.Stone, Entity.Stone, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Tree , Entity.Empty},
            {Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty},
            {Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty, Entity.Empty},

    };
    private final static int width = 17;
    private final static int height = 15;

    public MapLevelScoreMode() {
        super(width, height);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                set(i, j, level1[j][i]);
    }

}












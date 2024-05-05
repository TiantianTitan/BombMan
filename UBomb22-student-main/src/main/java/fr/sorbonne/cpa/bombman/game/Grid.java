package fr.sorbonne.cpa.bombman.game;


import fr.sorbonne.cpa.bombman.go.decor.Decor;

import java.util.*;

public interface Grid {
    int width();
    int height();

    Decor get(Position position);

    void remove(Position position);

    Collection<Decor> values();


    boolean inside(Position nextPos);

    void set(Position position, Decor decor);
}

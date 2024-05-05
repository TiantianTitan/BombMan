package fr.sorbonne.cpa.bombman.launcher;

public interface MapRepo {

     MapLevel load(String string);

    String export(MapLevel mapLevel);
}

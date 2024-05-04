package fr.ubx.poo.ubomb.launcher;

import fr.ubx.poo.ubomb.game.Configuration;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Level;
import fr.ubx.poo.ubomb.game.Position;

public class GameLauncher {

    public static Game load() {
        Configuration configuration = new Configuration(new Position(0, 0), 2, 2, 4000, 5, 1000);
        return new Game(configuration, new Level(new MapLevelDefault()));
    }

    public static Game loadScore(){
        Configuration configuration = new Configuration(new Position(7, 7), 2, 2, 4000, 5, 1000);
        return new Game(configuration, new Level(new MapLevelScoreMode()));
    }


    public static Game load2Players(){
        Configuration configuration = new Configuration(new Position(0, 0), 2,2, 4000, 5, 1000);
        Configuration configuration2 = new Configuration(new Position(16,14),2,2,4000,5,1000);
        return new Game(configuration,configuration2, new Level(new Map2Players()));
    }


    public static MapLevel load(String string) {
        //find the width and height
        int width = getMapWidth(string);
        int height = getMapHeight(string);
        // Initialisation de map
        MapLevel mapLevel = new MapLevel(width,height);
        
        // Save the string in the grid of map
        StringBuffer unzip = new StringBuffer();
        int index =0;
        int count = 0;
        char tmp = ' ';
        for(int n =0; n<string.length();n++){
            if(string.charAt(index) == 'x'){
                index++;
                continue;
            }
            if(string.charAt(index)> '2' && string.charAt(index)<= '9') {
                count = string.charAt(index) - '0';
            }

            if(count == 0){
                unzip.append(string.charAt(index));
                tmp = string.charAt(index);
            }else{
                for (int i = 0; i < count-1; i++) {
                    unzip.append(tmp);
                }
            }

            count =0;
            index++;
        }

        String unzipString = unzip.toString();
        for(int j =0; j< height; j++){
            for(int i= 0; i < width; i++){
                mapLevel.set(i,j, Entity.fromCode(unzipString.charAt(j*width+i)));
            }
        }

        return mapLevel;
    }


    public static String export(MapLevel mapLevel) {

        StringBuffer stringBuffer = new StringBuffer();
        int width = mapLevel.width();
        int height = mapLevel.height();

        for(int j = 0; j < height; j++){
            for (int i = 0; i < width; i++){
                stringBuffer.append(mapLevel.get(i,j).getCode());
            }
            stringBuffer.append('x');
        }

        return stringBuffer.toString();
    }



    private static int getMapHeight(String property) {
        int count = 0;
        int length = property.length();
        for(int i = 0; i <length;i++) {
            if (property.charAt(i) == 'x') count++;
        }

        return count;
    }


    private static int getMapWidth(String property) {
        int count = 0;
        int index = 0;
        while(property.charAt(index) != 'x'){
            if(property.charAt(index) >'2' && property.charAt(index) <='9' ){
                count += Character.getNumericValue(property.charAt(index));
                count -= 2;
            }
            count++;
            index++;
        }
        return count;
    }
    
    
}

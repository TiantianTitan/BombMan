/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.engine;

import fr.ubx.poo.ubomb.game.*;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.Bomb;
import fr.ubx.poo.ubomb.go.decor.Box;
import fr.ubx.poo.ubomb.go.decor.Decor;
import fr.ubx.poo.ubomb.go.decor.bonus.*;
import fr.ubx.poo.ubomb.launcher.Entity;
import fr.ubx.poo.ubomb.launcher.GameLauncher;
import fr.ubx.poo.ubomb.launcher.MapLevel;
import fr.ubx.poo.ubomb.view.*;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;


public final class GameEngine {

    private static AnimationTimer gameLoop;
    private final Game game;
    private final Player player;
    private final Player player2;
    private final List<Sprite> sprites = new LinkedList<>();
    private final Set<Sprite> cleanUpSprites = new HashSet<>();
    private final Stage stage;
    private StatusBar statusBar;
    private final boolean modeScore;
    private final boolean mode2Players ;

    private Pane layer;
    private Input input;
    private final int currentLevel ;
    private boolean up = true;
    private  boolean down = true;

    private boolean levelLast = false;
    private boolean request = false;
    private boolean request2 = false;
    private boolean end = false;


    private final Timer timerMonster;
    private final Timer timePlayerBless;
    private Timer timePlayerBless2;
    private final Timer timeCreatMonster = new Timer(10000);
    private boolean ret;

    public GameEngine(Game game, final Stage stage, int currentLevel,boolean modeScore, boolean mode2Players) {
        this.stage = stage;
        this.game = game;
        this.modeScore = modeScore;
        this.mode2Players = mode2Players;
        game.setCurrentLevel(currentLevel);
        this.player = game.player();
        this.player2 = game.player2();
        this.currentLevel = currentLevel;
        // Initialisation of the monster's life, level ++, speed ++
        if(currentLevel == 1) {
            // Depends on monsterVelocity
            timerMonster = new Timer(game.configuration().monsterVelocity()* 1000L);
        }else {
            timerMonster = new Timer(game.configuration().monsterVelocity() * 1000L / currentLevel + 1000);
        }
        // Initialisation of the monster's life, level += 2, life += 1
        for (var monster : game.grid().values()){
            if(monster!= null && monster.getClass() == Monster.class){
                ((Monster) monster).setLives((currentLevel/2)+1);
                ((Monster) monster).setInvisibility(new Timer(game.configuration().monsterInvisibilityTime()));
            }
        }
        // Initialisation of playerInvicilityTime
        timePlayerBless = new Timer(player.getTimeInvicility());
        if(player2 != null) timePlayerBless2 = new Timer(player2.getTimeInvicility());

        initialize();
        buildAndSetGameLoop();
    }


    private void initialize() {
        Group root = new Group();
        layer = new Pane();

        int height = game.grid().height();
        int width = game.grid().width();
        int sceneWidth = width * ImageResource.size;
        int sceneHeight = height * ImageResource.size;
        Scene scene = new Scene(root, sceneWidth, sceneHeight + StatusBar.height);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

        stage.setScene(scene);
        stage.setResizable(true);
        stage.sizeToScene();
        stage.hide();
        stage.show();

        input = new Input(scene);
        root.getChildren().add(layer);
        statusBar = new StatusBar(root, sceneWidth, sceneHeight, game, modeScore,mode2Players);

        // Create sprites
        for (var decor : game.grid().values()) {
            if(decor.getClass() == Princess.class){
                levelLast = true;
            }

            if(decor.getClass() == DoorNextClosed.class){
                sprites.add(new SpriteClosedDoor(layer,ImageResource.DOOR_CLOSED.getImage(), (DoorNextClosed)decor));
            }else if(decor.getClass() == Monster.class){
                sprites.add(new SpriteMonster(layer,(Monster) decor));
            }
            else {
                sprites.add(SpriteFactory.create(layer, decor));
                decor.setModified(true);
            }
        }

        sprites.add(new SpritePlayer(layer, player));
        if(player2 != null) sprites.add(new SpritePlayer(layer, player2));
    }

    void buildAndSetGameLoop() {
        gameLoop = new AnimationTimer() {
            public void handle(long now) {
                // Check keyboard actions
                processInput(now);

                // Do actions
                update(now);


                // Graphic update
                cleanupSprites();
                render();
                statusBar.update(game);
            }
        };
    }



    private void animateExplosion(Position src, Position dst) {
        ImageView explosion = new ImageView(ImageResource.EXPLOSION.getImage());
        TranslateTransition tt = new TranslateTransition(Duration.millis(200), explosion);
        tt.setFromX(src.x() * Sprite.size);
        tt.setFromY(src.y() * Sprite.size);
        tt.setToX(dst.x() * Sprite.size);
        tt.setToY(dst.y() * Sprite.size);
        tt.setOnFinished(e -> {
            layer.getChildren().remove(explosion);
        });
        layer.getChildren().add(explosion);
        tt.play();
    }



    Queue<Map.Entry> queueMapTB = new LinkedList<>();
    Queue<Map.Entry> queueMapTB2 = new LinkedList<>();

    private void createNewBombs(Player player, long now) {

        Bomb bomb = new Bomb(player.getPosition());
        Timer timer = new Timer(4000);
        sprites.add(new SpriteBomb(layer,bomb));

        //setTimer
        Map.Entry<Timer,Bomb> entryTB = new AbstractMap.SimpleEntry<>(timer,bomb);
        queueMapTB.add(entryTB);

        timer.start();

        ImageView bombImage = new ImageView(ImageResourceFactory.getBomb(3).getImage());
        sprites.add(new Sprite(layer,bombImage.getImage(),bomb));
        game.grid().set(bomb.getPosition(),bomb);

    }



    private void checkAnimateExplosion(Bomb bomb, int range,long now){
        // If something bloque
        boolean onlyOneBloqueL = false;
        boolean onlyOneBloqueR = false;
        boolean onlyOneBloqueU = false;
        boolean onlyOneBloqueD = false;

        Position lastLeft = bomb.getPosition();
        Position lastRight = bomb.getPosition();
        Position lastUp = bomb.getPosition();
        Position lastDown = bomb.getPosition();
        for (int i = 1; i <= range; i++) {
            // Gestion left explode
            Position explodeLeft = new Position(bomb.getPosition().x() - i, bomb.getPosition().y());
            Decor bloqueL = game.grid().get(explodeLeft);
            if (!onlyOneBloqueL && bloqueL!= null) {
                // Check if it's the bomb
                if(bloqueL.getClass() == Bomb.class){
                    ((Bomb) bloqueL).setExplode(true);
                }
                if (disappear(bloqueL)) {
                    // Gestion of giving the monster lives
                    if (bloqueL.getClass() == Monster.class) {

                        if(!((Monster) bloqueL).getInvisibility().isRunning()){
                            ((Monster) bloqueL).timeStart();
                            ((Monster) bloqueL).setLives(((Monster) bloqueL).getLives()-1);
                        }else {
                            ((Monster) bloqueL).timeUpdate(now);
                        }



                        // If player hurt a monster score + 5
                        player.setScores(player.getScores()+5);
                        if (((Monster) bloqueL).getLives() == 0) {
                            bloqueL.remove();
                            // If player kill a monster score + 10
                            player.setScores(player.getScores()+10);
                        }

                    } else bloqueL.remove();
                }
                onlyOneBloqueL = true;
                // Bonus can't stop the explode
                if (passByExplosion(bloqueL)) onlyOneBloqueL = false;
            }
            if (!onlyOneBloqueL) lastLeft = explodeLeft;

            // Gestion Right explode
            Position explodeRight = new Position(bomb.getPosition().x() + i, bomb.getPosition().y());
            Decor bloqueR = game.grid().get(explodeRight);
            if (!onlyOneBloqueR && bloqueR != null) {
                if(bloqueR.getClass() == Bomb.class) ((Bomb) bloqueR).setExplode(true);

                if (disappear(bloqueR)) {
                    if ( bloqueR.getClass() == Monster.class) {
                        if(!((Monster) bloqueR).getInvisibility().isRunning()){
                            ((Monster) bloqueR).timeStart();
                            ((Monster) bloqueR).setLives(((Monster) bloqueR).getLives()-1);
                        }else {
                            ((Monster) bloqueR).timeUpdate(now);
                        }

                        // If player hurt a monster score + 5
                        player.setScores(player.getScores()+5);
                        if (((Monster) bloqueR).getLives() == 0) {
                            // If player kill a monster score + 10
                            player.setScores(player.getScores()+10);bloqueR.remove();
                        }
                    } else bloqueR.remove();
                }
                onlyOneBloqueR = true;
                if (passByExplosion(bloqueR)) onlyOneBloqueR = false;
            }
            if (!onlyOneBloqueR) lastRight = explodeRight;

            // Gestion Up explode
            Position explodeUp = new Position(bomb.getPosition().x(), bomb.getPosition().y() + i);
            Decor bloqueU = game.grid().get(explodeUp);
            if (!onlyOneBloqueU && bloqueU != null) {
                if(bloqueU.getClass() == Bomb.class){
                    ((Bomb) bloqueU).setExplode(true);
                }
                if (disappear(bloqueU)) {
                    if (bloqueU.getClass() == Monster.class) {
                        if(!((Monster) bloqueU).getInvisibility().isRunning()){
                            ((Monster) bloqueU).timeStart();
                            ((Monster) bloqueU).setLives(((Monster) bloqueU).getLives()-1);
                        }else {
                            ((Monster) bloqueU).timeUpdate(now);
                        }


                        // If player hurt a monster score + 5
                        player.setScores(player.getScores()+5);
                        if (((Monster) bloqueU).getLives() == 0) {
                            bloqueU.remove();
                            // If player kill a monster score + 10
                            player.setScores(player.getScores()+10);
                        }
                    } else bloqueU.remove();
                }
                onlyOneBloqueU = true;
                if (passByExplosion(bloqueU)) onlyOneBloqueU = false;
            }
            if (!onlyOneBloqueU) lastUp = explodeUp;

            // Gestion Down explode
            Position explodeDown = new Position(bomb.getPosition().x(), bomb.getPosition().y() - i);
            Decor bloqueD = game.grid().get(explodeDown);
            if (!onlyOneBloqueD && bloqueD != null) {
                if(bloqueD.getClass() == Bomb.class){
                    ((Bomb) bloqueD).setExplode(true);
                }
                if (disappear(bloqueD)) {
                    if (bloqueD.getClass() == Monster.class) {
                        if(!((Monster) bloqueD).getInvisibility().isRunning()){
                            ((Monster) bloqueD).timeStart();
                            ((Monster) bloqueD).setLives(((Monster) bloqueD).getLives()-1);
                        }else {
                            ((Monster) bloqueD).timeUpdate(now);
                        }


                        // If player hurt a monster score + 5
                        player.setScores(player.getScores()+5);
                        if (((Monster) bloqueD).getLives() == 0) {
                            bloqueD.remove();
                            // If player kill a monster score + 10
                            player.setScores(player.getScores()+10);
                        }

                    } else bloqueD.remove();
                }
                onlyOneBloqueD = true;
                if (passByExplosion(bloqueD)) onlyOneBloqueD = false;
            }
            if (!onlyOneBloqueD) lastDown = explodeDown;

        }

        // The explode coundn't explode outside of Edge
        if (lastLeft.x() >= 0) animateExplosion(bomb.getPosition(), lastLeft);
        else animateExplosion(bomb.getPosition(), new Position(0, lastLeft.y()));

        if (lastRight.x() <= game.grid().width() - 1) animateExplosion(bomb.getPosition(), lastRight);
        else animateExplosion(bomb.getPosition(), new Position(game.grid().width() - 1, lastRight.y()));

        if (lastUp.y() <= game.grid().height() - 1) animateExplosion(bomb.getPosition(), lastUp);
        else animateExplosion(bomb.getPosition(), new Position(lastUp.x(), game.grid().height() - 1));

        if (lastDown.y() >= 0) animateExplosion(bomb.getPosition(), lastDown);
        else animateExplosion(bomb.getPosition(), new Position(lastDown.x(), 0));

        // If player is in the range of explosion, his life will -1
        if(hurtPlayer(player.getPosition(),lastLeft.x(),lastRight.x(),bomb.getPosition().y(),lastUp.y(),lastDown.y(),bomb.getPosition().x())){
            if(!player.isInvicility()){
                player.setInvicility(true);
                player.setBless(true);
                player.setModified(true);
            }
        }
        if(player2!=null) {
            if(hurtPlayer(player2.getPosition(),lastLeft.x(),lastRight.x(),bomb.getPosition().y(),lastUp.y(),lastDown.y(),bomb.getPosition().x())){
                if(!player2.isInvicility()){
                    player2.setInvicility(true);
                    player2.setBless(true);
                    player2.setModified(true);
                }
            }
        }
    }


    private boolean hurtPlayer(Position playerPosition, int xleft, int xright,int y,  int yup, int ydown, int x) {
        // Check explosions of bombs hurt the player or not
        return (playerPosition.y() == y && playerPosition.x() >= xleft && playerPosition.x() <= xright) || (playerPosition.x() == x && playerPosition.y() >= ydown && playerPosition.y() <= yup);
    }


    private boolean passByExplosion(Decor bloque) {
        // explosion can pass the bonus but not monster not doors not decors
        return bloque.getClass() == Monster.class || bloque.getClass() == BombNumberInc.class || bloque.getClass() == BombNumberDec.class || bloque.getClass() == BombRangeInc.class || bloque.getClass() == BombRangeDec.class || bloque.getClass() == Heart.class;
    }



    private boolean disappear(Decor bloque) {
        return bloque != null && (bloque.getClass() == Box.class || bloque.getClass() == Monster.class || bloque.getClass() == BombNumberInc.class || bloque.getClass() == BombNumberDec.class || bloque.getClass() == BombRangeDec.class || bloque.getClass() == BombRangeInc.class || bloque.getClass() == Heart.class);
    }



    private void processInput(long now) {
        if (input.isExit()) {
            gameLoop.stop();
            Platform.exit();
            System.exit(0);
        } else if (input.isMoveDown()) {
            request = true;
            player.requestMove(Direction.DOWN);
        } else if (input.isMoveLeft()) {
            request = true;
            player.requestMove(Direction.LEFT);
        } else if (input.isMoveRight()) {
            request = true;
            player.requestMove(Direction.RIGHT);
        } else if (input.isMoveUp()) {
            request = true;
            player.requestMove(Direction.UP);
        }
        else if (input.isMoveUpPlayer2()) {
            if (player2 != null) {
                request2 = true;
                player2.requestMove(Direction.UP);
            }
        }
         else if (input.isMoveDownPlayer2()) {
            if(player2 != null) {
                request2 = true;
                player2.requestMove(Direction.DOWN);
            }
        }
        else if (input.isMoveLeftPlayer2()) {
            if(player2 != null) {
                request2 = true;
                player2.requestMove(Direction.LEFT);
            }
        }
        else if (input.isMoveRightPlayer2()) {
            if(player2 != null) {
                request2 = true;
                player2.requestMove(Direction.RIGHT);
            }
        }
        else if (input.isKey()){
            Decor nextPos = game.grid().get(player.getDirection().nextPosition(player.getPosition()));
            // if we have key and we want to open the closed door
            if(nextPos!= null && nextPos.getClass() == DoorNextClosed.class && player.getKeys() >0 ){

                ((DoorNextClosed) nextPos).openDoor();
                nextPos.setModified(true);

                // Use the key for open the door
                player.setKeys(player.getKeys()-1);
            }
        }else if(input.isBomb()&&/* package of bombs */(queueMapTB.size() < player.getBombs()) && /*put only at the empty*/  game.grid().get(player.getPosition()) == null ){
            createNewBombs(player,now);
        }else if(player2 != null && input.isBombPlayer2() && queueMapTB2.size() <player2.getBombs() && game.grid().get(player2.getPosition()) == null){
            createNewBombs(player2,now);
        }
        else if (input.isBomb() && end) {
            // tant qu'on gagne ou échoue, on tape ESPACE le jeu va se finir
            Platform.exit();
            System.exit(0);
        }
        input.clear();
    }

    private void showMessage(String msg, Color color) {
        Text waitingForKey = new Text(msg);
        waitingForKey.setTextAlignment(TextAlignment.CENTER);
        waitingForKey.setFont(new Font(25));
        waitingForKey.setFill(color);
        StackPane root = new StackPane();
        root.getChildren().add(waitingForKey);
        Scene scene = new Scene(root, 400, 200, Color.WHITE);
        stage.setScene(scene);
        input = new Input(scene);
        stage.show();
        new AnimationTimer() {
            public void handle(long now) {
                processInput(now);
            }
        }.start();
    }


    boolean during = false;

    private void update(long now) {

        // if player is hurted, he will change the color and get a time invicility
        if((!timePlayerBless.isRunning())&& player.isBless()){
            gestionBless();
        }

        if((player2!=null && !timePlayerBless2.isRunning())&& player2.isBless()){
            gestionBless2();
        }


        if(timePlayerBless.isRunning()){
            timePlayerBless.update(now);
        }else {
            player.setInvicility(false);
            player.setBless(false);
            player.setModified(true);

            Decor testMonster = game.grid().get(player.getPosition());
            if(testMonster != null && testMonster.getClass() == Monster.class){
               gestionBless();
            }
        }

        if(player2 != null){
            if(timePlayerBless2.isRunning()){
                timePlayerBless2.update(now);
            }else {
                player2.setInvicility(false);
                player2.setBless(false);
                player2.setModified(true);

                Decor testMonster = game.grid().get(player2.getPosition());
                if(testMonster != null && testMonster.getClass() == Monster.class){
                    gestionBless2();
                }
            }
        }




        Decor nextDecor = game.grid().get(player.getDirection().nextPosition(player.getPosition()));

        // The detail for moving the box, when we forward the box but we dont push, the box dont move
        if(nextDecor != null){
            if(request && nextDecor.getClass() != Box.class) {
                player.update(now);
                request = false;
            }
        }else {
            player.update(now);
            request = false;
        }

        if(player2 != null){
            Decor nextDecorPlayer2 = game.grid().get(player2.getDirection().nextPosition(player2.getPosition()));

            // The detail for moving the box, when we forward the box but we dont push, the box dont move
            if(nextDecorPlayer2 != null){
                if(request2 && nextDecorPlayer2.getClass() != Box.class) {
                    player2.update(now);
                    request2 = false;
                }
            }else {
                player2.update(now);
                request2 = false;
            }
        }

        // Implementation of losing
        if(mode2Players){
            if (player.getLives() == 0) {
                gameLoop.stop();
                showMessage("Winner : Player2!", Color.RED);
                end = true;
            }

            if (player2 != null && player2.getLives() == 0) {
                gameLoop.stop();
                showMessage("Winner : Player1!", Color.RED);
                end = true;
            }
        }
        else {
                if (player.getLives() == 0 || (modeScore && (player.getScores() < 0))) {
                gameLoop.stop();
                showMessage("Perdu!", Color.RED);
                end = true;
            }
        }

        // Implementation of winning
        if (player.isWin() || (modeScore && (player.getScores()>200))) {
            gameLoop.stop();
            showMessage("Victoir!", Color.BLUE);
            end = true;
        }

        // Implementation of going upstairs
        if(up && player.isGo_upstairs()){
            // load new level
            String string = game.stringMaps[currentLevel];
            Level levelLoad = new Level(GameLauncher.load(string));
            Game newGame = new Game(game.configuration(),levelLoad);
            // save ancient level
            int width = game.grid().width();
            int height = game.grid().height();
            MapLevel levelSave = new MapLevel(width,height);
            for(int j = 0; j <height; j++)
                for (int i = 0; i <width; i++){
                    Decor decor = game.grid().get(new Position(i,j));
                    if(decor == null){levelSave.set(i,j,Entity.Empty);}
                    else {
                        if(decor.getClass() == DoorNextClosed.class && ((DoorNextClosed) decor).isOpened()){
                            levelSave.set(i,j,Entity.DoorNextOpened);
                        }else  levelSave.set(i,j,decor.getEntity());
                    }
                }
            game.stringMaps[currentLevel-1] = GameLauncher.export(levelSave);

            // Initialisation of player position
            Position initialisationPlayer = new Position(0,0);
            if(string.indexOf('V') != -1){
                initialisationPlayer = levelLoad.getPlayerInitPositionPrev();
            }

            newGame.stringMaps = game.stringMaps;
            newGame.player().setPosition(initialisationPlayer);
            // Save the statistics of player
            newGame.player().setKeys(player.getKeys());
            newGame.player().setBombs(player.getBombs());
            newGame.player().setRange(player.getRange());
            newGame.player().setLives(player.getLives());

            GameEngine engine = new GameEngine(newGame,stage,currentLevel+1,false,false);
            engine.start();
            up = false;
        }

        // Implementation of going downstairs
        if(down && player.isGo_downstairs()){
            String string = game.stringMaps[currentLevel-2];
            Level level = new Level(GameLauncher.load(string));
            Game newGame = new Game(game.configuration(),level);
            // save ancient level
            int width = game.grid().width();
            int height = game.grid().height();
            MapLevel levelSave = new MapLevel(width,height);
            for(int j = 0; j <height; j++)
                for (int i = 0; i <width; i++){
                    Decor decor = game.grid().get(new Position(i,j));
                    if(decor == null){levelSave.set(i,j,Entity.Empty);}
                    else {
                        if(decor.getClass() == DoorNextClosed.class && ((DoorNextClosed) decor).isOpened()){
                            levelSave.set(i,j,Entity.DoorNextOpened);
                        }else  levelSave.set(i,j,decor.getEntity());
                    }
                }
            game.stringMaps[currentLevel-1] = GameLauncher.export(levelSave);


            Position initialisationPlayer = new Position(0,0);
            if(string.indexOf('N') != -1){
                initialisationPlayer = level.getPlayerInitPositionNext();
            }
            newGame.stringMaps = game.stringMaps;
            newGame.player().setPosition(initialisationPlayer);
            newGame.player().setKeys(player.getKeys());
            newGame.player().setBombs(player.getBombs());
            newGame.player().setRange(player.getRange());
            newGame.player().setLives(player.getLives());

            GameEngine engine = new GameEngine(newGame,stage,currentLevel-1,false,false);
            engine.start();
            down = false;
        }

        Decor nextPos = game.grid().get(player.getDirection().nextPosition(player.getPosition()));

        // Implementation of push the box
        if (request && nextPos != null && nextPos.getClass() == Box.class ) {
            Direction direction = player.getDirection();
            // La condition c'est s'il y a  de bonus ou decor derrière de caisse, il peut pas bouger
            boolean condition1 = game.grid().get(direction.nextPosition(nextPos.getPosition())) != null;
            // La condition c'est si la caisse est au bord du map ,il peut pas bouger
            boolean condition2 = direction.nextPosition(nextPos.getPosition()).x() < 0;
            boolean condition3 = direction.nextPosition(nextPos.getPosition()).y() < 0;
            boolean condition4 = direction.nextPosition(nextPos.getPosition()).x() > game.grid().width()-1;
            boolean condition5 = direction.nextPosition(nextPos.getPosition()).y() > game.grid().height()-1;
            boolean condition6 =
                    (direction.nextPosition(nextPos.getPosition()).x() == game.player().getPosition().x()
                            && direction.nextPosition(nextPos.getPosition()).y() == game.player().getPosition().y())
                            || (direction.nextPosition(nextPos.getPosition()).x() == game.player2().getPosition().x()
                            && direction.nextPosition(nextPos.getPosition()).y() == game.player2().getPosition().y()); // All the conditions
            boolean allCondition = condition1||condition2||condition3||condition4||condition5||condition6;
            if (!allCondition) {
                Position position = direction.nextPosition(nextPos.getPosition());
                Box box = new Box(position);

                // Add the box
                game.grid().set(position, box);
                sprites.add(SpriteFactory.create(layer, box));

                // Delete the box
                nextPos.remove();
                game.grid().remove(nextPos.getPosition());

                box.setModified(true);
            }
            request = false;
        }
        if(player2 != null){
            Decor nextPos2 = game.grid().get(player2.getDirection().nextPosition(player2.getPosition()));

            // Implementation of push the box
            if (request2 && nextPos2 != null && nextPos2.getClass() == Box.class ) {
                Direction direction = player2.getDirection();
                // La condition c'est s'il y a  de bonus ou decor derrière de caisse, il peut pas bouger
                boolean condition1 = game.grid().get(direction.nextPosition(nextPos2.getPosition())) != null;
                // La condition c'est si la caisse est au bord du map ,il peut pas bouger
                boolean condition2 = direction.nextPosition(nextPos2.getPosition()).x() < 0;
                boolean condition3 = direction.nextPosition(nextPos2.getPosition()).y() < 0;
                boolean condition4 = direction.nextPosition(nextPos2.getPosition()).x() > game.grid().width()-1;
                boolean condition5 = direction.nextPosition(nextPos2.getPosition()).y() > game.grid().height()-1;
                boolean condition6 =
                        (direction.nextPosition(nextPos2.getPosition()).x() == game.player().getPosition().x()
                        && direction.nextPosition(nextPos2.getPosition()).y() == game.player().getPosition().y())
                                || (direction.nextPosition(nextPos2.getPosition()).x() == game.player2().getPosition().x()
                                && direction.nextPosition(nextPos2.getPosition()).y() == game.player2().getPosition().y());
                // All the conditions
                boolean allCondition = condition1||condition2||condition3||condition4||condition5||condition6;
                if (!allCondition) {
                    Position position = direction.nextPosition(nextPos2.getPosition());
                    Box box = new Box(position);

                    // Add the box
                    game.grid().set(position, box);
                    sprites.add(SpriteFactory.create(layer, box));

                    // Delete the box
                    nextPos2.remove();
                    game.grid().remove(nextPos2.getPosition());

                    box.setModified(true);
                }
                request2 = false;
            }
        }



        // Implementation of Bomb by using the queue of entry<Timer,Bomb>

        checkQueueEntryTimer();
        operationCheckQueue(queueMapTB,this.player,now);
        if(player2 != null) operationCheckQueue(queueMapTB2,this.player2,now);
        checkQueueEntryTimer();

        // Implementation of moving of the monster
        if(!timerMonster.isRunning()){
            timerMonster.start();
            ret = true;
        }
        if(timerMonster.isRunning()){
            timerMonster.update(now);
            if(onceMove(timerMonster.isRunning())){
                if(levelLast || modeScore){
                    // Monsters move forward the player
                    forwardMoveMonster();
                }
                else{
                    randomMoveMonster();
                }

            }
        }


        if(modeScore){
            if(!timeCreatMonster.isRunning()){
                timeCreatMonster.start();
                // increase the speed of the born of the monster
                timeCreatMonster.setDuration((long) (timeCreatMonster.getDuration()*0.98));

                // Create random Monster
                int creatXMonster = (int) (game.grid().width()*Math.random());
                int creatYMonster = (int)(game.grid().height() * Math.random());
                while(game.grid().get(new Position(creatXMonster,creatYMonster)) != null) {
                    creatXMonster = (int) (game.grid().width() * Math.random());
                    creatYMonster = (int) (game.grid().height() * Math.random());
                }
                Position createPosition = new Position(creatXMonster,creatYMonster);
                Monster monster = new Monster(createPosition);
                monster.setInvisibility(new Timer(game.configuration().monsterInvisibilityTime()));
                game.grid().set(createPosition, monster);
                sprites.add(new SpriteMonster(layer,monster));

                // Create random Bonus or malus
                boolean bonusOuMalus = false;
                if(Math.random() <= 0.5) bonusOuMalus = true;

                int creatXBM = (int) (game.grid().width()*Math.random());
                int creatYBM = (int)(game.grid().height() * Math.random());
                while(game.grid().get(new Position(creatXBM,creatYBM)) != null) {
                    creatXBM = (int) (game.grid().width() * Math.random());
                    creatYBM = (int) (game.grid().height() * Math.random());
                }
                Position createPositionBM = new Position(creatXBM,creatYBM);

                if( bonusOuMalus){
                    Bonus bonus = BonusRandom.random().getElement(createPositionBM);
                    game.grid().set(createPositionBM,bonus);
                    sprites.add(new Sprite(layer,bonus.getImageResource().getImage(),bonus));

                }else {
                    Bonus malus = MalusRandom.random().getElement(createPositionBM);
                    game.grid().set(createPositionBM,malus);
                    sprites.add(new Sprite(layer,malus.getImageResource().getImage(), malus));

                }



            }
            else {
                timeCreatMonster.update(now);
            }
        }

    }

    private void operationCheckQueue(Queue<Map.Entry> queueMapTB,Player player, long now) {
        for(Map.Entry entry: queueMapTB){

            if(entry.getKey()!= null) {
                Timer timer = ((Timer)entry.getKey());
                Bomb bomb = (Bomb) entry.getValue();
                //the bomb explode immediately if else bomb explode to it
                if(bomb.isExplode()) {
                    timer.setRunning(false);
                }
                if (timer.isRunning()) {
                    timer.update(now);
                    if(timer.remaining()<3000 && timer.remaining() >= 2000){
                        bomb.setSecond(2);
                        bomb.setModified(true);
                    }
                    if(timer.remaining()<2000 && timer.remaining() >= 1000){
                        bomb.setSecond(1);
                        bomb.setModified(true);
                    }
                    if(timer.remaining()<1000 && timer.remaining() >=0){
                        bomb.setSecond(0);
                        bomb.setModified(true);
                    }
                }
                if(!timer.isRunning()){
                    checkAnimateExplosion(bomb, player.getRange(),now);
                    bomb.remove();
                }
            }
        }
    }

    private void gestionBless() {
        player.setModified(true);
        player.setBless(false);
        player.setLives(player.getLives()-1);
        player.setScores(player.getScores()-10);
        timePlayerBless.start();
    }

    private void gestionBless2() {
        player2.setModified(true);
        player2.setBless(false);
        player2.setLives(player2.getLives()-1);
        player2.setScores(player2.getScores()-10);
        timePlayerBless2.start();
    }



    private void checkQueueEntryTimer() {
        if(queueMapTB.peek() != null && !((Timer)queueMapTB.peek().getKey()).isRunning()){
            queueMapTB.remove();
        }
        if(queueMapTB2.peek() != null && !((Timer)queueMapTB2.peek().getKey()).isRunning()){
            queueMapTB2.remove();
        }
    }


    public void cleanupSprites() {
        sprites.forEach(sprite -> {
            if (sprite.getGameObject().isDeleted()) {
                game.grid().remove(sprite.getPosition());
                cleanUpSprites.add(sprite);
            }
        });
        cleanUpSprites.forEach(Sprite::remove);
        sprites.removeAll(cleanUpSprites);
        cleanUpSprites.clear();
    }

    private void render() {
        sprites.forEach(Sprite::render);
    }

    public void start() {
        gameLoop.start();
    }


    private boolean onceMove(boolean running) {
        if(ret != running){
            return true;
        }
        return false;
    }

    private void randomMoveMonster() {
        int height = game.grid().height();
        int width = game.grid().width();

        Monster[] allMonster = new Monster[height*width];
        int index = 0;
        for(var monster: game.grid().values()){
            if(monster != null && monster.getClass() == Monster.class){
                allMonster[index++] = (Monster) monster;
            }
        }

        Position nextPosition;

        // Random move all the monster
        for(Monster monster: allMonster){
            if(monster != null) {
                monster.setAttack(false);
                monster.setModified(true);
                Direction random = Direction.random();
                // If the monster can't move

                if (!(canMove(monster,Direction.UP) || canMove( monster, Direction.DOWN) || canMove( monster, Direction.LEFT) || canMove( monster, Direction.RIGHT))) {
                    continue;
                }
                while (!canMove(monster, random)) random = Direction.random();

                nextPosition = random.nextPosition(monster.getPosition());

                // If monster touch the player, life - 1
                if(nextPosition.x() == player.getPosition().x() && nextPosition.y() == player.getPosition().y()) {
                    if(!player.isInvicility()){
                        player.setInvicility(true);
                        player.setBless(true);
                    }
                }
                game.grid().remove(monster.getPosition());
                monster.setPosition(nextPosition);
                monster.setDirection(random);
                monster.setModified(true);
                game.grid().set(nextPosition,monster);


            }
        }
    }


    private boolean canMove(Monster monster, Direction direction) {
        // Pour judger le monster peut passer ou pas
        Decor nextPos = game.grid().get(direction.nextPosition(monster.getPosition()));

        if(direction == Direction.LEFT && monster.getPosition().x() == 0) return false;
        if(direction == Direction.UP && monster.getPosition().y() == 0) return false;
        if(direction == Direction.RIGHT && monster.getPosition().x() == game.grid().width()-1) return false;
        if(direction == Direction.DOWN && monster.getPosition().y() == game.grid().height()-1) return false;

        // Implémentation de la capabilité de passer
        boolean ret = true;
        if(nextPos !=null){
            if(nextPos.getClass() == BombNumberInc.class || nextPos.getClass() == BombNumberDec.class || nextPos.getClass() == BombRangeInc.class || nextPos.getClass() == BombRangeDec.class || nextPos.getClass() == Heart.class || nextPos.getClass() == Key.class){
                ret = true;
            }
            else ret = false;
        }
        return ret;
    }


    private void forwardMoveMonster(){
        int height = game.grid().height();
        int width = game.grid().width();

        Monster[] allMonster = new Monster[height*width];
        int index = 0;
        for(var monster: game.grid().values()){
            if(monster != null && monster.getClass() == Monster.class){
                allMonster[index++] = (Monster) monster;
            }
        }

        Position playerPosition = player.getPosition();
        Position nextPosition;

        // Move forward the player for all the monster
        for(Monster monster: allMonster){
            if(monster != null) {

                // For the forward if monster stay with player
                if(monster.getPosition().x() == player.getPosition().x() && monster.getPosition().y() == player.getPosition().y()){
                    continue;
                }

                // If the monster can't move
                if (!(canMove(monster,Direction.UP) || canMove( monster, Direction.DOWN) || canMove( monster, Direction.LEFT) || canMove( monster, Direction.RIGHT))) {
                    continue;
                }

                Direction newDirection;

                // mode attack
                if(areaMonster(monster,player)){
                    monster.setAttack(true);
                    // Gestion forward player
                    Direction forward = Direction.random();
                    if(monster.getPosition().x() != playerPosition.x()) {
                        if(monster.getPosition().x() < playerPosition.x()){
                            forward = Direction.RIGHT;
                        }else{
                            forward = Direction.LEFT;
                        }
                    }

                    if(monster.getPosition().y() != playerPosition.y()) {
                        if (monster.getPosition().y() < playerPosition.y()) {
                            forward = Direction.DOWN;
                        } else {
                            forward = Direction.UP;
                        }
                    }

                    if(!canMove(monster,forward)) continue;

                    nextPosition = forward.nextPosition(monster.getPosition());
                    newDirection = forward;

                }else{
                    // mode random
                    monster.setAttack(false);
                    Direction random = Direction.random();

                    while (!canMove(monster, random)) {
                        random = Direction.random();
                    }

                    nextPosition = random.nextPosition(monster.getPosition());
                    newDirection = random;
                }


                // If monster touch the player, life - 1
                if(nextPosition.x() == player.getPosition().x() && nextPosition.y() == player.getPosition().y()) {
                    if(!player.isInvicility()){
                        player.setInvicility(true);
                        player.setBless(true);
                    }
              }
                // Update the positions of monsters
                game.grid().remove(monster.getPosition());
                monster.setPosition(nextPosition);
                monster.setDirection(newDirection);
                monster.setModified(true);
                game.grid().set(nextPosition,monster);
            }
        }
    }

    private boolean areaMonster(Monster monster, Player player) {
        // BUG
        boolean attack = false;
        int area = 4;
        int mX = monster.getPosition().x() , mY = monster.getPosition().y();
        int pX = player.getPosition().x() , pY = player.getPosition().y();
        if((mX-pX <= area && mX - pX >= area*(-1)) && (mY-pY <= area && mY-pY >= (-1)* area)) attack = true;
        return attack;
    }

}
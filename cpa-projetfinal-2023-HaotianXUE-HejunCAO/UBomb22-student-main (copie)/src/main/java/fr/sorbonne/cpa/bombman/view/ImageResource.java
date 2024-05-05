package fr.sorbonne.cpa.bombman.view;

import javafx.scene.image.Image;


public enum ImageResource {
    // Only for the editor
    GROUND("ground.png"),

    STONE("stone.png"),
    TREE("tree.png"),
    DOOR_OPENED("door_opened.png"),
    DOOR_CLOSED("door_closed.png"),

    // Bonus
    HEART("heart.png"),
    KEY("key.png"),

    BONUS_BOMB_NB_INC("bonus_bomb_nb_inc.png"),
    BONUS_BOMB_NB_DEC("bonus_bomb_nb_dec.png"),
    BONUS_BOMB_RANGE_INC("bonus_bomb_range_inc.png"),
    BONUS_BOMB_RANGE_DEC("bonus_bomb_range_dec.png"),

    BOX("box.png"),

    // Player, princess and monsters
    PLAYER_UP("player_up.png"),
    PLAYER_RIGHT("player_right.png"),
    PLAYER_DOWN("player_down.png"),
    PLAYER_LEFT("player_left.png"),

    // Player2
    PLAYER2_UP("player2_up.png"),
    PLAYER2_RIGHT("player2_right.png"),
    PLAYER2_DOWN("player2_down.png"),
    PLAYER2_LEFT("player2_left.png"),


    // Player, hurted
    PLAYER_BLESS_UP("player_bless_up.png"),
    PLAYER_BLESS_DOWN("player_bless_down.png"),
    PLAYER_BLESS_LEFT("player_bless_left.png"),
    PLAYER_BLESS_RIGHT("player_bless_right.png"),

    PLAYER2_BLESS_UP("player2_bless_up.png"),
    PLAYER2_BLESS_DOWN("player2_bless_down.png"),
    PLAYER2_BLESS_LEFT("player2_bless_left.png"),
    PLAYER2_BLESS_RIGHT("player2_bless_right.png"),

    MONSTER_UP("monster_up.png"),
    MONSTER_RIGHT("monster_right.png"),
    MONSTER_DOWN("monster_down.png"),
    MONSTER_LEFT("monster_left.png"),
    // Monster attack
    MONSTER_ATTACK_UP("monster_attack_up.png"),
    MONSTER_ATTACK_DOWN("monster_attack_down.png"),
    MONSTER_ATTACK_LEFT("monster_attack_left.png"),
    MONSTER_ATTACK_RIGHT("monster_attack_right.png"),
    PRINCESS("princess.png"),

    // Bombs
    BOMB_0("bomb_0.png"),
    BOMB_1("bomb_1.png"),
    BOMB_2("bomb_2.png"),
    BOMB_3("bomb_3.png"),
    EXPLOSION("explosion.png"),

    // Status bar
    BANNER_BOMB("banner_bomb.png"),
    BANNER_RANGE("banner_range.png"),

    DIGIT_0("banner_0.jpg"),
    DIGIT_1("banner_1.jpg"),
    DIGIT_2("banner_2.jpg"),
    DIGIT_3("banner_3.jpg"),
    DIGIT_4("banner_4.jpg"),
    DIGIT_5("banner_5.jpg"),
    DIGIT_6("banner_6.jpg"),
    DIGIT_7("banner_7.jpg"),
    DIGIT_8("banner_8.jpg"),
    DIGIT_9("banner_9.jpg"),


    // Score
    SCORE("score.png"),

    // For the editor
    DOOR_OPENED_MINUS("door_opened_minus.png"),
    DOOR_OPENED_PLUS("door_opened_plus.png"),
    DOOR_CLOSED_MINUS("door_closed_minus.png"),
    DOOR_CLOSED_PLUS("door_closed_plus.png");


    private final Image image;

    public static final int size = 40;

    ImageResource(String file) {
        try {
            this.image = new Image(ImageResource.class.getResourceAsStream("/images/" + file));
            if (image.getWidth() != size && image.getHeight() != size) {
                String msg = "File " + file + " does not have the correct size " + image.getWidth() + " x " + image.getHeight();
                throw new RuntimeException (msg);
            }
        } catch (NullPointerException  e) {
            System.err.println("Resource not found : " + file);
            throw e;
        }
    }

    public Image getImage() {
        return image;
    }

}


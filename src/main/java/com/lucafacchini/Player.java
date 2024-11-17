package com.lucafacchini;

import java.awt.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Player extends Entity {

    // Debug & Logging
    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    // Center player on screen
    int screenX, screenY;

    public Player(WindowManager wm) {
        super(wm);
        speed = 10;
        screenX = wm.SCREEN_WIDTH / 2 - wm.TILE_SIZE / 2;
        screenY = wm.SCREEN_HEIGHT / 2 - wm.TILE_SIZE / 2;
        loadSprites("/sprites/player");
    }

    @Override
    public void draw(Graphics2D g2d) {
        switch(currentDirection) {
            case UP -> g2d.drawImage(sprites.get("up")[0], screenX, screenY, null);
            case DOWN -> g2d.drawImage(sprites.get("down")[0], screenX, screenY, null);
            case LEFT -> g2d.drawImage(sprites.get("left")[0], screenX, screenY, null);
            case RIGHT -> g2d.drawImage(sprites.get("right")[0], screenX, screenY, null);
        }
    }
}

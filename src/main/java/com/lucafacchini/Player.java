package com.lucafacchini;

import java.util.logging.Logger;

public class Player extends Entity {

    // Debug & Logging
    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    // Center player on screen
    int screenX, screenY;

    public Player(WindowManager wm) {
        super(wm);
        screenX = wm.SCREEN_WIDTH / 2 - wm.TILE_SIZE / 2;
        screenY = wm.SCREEN_HEIGHT / 2 - wm.TILE_SIZE / 2;
        loadSprites("/sprites/player/", 2, 2);
        setDefaultValues();
    }

    private void setDefaultValues() {
        speed = 5;
        worldX = wm.SCREEN_WIDTH / 2 - wm.TILE_SIZE / 2;
        worldY = wm.SCREEN_HEIGHT / 2 - wm.TILE_SIZE / 2;
    }
}

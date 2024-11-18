package com.lucafacchini;

import java.util.logging.Logger;

public class Player extends Entity {

    // Debug & Logging
    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    // Player's dimensions
    private final int HEIGHT = 19;
    private final int WIDTH = 11;
    private final int SCALED_HEIGHT = HEIGHT * wm.TILE_SCALE;
    private final int SCALED_WIDTH = WIDTH * wm.TILE_SCALE;

    // Center player on screen
    int screenX, screenY;

    public Player(WindowManager wm) {
        super(wm);
        screenX = wm.SCREEN_WIDTH / 2 - wm.TILE_SIZE / 2;
        screenY = wm.SCREEN_HEIGHT / 2 - wm.TILE_SIZE / 2;
        loadSprites("/sprites/player/", 2, 2);
        resizeSprites(SCALED_WIDTH, SCALED_HEIGHT);
        setDefaultValues();
    }

    private void setDefaultValues() {
        speed = 1;
        worldX = wm.TILE_SIZE * 25 - wm.TILE_SIZE; // Spawn at the center of the map
        worldY = wm.TILE_SIZE * 25 - wm.TILE_SIZE; // Spawn at the center of the map
        boundingBox.x = 0;
        boundingBox.y = 30;
        boundingBox.width = wm.TILE_SIZE - 20;
        boundingBox.height = wm.TILE_SIZE - 20;
    }
}

package com.lucafacchini;

import java.awt.*;
import java.awt.image.BufferedImage;
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
        loadSprites("/sprites/player/", 2, 2);
    }
}

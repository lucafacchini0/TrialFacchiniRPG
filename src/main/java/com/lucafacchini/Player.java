package com.lucafacchini;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Player extends Entity {

    // Debug & Logging
    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    public Player(WindowManager wm) {
        super(wm);
        speed = 10;
        loadSprites("/sprites/player");
    }
}

package com.lucafacchini;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.imageio.ImageIO;

public class Player extends Entity {

    // Debug & Logging
    private static final Logger LOGGER = Logger.getLogger(WindowManager.class.getName());

    WindowManager wm;
    KeyHandler kh;

    public Player(WindowManager wm, KeyHandler kh) {
        super();
        this.wm = wm;
        this.kh = kh;

        loadSprites();
    }

    public void loadSprites() {
        String[] directions = { "up", "down", "left", "right" };

        try {
            for(String direction : directions) {
                BufferedImage[] tempSprites = new BufferedImage[2];
                for(int i = 0; i < 2; i++) {
                    tempSprites[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/player/walk_" + direction + "_" + (i+1) + ".png")));
                }
                sprites.put(direction, tempSprites);
            }
        } catch(IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading player sprites.");
        }
    }

    public void update() {
        if(kh.isDownPressed) {
            currentDirection = Direction.DOWN;
            worldY += speed;
        }
        if(kh.isLeftPressed) {
            currentDirection = Direction.LEFT;
            worldX -= speed;
        }
        if(kh.isRightPressed) {
            currentDirection = Direction.RIGHT;
            worldX += speed;
        }
        if(kh.isUpPressed) {
            currentDirection = Direction.UP;
            worldY -= speed;
        }
    }

    public void draw(Graphics2D g2d) {
        switch(currentDirection) {
            case UP -> g2d.drawImage(sprites.get("up")[0], worldX, worldY, null);
            case DOWN -> g2d.drawImage(sprites.get("down")[0], worldX, worldY, null);
            case LEFT -> g2d.drawImage(sprites.get("left")[0], worldX, worldY, null);
            case RIGHT -> g2d.drawImage(sprites.get("right")[0], worldX, worldY, null);
        }
    }
}

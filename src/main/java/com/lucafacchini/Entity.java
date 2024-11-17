package com.lucafacchini;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Entity {

    // Debug & Logging
    private static final Logger LOGGER = Logger.getLogger(Entity.class.getName());

    // Entity attributes
    public int speed = 1;

    // Sprite settings
    public HashMap<String, BufferedImage[]> sprites = new HashMap<>();

    // Direction settings
    public enum Direction { UP, DOWN, LEFT, RIGHT }
    Direction currentDirection = Direction.DOWN;

    // Coordinates
    public int worldX = 0, worldY = 0;

    // Window manager reference
    WindowManager wm;

    // Utility
    Utility util = new Utility();

    public Entity(WindowManager wm) {
        this.wm = wm;
    }

    // Load sprites
    protected void loadSprites(String path) {
        String[] directions = { "up", "down", "left", "right" };

        try {
            for(String direction : directions) {
                BufferedImage[] tempSprites = new BufferedImage[2];
                for(int i = 0; i < 2; i++) {
                    tempSprites[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + "/walk_" + direction + "_" + (i+1) + ".png")));
                }
                sprites.put(direction, tempSprites);
            }
        } catch(IOException e) { LOGGER.log(Level.SEVERE, "Error loading player sprites."); }

        resizeSprites();
    }

    private void resizeSprites() {
        for(String direction : sprites.keySet()) {
            for(int i = 0; i < sprites.get(direction).length; i++) {
                sprites.get(direction)[i] = util.rescaleImage(sprites.get(direction)[i], wm.TILE_SIZE, wm.TILE_SIZE);
            }
        }
    }

    public void update(KeyHandler kh) {
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

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
    public int speed = 0;

    // Sprite settings
    protected enum SpriteDirection { WALK_UP, WALK_DOWN, WALK_LEFT, WALK_RIGHT, IDLE_UP, IDLE_DOWN, IDLE_LEFT, IDLE_RIGHT }
    public HashMap<SpriteDirection, BufferedImage[]> sprites = new HashMap<>();
    private final int SPRITE_FRAME_DELAY = 10;
    private int spriteFrameCounter = 0;
    protected int spriteIndex = 1;

    // Direction settings

    protected enum Direction { UP, DOWN, LEFT, RIGHT }
    protected enum State { IDLE, WALKING }

    Direction currentDirection = Direction.DOWN;
    State currentState = State.IDLE;

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
    protected void loadSprites(String path, int walkingSpritesNum, int idleSpritesNum) {
        SpriteDirection[] walkDirections = { SpriteDirection.WALK_UP, SpriteDirection.WALK_DOWN, SpriteDirection.WALK_LEFT, SpriteDirection.WALK_RIGHT };

        try {
            for(SpriteDirection direction : walkDirections) {
                BufferedImage[] walkSprites = new BufferedImage[walkingSpritesNum];
                for (int i = 0; i < walkingSpritesNum; i++) {
                    walkSprites[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + direction.name().toLowerCase() + "_" + (i + 1) + ".png")));
                }
                sprites.put(direction, walkSprites);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading walking sprites.", e);
        }

        SpriteDirection[] idleDirections = { SpriteDirection.IDLE_UP, SpriteDirection.IDLE_DOWN, SpriteDirection.IDLE_LEFT, SpriteDirection.IDLE_RIGHT };

        try {
            for(SpriteDirection direction : idleDirections) {
                BufferedImage[] idleSprites = new BufferedImage[idleSpritesNum];
                for (int i = 0; i < idleSpritesNum; i++) {
                    idleSprites[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path + direction.name().toLowerCase() + "_" + (i + 1) + ".png")));
                }
                sprites.put(direction, idleSprites);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading idle sprites.", e);
        }

        resizeSprites();
    }

    private void resizeSprites() {
        for(SpriteDirection direction : sprites.keySet()) {
            for(int i = 0; i < sprites.get(direction).length; i++) {
                sprites.get(direction)[i] = util.rescaleImage(sprites.get(direction)[i], wm.TILE_SIZE, wm.TILE_SIZE);
            }
        }
    }

    public void update(KeyHandler kh) {
        updateDirection(kh);
        updateSprite();

        if(currentState == State.WALKING) {
            switch(currentDirection) {
                case UP -> worldY -= speed;
                case DOWN -> worldY += speed;
                case LEFT -> worldX -= speed;
                case RIGHT -> worldX += speed;
            }
        }
    }

    private void updateDirection(KeyHandler kh) {
        if(kh.isDownPressed) { currentDirection = Direction.DOWN; currentState = State.WALKING; }
        if(kh.isLeftPressed) { currentDirection = Direction.LEFT; currentState = State.WALKING; }
        if(kh.isRightPressed) { currentDirection = Direction.RIGHT; currentState = State.WALKING; }
        if(kh.isUpPressed) { currentDirection = Direction.UP; currentState = State.WALKING; }
        if(!(kh.isDownPressed || kh.isLeftPressed || kh.isRightPressed || kh.isUpPressed)) { currentState = State.IDLE; }
    }

    private void updateSprite() {
        spriteFrameCounter++;


        if(spriteFrameCounter >= SPRITE_FRAME_DELAY) {
            switch(spriteIndex) {
                case 0 -> spriteIndex = 1;
                case 1 -> spriteIndex = 0;
            }
            spriteFrameCounter = 0;
        }


    }

    public void draw(Graphics2D g2d, boolean isPlayer, int screenX, int screenY) {
        BufferedImage currentSprite;

        if(currentState == State.WALKING) {
            switch(currentDirection) {
                case UP -> currentSprite = sprites.get(SpriteDirection.WALK_UP)[spriteIndex];
                case DOWN -> currentSprite = sprites.get(SpriteDirection.WALK_DOWN)[spriteIndex];
                case LEFT -> currentSprite = sprites.get(SpriteDirection.WALK_LEFT)[spriteIndex];
                case RIGHT -> currentSprite = sprites.get(SpriteDirection.WALK_RIGHT)[spriteIndex];
                default -> currentSprite = sprites.get(SpriteDirection.WALK_UP)[0];
            }
        } else {
            switch(currentDirection) {
                case UP -> currentSprite = sprites.get(SpriteDirection.IDLE_UP)[spriteIndex];
                case DOWN -> currentSprite = sprites.get(SpriteDirection.IDLE_DOWN)[spriteIndex];
                case LEFT -> currentSprite = sprites.get(SpriteDirection.IDLE_LEFT)[spriteIndex];
                case RIGHT -> currentSprite = sprites.get(SpriteDirection.IDLE_RIGHT)[spriteIndex];
                default -> currentSprite = sprites.get(SpriteDirection.IDLE_UP)[spriteIndex];
            }
        }

        if(!isPlayer) { g2d.drawImage(currentSprite, worldX, worldY, null); }
        else { g2d.drawImage(currentSprite, screenX, screenY, null); }
    }
}

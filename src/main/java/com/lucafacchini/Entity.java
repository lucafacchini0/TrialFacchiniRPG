package com.lucafacchini;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Entity {

    // Speed
    public int speed = 1;

    // Sprite settings
    public HashMap<String, BufferedImage[]> sprites = new HashMap<>();

    // Direction settings
    public enum Direction { UP, DOWN, LEFT, RIGHT }
    Direction currentDirection = Direction.DOWN;

    // Coordinates
    public int worldX, worldY;
}

package com.lucafacchini;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TileManager {

    // Debug & Logging
    private static final Logger LOGGER = Logger.getLogger(TileManager.class.getName());

    // Coordinates
    public static int worldX, worldY;
    public static int screenX, screenY;

    // Map management
    public HashMap<Integer, Tile> tileMap;
    public final int[][] GAME_MAP;

    // World Settings
    public final int WORLD_COLUMNS = 50;
    public final int WORLD_ROWS = 50;

    // Window manager reference
    WindowManager wm;

    // Utility (used for rescaling)
    Utility util = new Utility();

    public TileManager(WindowManager wm, String path) {
        this.wm = wm;
        GAME_MAP = new int[WORLD_ROWS][WORLD_COLUMNS];
        tileMap = new HashMap<>();

        loadMap(path);
        rescaleAllTileImages();

        // put tile 4 solid
        tileMap.get(4).isSolid = true;

        // Debug
        for (int row = 0; row < WORLD_ROWS; row++) {
            for (int col = 0; col < WORLD_COLUMNS; col++) {
                System.out.print(GAME_MAP[col][row] + " ");
            }
            System.out.println();
        }

    }

    // check if solid
    public boolean isSolid(int x, int y) {
        int col = x / wm.TILE_SIZE;
        int row = y / wm.TILE_SIZE;

        // Ensure indices are within bounds
        if (col < 0 || col >= WORLD_COLUMNS || row < 0 || row >= WORLD_ROWS) {
            return false; // Out of bounds means no collision
        }

        Tile tile = tileMap.get(GAME_MAP[col][row]);
        return tile != null && tile.isSolid;
    }



    public void loadMap(String filePath) {
        try {
            InputStream inputFile = getClass().getResourceAsStream(filePath);

            if(inputFile == null) { LOGGER.log(Level.SEVERE, "Error loading map file."); return; }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputFile));

            int currentWorldColumn = 0;
            int currentWorldRow = 0;

            while (currentWorldRow < WORLD_ROWS) {
                String line = reader.readLine();
                if (line == null) break; // Stop if no more lines

                String[] numbers = line.split(",");

                while (currentWorldColumn < WORLD_COLUMNS && currentWorldColumn < numbers.length) {
                    int number = Integer.parseInt(numbers[currentWorldColumn]);
                    GAME_MAP[currentWorldColumn][currentWorldRow] = number;
                    currentWorldColumn++;
                }
                currentWorldColumn = 0;
                currentWorldRow++;
            }
            reader.close();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        rescale();
    }

    // Method to load all tile images
    private void rescale() {
        for (int row = 0; row < WORLD_ROWS; row++) {
            for (int col = 0; col < WORLD_COLUMNS; col++) {
                int tileID = GAME_MAP[col][row];
                if (tileID != -1) {
                    loadTileImage(tileID);
                }
            }
        }
    }

    // Load images with the specified ID, if not already loaded
    private void loadTileImage(int id) {
        if (!tileMap.containsKey(id)) {
            try {
                String imagePath = "/tiles/tile_" + id + ".png";
                InputStream imageStream = getClass().getResourceAsStream(imagePath);

                if (imageStream == null) { LOGGER.log(Level.WARNING, "Tile image not found at path: " + imagePath); return; }

                Tile tile = new Tile();
                tile.image = ImageIO.read(imageStream);

                tileMap.put(id, tile);

            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error reading the tile image file for tile ID " + id, e);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Unexpected error while loading tile image for ID " + id, e);
            }
        }
    }

    private void rescaleAllTileImages() {
        for (Tile tile : tileMap.values()) {
            tile.image = util.rescaleImage(tile.image, wm.TILE_SIZE, wm.TILE_SIZE);
        }
    }

    public void draw(Graphics2D g2d) {
        int currentWorldColumn = 0;
        int currentWorldRow = 0;

        while (currentWorldColumn < WORLD_COLUMNS && currentWorldRow < WORLD_ROWS) {
            int currentTileIndex = GAME_MAP[currentWorldColumn][currentWorldRow];

            // Skip if the index is -1
            if (currentTileIndex == -1) {
                currentWorldColumn++;
                if (currentWorldColumn == WORLD_COLUMNS) {
                    currentWorldRow++;
                    currentWorldColumn = 0;
                }
                continue;
            }

            worldX = currentWorldColumn * wm.TILE_SIZE;
            worldY = currentWorldRow * wm.TILE_SIZE;
            screenX = worldX - wm.player.worldX + wm.player.screenX;
            screenY = worldY - wm.player.worldY + wm.player.screenY;

            // Draw the tile only if it's visible
            if (worldX + wm.TILE_SIZE > wm.player.worldX - wm.player.screenX &&
                    worldX - wm.TILE_SIZE < wm.player.worldX + wm.player.screenX &&
                    worldY + wm.TILE_SIZE > wm.player.worldY - wm.player.screenY &&
                    worldY - wm.TILE_SIZE < wm.player.worldY + wm.player.screenY) {

                Tile tile = tileMap.get(currentTileIndex);
                if (tile != null && tile.image != null) {
                    g2d.drawImage(tile.image, screenX, screenY, null);
                }
            }

            currentWorldColumn++;

            if (currentWorldColumn == WORLD_COLUMNS) {
                currentWorldRow++;
                currentWorldColumn = 0;
            }

            // Debug
            g2d.setColor(Color.pink);
            g2d.drawRect(screenX, screenY, wm.TILE_SIZE, wm.TILE_SIZE);
        }
    }
}

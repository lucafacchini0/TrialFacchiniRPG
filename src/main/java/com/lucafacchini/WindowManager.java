package com.lucafacchini;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;

public class WindowManager extends JPanel implements Runnable {

    // Tile settings
    public final int ORIGINAL_TILE_SIZE = 16;
    public final int TILE_SCALE = 4;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * TILE_SCALE;

    // Window settings
    public final int MAX_ROWS = 12;
    public final int MAX_COLUMNS = 16;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_COLUMNS;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_ROWS;

    public WindowManager() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        KeyHandler kh = new KeyHandler();
        this.addKeyListener(kh);
    }

    @Override
    public void run() {
    }
}

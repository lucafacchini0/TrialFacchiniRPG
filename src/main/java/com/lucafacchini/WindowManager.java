package com.lucafacchini;

import java.awt.*;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.swing.JPanel;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WindowManager extends JPanel implements Runnable {

    // Debug & Logging
    private static final Logger LOGGER = Logger.getLogger(WindowManager.class.getName());

    // Tile settings
    public final int ORIGINAL_TILE_SIZE = 16;
    public final int TILE_SCALE = 4;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * TILE_SCALE;

    // Window settings
    public final int MAX_ROWS = 12;
    public final int MAX_COLUMNS = 16;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_COLUMNS;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_ROWS;

    // Pause settings
    public boolean isPaused = false;

    // KeyHandler
    KeyHandler kh = new KeyHandler(this);

    // Entities
    public Player player = new Player(this, kh);

    public WindowManager() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        // Add key listener
        this.addKeyListener(kh);

        // Executor service for game loop
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this, 0, 16, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        update();
        repaint();
    }

    public void update() {
        if (!isPaused) {
            player.update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if(!isPaused) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            drawComponents(g2d);
            g2d.dispose();
        }
    }

    private void drawComponents(Graphics2D g2d) {
        player.draw(g2d);
    }
}
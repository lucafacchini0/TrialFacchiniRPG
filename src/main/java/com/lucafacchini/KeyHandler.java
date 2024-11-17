package com.lucafacchini;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    // Direction settings
    public boolean isLeftPressed = false;
    public boolean isRightPressed = false;
    public boolean isUpPressed = false;
    public boolean isDownPressed = false;

    // Reference to the window manager
    WindowManager wm;

    public KeyHandler(WindowManager wm) {
        this.wm = wm;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) { isLeftPressed = true; }
        if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) { isRightPressed = true; }
        if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) { isUpPressed = true; }
        if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) { isDownPressed = true; }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_ESCAPE) { wm.isPaused = !wm.isPaused; }
        if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) { isLeftPressed = false; }
        if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) { isRightPressed = false; }
        if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) { isUpPressed = false; }
        if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) { isDownPressed = false; }
    }
}

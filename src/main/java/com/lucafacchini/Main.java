package com.lucafacchini;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        WindowManager windowManager = new WindowManager();
        window.add(windowManager);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
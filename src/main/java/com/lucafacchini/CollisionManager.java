package com.lucafacchini;

public class CollisionManager {

    WindowManager wm;

    public CollisionManager(WindowManager wm) { this.wm = wm; }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.boundingBox.x;
        int entityRightWorldX = entityLeftWorldX + entity.boundingBox.width; // Cambiato per maggiore chiarezza
        int entityTopWorldY = entity.worldY + entity.boundingBox.y;
        int entityBottomWorldY = entityTopWorldY + entity.boundingBox.height; // Cambiato per maggiore chiarezza


        int entityLeftColumn = entityLeftWorldX / wm.TILE_SIZE;
        int entityRightColumn = entityRightWorldX / wm.TILE_SIZE;
        int entityTopRow = entityTopWorldY / wm.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / wm.TILE_SIZE;

        switch (entity.currentDirection) {
            case UP -> entityTopRow = (entityTopWorldY - entity.speed) / wm.TILE_SIZE;
            case DOWN -> entityBottomRow = (entityBottomWorldY + entity.speed) / wm.TILE_SIZE;
            case LEFT -> entityLeftColumn = (entityLeftWorldX - entity.speed) / wm.TILE_SIZE;
            case RIGHT -> entityRightColumn = (entityRightWorldX + entity.speed) / wm.TILE_SIZE;
        }

        int[] topTiles = {
                wm.firstLayerMap.GAME_MAP[entityLeftColumn][entityTopRow],
                wm.firstLayerMap.GAME_MAP[entityRightColumn][entityTopRow]
        };

        int[] bottomTiles = {
                wm.firstLayerMap.GAME_MAP[entityLeftColumn][entityBottomRow],
                wm.firstLayerMap.GAME_MAP[entityRightColumn][entityBottomRow]
        };

        if (isTileColliding(topTiles) || isTileColliding(bottomTiles)) {
            entity.isColliding = true;
        }
    }

    private boolean isTileColliding(int... tileNums) {
        for (int tileNum : tileNums) {
            if (tileNum >= 0 && isTileSolid(tileNum, wm.firstLayerMap)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTileSolid(int tileNum, TileManager layer) {
        return layer != null && layer.tileMap.get(tileNum) != null && layer.tileMap.get(tileNum).isSolid;
    }
}

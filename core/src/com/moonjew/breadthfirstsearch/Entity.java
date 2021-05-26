package com.moonjew.breadthfirstsearch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

public class Entity {
    public Vector2 position;
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;
    public static final int MOVESPEED = 200;

    public Entity(int x, int y){
        position = new Vector2(x, y);
    }

    public void update(Grid grid, float deltaTime){
//        grid.setStartNode((int)position.x, (int)position.y);
//        Queue<Tile> path = grid.breadthFirst();
//        move(path, deltaTime);
    }

    public void render(ShapeRenderer renderer){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLUE);
        renderer.rect(position.x, position.y, WIDTH, HEIGHT);
        renderer.end();
    }

    public void move(Queue<Tile> path, float deltaTime){
        if(path != null) {
            Tile nextPosition = path.removeFirst();
            position.x += nextPosition.x * deltaTime * MOVESPEED;
            position.y += nextPosition.y * deltaTime * MOVESPEED;
        }
    }

    public void moveRight(float deltaTime){
        position.x += MOVESPEED * deltaTime;
    }
    public void moveTo(Tile tile, float deltaTime){
        Tile pos = getTileFromPosition();

        if(pos.x > tile.x){
            position.x -= MOVESPEED * deltaTime;
        } else if (pos.x < tile.x){
            position.x += MOVESPEED * deltaTime;
        }
        if(pos.y > tile.y){
            position.y -= MOVESPEED * deltaTime;
        } else if (pos.y < tile.y){
            position.y += MOVESPEED * deltaTime;
        }
    }

    public Tile getTileFromPosition(){
        int x = (int) (position.x / Grid.TILE_WIDTH);
        int y = (int) (position.y / Grid.TILE_HEIGHT);
        return new Tile(x, y);
    }
}

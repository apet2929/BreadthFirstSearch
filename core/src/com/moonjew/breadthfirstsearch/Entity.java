package com.moonjew.breadthfirstsearch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

public class Entity {
    public Vector2 position;
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    public static final int MOVESPEED = 20;

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
}

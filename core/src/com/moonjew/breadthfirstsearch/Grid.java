package com.moonjew.breadthfirstsearch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Queue;

public class Grid {
    public static final int TILE_WIDTH = 40;
    public static final int TILE_HEIGHT = 40;

    public static final int MAP_WIDTH = Main.SCREEN_WIDTH / TILE_WIDTH;
    public static final int MAP_HEIGHT = Main.SCREEN_HEIGHT / TILE_HEIGHT;

    private Tile[][] tiles;
    private Tile start;
    private Tile end;
    private boolean[][] visited;
    private Queue<Tile> queue;
    private Queue<Tile> queueBuffer;
    private Queue<Tile> path;
    boolean visitedAll;

    public Grid(int startX, int startY, int endX, int endY) {

        tiles = new Tile[MAP_WIDTH][MAP_HEIGHT];
        visited = new boolean[MAP_WIDTH][MAP_HEIGHT];
        visitedAll = false;
        for(int i = 0; i < MAP_WIDTH; i++){
            for(int j = 0; j < MAP_HEIGHT; j++){
                tiles[i][j] = new Tile(i, j);
                visited[i][j] = false;
            }
        }
        end = tiles[endX][endY];
        end.type = Tile.Type.END;

        start = tiles[startX][startY];
        start.type = Tile.Type.START;
        queue = new Queue<>();
        queueBuffer = new Queue<>();
        addToQueue(start);
        tiles[10][10].type = Tile.Type.WALL;
    }

    public void render(ShapeRenderer renderer){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        if(visited != null){
            renderer.setColor(Color.PURPLE);
            for(int i = 0; i < visited.length; i++){
                for(int j = 0; j < visited[i].length; j++){
                    if(visited[i][j]){
                        drawRect(renderer, tiles[i][j]);
                    }
                }
            }
        }
        if(path != null){
            renderer.setColor(Color.BROWN);
            for(Tile t : path){
                drawRect(renderer, t);
            }
        }

        for(Tile[] tileRay : tiles){
            for(Tile tile: tileRay){
                if (tile.type == Tile.Type.START) {
                    renderer.set(ShapeRenderer.ShapeType.Filled);
                    renderer.setColor(Color.GREEN);
                    drawRect(renderer, tile);
                }
                else if (tile.type == Tile.Type.END) {
                    renderer.set(ShapeRenderer.ShapeType.Filled);
                    renderer.setColor(Color.RED);
                    drawRect(renderer, tile);
                }
                else if (tile.type == Tile.Type.WALL) {
                    renderer.set(ShapeRenderer.ShapeType.Filled);
                    renderer.setColor(Color.BLACK);
                    drawRect(renderer, tile);
                }

                renderer.set(ShapeRenderer.ShapeType.Line);
                renderer.setColor(Color.BLACK);
                renderer.rect(tile.x * TILE_WIDTH, tile.y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
            }
        }
        renderer.end();
    }

    public void addWall(int x, int y) {
        tiles[x][y].type = Tile.Type.WALL;
    }

    public Queue<Tile> breadthFirst(){
        if(!queue.isEmpty()) {
            while(queue.notEmpty()){
                Tile tile = queue.removeFirst();
                if (checkFinished(tile) != null) {
                    System.out.println("Goal found");
                    return path;
                }
                searchEdges(tile);
            }
            for(Tile tile : queueBuffer){
                queue.addFirst(tile);
            }
            queueBuffer = new Queue<>();
        }
        else {
            System.out.println("The end and start tiles are not connected!");
        }
        return null;
    }

    public Queue<Tile> checkFinished(Tile current){
        if (current.type == Tile.Type.END) {
            path = new Queue<>();
            boolean recounting = true;
            Tile p = current;
            path.addFirst(current);
            while (recounting) {
                path.addFirst(p.parent);
                p = p.parent;
                if (p.type == Tile.Type.START) {
                    recounting = false;
                }
            }
            System.out.println(path);
            return path;
        }

        return null;
    }

    public void searchEdges(Tile tile){
        Tile leftEdge = getTile(tile.x-1, tile.y);
        Tile rightEdge = getTile(tile.x+1, tile.y);
        Tile upEdge = getTile(tile.x, tile.y+1);
        Tile downEdge = getTile(tile.x, tile.y-1);

        if(leftEdge != null){
            if(!isVisited(leftEdge)&& leftEdge.type != Tile.Type.WALL) {
                visit(leftEdge);
                leftEdge.parent = tile;
            }
        }
        if(rightEdge != null){
            if(!isVisited(rightEdge)&& rightEdge.type != Tile.Type.WALL){
                visit(rightEdge);
                rightEdge.parent = tile;
            }
        }
        if(upEdge != null){
            if(!isVisited(upEdge) && upEdge.type != Tile.Type.WALL){
                visit(upEdge);
                upEdge.parent = tile;
            }
        }
        if(downEdge != null){
            if(!isVisited(downEdge)&& downEdge.type != Tile.Type.WALL){
                visit(downEdge);
                downEdge.parent = tile;
            }
        }

        //diagonals
        Tile leftDownEdge = getTile(tile.x-1, tile.y-1);
        Tile rightDownEdge = getTile(tile.x+1, tile.y-1);
        Tile leftUpEdge = getTile(tile.x-1, tile.y+1);
        Tile rightUpEdge = getTile(tile.x+1, tile.y+1);
        if(leftDownEdge != null){
            if(!isVisited(leftDownEdge) && leftDownEdge.type != Tile.Type.WALL){
                visit(leftDownEdge);
                leftDownEdge.parent = tile;
            }
        }
        if(leftUpEdge != null){
            if(!isVisited(leftUpEdge)&& leftUpEdge.type != Tile.Type.WALL){
                visit(leftUpEdge);
                leftUpEdge.parent = tile;
            }
        }
        if(rightDownEdge != null){
            if(!isVisited(rightDownEdge)&& rightDownEdge.type != Tile.Type.WALL) {
                visit(rightDownEdge);
                rightDownEdge.parent = tile;
            }
        }
        if(rightUpEdge != null){
            if(!isVisited(rightUpEdge)&& rightUpEdge.type != Tile.Type.WALL){
                visit(rightUpEdge);
                rightUpEdge.parent = tile;
            }
        }

    }

    public void drawRect(ShapeRenderer renderer, Tile tile){
        renderer.rect(tile.x * TILE_WIDTH, tile.y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
    }
    public boolean isValid(int x, int y){
        boolean inBounds = x < MAP_WIDTH && x >= 0 && y < MAP_HEIGHT && y >= 0;
        return inBounds && !visited[x][y];
    }
    public Tile getTile(int x, int y){
        if(isValid(x,y)) return tiles[x][y];
        return null;
    }

    public void addToQueue(Tile tile){
        queue.addFirst(tile);
        visited[tile.x][tile.y] = true;
    }

    public boolean isVisited(Tile tile){
        return visited[tile.x][tile.y];
    }

    public void visit(Tile tile){
        visited[tile.x][tile.y] = true;
        queueBuffer.addFirst(tile);
    }

    public void printQueue(){
        System.out.println(queue);
    }

}

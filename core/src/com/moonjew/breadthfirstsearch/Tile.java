package com.moonjew.breadthfirstsearch;

public class Tile {
    public int x, y;
    public Type type;
    public Tile parent;

    enum Type {
        EMPTY,
        WALL,
        START,
        END
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = Type.EMPTY;
    }

    @Override
    public String toString() {
        if(this.parent != null) {
            return "Tile @ x: " + x + " y: " + y + " Parent x: " + parent.x + " Parent y: " + parent.y + "\n";
        } else {
            return "Tile @ x: " + x + " y: " + y + "\n";
        }
    }
}

package com.moonjew.breadthfirstsearch;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 800;

	SpriteBatch batch;
	Grid grid;
	boolean run;
	boolean found;
	ShapeRenderer renderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		grid = new Grid(25, 25, 20, 20);
		run = false;
		found = false;
		renderer = new ShapeRenderer();
		renderer.setAutoShapeType(true);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);

		if(Gdx.input.justTouched()){
			int mouseX = Gdx.input.getX();
			int mouseY = Gdx.input.getY();
			int gridX = mouseX / Grid.TILE_WIDTH;
			int gridY = mouseY / Grid.TILE_HEIGHT;
			grid.addWall(gridX,39 - gridY);
		}

		if((Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT) || run) && !found) {
			found = grid.breadthFirst() != null;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.I)) {
			grid.printQueue();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			run = true;
		}

		grid.render(renderer);

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}

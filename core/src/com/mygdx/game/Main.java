package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {

	Game game;

	static Main main;

	public static Main getInstance(){
		if (main==null){
			main = new Main();
			return main;
		}
		else {
			return main;
		}
	}
	private Main(){}
	@Override
	public void create () {

		game = new Game();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		game.render();
	}
	
	@Override
	public void dispose () {

	}
}

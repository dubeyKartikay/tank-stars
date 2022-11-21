package com.mygdx.Screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Game;

public abstract class GameScreen implements Screen {
    private Game game;
    GameScreen(Game game){
        this.game = game;
    }

    public abstract void update (float delta);
    public abstract void draw (float delta);

    public abstract boolean isDone ();

    @Override
    public void render (float delta) {
        update(delta);
        draw(delta);
    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void show () {
    }

    @Override
    public void hide () {
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
    }

    public Game getGame() {
        return game;
    }
}

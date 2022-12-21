package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game;

public class LoadingScreen extends GameScreen{
    SpriteBatch batch;
    Texture img;

    ProgressBar p;

    public LoadingScreen(Game game) {
        super(game);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        img = new Texture("img/tempPlayscreen/tankStarLoading.jpg");
        Skin loadingBarSkin = new Skin(Gdx.files.internal("skins/gdx-skins/comic/skin/comic-ui.json"));
    }

    @Override

    public void update(float delta) {
        if(Gdx.input.justTouched()){
            getGame().setCurrScreen(new TankSelect(getGame()));
        }
    }

    @Override
    public void draw(float delta) {
        float delta2 = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(img, 0, 0, 1366, 720);
        stage.getBatch().end();
        stage.draw();

    }

    @Override
    public boolean isDone() {
        return false;
    }
}

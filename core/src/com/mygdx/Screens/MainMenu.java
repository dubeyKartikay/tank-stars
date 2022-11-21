package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game;

public class MainMenu extends GameScreen {
    SpriteBatch batch;
    Texture img;
    private Stage stage;
    private Table table;
    TextButton playButton;
    TextButton loadButton;
    TextButton exitButton;
    public MainMenu(Game game) {
        super(game);
        stage = new Stage(new ScreenViewport());

        table = new Table();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        Skin mySkin = new Skin(Gdx.files.internal("skins/placeholderUISkin/uiskin.json"));
        playButton = new TextButton("Play",mySkin);
        loadButton = new TextButton("Load",mySkin);
        exitButton = new TextButton("exit",mySkin);
        playButton.getLabel().setFontScale(3);
        loadButton.getLabel().setFontScale(3);
        exitButton.getLabel().setFontScale(3);
        table.setFillParent(true);
        table.setZIndex(0);
        stage.addActor(table);
        table.setDebug(true);
        table.right().bottom().padRight(10).padBottom(30);
        table.add(playButton).right().padRight(10).width(300).height(100);
        table.row().pad(10);
        table.add(loadButton).right().padRight(10).width(300).height(100);
        table.row();
        table.add(exitButton).right().padRight(10).width(300).height(100);
    }

    @Override
    public void update(float delta) {


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


    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        stage.dispose();
    }




}

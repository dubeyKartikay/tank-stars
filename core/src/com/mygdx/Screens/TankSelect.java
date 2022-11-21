package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Game;

public class TankSelect extends GameScreen{
    Stage stage;
    Table table;
    Texture bg;
    TankSelect(Game game){
        super(game);
        bg = new Texture(Gdx.files.internal("img/zoro.png"));
        stage = new Stage();
        table = new Table();
        stage.addActor(table);
    }

    @Override
    public void update(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        stage.getBatch().draw(bg, 0, 0, 1366, 720);
        stage.getBatch().end();

        stage.draw();
    }

    @Override
    public void draw(float delta) {

    }

    @Override
    public boolean isDone() {
        return false;
    }
}

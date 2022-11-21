package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Game;

public class PlayScreen extends GameScreen {

    Stage stage;
    Table table;

    Texture bg;
    PlayScreen(Game game) {
        super(game);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        bg = new Texture(Gdx.files.internal("img/tempPlayscreen/playScreen.jpg"));
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        stage.getBatch().draw(bg, 0, 0, 1366, 720);
        stage.getBatch().end();

        stage.draw();
    }

    @Override
    public boolean isDone() {
        return false;
    }
}

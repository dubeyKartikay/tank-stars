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

    }

    @Override
    public boolean isDone() {
        return false;
    }
}

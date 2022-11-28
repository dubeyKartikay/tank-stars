package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game;

public class ConfirmationSaveScreen extends GameScreen{
    TextButton yes;
    TextButton no;
    TextButton ask;
    SpriteBatch batch;
    Texture img;
    private Table table;
    private BitmapFont font;
    ConfirmationSaveScreen(Game game) {
        super(game);
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        font.getData().setScale(3);
        stage = new Stage(new ScreenViewport());
        table = new Table();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        final Skin mySkin = new Skin(Gdx.files.internal("skins/placeholderUISkin/uiskin.json"));
        yes=new TextButton("YES",mySkin);
        no = new TextButton("NO",mySkin);

        yes.getLabel().setFontScale(3);
        no.getLabel().setFontScale(3);
        yes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().setCurrScreen(new PauseMenu(getGame()));
            }
        });
        no.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().setCurrScreen(new SaveScreen(getGame()));
            }
        });
        table.setFillParent(true);
        table.setZIndex(0);
        stage.addActor(table);
        table.setDebug(true);
        table.add(yes).right().padRight(10).width(300).height(100);
        table.row().pad(10);
        table.add(no).right().padRight(10).width(300).height(100);

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
        batch.begin();
        font.draw(batch, "DO YOU WANT TO SAVE", 425, 550);
        batch.end();
    }

    @Override
    public boolean isDone() {
        return false;
    }
}

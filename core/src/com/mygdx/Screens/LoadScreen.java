package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game;

public class LoadScreen extends GameScreen{
    SpriteBatch batch;
    Texture img;
    private Table table;
    TextButton slot1;
    TextButton slot2;
    TextButton slot3;
    TextButton slot4;
    TextButton slot5;
    TextButton back;

    LoadScreen(Game game) {
        super(game);
        stage = new Stage(new ScreenViewport());
        table = new Table();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        final Skin mySkin = new Skin(Gdx.files.internal("skins/placeholderUISkin/uiskin.json"));
        slot1 = new TextButton("slot1",mySkin);
        slot2 = new TextButton("slot2",mySkin);
        slot3 = new TextButton("slot3",mySkin);
        slot4 = new TextButton("slot4",mySkin);
        slot5 = new TextButton("slot5",mySkin);

        back = new TextButton("back",mySkin);
        back.setPosition(12,662);
        back.setWidth(80);
        back.setHeight(40);
        back.getLabel().setFontScale(1.5f);
        stage.addActor(back);

        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().setCurrScreen(new MainMenu(getGame()));
            }
        });
        slot1.getLabel().setFontScale(3);
        slot2.getLabel().setFontScale(3);
        slot3.getLabel().setFontScale(3);
        slot4.getLabel().setFontScale(3);
        slot5.getLabel().setFontScale(3);

        table.setFillParent(true);
        table.setZIndex(0);
        stage.addActor(table);
        table.setDebug(true);
        table.add(slot1).right().padRight(10).width(300).height(100);
        table.row().pad(10);
        table.add(slot2).right().padRight(10).width(300).height(100);
        table.row().pad(10);
        table.add(slot3).right().padRight(10).width(300).height(100);
        table.row().pad(10);
        table.add(slot4).right().padRight(10).width(300).height(100);
        table.row().pad(10);
        table.add(slot5).right().padRight(10).width(300).height(100);
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
}

package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Game;

public class PlayScreen extends GameScreen {

    Stage stage;
    Table table;

    ProgressBar player1Health;
    ProgressBar player2Health;

    Button pauseButton;
    Texture bg;

    PlayScreen(final Game game) {
        super(game);
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);
        bg = new Texture(Gdx.files.internal("img/tempPlayscreen/playScreen.jpg"));

       Skin healthBarSkin = new Skin(Gdx.files.internal("skins/gdx-skins/comic/skin/comic-ui.json"));
       Skin pauseButtonSkin = new Skin(Gdx.files.internal("skins/gdx-skins/arcade/skin/arcade-ui.json"));

       player1Health = new ProgressBar(0,100,1,false,healthBarSkin);
       player2Health = new ProgressBar(0,100,1,false,healthBarSkin);

       pauseButton = new Button(pauseButtonSkin,"red");

        stage.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
               if (keycode == 111){
                   getGame().setCurrScreen(new PauseMenu(getGame()));
               }
               return false;
            }
        });
       pauseButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               getGame().setCurrScreen(new PauseMenu(getGame()));
           }
       });

       player1Health.setValue(50);
       player2Health.setValue(100);
       table.top().padTop(50);

       table.add(player1Health).width(500);
       table.add(pauseButton).padLeft(50).padRight(50);
       table.add(player2Health).width(500);
       stage.addActor(table);

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

package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game;

public class PauseMenu extends GameScreen{
    SpriteBatch batch;
    Texture img;
    private Stage stage;
    private Table table;
    TextButton resumeButton;
    TextButton musicButton;
    TextButton saveButton;
    TextButton restartButton;
    TextButton exitButton;
    public PauseMenu(Game game) {
        super(game);
        stage = new Stage(new ScreenViewport());
        table = new Table();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        final Skin mySkin = new Skin(Gdx.files.internal("skins/placeholderUISkin/uiskin.json"));
        restartButton = new TextButton("Restart",mySkin);


        final String[] musicStatus = new String[1];
        if(game.isMusicPlaying()){
            musicStatus[0] ="ON";
        }
        else{
            musicStatus[0] ="OFF";
        }

        resumeButton = new TextButton("Resume",mySkin);
        resumeButton.addListener( new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                getGame().setCurrScreen( new PlayScreen(getGame()) );
            }
        });
        stage.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                if (keycode == 111){
                    getGame().setCurrScreen(new PlayScreen(getGame()));
                }
                return false;
            }
        });

        musicButton=new TextButton("Music "+ musicStatus[0],mySkin);
        musicButton.addListener( new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                if(musicStatus[0].equals("ON")){
                    getGame().pauseMusic();
                    musicStatus[0] ="OFF";
                    musicButton.setText("Music "+ musicStatus[0]);
                }
                else{
                    getGame().playMusic();
                    musicStatus[0] ="ON";
                    musicButton.setText("Music "+ musicStatus[0]);
                }
            }
        });


        saveButton = new TextButton("Save",mySkin);
        saveButton.addListener( new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                getGame().setCurrScreen( new SaveScreen(getGame()) );
            }
        });


        exitButton=new TextButton("Exit",mySkin);
        exitButton.addListener( new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                getGame().setCurrScreen( new MainMenu(getGame()) );
            }
        });


        resumeButton.getLabel().setFontScale(3);
        restartButton.getLabel().setFontScale(3);
        saveButton.getLabel().setFontScale(3);
        exitButton.getLabel().setFontScale(3);
        musicButton.getLabel().setFontScale(3);
        table.setFillParent(true);
        table.setZIndex(0);
        stage.addActor(table);
        table.setDebug(true);
        table.add(resumeButton).right().padRight(10).width(300).height(100);
        table.row().pad(10);
        table.add(restartButton).right().padRight(10).width(300).height(100);
        table.row().pad(10);
        table.add(musicButton).right().padRight(10).width(300).height(100);
        table.row().pad(10);
        table.add(saveButton).right().padRight(10).width(300).height(100);
        table.row().pad(10);
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
}
